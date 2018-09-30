package com.example.hp.quesec.Fragments;

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
import com.example.hp.quesec.Beans.QuestionBean;
import com.example.hp.quesec.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllQuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllQuestionFragment extends Fragment implements SingleQuestionFragment.OnFragmentInteractionListener {
    private ListView allquesList;
    private ArrayList<QuestionBean> list;
    private SharedPreferences sharedPreferences;
    private String categoryId,categoryname;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AllQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllQuestionFragment newInstance(String param1, String param2) {
        AllQuestionFragment fragment = new AllQuestionFragment();
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
        View view=inflater.inflate(R.layout.fragment_all_question, container, false);
        allquesList=view.findViewById(R.id.allquesList);
        sharedPreferences=getActivity().getSharedPreferences("mydb",MODE_PRIVATE);
        categoryId=sharedPreferences.getString("keycategoryIdasp","");
        categoryname=sharedPreferences.getString("keycategoryNameasp","");
       // Toast.makeText(getActivity(), "categoryid:"+categoryId, Toast.LENGTH_SHORT).show();
        getActivity().setTitle("Quesec");
        list = new ArrayList<>();

       // Toast.makeText(getActivity(),"not clicked",Toast.LENGTH_LONG).show();
        allquesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      // Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
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
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Questions");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                QuestionBean q = dataSnapshot.getValue(QuestionBean.class);

                if (q!=null) {
                    //Toast.makeText(getContext(), "ques : "+q.getTitle(), Toast.LENGTH_SHORT).show();
                    if (categoryId.equals(q.getCategoryId())) {
                        if (list.add(q))
                            allquesList.setAdapter(new QuestionAdapter(getActivity(), list));
                    }
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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

    private class LoadQuestions extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Questions");
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    QuestionBean q = dataSnapshot.getValue(QuestionBean.class);
                   // Log.d("Data",q.getTitle());
                    if(categoryId.equals(q.getCategoryId()))
                    list.add(q);
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            allquesList.setAdapter(new QuestionAdapter(getActivity(),list));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list = new ArrayList<>();
        }
    }
}
