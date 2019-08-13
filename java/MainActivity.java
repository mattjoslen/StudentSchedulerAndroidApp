package com.example.mattjoslenproject;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    DBHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        myHelper = new DBHelper(MainActivity.this);

        myHelper.getWritableDatabase();

        Toast.makeText(MainActivity.this, myHelper.getDatabaseName(), Toast.LENGTH_SHORT).show();

        myHelper.createTermTable("term_tbl");
        myHelper.createCourseTable("course_tbl");
        myHelper.createAssessmentTable("assessment_tbl");

        if ("backToTermsFrag".equals(getIntent().getStringExtra("EXTRA"))) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TermsFragment()).commit();
        }

        if ("backToAssessmentFrag".equals(getIntent().getStringExtra("EXTRA2"))) {
            Bundle extras = getIntent().getExtras();
            int courseID = extras.getInt("courseID", -1);
            String mentorName = extras.getString("mentorName");
            String mentorPhone = extras.getString("mentorPhone");
            String mentorEmail = extras.getString("mentorEmail");



            Fragment assessmentFragment = new AssessmentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("courseID", courseID);
            bundle.putString("mentorName", mentorName);
            bundle.putString("mentorPhone", mentorPhone);
            bundle.putString("mentorEmail", mentorEmail);
            assessmentFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, assessmentFragment).commit();
        }

        if ("backToCoursesFrag".equals(getIntent().getStringExtra("EXTRA3"))) {
            Bundle extras = getIntent().getExtras();
            int termID = extras.getInt("termID", -1);

            Fragment termCoursesFragment = new TermCoursesFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("termID", termID);
            termCoursesFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, termCoursesFragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch(item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_terms:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TermsFragment()).commit();
                break;
            case R.id.nav_courses:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CoursesFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
