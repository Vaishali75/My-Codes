package com.example.hp.quesec.Fragments;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.quesec.Activities.LogIn;
import com.example.hp.quesec.Adapter.QuestionAdapter;
import com.example.hp.quesec.Adapter.UriAdapter;
import com.example.hp.quesec.Beans.QuestionBean;
import com.example.hp.quesec.Beans.UploadInQuesBean;
import com.example.hp.quesec.Beans.UriBean;
import com.example.hp.quesec.Beans.UserBean;
import com.example.hp.quesec.Beans.imgclass;
import com.example.hp.quesec.MainActivity;
import com.example.hp.quesec.R;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AskQuestionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AskQuestionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AskQuestionsFragment extends Fragment {
    private GridView imgList;
    private ArrayList<UriBean> list;
    private Button btnPost;
    private ImageButton imgattach;
    private EditText txtTitle,txtDiscription;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LinearLayout layout;

    private int f;

    private Uri uri;
    private FirebaseDatabase firebaseDatabase;
    private QuestionBean questionBean;
    private UploadInQuesBean uploadInQuesBean;
    //private Bundle b;
    private String categoryname,categoryId,currentUserID;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AskQuestionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AskQuestionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AskQuestionsFragment newInstance(String param1, String param2) {
        AskQuestionsFragment fragment = new AskQuestionsFragment();
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
        View view =inflater.inflate(R.layout.fragment_ask_questions, container, false);
        txtTitle=view.findViewById(R.id.txtpost);
        txtDiscription=view.findViewById(R.id.txtDescription);
        btnPost=view.findViewById(R.id.btnpost);
        imgattach=view.findViewById(R.id.imgattach);
       // imgList=view.findViewById(R.id.imglist);
        layout=view.findViewById(R.id.imagelayout);

//        b=getArguments();
//       categoryId=b.getString("keycategoryId");
//       categoryname=b.getString("keycategoryName");

        sharedPreferences=getActivity().getSharedPreferences("mydb",MODE_PRIVATE);
        categoryId=sharedPreferences.getString("keycategoryIdasp","");
        categoryname=sharedPreferences.getString("keycategoryNameasp","");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
            }

            else
            {
                imgattach.setOnClickListener(new View.OnClickListener() {
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






        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTitle.getText().toString().isEmpty())
                {
                    txtTitle.setError("Required to be filled");
                }

                else if (txtDiscription.getText().toString().isEmpty())
                {
                    txtDiscription.setError("Required to be Filled");
                }
                else
                    {
                    questionBean = new QuestionBean();

                    questionBean.setTitle(txtTitle.getText().toString());
                    questionBean.setDescription(txtDiscription.getText().toString());
                    questionBean.setPostdate(new Date());
                    questionBean.setCategoryId(categoryId);
                    questionBean.setCategoryname(categoryname);
                    //                UserBean userBean= new UserBean();
                    sharedPreferences = getActivity().getSharedPreferences("mydb", MODE_PRIVATE);
                    currentUserID = sharedPreferences.getString("keyUserId", "");
                    // String currentUserName = sharedPreferences.getString("keyUserName","");

                    //Toast.makeText(getActivity(), "id : " + currentUserID, Toast.LENGTH_LONG).show();
                    //  Toast.makeText(getActivity(), "name : "+currentUserName, Toast.LENGTH_LONG).show();
                    questionBean.setUserId(currentUserID);
                   questionBean.setAnscount(0);
                    questionBean.setPostdate(new Date());

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference1 = firebaseDatabase.getReference("Users");

                    databaseReference1.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            UserBean userBean = dataSnapshot.getValue(UserBean.class);
                            questionBean.setUserId(currentUserID);
                            //questionBean.setUserId(auth.getInstance().getCurrentUser().getUid());
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

                 //   DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference1.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            //QuestionBean q=dataSnapshot.getValue(QuestionBean.class);
                            UserBean ub = dataSnapshot.getValue(UserBean.class);

                            if (currentUserID.equals(ub.getId())) {
                                questionBean.setUsername(ub.getName());
                               // Toast.makeText(getActivity(), "name" + ub.getName(), Toast.LENGTH_SHORT).show();
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



                    if (uri == null) {

                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run()
                            {
                                DatabaseReference databaseReference = firebaseDatabase.getReference("Questions");
                                questionBean.setQueId(databaseReference.push().getKey());

                                databaseReference.child(questionBean.getQueId()).setValue(questionBean);
                                txtTitle.setText("");
                                txtDiscription.setText("");

                                Toast.makeText(getActivity(), "Question Posted", Toast.LENGTH_LONG).show();

                            }
                        },1000);

                    }


                    else if (uri != null) {
                        storage = FirebaseStorage.getInstance();
                        final StorageReference sr = storage.getReference("SQuestionUploads" + System.currentTimeMillis() + "." + getFileExtension(uri));
                       // Toast.makeText(getContext(), "sr:" + sr, Toast.LENGTH_LONG).show();
                        //Toast.makeText(getContext(), "uri" + uri, Toast.LENGTH_LONG).show();

                        f=0;
                        for(String uri1 :imgclass.list)

                        {


                            sr.putFile(Uri.parse(uri1)).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                                        Uri downloadUri = task.getResult();

                                        imgclass.liststore.add(downloadUri.toString());
                                        Toast.makeText(getActivity(), "storage uploaded", Toast.LENGTH_SHORT).show();

                                        f++;

                                        if (f == imgclass.list.size()) {

                                            DatabaseReference dataref1 = firebaseDatabase.getReference("Questions");
                                            questionBean.setQueId(dataref1.push().getKey());
                                            questionBean.setUri(imgclass.liststore);
                                            dataref1.child(questionBean.getQueId()).setValue(questionBean);
                                            txtTitle.setText("");
                                            txtDiscription.setText("");


                                            txtTitle.setText("");
                                            txtDiscription.setText("");


                                            Toast.makeText(getActivity(), "Question Posted", Toast.LENGTH_LONG).show();

                                        }

                                    } else {
                                        Toast.makeText(getActivity(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                    }


                }

            }
        });


              getActivity().setTitle("Post Query");

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        if (requestCode == 110)
        {
            if (resultCode == RESULT_OK)
            {
             // ArrayList<Uri> list = new ArrayList<>();


                if (data !=null)
                {
                    uri = data.getData();

                    ImageView image=new ImageView(getContext());

                    image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
                    image.setMaxHeight(20);
                    image.setMaxWidth(20);
                    Glide.with(getActivity()).load(uri).into(image);
                    layout.addView(image);




                   imgclass.list.add(uri.toString());

                    //
                    // imgList.setAdapter(new UriAdapter(getActivity(),list));
                }
                else {
                if (data.getClipData() != null) {

                    Toast.makeText(getContext(), "multiple pics selected...", Toast.LENGTH_SHORT).show();
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);


                        ImageView image=new ImageView(getContext());

                        image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
                        image.setMaxHeight(20);
                        image.setMaxWidth(20);
                        Glide.with(getActivity()).load(uri).into(image);
                        layout.addView(image);



                        imgclass.list.add(uri.toString());

                    }
                    //  Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                }

                   // imgList.setAdapter(new UriAdapter(getContext(),list));
            }

            }

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
