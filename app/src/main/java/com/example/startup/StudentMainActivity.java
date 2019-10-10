package com.example.startup;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.startup.FragmentStudent.Assingments;
import com.example.startup.FragmentStudent.Attendance;
import com.example.startup.FragmentStudent.Fees;
import com.example.startup.FragmentStudent.Home;
import com.example.startup.FragmentStudent.Profile;
import com.example.startup.FragmentStudent.Query;
import com.example.startup.FragmentStudent.TimeTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class StudentMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RelativeLayout notificationCount1;
    private Menu menu;
    private ImageButton notification;
    SharedPreferences sp;
    private String JsonURL = "https://edfinix.herokuapp.com/api/students/";
    RequestQueue requestQueue;
    ImageView imageView;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        notification = findViewById(R.id.notification_click);

        sp = Objects.requireNonNull(this).getSharedPreferences("login", MODE_PRIVATE);
        String value = sp.getString("username", "");
        //Log.d(TAG, value);
        String link_student = JsonURL+value;
        Log.d(TAG, link_student);



        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentMainActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View dialoglayout = layoutInflater.inflate(R.layout.notification_activity, null);
                builder.setView(dialoglayout);

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(StudentMainActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        notificationCount1 = findViewById(R.id.relative_layout_item_count);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton floatingActionButton =
                findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        displaySelectedScreen(R.id.nav_home);

        View headerView = navigationView.getHeaderView(0);
          imageView = headerView.findViewById(R.id.imageView_navigation);
          t = headerView.findViewById(R.id.name_navigation);
//          imageView.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View view) {
//                  Intent a = new Intent(StudentMainActivity.this,Profile.class);
//                  startActivity(a);
//
//              }
//          });

        navigation(link_student);




    }
public void navigation(String link_student)
{
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, link_student,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray studentinfo= response.getJSONArray("student");
                        JSONObject studentObj = (JSONObject) studentinfo.get(0);
                        String fname = studentObj.getString("firstName");
                        String lname = studentObj.getString("lastName");
                        String sphoto = studentObj.getString("image");
                        String sclass = studentObj.getString("class");
                        String sroll= studentObj.getString("rollNo");
                        String ssection = studentObj.getString("section");
                        String id = studentObj.getString("admissionNo");

                        t.setText(fname+lname);
                        Glide.with(getApplicationContext())
                                .load(sphoto)
                                .apply(RequestOptions.circleCropTransform())
                                .into(imageView);
                        SharedPreferences.Editor e=sp.edit();
                        e.putString("sClass",sclass);
                        e.putString("sRoll",sroll);
                        e.putString("sSection",ssection);
                        e.putString("sAdmission",id);
                        e.apply();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Log.d("response", String.valueOf(response));
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("Volley", "Error");

                }
            });

// Access the RequestQueue through your singleton class.
    requestQueue.add(jsonObjectRequest);


}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

/*
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item1 = menu.findItem(R.id.actionbar_item);
        MenuItemCompat.setActionView(item1, R.layout.notification_update_count_layout);
        notificationCount1 = (RelativeLayout) MenuItemCompat.getActionView(item1);

*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       /* if (item.getItemId() == R.id.actionbar_item) {
            AlertDialog.Builder builder = new AlertDialog.Builder(StudentMainActivity.this);
            LayoutInflater layoutInflater = getLayoutInflater();
            View dialoglayout = layoutInflater.inflate(R.layout.notification_activity, null);
            builder.setView(dialoglayout);
            builder.show();
        }
        */

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {


        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_home:
                fragment = new Home();
                break;
            case R.id.nav_timetable:
                fragment = new TimeTable();
                break;
            case R.id.nav_attendance:
                fragment = new Attendance();
                break;
            case R.id.nav_profile:
                fragment = new Profile();
                break;
            case R.id.nav_query:
                fragment = new Query();
                break;
            case R.id.nav_assignments:
                fragment = new Assingments();
                break;
            case R.id.nav_fees:
                fragment = new Fees();
                break;
            case R.id.nav_logout:
                //Intent a = new Intent(StudentMainActivity.this,Login.class);
                //Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                //startActivity(a);

                SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor e1=sp.edit();
                e1.clear();
                e1.apply();
                startActivity(new Intent(StudentMainActivity.this,Login.class));
                finish();   //finish current activity
                break;
        }


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        displaySelectedScreen(item.getItemId());
        return true;
    }





}



