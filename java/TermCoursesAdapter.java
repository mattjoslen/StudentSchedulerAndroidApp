package com.example.mattjoslenproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TermCoursesAdapter extends RecyclerView.Adapter<TermCoursesAdapter.ViewHolder> {

    private static final String TAG = "TermCoursesAdapter";

    private Context mContext;
    private Cursor mCursor;


    public TermCoursesAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_courses_list, parent, false);
        TermCoursesAdapter.ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TermCoursesAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String courseName = mCursor.getString(mCursor.getColumnIndex(Courses.CourseEntry.COLUMN_NAME));
        String endDate = mCursor.getString(mCursor.getColumnIndex(Courses.CourseEntry.COLUMN_END));
        String startDate = mCursor.getString(mCursor.getColumnIndex(Courses.CourseEntry.COLUMN_START));
        String status = mCursor.getString(mCursor.getColumnIndex(Courses.CourseEntry.COLUMN_STATUS));
        final String mentorName = mCursor.getString(mCursor.getColumnIndex(Courses.CourseEntry.COLUMN_MENTOR_NAME));
        final String mentorPhone = mCursor.getString(mCursor.getColumnIndex(Courses.CourseEntry.COLUMN_MENTOR_PHONE));
        final String mentorEmail = mCursor.getString(mCursor.getColumnIndex(Courses.CourseEntry.COLUMN_MENTOR_EMAIL));
        final int courseID = mCursor.getInt(mCursor.getColumnIndex(Courses.CourseEntry.COLUMN_ID));

        holder.courseNameText.setText(courseName);
        holder.startDateText.setText(startDate);
        holder.endDateText.setText(endDate);
        holder.hyphenText.setText("-");
        holder.statusText.setText(status);
        holder.itemView.setTag(courseID);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment assessmentFragment = new AssessmentFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("courseID", courseID);
                bundle.putString("mentorName", mentorName);
                bundle.putString("mentorPhone", mentorPhone);
                bundle.putString("mentorEmail", mentorEmail);
                assessmentFragment.setArguments(bundle);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, assessmentFragment).commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor (Cursor newCursor) {
        if(mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if(newCursor != null) {
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView courseNameText;
        public TextView startDateText;
        public TextView endDateText;
        public TextView hyphenText;
        public TextView statusText;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            courseNameText = itemView.findViewById(R.id.courseNameText);
            startDateText = itemView.findViewById(R.id.startDateText);
            endDateText = itemView.findViewById(R.id.endDateText);
            hyphenText = itemView.findViewById(R.id.hyphenText);
            statusText = itemView.findViewById(R.id.statusText);
            parentLayout = itemView.findViewById(R.id.parent_course_layout);
        }
    }


}
