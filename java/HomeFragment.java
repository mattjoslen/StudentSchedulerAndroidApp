package com.example.mattjoslenproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    DBHelper myHelper;
    private SQLiteDatabase mDatabase;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        myHelper = new DBHelper(getActivity());
        mDatabase = myHelper.getReadableDatabase();


        String[] termColumn = {Terms.TermEntry.COLUMN_NAME};

        Cursor termCursor = mDatabase.query(
                Terms.TermEntry.TABLE_NAME,
                termColumn,
                "DATE('now') BETWEEN " + Terms.TermEntry.COLUMN_START + " AND " + Terms.TermEntry.COLUMN_END,
                null,
                null,
                null,
                null
        );

        TextView termName = view.findViewById(R.id.etTermNameHome);

        if(termCursor.moveToFirst()) {
            termName.setText(termCursor.getString(0));
        } else {
            termName.setText("");
        }


        String[] courseColumns = {Courses.CourseEntry.COLUMN_NAME, Courses.CourseEntry.COLUMN_END};

        Cursor courseCursor = mDatabase.query(
                Courses.CourseEntry.TABLE_NAME,
                courseColumns,
                "DATE('now') < " + Courses.CourseEntry.COLUMN_END,
                null,
                null,
                null,
                Courses.CourseEntry.COLUMN_END
        );

        TextView courseName = view.findViewById(R.id.etCourseNameHome);
        TextView courseEnd = view.findViewById(R.id.etCourseEndHome);

        if (courseCursor.moveToFirst()) {
            courseName.setText(courseCursor.getString(0));
            courseEnd.setText(courseCursor.getString(1));
        }


        String[] assessmentColumns = {Assessments.AssessmentEntry.COLUMN_NAME, Assessments.AssessmentEntry.COLUMN_DUE};

        Cursor assessmentCursor = mDatabase.query(
                Assessments.AssessmentEntry.TABLE_NAME,
                assessmentColumns,
                "DATE('now') < " + Assessments.AssessmentEntry.COLUMN_DUE,
                null,
                null,
                null,
                Assessments.AssessmentEntry.COLUMN_DUE
        );

        TextView assessmentName = view.findViewById(R.id.etAssessmentNameHome);
        TextView assessmentDue = view.findViewById(R.id.etAssessmentDueHome);

        if (assessmentCursor.moveToFirst()) {
            assessmentName.setText(assessmentCursor.getString(0));
            assessmentDue.setText(assessmentCursor.getString(1));
        }

        return view;
    }
}
