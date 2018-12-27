package com.example.ravish.testro;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ResturantDetail extends AppCompatActivity {
    private ViewPager viewPager;
    private SlideAdapter myadapter;
    Dialog myDialog;
    TextView addReview;
    TextView rname,rphone,raddress,rtimming,rPhone;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_detail);
        mAuth = FirebaseAuth.getInstance();
        rname = findViewById(R.id.RName);
        rtimming = findViewById(R.id.RTimming);
        rphone = findViewById(R.id.RPhone);
        raddress = findViewById(R.id.RAddress);
        addReview = findViewById(R.id.addReview);
        myDialog = new Dialog(ResturantDetail.this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        myadapter = new SlideAdapter(this);
        viewPager.setAdapter(myadapter);
        String name = getIntent().getStringExtra("name");
        String time = getIntent().getStringExtra("time");
        String image = getIntent().getStringExtra("image");
        String address = getIntent().getStringExtra("address");
        String phone = getIntent().getStringExtra("phone");
          rname.setText(name);
          raddress.setText(address);
          rtimming.setText(time);
          rphone.setText(phone);


    addReview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        TextView txtclose;
        TextView username,email;
        ImageView profile;
        Button btnSubmit;
        FirebaseUser user = mAuth.getCurrentUser();
        myDialog.setContentView(R.layout.custompopup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        btnSubmit = (Button) myDialog.findViewById(R.id.btnSubmit);
        username = myDialog.findViewById(R.id.popupUsername);
        email = myDialog.findViewById(R.id.popupEmail);
        profile = myDialog.findViewById(R.id.popupImage);
        if (user != null) {
           username.setText(user.getDisplayName());
           email.setText(user.getEmail());
            if(user.getPhotoUrl() != null){
                Picasso.get().load(user.getPhotoUrl()).into(profile);
            }
        }

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }
});

    }
    public void back(View view){
        Intent intent = new Intent(getBaseContext(),Resturants.class);
        startActivity(intent);

    }
}
