package com.example.sharenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sharenotes.Model.Users;
import com.example.sharenotes.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class AdminLogin extends AppCompatActivity {

    EditText number,password;
    Button login;
    ProgressDialog progressDialog;

    private CheckBox chkBowRememberme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Login Acc");
        //enable back

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        number = (EditText) findViewById(R.id.number);
        password = (EditText) findViewById(R.id.password);

        login=findViewById(R.id.loginuser);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Plz Wait");

        chkBowRememberme = (CheckBox) findViewById(R.id.checkbox);
        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Number= number.getText().toString().trim();
                String pass= password.getText().toString().trim();

                if (TextUtils.isEmpty(Number))
                {
                    Toast.makeText(AdminLogin.this, "Fill all detail", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(pass))
                {
                    Toast.makeText(AdminLogin.this, "Fill all detail", Toast.LENGTH_SHORT).show();
                }

                else if (Number.length()>9)
                {
                    //set error to email edittext
                    loginuser(Number,pass);

                }
                else
                {
                    number.setError("Invalid Number");
                    number.setFocusable(true);
                }

//                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
//                startActivity(intent);

            }
        });

    }



    private void loginuser(final String number, final String pass)
    {
        progressDialog.show();

        if (chkBowRememberme.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey, number);
            Paper.book().write(Prevalent.UserPasswordKey, pass);

        }

        if (number.equals("7303906045") && pass.equals("password"))
        {
            Toast.makeText(this, "Admin Login", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

            Intent intent = new Intent (  AdminLogin.this, SuperAdmin.class);
            startActivity(intent);
            //Intent
        }
        else
        {
            Toast.makeText(this, "Admin login in Process login", Toast.LENGTH_SHORT).show();

            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference().child("Admin");

            Toast.makeText(AdminLogin.this, "Admin child found", Toast.LENGTH_SHORT).show();

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.child(number).exists())
                    {
                        Toast.makeText(AdminLogin.this, "Number exsists", Toast.LENGTH_SHORT).show();


                       String fpass= dataSnapshot.child(number).child("Password").getValue().toString();

                        Toast.makeText(AdminLogin.this, "Password Fetch", Toast.LENGTH_SHORT).show();


                            if (fpass.equals(pass))
                            {
                                Toast.makeText(AdminLogin.this, "Loggin Done", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent (  AdminLogin.this, SuperAdmin.class);
                                    startActivity(intent);
                                progressDialog.dismiss();

                            }
                            else
                            {
                                Toast.makeText(AdminLogin.this, "Password is incorect ", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                    }
                    else
                    {
                        Toast.makeText(AdminLogin.this, "Create Account With This" + number + "Number", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminLogin.this, "Database eror", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();


    }
}
