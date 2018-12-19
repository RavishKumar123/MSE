package com.example.ravish.testro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signupScreen(View view){
        Intent intent = new Intent(getBaseContext(),Signup.class);
        startActivity(intent);
    }

    public void dashoardScreen(View view){
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
    }
}
