package com.example.hp.quesec.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.quesec.Adapter.QuestionAdapter;
import com.example.hp.quesec.Beans.AnswerBean;
import com.example.hp.quesec.Beans.QuestionBean;
import com.example.hp.quesec.Beans.UserBean;
import com.example.hp.quesec.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UnansweredFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UnansweredFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnansweredFragment extends Fragment implements SingleQuestionFragment.OnFragmentInteractionListener  {
    private ListView unansweredlist;
    private ArrayList<QuestionBean> list;

    private String unanskey,categoryId,categoryname;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UnansweredFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnansweredFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UnansweredFragment newInstance(String param1, String param2) {
        UnansweredFragment fragment = new UnansweredFragment();
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

        View view=inflater.inflate(R.layout.fragment_unanswered, container, false);
        unansweredlist=(ListView) view.findViewById(R.id.unansweredlist);
        getActivity().setTitle("Unanswered Questions");
        sharedPreferences=getActivity().getSharedPreferences("mydb",MODE_PRIVATE);
        categoryId=sharedPreferences.getString("keycategoryIdasp","");
        categoryname=sharedPreferences.getString("keycategoryNameasp","");

        list = new ArrayList<>();
        DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("Questions");
        reference1.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
//                    Toast.makeText(getActivity(), "check2", Toast.LENGTH_SHORT).show();
                QuestionBean q = dataSnapshot.getValue(QuestionBean.class);
                //UserBean ub=dataSnapshot.getValue(UserBean.class);
                AnswerBean ab=dataSnapshot.getValue(AnswerBean.class);
                String que=ab.getQuestionID();
                Toast.makeText(getActivity(), "ans"+que, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "que"+q.getQueId(), Toast.LENGTH_SHORT).show();
                if(categoryId.equals(q.getCategoryId()))
                {
                    Toast.makeText(getActivity(), "check2", Toast.LENGTH_SHORT).show();
//                        if (unanskey.isEmpty()) {
//                            Log.d("Data", q.getTitle());
//                            list.add(q);
//                        Log.d("Data", q.getTitle());
//                                              }
                    if(list.add(q));
                    unansweredlist.setAdapter(new QuestionAdapter(getActivity(),list));
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



//        unansweredlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
//                TextView key=(TextView) view.findViewById(R.id.questionID);
//                TextView title=(TextView)view.findViewById(R.id.questionTitle);
//                SingleQuestionFragment singleQuestionFragment=new SingleQuestionFragment();
//                Bundle b=new Bundle();
//                b.putString("key",key.getText().toString());
//                b.putString("title",title.getText().toString());
//                singleQuestionFragment.setArguments(b);
//                getFragmentManager().beginTransaction().replace(R.id.frame,singleQuestionFragment).commit();
//            }
//        });

        unansweredlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
                TextView key = (TextView) view.findViewById(R.id.questionID);
                TextView title = (TextView) view.findViewById(R.id.questionTitle);
                SingleQuestionFragment singleQuestionFragment = new SingleQuestionFragment();
                Bundle b = new Bundle();
                b.putString("key", key.getText().toString());
                b.putString("title", title.getText().toString());
                singleQuestionFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.homeframe, singleQuestionFragment).addToBackStack(null).commit();
            }
        });
//unanskey= getArguments().getString("keyunans");


        return view;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
