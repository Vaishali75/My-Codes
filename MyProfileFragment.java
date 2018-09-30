package com.example.hp.quesec.Fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.quesec.Beans.QuestionBean;
import com.example.hp.quesec.Beans.UserBean;
import com.example.hp.quesec.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends Fragment {
    private EditText editstatus,editname;
    private String status,name,userid;
    private ImageView dp;
    private Button save;
    private SharedPreferences sharedPreferences;
    private UserBean userBean;
    private DatabaseReference databaseReference,databaseReference1;
    private Uri uri;
    private FirebaseStorage storage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
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
        View view=inflater.inflate(R.layout.fragment_my_profile, container, false);
        editname=view.findViewById(R.id.editname);
        editstatus=view.findViewById(R.id.editstatus);
        dp=view.findViewById(R.id.dp);
        save=view.findViewById(R.id.save);

        userBean=new UserBean();

        sharedPreferences = getActivity().getSharedPreferences("mydb",MODE_PRIVATE);
        userid=sharedPreferences.getString("keyUserId","");
        Toast.makeText(getActivity(),"id:"+userid,Toast.LENGTH_SHORT).show();


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
            }

            else
            {
                dp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "selecting", Toast.LENGTH_SHORT).show();

                        Intent it = new Intent();
                        it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                        it.setAction(Intent.ACTION_GET_CONTENT);
                        it.setType("img/*");
                        startActivityForResult(it,110);



                    }
                });
            }
        }

        else
        {
            Toast.makeText(getActivity(), "please check the permission first", Toast.LENGTH_SHORT).show();
        }



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editname.getText().toString();
                status=editstatus.getText().toString();
                Toast.makeText(getActivity(),"name"+name,Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),"status"+status,Toast.LENGTH_SHORT).show();
                 databaseReference= FirebaseDatabase.getInstance().getReference("Users");


                if (uri == null) {

                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
                            databaseReference.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                                {
                                    UserBean userBean=new UserBean();

                                    UserBean ub=dataSnapshot.getValue(UserBean.class);

                                    if(ub.getId().equals(userid))
                                    {

//
//
//                            Toast.makeText(getActivity(),"Saved",Toast.LENGTH_SHORT).show();

                                        userBean.setName(name);
                                        userBean.setEmail(ub.getEmail());
                                        userBean.setId(ub.getId());
                                        userBean.setPass(ub.getPass());
                                        userBean.setStatus(status);
                                        userBean.setUseruri("");
                                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                        reference.setValue(userBean);

                                        Toast.makeText(getActivity(),"name"+name,Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getActivity(),"status"+status,Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();


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


                        }
                    },1000);

                }

                else if (uri != null) {
                    storage = FirebaseStorage.getInstance();
                    final StorageReference sr = storage.getReference("SUserUploads" + System.currentTimeMillis() + "." + getFileExtension(uri));
                   // Toast.makeText(getContext(), "sr:" + sr, Toast.LENGTH_LONG).show();
                   // Toast.makeText(getContext(), "uri" + uri, Toast.LENGTH_LONG).show();

                    sr.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return sr.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                final Uri downloadUri = task.getResult();

                                databaseReference.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                                    {
                                        UserBean userBean=new UserBean();

                                        UserBean ub=dataSnapshot.getValue(UserBean.class);

                                        if(ub.getId().equals(userid))
                                        {


                                            userBean.setUseruri(downloadUri.toString());
                                            userBean.setName(name);
                                            userBean.setEmail(ub.getEmail());
                                            userBean.setId(ub.getId());
                                            userBean.setPass(ub.getPass());
                                            userBean.setStatus(status);

                                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                            reference.setValue(userBean);



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


                                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getActivity(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

//
            }

        });

        return view;
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==101)
        {
            if (permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getActivity(), "permission granted.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "your app will crash soon.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110) {
            if (resultCode == RESULT_OK && data != null) {
                uri = data.getData();
                dp.setImageURI(uri);
            }

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
