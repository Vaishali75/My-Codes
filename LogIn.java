package com.example.hp.quesec.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.quesec.Beans.UserBean;
import com.example.hp.quesec.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogIn extends AppCompatActivity {

    private FirebaseAuth auth;
    private String emaillog,passlog,semail,spass,currentUserId,currentUserName;
    private EditText eemaillog,epasslog;
    private Button logbtn;
    private SharedPreferences.Editor editor;
    private SharedPreferences  sp;
    private UserBean userBean;
    private TextView signuptxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        eemaillog=findViewById(R.id.eemaillog);
        epasslog=findViewById(R.id.epasslog);
        logbtn=findViewById(R.id.logbtn);
        userBean=new UserBean();
      signuptxt=findViewById(R.id.signuptxt);


      signuptxt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(LogIn.this,UserSignUp.class));
          }
      });

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                emaillog=eemaillog.getText().toString();
                passlog=epasslog.getText().toString();

                auth=FirebaseAuth.getInstance();


                auth.signInWithEmailAndPassword(emaillog,passlog)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
                               // editor.putString("keyId",auth.getInstance().getCurrentUser().getUid());
                                editor.putString("keyemail",emaillog);
                                editor.putString("keypass",passlog);



                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                DatabaseReference reference=database.getReference("Users");

                                reference.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        UserBean ub = dataSnapshot.getValue(UserBean.class);
                                        if(ub.getEmail().equals(emaillog) && ub.getPass().equals(passlog))
                                        {
//
                                            currentUserId=ub.getId();
                                            currentUserName=ub.getName();
                                            editor.putString("keyUserId",currentUserId);
                                            editor.putString("keyUserName",currentUserName);
                                            editor.commit();
                                            startActivity(new Intent(LogIn.this,Categories.class));


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

                                editor.commit();

                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LogIn.this, "Invalid user,pls Sign up", Toast.LENGTH_SHORT).show();

                            }
                        });



            }
        });


    }
}
