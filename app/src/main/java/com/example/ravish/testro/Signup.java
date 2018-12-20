package com.example.ravish.testro;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar loadingProgressBar;
    EditText email;
    EditText username;
    EditText password;
    Button create;
    //Images upload
    de.hdodenhof.circleimageview.CircleImageView ImgUserPhoto;
    static int PReqCode = 1 ;
    static int REQUESCODE = 1 ;
    Uri pickedImgUri ;

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
        loadingProgressBar = findViewById(R.id.progressBar);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                create.setVisibility(View.INVISIBLE);
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
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    create.setVisibility(View.VISIBLE);
                }else{
                    createAccount(email.getText().toString(),password.getText().toString(),username.getText().toString());
                }

            }
        });

        ImgUserPhoto = findViewById(R.id.regUserPhoto);
        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 22) {

                    checkAndRequestForPermission();


                }
                else
                {
                    openGallery();
                }
            }
        });
    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(Signup.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Signup.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(Signup.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(Signup.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            openGallery();

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }


    public void createAccount(final String email, String password, final String username){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(email,"createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUserInfo(username,pickedImgUri,user);


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
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    create.setVisibility(View.VISIBLE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {
            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            ImgUserPhoto.setImageURI(pickedImgUri);
        }
    }

    // update user photo and name
    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {

        // first we need to upload user photo to firebase storage and get url

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // image uploaded succesfully
                // now we can get our image url

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        // uri contain user image url


                        UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();


                        currentUser.updateProfile(profleUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            // user info updated successfully
                                            Intent intent = new Intent(Signup.this,MainActivity.class);
                                            startActivity(intent);

                                        }

                                    }
                                });

                    }
                });





            }
        });






    }
}
