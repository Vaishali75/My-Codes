package com.example.hp.quesec.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.quesec.Beans.UserBean;
import com.example.hp.quesec.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignUp extends AppCompatActivity {


    private EditText ename,eemail,epass,ecpass;
    private Button signupbtn;
    private String name,email,pass,cpass;
    private TextView logintxt;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);


        ename=findViewById(R.id.ename);
        eemail=findViewById(R.id.eemail);

        epass=findViewById(R.id.epass);
        ecpass=findViewById(R.id.ecpass);
        signupbtn=findViewById(R.id.signupbtn);

        logintxt=findViewById(R.id.logintxt);

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserSignUp.this,LogIn.class));
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (ename.getText().toString().isEmpty())
                {
                    ename.setError("Required to be Filled");
                }

                else if(eemail.getText().toString().isEmpty())
                {
                    eemail.setError("Required to be Filled");
                }
                else if(epass.getText().toString().isEmpty())
                {
                    epass.setError("Required to be Filled");
                }
                else if(ecpass.getText().toString().isEmpty())
                {
                    ecpass.setError("Required to be Filled");
                }
                else {
                    name = ename.getText().toString();
                    email = eemail.getText().toString();

                    pass = epass.getText().toString();
                    cpass = ecpass.getText().toString();

                    final UserBean ub = new UserBean();
                    ub.setName(name);

                    ub.setEmail(email);
                    ub.setPass(pass);
                    ub.setStatus("");
                    ub.setUseruri("");


                    if (!(cpass.equals(pass)))
                        Toast.makeText(UserSignUp.this, "Password and Confirm Password doesn't match", Toast.LENGTH_SHORT).show();


                    else {
                        auth = FirebaseAuth.getInstance();
                        auth.createUserWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference reference = database.getReference("Users");
                                        ub.setId(reference.push().getKey());
                                        reference.child(ub.getId()).setValue(ub);

                                        Toast.makeText(UserSignUp.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar snackbar = Snackbar.make(view, "Either you are already registered or wrong email", Snackbar.LENGTH_LONG);
                                        snackbar.show();

                                        ename.setText("");
                                        eemail.setText("");

                                        epass.setText("");
                                        ecpass.setText("");
                                    }
                                });
                    }
                }

            }
        });



    }
}
