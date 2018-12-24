package com.example.ravish.testro;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class login extends AppCompatActivity {
   private FirebaseAuth mAuth;
   EditText email,pass;
   Button login;
    private ProgressBar loadingProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.progressBarLogin);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                login.setVisibility(View.INVISIBLE);
                if(email.getText().toString().equals("") || pass.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    View views = LayoutInflater.from(login.this).inflate(R.layout.resturant_list_view,null);
                    TextView title = views.findViewById(R.id.lName);
                    TextView invisible = views.findViewById(R.id.timming);
//                    android.support.v7.widget.CardView cart = view.findViewById(R.id.bankcardId);
//                    cart.setMinimumHeight(500);
                    invisible.setTextColor(Color.TRANSPARENT);
                    ImageView image = views.findViewById(R.id.profile_image);
                    image.setImageDrawable(getResources().getDrawable(R.drawable.fail));
                    title.setText("Please enter Email and Password");
                    title.setTextSize(20);
                    title.setTextColor(Color.BLACK);
                    builder.setView(views);
                    builder.create();
                    builder.show();
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                }else{
                    loginUser(email.getText().toString(),pass.getText().toString());
                }
            }
        });
    }

    public void signupScreen(View view){
        Intent intent = new Intent(getBaseContext(),Signup.class);
        startActivity(intent);
    }

    public void dashoardScreen(View view){
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
    }
    public void loginUser(final String email, String password){
              mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          Intent intent = new Intent(login.this,MainActivity.class);
                          startActivity(intent);
                      }else{
                          Log.w(email,"createUserWithEmail:failure",task.getException());
                          AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                          builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                                  // User cancelled the dialog
                              }
                          });
                          View view = LayoutInflater.from(login.this).inflate(R.layout.alert_box,null);
                          TextView title = view.findViewById(R.id.lName);
                          TextView invisible = view.findViewById(R.id.lNames);
                          android.support.v7.widget.CardView cart = view.findViewById(R.id.bankcardId);
                          invisible.setTextColor(Color.TRANSPARENT);
                          ImageView image = view.findViewById(R.id.profile_image);
                          image.setImageDrawable(getResources().getDrawable(R.drawable.fail));
                          title.setText(task.getException().getMessage().toString());
                          title.setTextSize(20);
                          title.setTextColor(Color.BLACK);
                          builder.setView(view);
                          builder.create();
                          builder.show();
                          loadingProgressBar.setVisibility(View.INVISIBLE);
                          login.setVisibility(View.VISIBLE);
                      }
                  }
              });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
        }
    }
}
