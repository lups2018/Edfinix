package com.example.startup;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.startup.FragmentStudent.Assingments;
import com.example.startup.FragmentStudent.Attendance;
import com.example.startup.FragmentStudent.Fees;
import com.example.startup.FragmentStudent.Home;
import com.example.startup.FragmentStudent.Profile;
import com.example.startup.FragmentStudent.Query;
import com.example.startup.FragmentStudent.TimeTable;
import com.example.startup.FragmentTeacher.ClassRoutine;
import com.example.startup.FragmentTeacher.GiveAssignments;
import com.example.startup.FragmentTeacher.StudentAttendance;
import com.example.startup.FragmentTeacher.StudentQueries;
import com.example.startup.FragmentTeacher.TeacherHome;
import com.example.startup.FragmentTeacher.TeacherProfile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
import android.widget.Toast;

public class TeacherMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageButton notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        notification = findViewById(R.id.notification_click_teacher);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherMainActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View dialoglayout = layoutInflater.inflate(R.layout.notification_activity, null);
                builder.setView(dialoglayout);

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(TeacherMainActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });



        Toolbar toolbar = findViewById(R.id.toolbar_teacher);
        setSupportActionBar(toolbar);
        FloatingActionButton floatingActionButton =
                findViewById(R.id.fab_teacher);

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

        NavigationView navigationView = findViewById(R.id.nav_view_teacher);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        displaySelectedScreen(R.id.nav_home);
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
                fragment = new TeacherHome();
                break;
            case R.id.nav_class_routine:
                fragment = new ClassRoutine();
                break;
            case R.id.nav_attendance:
                fragment = new StudentAttendance();
                break;
            case R.id.nav_profile:
                fragment = new TeacherProfile();
                break;
            case R.id.nav_query:
                fragment = new StudentQueries();
                break;
            case R.id.nav_assignments:
                fragment = new GiveAssignments();
                break;
            case R.id.nav_logout:
                //Intent a = new Intent(TeacherMainActivity.this,Login.class);
                //Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                //startActivity(a);
                SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor e=sp.edit();
                e.clear();
                e.apply();
                startActivity(new Intent(TeacherMainActivity.this,Login.class));
                finish();   //finish current activity
                break;
        }


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame_teacher, fragment);
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

