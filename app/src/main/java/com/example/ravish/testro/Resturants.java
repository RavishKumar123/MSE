package com.example.ravish.testro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Resturants extends AppCompatActivity {


    ListView resturantListView;
    List<ResturantModel> resturantList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturants);

        resturantList = new ArrayList<>();
        resturantList.add(new ResturantModel("Bilal Broast","2:00",2));
        resturantList.add(new ResturantModel("MC Donalds","2:00",2));
        resturantList.add(new ResturantModel("MC Donalds","2:00",2));
        resturantList.add(new ResturantModel("MC Donalds","2:00",2));
        resturantList.add(new ResturantModel("MC Donalds","2:00",2));


        resturantListView = findViewById(R.id.resturanListview);
        resturantListView.setDivider(null);
        resturantListViewAdapter resturantListViewAdapter =
                new resturantListViewAdapter(getBaseContext(),R.layout.resturant_list_view,resturantList);
        resturantListView.setAdapter(resturantListViewAdapter);
        resturantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),"dsds",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Resturants.this,ResturantDetail.class);
                startActivity(intent);
            }
        });
    }
    public void back(View view){
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);

    }
    public void check(View view){
        Intent intent = new Intent(getBaseContext(),ResturantDetail.class);
        startActivity(intent);

    }

}
