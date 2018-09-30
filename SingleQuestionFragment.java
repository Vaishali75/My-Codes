package com.example.hp.quesec.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.quesec.Adapter.AnswerAdapter;
import com.example.hp.quesec.Adapter.QuestionAdapter;
import com.example.hp.quesec.Beans.AnswerBean;
import com.example.hp.quesec.Beans.QuestionBean;
import com.example.hp.quesec.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingleQuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingleQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleQuestionFragment extends Fragment {
    private ArrayList<AnswerBean> list;
    private TextView singleQuestionID,singleQuestion;
    private EditText userAnswer;
    private Button btnReply;
    private AnswerBean answer;
    private DatabaseReference reference;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int count=0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SingleQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingleQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleQuestionFragment newInstance(String param1, String param2) {
        SingleQuestionFragment fragment = new SingleQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_single_question, container, false);

        singleQuestionID= view.findViewById(R.id.singleQuestionID);
        singleQuestion=  view.findViewById(R.id.singleQuestion);
        userAnswer=view.findViewById(R.id.userAnswer);
        btnReply=view.findViewById(R.id.btnReply);
        sharedPreferences = getActivity().getSharedPreferences("mydb", MODE_PRIVATE);
        final String currentUserName = sharedPreferences.getString("keyUserName","");
        list=new ArrayList<>();
        Bundle b= getArguments();
        singleQuestionID.setText(b.getString("key"));
        singleQuestion.setText(b.getString("title"));
        getActivity().setTitle("Reply Query");
        ListView listView=(ListView)view.findViewById(R.id.answerList);
        listView.setAdapter(new AnswerAdapter(getActivity(),list));
        reference= FirebaseDatabase.getInstance().getReference("Answers");
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Answers");
                if (userAnswer.getText().toString().isEmpty()) {
                    userAnswer.setError("Required to be filled");

                }
                else {
                    final AnswerBean answer = new AnswerBean();
                    answer.setAnswerID(reference.push().getKey());
                    answer.setQuestionID(singleQuestionID.getText().toString());
                    answer.setAnswer(userAnswer.getText().toString());
                    answer.setPostedBy(currentUserName);
                    answer.setPostDate(new Date().getTime());

                    reference.child(answer.getAnswerID()).setValue(answer);
                    Toast.makeText(getActivity(), "Answer Posted", Toast.LENGTH_LONG).show();
                    userAnswer.setText("");

                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Questions");
                    reference1.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            QuestionBean questionBean=dataSnapshot.getValue(QuestionBean.class);
                            if(questionBean.getQueId().equals(answer.getQuestionID()))
                            {

                                int i = questionBean.getAnscount();

                                QuestionBean qb = new QuestionBean();
                                qb.setAnscount(i+1);
                                qb.setUri(questionBean.getUri());
                                qb.setUsername(questionBean.getUsername());
                                qb.setPostdate(questionBean.getPostdate());
                                qb.setCategoryname(questionBean.getCategoryname());
                                qb.setTitle(questionBean.getTitle());
                                qb.setQueId(questionBean.getQueId());
                                qb.setCategoryId(questionBean.getCategoryId());
                                qb.setDescription(questionBean.getDescription());
                                qb.setUserId(questionBean.getUserId());



                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Questions");
                                ref1.child(qb.getQueId()).setValue(qb);

                            }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



//                Bundle b=new Bundle();
//                b.putString("keyunans",answer.getAnswerID());
//                UnansweredFragment unansweredFragment=new UnansweredFragment();
//                unansweredFragment.setArguments(b);b
                }
            }


        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AnswerBean answer=dataSnapshot.getValue(AnswerBean.class);
        if(singleQuestionID.getText().toString().equals(answer.getQuestionID()))
        {
            list.add(answer);
            count++;
        }



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
      // int countint=listView.getAdapter().getCount();
       String countstr=String.valueOf(count);
     //  Bundle bundle=new Bundle();
     //  b.putString("countansKey", String.valueOf(count));

       //void calldatacount(String count);
        editor=getActivity().getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycount",countstr);
        editor.commit();


        //QuestionAdapter questionAdapter=new QuestionAdapter(Context context,ArrayList< QuestionBean> list);



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
