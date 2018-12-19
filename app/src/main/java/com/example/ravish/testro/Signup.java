package com.example.ravish.testro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    public void loginScreen(View view){
        Intent intent = new Intent(getBaseContext(),login.class);
        startActivity(intent);
    }
}
