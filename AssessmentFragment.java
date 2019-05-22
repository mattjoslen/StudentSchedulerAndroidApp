package com.example.mattjoslenproject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AssessmentFragment extends Fragment {

    private SQLiteDatabase mDatabase;
    private AssessmentAdapter mAdapter;
    public int courseID;
    public String mentorNameStr, mentorPhoneStr, mentorEmailStr;
    public TextView textMentorName, textMentorPhone, textMentorEmail;
    Dialog myDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assessment_list, container, false);

        courseID = getArguments().getInt("courseID");
        mentorNameStr = getArguments().getString("mentorName");
        mentorPhoneStr = getArguments().getString("mentorPhone");
        mentorEmailStr = getArguments().getString("mentorEmail");

        textMentorName = view.findViewById(R.id.textMentorName);
        textMentorPhone = view.findViewById(R.id.textMentorPhone);
        textMentorEmail = view.findViewById(R.id.textMentorEmail);

        textMentorName.setText(mentorNameStr);
        textMentorPhone.setText(mentorPhoneStr);
        textMentorEmail.setText(mentorEmailStr);

        FloatingActionButton addAssessmentButton = view.findViewById(R.id.addAssessmentButton);
        addAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddAssessment.class);
                intent.putExtra("courseID", courseID);
                intent.putExtra("mentorName", mentorNameStr);
                intent.putExtra("mentorPhone", mentorPhoneStr);
                intent.putExtra("mentorEmail", mentorEmailStr);
                startActivity(intent);
            }
        });

        myDialog = new Dialog(getActivity());
        final String courseIDStr = Integer.toString(courseID);
        myDialog.setContentView(R.layout.notes_popup);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(false);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String notes = preferences.getString(courseIDStr, "");
        final TextView textEditNotes = myDialog.findViewById(R.id.editNotesText);
        textEditNotes.setText(notes);

        Button showNotesButton = view.findViewById(R.id.showNotesButton);
        showNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.show();

                Button saveButton = myDialog.findViewById(R.id.saveButton);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String updatedNotes = textEditNotes.getText().toString();

                        preferences.edit().putString(courseIDStr, updatedNotes).apply();

                        myDialog.dismiss();
                    }
                });
            }
        });

        Button emailNotesButton = view.findViewById(R.id.emailNotesButton);
        emailNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(notes);
            }
        });

        DBHelper myHelper = new DBHelper(getActivity());
        mDatabase = myHelper.getWritableDatabase();

        RecyclerView recyclerView = view.findViewById(R.id.assessmentRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new AssessmentAdapter(getActivity(), getAllItems(courseID));
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((int) viewHolder.itemView.getTag(), courseID);
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

    private void sendMail(String notes) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Course Notes");
        intent.putExtra(Intent.EXTRA_TEXT, notes);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client:"));
    }

    private void removeItem(int id, int courseID) { // Remove assessment by swiping it right
        mDatabase.delete(Assessments.AssessmentEntry.TABLE_NAME,
                Assessments.AssessmentEntry.COLUMN_ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems(courseID));
    }

    private void editItem(int id) { //Edit assessment by swiping it left
        Intent intent = new Intent(getActivity(), EditAssessment.class);
        intent.putExtra("assessmentID", id);
        intent.putExtra("mentorName", mentorNameStr);
        intent.putExtra("mentorPhone", mentorPhoneStr);
        intent.putExtra("mentorEmail", mentorEmailStr);
        this.startActivity(intent);

    }

    private Cursor getAllItems(int courseID) {
        String courseIDString = Integer.toString(courseID);
        return mDatabase.query(
                Assessments.AssessmentEntry.TABLE_NAME,
                null,
                Assessments.AssessmentEntry.COLUMN_COURSE_ID + "=" + courseIDString,
                null,
                null,
                null,
                Assessments.AssessmentEntry.COLUMN_ID
        );
    }
}
