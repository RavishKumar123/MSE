package com.example.ravish.testro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Resturants extends AppCompatActivity {

    FirebaseFirestore db;
    resturantListViewAdapter resturantListViewAdapter;
    ListView resturantListView;
    List<ResturantModel> resturantList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturants);

        resturantList = new ArrayList<>();
//        resturantList.add(new ResturantModel("Bilal Broast","2:00",2));
//        resturantList.add(new ResturantModel("MC Donalds","2:00",2));
//        resturantList.add(new ResturantModel("MC Donalds","2:00",2));
//        resturantList.add(new ResturantModel("MC Donalds","2:00",2));

          db = FirebaseFirestore.getInstance();
          db.collection("Resturants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        resturantList.add(new ResturantModel(document.get("Name").toString(),document.get("Timming").toString(),document.get("homeImage").toString(),document.get("Address").toString(),document.get("contactNo").toString()));
//                        Toast.makeText(getBaseContext(),document.get("Timming").toString(),Toast.LENGTH_LONG).show();
                    }
                    resturantListViewAdapter.notifyDataSetChanged();
                }else{
//                    Toast.makeText(getBaseContext(),"sorry",Toast.LENGTH_LONG).show();
                }
              }
          });
        resturantListView = findViewById(R.id.resturanListview);
        resturantListView.setDivider(null);
        resturantListViewAdapter =
                new resturantListViewAdapter(getBaseContext(),R.layout.resturant_list_view,resturantList);
        resturantListView.setAdapter(resturantListViewAdapter);
        resturantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getBaseContext(),resturantList.get(i),Toast.LENGTH_LONG).show();
                ResturantModel model = resturantList.get(i);
                Intent intent = new Intent(getBaseContext(),ResturantDetail.class);
                intent.putExtra("name",model.getResturantName());
                intent.putExtra("time",model.getTime());
                intent.putExtra("image",model.getHomeImage());
                intent.putExtra("address",model.getAddress());
                intent.putExtra("phone",model.getcontact());

                startActivity(intent);
            }
        });
    }
    public void back(View view){
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);

    }




}
