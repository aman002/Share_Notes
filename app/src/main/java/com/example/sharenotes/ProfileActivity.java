package com.example.sharenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    TextView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        profile =findViewById(R.id.profile);



        //action bar

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Create acc");
        //enable back


        firebaseAuth=FirebaseAuth.getInstance();


    }

    private void checkuserstatus(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
        {
            profile.setText(firebaseUser.getEmail());

        }
        else {
            Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        checkuserstatus();
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id= item.getItemId();
        if (id==R.id.action_logout)
        {
            firebaseAuth.signOut();
            checkuserstatus();


        }

        return super.onOptionsItemSelected(item);
    }
}
