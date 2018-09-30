package com.example.hp.quesec.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyQuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyQuestionFragment extends Fragment implements SingleQuestionFragment.OnFragmentInteractionListener {
    private ListView myquelist;
    private ArrayList<QuestionBean> list;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String id,categoryId,categoryname;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyQuestionFragment newInstance(String param1, String param2) {
        MyQuestionFragment fragment = new MyQuestionFragment();
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
        View view=inflater.inflate(R.layout.fragment_my_question, container, false);
        myquelist=view.findViewById(R.id.myquelist);
        sharedPreferences = getActivity().getSharedPreferences("mydb",MODE_PRIVATE);
        categoryId=sharedPreferences.getString("keycategoryIdasp","");
        categoryname=sharedPreferences.getString("keycategoryNameasp","");

        getActivity().setTitle("My Questions");
        id=sharedPreferences.getString("keyUserId","");
        new loadMyQuestions().execute();

        myquelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
                TextView key=(TextView) view.findViewById(R.id.questionID);
                TextView title=(TextView)view.findViewById(R.id.questionTitle);
                SingleQuestionFragment singleQuestionFragment=new SingleQuestionFragment();
                Bundle b=new Bundle();
                b.putString("key",key.getText().toString());
                b.putString("title",title.getText().toString());
                singleQuestionFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.homeframe,singleQuestionFragment).addToBackStack(null).commit();
            }
        });

//        allquesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            // Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
//                TextView key = (TextView) view.findViewById(R.id.questionID);
//                TextView title = (TextView) view.findViewById(R.id.questionTitle);
//                SingleQuestionFragment singleQuestionFragment = new SingleQuestionFragment();
//                Bundle b = new Bundle();
//                b.putString("key", key.getText().toString());
//                b.putString("title", title.getText().toString());
//                singleQuestionFragment.setArguments(b);
//                getFragmentManager().beginTransaction().replace(R.id.homeframe, singleQuestionFragment).addToBackStack(null).commit();
//            }
//        });

        return view;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class loadMyQuestions extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Questions");
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    QuestionBean q = dataSnapshot.getValue(QuestionBean.class);
        if(id.equals(q.getUserId()) && categoryId.equals(q.getCategoryId())) {
            // Log.d("Data",q.getTitle());
            list.add(q);
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            myquelist.setAdapter(new QuestionAdapter(getActivity(),list));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list = new ArrayList<>();
        }
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
