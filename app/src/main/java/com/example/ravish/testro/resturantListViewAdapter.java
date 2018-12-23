package com.example.ravish.testro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class resturantListViewAdapter extends ArrayAdapter<ResturantModel> {

    Context context;
    int resourse;
    List<ResturantModel> resturantModelList;

    public resturantListViewAdapter(Context context, int resourse, List<ResturantModel> resturantModelList) {
        super(context,resourse,resturantModelList);
        this.context = context;
        this.resourse = resourse;
        this.resturantModelList = resturantModelList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resourse,null);

        TextView name = view.findViewById(R.id.lName);
        TextView timming = view.findViewById(R.id.timming);
        ImageView homeImage = view.findViewById(R.id.profile_image);

        ResturantModel rm = resturantModelList.get(position);
        name.setText(rm.resturantName);
        timming.setText(rm.time);
        Picasso.get().load(rm.homeImage).into(homeImage);
        return  view;
    }
}
