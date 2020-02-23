package com.example.sharenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText email,password;
    Button register;
    ProgressDialog progressDialog;
    TextView alreadyacc;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //action bar

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Create acc");
        //enable back

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        alreadyacc= findViewById(R.id.alreadyhaveaccount);

        register=findViewById(R.id.regiesteruser);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Plz Wait");

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailid= email.getText().toString().trim();
                String pass= password.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(emailid).matches())
                {
                    //set error to email edittext
                    email.setError("Invalid Email");
                    email.setFocusable(true);



                }
                else if(password.length()<6) {

                    //set error to email edittext
                    password.setError("Password length at least 6 char");
                    password.setFocusable(true);

                }
                else
                {
                    registerUser(emailid,pass);
                }

//                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
//                startActivity(intent);

            }
        });

        alreadyacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });


    }

    private void registerUser(String emailid, String pass)
    {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(emailid, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
//                            updateUI(currentUser);
                            Toast.makeText(RegisterActivity.this, "Registered...\n"+currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,ProfileActivity.class));
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();


    }
    private void updateUI(FirebaseUser user) {
        if (user != null){

        }
    }



}
