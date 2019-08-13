package com.example.mattjoslenproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CoursesFragment extends Fragment {

    private SQLiteDatabase mDatabase;
    private CoursesAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list, container, false);

        DBHelper myHelper = new DBHelper(getActivity());
        mDatabase = myHelper.getWritableDatabase();

        RecyclerView recyclerView = view.findViewById(R.id.courseRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CoursesAdapter(getActivity(), getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((int) viewHolder.itemView.getTag());
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

    private void removeItem(int id) { // Remove course by swiping it right
        mDatabase.delete(Courses.CourseEntry.TABLE_NAME,
                Courses.CourseEntry.COLUMN_ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    private void editItem(int id) { //Edit course by swiping it left
        Intent intent = new Intent(getContext(), EditCourse.class);
        intent.putExtra("courseID", id);
        getContext().startActivity(intent);

    }

    private Cursor getAllItems() {
        return mDatabase.query(
                Courses.CourseEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Courses.CourseEntry.COLUMN_START
        );
    }

}
