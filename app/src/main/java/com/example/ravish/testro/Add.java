package com.example.ravish.testro;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add extends Fragment {
    private Spinner startTime,endTime,startTimeExt,endTimeExt;
    Button addResturantButton;
    Boolean buttonClicked = false;
    TextView resturantName,resturantAddress,resturantPhone;
    FirebaseFirestore db;
    private ProgressBar loadingProgressBar;
    de.hdodenhof.circleimageview.CircleImageView ImgUserPhoto;
    static int PReqCode = 1 ;
    static int REQUESCODE = 1 ;
    Uri pickedImgUri ;
    public Add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View addResturantView = inflater.inflate(R.layout.fragment_add, container, false);
        resturantName = addResturantView.findViewById(R.id.addName);
        resturantAddress = addResturantView.findViewById(R.id.addAddress);
        resturantPhone = addResturantView.findViewById(R.id.addPhone);
        startTime = addResturantView.findViewById(R.id.startTime);
        loadingProgressBar = addResturantView.findViewById(R.id.progressBar);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        endTime = addResturantView.findViewById(R.id.endTime);
        endTimeExt = addResturantView.findViewById(R.id.endtimeExt);
        startTimeExt = addResturantView.findViewById(R.id.startTimeExt);
        db = FirebaseFirestore.getInstance();
        addResturantButton = addResturantView.findViewById(R.id.AddResturant);
        addResturantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                addResturantButton.setVisibility(View.INVISIBLE);
                String time = startTime.getSelectedItem().toString() + " " + startTimeExt.getSelectedItem().toString() + " To "
                        + endTime.getSelectedItem().toString() + " " + endTimeExt.getSelectedItem().toString();
                if(resturantName.getText().toString().equals("") || resturantAddress.getText().toString().equals("") || resturantPhone.getText().toString()
                        .equals("") || time.equals("") || buttonClicked == false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    View views = LayoutInflater.from(getContext()).inflate(R.layout.resturant_list_view,null);
                    TextView title = views.findViewById(R.id.lName);
                    TextView invisible = views.findViewById(R.id.timming);

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
                    addResturantButton.setVisibility(View.VISIBLE);

                }else{
                    //Toast.makeText(getContext(),"Ready",Toast.LENGTH_LONG).show();

                    getImageURL(resturantName.getText().toString(),resturantAddress.getText().toString(),resturantPhone.getText().toString(),time);
                }

            }


        });
        ImgUserPhoto = addResturantView.findViewById(R.id.resturantPhoto);
//        Bitmap bitmap  = BitmapFactory.decodeResource(getResources(),R.drawable.userphoto);
//        ImgUserPhoto.setImageBitmap(bitmap);
        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked = true;
                if (Build.VERSION.SDK_INT >= 22) {

                    checkAndRequestForPermission();


                }
                else
                {
                    openGallery();
                }
            }
        });


        return addResturantView;
    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(getContext(),"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(getActivity(),
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {
            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            ImgUserPhoto.setImageURI(pickedImgUri);
        }
    }

    private void getImageURL(final String name, final String address, final String phone, final String time) {

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
                        addResturant(name,address,phone,time,uri.toString());
                    }
                });
            }
        });
    }
    private void addResturant(String name,String address,String phone,String time,String imageUrl) {
        Map<String,Object> item = new HashMap<>();
        item.put("Address",address);
        item.put("Name",name);
        item.put("Timming",time);
        item.put("contactNo",phone);
        item.put("homeImage",imageUrl);
        db.collection("Resturants").add(item).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                View view = LayoutInflater.from(getContext()).inflate(R.layout.alert_box,null);
                TextView title = view.findViewById(R.id.lName);
                TextView invisible = view.findViewById(R.id.lNames);
                android.support.v7.widget.CardView cart = view.findViewById(R.id.bankcardId);
                invisible.setTextColor(Color.TRANSPARENT);
                ImageView image = view.findViewById(R.id.profile_image);
                image.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                title.setText("Resturant Added Successfuly");
                title.setTextSize(20);
                title.setTextColor(Color.BLACK);
                builder.setView(view);
                builder.create();
                builder.show();
                resturantName.setText("");
                resturantAddress.setText("");
                resturantPhone.setText("");
//                ImageView images = view.findViewById(R.id.resturantPhoto);
//                images.setImageDrawable(getResources().getDrawable(R.drawable.profile));
                loadingProgressBar.setVisibility(View.INVISIBLE);
                addResturantButton.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                View view = LayoutInflater.from(getContext()).inflate(R.layout.alert_box,null);
                TextView title = view.findViewById(R.id.lName);
                TextView invisible = view.findViewById(R.id.lNames);
                android.support.v7.widget.CardView cart = view.findViewById(R.id.bankcardId);
                invisible.setTextColor(Color.TRANSPARENT);
                ImageView image = view.findViewById(R.id.profile_image);
                image.setImageDrawable(getResources().getDrawable(R.drawable.fail));
                title.setText("Resturant uploading failed");
                title.setTextSize(20);
                title.setTextColor(Color.BLACK);
                builder.setView(view);
                builder.create();
                builder.show();
                loadingProgressBar.setVisibility(View.INVISIBLE);
                addResturantButton.setVisibility(View.VISIBLE);
            }
        });
    }

}
