package com.example.mattjoslenproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

public class TermCoursesFragment extends Fragment {

    private SQLiteDatabase mDatabase;
    private TermCoursesAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.term_courses_list, container, false);



        final int termID = getArguments().getInt("termID");

        Toast.makeText(getActivity(), "Term ID chosen: " + termID, Toast.LENGTH_LONG).show();

        FloatingActionButton addCourseButton = view.findViewById(R.id.addCourseButton);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCourse.class);
                intent.putExtra("termID", termID);
                startActivity(intent);
            }
        });



        DBHelper myHelper = new DBHelper(getActivity());
        mDatabase = myHelper.getWritableDatabase();

        RecyclerView recyclerView = view.findViewById(R.id.termCourseRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new TermCoursesAdapter(getActivity(), getAllItems(termID));
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((int) viewHolder.itemView.getTag(), termID);
            }
        }).attachToRecyclerView(recyclerView);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                editItem((int) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    private void removeItem(int id, int termID) { // Remove course by swiping it left or right
        mDatabase.delete(Courses.CourseEntry.TABLE_NAME,
                Courses.CourseEntry.COLUMN_ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems(termID));
    }

    private void editItem(int id) { //Edit course by swiping it left
        Intent intent = new Intent(getActivity(), EditCourse.class);
        intent.putExtra("courseID", id);
        this.startActivity(intent);

    }

    private Cursor getAllItems(int termID) {
        String termIDString = Integer.toString(termID);
        return mDatabase.query(
                Courses.CourseEntry.TABLE_NAME,
                null,
                Courses.CourseEntry.COLUMN_TERM_ID + "=" + termIDString,
                null,
                null,
                null,
                Courses.CourseEntry.COLUMN_START
        );
    }
}
