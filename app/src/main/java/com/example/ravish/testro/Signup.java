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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email;
    EditText username;
    EditText password;
    Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        create = findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("") || password.getText().toString().equals("") || username.getText().toString()
                        .equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    View views = LayoutInflater.from(Signup.this).inflate(R.layout.resturant_list_view,null);
                    TextView title = views.findViewById(R.id.lName);
                    TextView invisible = views.findViewById(R.id.lNames);
//                    android.support.v7.widget.CardView cart = view.findViewById(R.id.bankcardId);
//
//                    cart.setLayoutParams(new android.support.v7.widget.CardView.LayoutParams(
//                            android.support.v7.widget.CardView.LayoutParams.WRAP_CONTENT, 10));
//
//                    cart.setMinimumHeight(10);

                    invisible.setTextColor(Color.TRANSPARENT);
                    ImageView image = views.findViewById(R.id.profile_image);
                    image.setImageDrawable(getResources().getDrawable(R.drawable.fail));
                    title.setText("All fields are required");
                    title.setTextSize(20);
                    title.setTextColor(Color.BLACK);
                    builder.setView(views);
                    builder.create();
                    builder.show();
                }else{
                    createAccount(email.getText().toString(),password.getText().toString(),username.getText().toString());
                }

            }
        });
    }


    public void createAccount(final String email, String password, final String username){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(email,"createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user!=null) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username).build();
                        user.updateProfile(profileUpdates);
                    }
                    Intent intent = new Intent(Signup.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Log.w(email,"createUserWithEmail:failure",task.getException());
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    View view = LayoutInflater.from(Signup.this).inflate(R.layout.alert_box,null);
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
    public void loginScreens(View view){
        Intent intent = new Intent(getBaseContext(),login.class);
        startActivity(intent);
    }


}
