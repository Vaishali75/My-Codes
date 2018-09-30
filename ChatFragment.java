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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.quesec.Adapter.ChatAdapter;
import com.example.hp.quesec.Adapter.QuestionAdapter;
import com.example.hp.quesec.Beans.ChatBean;
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
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    private TextView chatname;
    private ImageView chatimgperson,sendtxt;
    private EditText msgtxt;
    private ChatBean chatBean;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String msg,receiverid,senderid,sendername;
    private ListView chatlistview;
    private ArrayList<ChatBean> list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        View view=inflater.inflate(R.layout.fragment_chat, container, false);
        chatimgperson=view.findViewById(R.id.chatimgperson);
        chatname=view.findViewById(R.id.chatname);
        msgtxt=view.findViewById(R.id.msgtxt);
        sendtxt=view.findViewById(R.id.sendtxt);
        chatlistview=view.findViewById(R.id.chatlistview);
        Bundle b=getArguments();
        receiverid=b.getString("keyreceiverid");
        chatname.setText(b.getString("keyreceivername"));
        chatBean=new ChatBean();
        list = new ArrayList<>();
        sharedPreferences= getActivity().getSharedPreferences("mydb",MODE_PRIVATE);
        senderid=sharedPreferences.getString("keyUserId","");
        sendername=sharedPreferences.getString("keyUserName","");


        sendtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg =msgtxt.getText().toString();
                if(msg.isEmpty())
                    msgtxt.setError("Message required");
                else
                {
                    chatBean.setReceiverId(receiverid);
                    chatBean.setSenderId(senderid);
                    chatBean.setChatmsg(msg);

                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Chat");
                    chatBean.setChatId(reference.push().getKey());
                    reference.child(chatBean.getChatId()).setValue(chatBean);
                    msgtxt.setText("");


                    Toast.makeText(getActivity(), "msg sent", Toast.LENGTH_LONG).show();



                }

            }

        });

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Chat");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                chatBean=dataSnapshot.getValue(ChatBean.class);
                if(((chatBean.getReceiverId()).equals(receiverid) && (chatBean.getSenderId()).equals(senderid))
                        || (chatBean.getReceiverId().equals(senderid) && (chatBean.getSenderId().equals(receiverid))))
                {
                    if (list.add(chatBean))
                        chatlistview.setAdapter(new ChatAdapter(getActivity(),list));
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
