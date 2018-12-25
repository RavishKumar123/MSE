package com.example.ravish.testro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    Fragment fragment;
    private FirebaseAuth mAuth;
    TextView userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userEmail = findViewById(R.id.userEmail);
        mAuth = FirebaseAuth.getInstance();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomTab);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.nav_home:

                        fragment = new Home();
                        setFragment(fragment,"Tesro");
                        return true;
                    case R.id.nav_about:

                        fragment = new About();
                        setFragment(fragment,"About");
                        return true;
                    case R.id.nav_Pofile:

                        fragment = new Profile();
                        setFragment(fragment,"Profile");
                        return true;case
                     R.id.nav_Add:
                        fragment = new Add();
                        setFragment(fragment,"Add Resturant");
                        return true;
                        default:
                            return false;

                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void logout() {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this,login.class);
        startActivity(intent);

    }

    private void setFragment(Fragment fragment,String title) {
        ActionBar ab = getSupportActionBar();
        ab.setTitle(title);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag,fragment);
        ft.commit();

    }
    public void resturantScreen(View view){
        Intent intent = new Intent(getBaseContext(),Resturants.class);
        startActivity(intent);
    }
    public void mapsScreen(View view){
        Intent intent = new Intent(getBaseContext(),googleMap.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fragment = new Home();
            setFragment(fragment,"Testro");
        }else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_Logout) {
            logout();

        }else if(id == R.id.nav_add_resturant){
            fragment = new Add();
            setFragment(fragment,"Add Resturant");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(R.id.userNaming);
            TextView navUserEmail = (TextView) headerView.findViewById(R.id.userEmail);
            ImageView profilePic = (ImageView) headerView.findViewById(R.id.imageView);
            if(user.getPhotoUrl() != null){
                Picasso.get().load(user.getPhotoUrl()).into(profilePic);
            }
            navUserEmail.setText(user.getEmail());
            navUsername.setText(user.getDisplayName());
        }else{
            Intent intent = new Intent(MainActivity.this,login.class);
            startActivity(intent);
        }



    }
}
