package com.example.mattjoslenproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditAssessment extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    DBHelper myHelper;

    Button submitButton;
    EditText etAssessmentName, etDueDate;
    Switch typeSwitch, editAssessmentAlertSwitch;
    int courseID, assessmentID;
    String type, mentorName, mentorPhone, mentorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

        Intent intent = getIntent();
        assessmentID = intent.getIntExtra("assessmentID", -1);
        mentorName = intent.getStringExtra("mentorName");
        mentorPhone = intent.getStringExtra("mentorPhone");
        mentorEmail = intent.getStringExtra("mentorEmail");

        myHelper = new DBHelper(EditAssessment.this);
        mDatabase = myHelper.getReadableDatabase();

        Cursor cursor = mDatabase.query(
                Assessments.AssessmentEntry.TABLE_NAME,
                null,
                Assessments.AssessmentEntry.COLUMN_ID + "=" + assessmentID,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        etAssessmentName = findViewById(R.id.etAssessmentNameEdit);
        etDueDate = findViewById(R.id.etDueDateEdit);
        typeSwitch = findViewById(R.id.typeSwitchEdit);
        editAssessmentAlertSwitch = findViewById(R.id.editAssessmentAlertSwitch);
        submitButton = findViewById(R.id.submitButtonEditA);

        etAssessmentName.setText(cursor.getString(1));
        etDueDate.setText(cursor.getString(3));

        courseID = cursor.getInt(2);

        EditData();
    }

    public void EditData() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String assessmentName = etAssessmentName.getText().toString();
                String dueDate = etDueDate.getText().toString();

                if (typeSwitch.isChecked()) {
                    type = "PA";
                } else {
                    type = "OA";
                }

                boolean insertData = myHelper.editAssessmentData(assessmentID, assessmentName, dueDate, type, courseID);

                if(insertData == true) {
                    Toast.makeText(EditAssessment.this, "Data Successfully Edited!", Toast.LENGTH_LONG).show();

                    if (editAssessmentAlertSwitch.isChecked()) {
                        try {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            Date newDueDate = df.parse(dueDate);

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(newDueDate);
                            Long millis = calendar.getTimeInMillis();

                            System.out.println("Millis: " + millis);

                            Intent i = new Intent(EditAssessment.this, MyReceiver.class);
                            i.putExtra("title", "An assessment is due today!");
                            PendingIntent sender = PendingIntent.getBroadcast(EditAssessment.this,0, i,0);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, millis, sender);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    Intent intent = new Intent (EditAssessment.this, MainActivity.class);
                    intent.putExtra("EXTRA2", "backToAssessmentFrag");

                    Bundle extras = new Bundle();
                    extras.putInt("courseID", courseID);
                    extras.putString("mentorName", mentorName);
                    extras.putString("mentorPhone", mentorPhone);
                    extras.putString("mentorEmail", mentorEmail);

                    intent.putExtras(extras);

                    myHelper.close();

                    startActivity(intent);
                } else {
                    Toast.makeText(EditAssessment.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (EditAssessment.this, MainActivity.class);
        intent.putExtra("EXTRA2", "backToAssessmentFrag");

        System.out.println("Course ID: " + courseID);
        System.out.println("Mentor Name: " + mentorName);
        System.out.println("Mentor Phone: " + mentorPhone);
        System.out.println("Mentor Email: " + mentorEmail);

        Bundle extras = new Bundle();
        extras.putInt("courseID", courseID);
        extras.putString("mentorName", mentorName);
        extras.putString("mentorPhone", mentorPhone);
        extras.putString("mentorEmail", mentorEmail);
        intent.putExtras(extras);

        myHelper.close();

        startActivity(intent);
    }

}
