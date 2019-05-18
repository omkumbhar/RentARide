package com.omkar.kumbhar.rentaride.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.omkar.kumbhar.rentaride.Fragment.CitySelectionFragment;
import com.omkar.kumbhar.rentaride.Fragment.HomeFragment;
import com.omkar.kumbhar.rentaride.Fragment.LicenseUploadFragment;
import com.omkar.kumbhar.rentaride.Fragment.OderFragment;
import com.omkar.kumbhar.rentaride.Fragment.UploadFragment;
import com.omkar.kumbhar.rentaride.Method.UserData;
import com.omkar.kumbhar.rentaride.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvProName,UserEmail;
    ImageView imageProfile;
    UserData userData;
    NavigationView navigationView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser cUser;
    Menu menu;
    MenuItem target,target2,target3,target4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cUser = mAuth.getCurrentUser();

        userData = new UserData();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menu =navigationView.getMenu();




        if(userData.getLoginStatus(getApplicationContext())){
            SignedHeader();
        }
        else {
            UnsignedHeader();
        }

        //To open default fragment in the container
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager  = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.commit();

    }

    private void SignedHeader() {
        //TO set Navigation header data
        View headerView = navigationView.getHeaderView(0);
        tvProName = (TextView) headerView.findViewById(R.id.tvProName);
        UserEmail = (TextView) headerView.findViewById(R.id.UserEmail);
        imageProfile = (ImageView) headerView.findViewById(R.id.imageProfile);

        if(userData.getProfilePic(getApplicationContext()) != null ){
            Glide.with(HomeActivity.this).load(userData.getProfilePic(getApplicationContext())).into(imageProfile);
        }



        if (userData.getUserName(getApplicationContext())!= null) {
            tvProName.setText(userData.getUserName(getApplicationContext()));
            UserEmail.setText(userData.getEmail(getApplicationContext()));
        }

        // To handle on click on the navigation header
        LinearLayout header = (LinearLayout) headerView.findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(HomeActivity.this, "On header Click weeeeeeee!", Toast.LENGTH_SHORT).show();
                Intent profileIntent = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(profileIntent);


            }
        });
    }

    private void UnsignedHeader() {
        navigationView.getHeaderView(0).setVisibility(View.GONE); //To hide header from navigation drawer
        View headerView= LayoutInflater.from(this).inflate(R.layout.nav_header_unsigned, null);
        navigationView.addHeaderView(headerView);
        LinearLayout header = (LinearLayout) headerView.findViewById(R.id.unsigned_header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(HomeActivity.this, "Unsighned header", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        target = menu.findItem(R.id.lic_upload);
        target.setVisible(false);
        target2 = menu.findItem(R.id.upload);
        target2.setVisible(false);

        target3 = menu.findItem(R.id.nav_logout);
        target3.setVisible(false);
        target4 = menu.findItem(R.id.nav_order);
        target4.setVisible(false);

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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.city_selection) {
            // Handle the camera action
            fragment = new CitySelectionFragment();
        } else if (id == R.id.lic_upload) {
            fragment = new LicenseUploadFragment();

        } else if (id == R.id.upload) {

            if(userData.getEmailVerified(getApplicationContext())){
                fragment = new UploadFragment();
            }else {
                Toast.makeText(this, "Your email address is not verified", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_order) {
            fragment = new OderFragment();

        } else if (id == R.id.nav_logout) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            UnsignedHeader();
            UserData userData = new UserData();
            userData.RemoveAddData(getApplicationContext());
            finish();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(getIntent(),ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            }else {
                startActivity(getIntent());
            }
        }

        if(fragment != null){
            FragmentManager fragmentManager  = getSupportFragmentManager();
            FragmentTransaction  ft = fragmentManager.beginTransaction();
            ft.replace(R.id.container,fragment).addToBackStack("HomeActivity");
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(userData.getLoginStatus(getApplicationContext())){
            userData.AddEmailVerified(cUser.isEmailVerified(),getApplicationContext());
        }






    }
}
