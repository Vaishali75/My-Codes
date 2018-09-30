package com.example.hp.quesec.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.quesec.Beans.RateBean;
import com.example.hp.quesec.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RateFragment extends Fragment {
    private RatingBar ratingBar,overall;
    private EditText txtComment;
    private Button btnRate;
    private TextView txtOverall;
    private float overallpoints;
    private int count;
    private RateBean rateBean;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RateFragment newInstance(String param1, String param2) {
        RateFragment fragment = new RateFragment();
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
        View view=inflater.inflate(R.layout.fragment_rate, container, false);
        ratingBar=(RatingBar) view.findViewById(R.id.ratingBar);
        overall=(RatingBar) view.findViewById(R.id.overall);
        txtOverall=(TextView)view.findViewById(R.id.txtoverall);
        btnRate=(Button) view.findViewById(R.id.btnRate);
        txtComment=(EditText) view.findViewById(R.id.txtComment);
        new LoadRatings().execute();

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getActivity(), "check", Toast.LENGTH_SHORT).show();
                if(ratingBar.getRating()>0 && txtComment.getText().length()>=0)
                {
                 //   Toast.makeText(getActivity(), "check", Toast.LENGTH_SHORT).show();

                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Ratings");
                     rateBean=new RateBean();
                    rateBean.setRating(ratingBar.getRating());
                    rateBean.setComment(txtComment.getText().toString());
                    rateBean.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    rateBean.setRateId(reference.push().getKey());
                    reference.child(rateBean.getRateId()).setValue(rateBean);
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("Quesec");
                    builder.setMessage("Thank you for your valuable rating..");
                    builder.setCancelable(true);
                    builder.show();
                    new LoadRatings().execute();
                }
            }
        });

        return view;
    }

    public void overall()
    {
        Log.d("Overall Points : ",overallpoints+"");
        Log.d("Overall Count : ",count+"");
        if(rateBean!=null)
        {
            ratingBar.setRating(rateBean.getRating());
            txtComment.setText(rateBean.getComment());
            btnRate.setEnabled(false);
        }

        if(overallpoints>0 && count>0)
        {
            float rating=overallpoints/count;
            overall.setRating(rating);
            txtOverall.setText("Overall Rating : "+rating);
        }
    }

    private class LoadRatings extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog progressDialog;
        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Ratings");
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    RateBean rate= dataSnapshot.getValue(RateBean.class);
                    Log.d("Rating : ",rate.getRating()+"");


                    overallpoints+=rate.getRating();
                    count++;


                    if(rate.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        rateBean = rate;
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
        protected void onPreExecute() {
            super.onPreExecute();
            overallpoints=0;
            count=0;
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setTitle("Disome");
            progressDialog.setMessage("Loading...");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            overall();
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
