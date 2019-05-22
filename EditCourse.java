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

public class EditCourse extends AppCompatActivity {

    DBHelper myHelper;
    private SQLiteDatabase mDatabase;

    Button submitButton;
    EditText etCourseName, etStartDate, etEndDate, etMentorName, etMentorPhone, etMentorEmail, etStatus;
    Switch editCourseAlertSwitch;
    int courseID, termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        myHelper = new DBHelper(EditCourse.this);
        mDatabase = myHelper.getReadableDatabase();

        Intent intent = getIntent();
        courseID = intent.getIntExtra("courseID", -1);

        Cursor cursor = mDatabase.query(
                Courses.CourseEntry.TABLE_NAME,
                null,
                Courses.CourseEntry.COLUMN_ID + "=" + courseID,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        etCourseName = findViewById(R.id.etCourseNameEdit);
        etStartDate = findViewById(R.id.etStartDateEdit);
        etEndDate = findViewById(R.id.etEndDateEdit);
        etMentorName = findViewById(R.id.textMentorNameEdit);
        etMentorPhone = findViewById(R.id.textMentorPhoneEdit);
        etMentorEmail = findViewById(R.id.textMentorEmailEdit);
        etStatus = findViewById(R.id.etStatusEdit);
        editCourseAlertSwitch = findViewById(R.id.editCourseAlertSwitch);
        submitButton = findViewById(R.id.submitButtonEdit);

        etCourseName.setText(cursor.getString(1));
        etStartDate.setText(cursor.getString(3));
        etEndDate.setText(cursor.getString(4));
        etStatus.setText(cursor.getString(5));
        etMentorName.setText(cursor.getString(6));
        etMentorPhone.setText(cursor.getString(7));
        etMentorEmail.setText(cursor.getString(8));

        termID = cursor.getInt(2);

        EditData();
    }

    public void EditData() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = etCourseName.getText().toString();
                String startDate = etStartDate.getText().toString();
                String endDate = etEndDate.getText().toString();
                String mentorName = etMentorName.getText().toString();
                String mentorPhone = etMentorPhone.getText().toString();
                String mentorEmail = etMentorEmail.getText().toString();
                String status = etStatus.getText().toString();



                boolean editData = myHelper.editCourseData(courseID, courseName, termID, startDate, endDate, mentorName, mentorPhone, mentorEmail, status);

                if(editData == true) {
                    if (editCourseAlertSwitch.isChecked()) {
                        try {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            Date newStartDate = df.parse(startDate);

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(newStartDate);
                            Long millisStart = calendar.getTimeInMillis();

                            System.out.println("Millis Start: " + millisStart);

                            Intent i = new Intent(EditCourse.this, MyReceiver.class);
                            i.putExtra("title", "A course starts today!");
                            PendingIntent sender = PendingIntent.getBroadcast(EditCourse.this, 0, i, 0);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, millisStart, sender);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            Date newEndDate = df.parse(endDate);

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(newEndDate);
                            Long millisEnd = calendar.getTimeInMillis();

                            System.out.println("Millis End: " + millisEnd);

                            Intent i = new Intent(EditCourse.this, MyReceiver.class);
                            i.putExtra("title", "A course ends today!");
                            PendingIntent sender = PendingIntent.getBroadcast(EditCourse.this, 0, i, 0);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, millisEnd, sender);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    Intent intent = new Intent (EditCourse.this, MainActivity.class);
                    intent.putExtra("EXTRA3", "backToCoursesFrag");

                    Bundle extras = new Bundle();
                    extras.putInt("termID", termID);

                    intent.putExtras(extras);

                    myHelper.close();

                    startActivity(intent);
                } else {
                    Toast.makeText(EditCourse.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (EditCourse.this, MainActivity.class);
        intent.putExtra("EXTRA3", "backToCoursesFrag");

        Bundle extras = new Bundle();
        extras.putInt("termID", termID);

        intent.putExtras(extras);

        myHelper.close();

        startActivity(intent);
    }

}
