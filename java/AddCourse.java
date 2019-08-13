package com.example.mattjoslenproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

public class AddCourse extends AppCompatActivity {

    DBHelper myHelper;

    Button submitButton;
    Switch addCourseAlertSwitch;
    EditText etCourseName, etStartDate, etEndDate, etMentorName, etMentorPhone, etMentorEmail, etStatus;
    int termID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        Intent intent = getIntent();
        termID = intent.getIntExtra("termID", -1);

        Toast.makeText(AddCourse.this, "Term ID chosen: " + termID, Toast.LENGTH_LONG).show();

        myHelper = new DBHelper(AddCourse.this);

        etCourseName = findViewById(R.id.etCourseName);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etMentorName = findViewById(R.id.textMentorName);
        etMentorPhone = findViewById(R.id.textMentorPhone);
        etMentorEmail = findViewById(R.id.textMentorEmail);
        etStatus = findViewById(R.id.etStatus);
        addCourseAlertSwitch = findViewById(R.id.addCourseAlertSwitch);
        submitButton = findViewById(R.id.submitButton);

        AddData();
    }

    public void AddData() {
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



                boolean insertData = myHelper.addCourseData(courseName, termID, startDate, endDate, mentorName, mentorPhone, mentorEmail, status);

                if(insertData == true) {

                    if (addCourseAlertSwitch.isChecked()) {
                        try {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            Date newStartDate = df.parse(startDate);

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(newStartDate);
                            Long millisStart = calendar.getTimeInMillis();

                            System.out.println("Millis Start: " + millisStart);

                            Intent i = new Intent(AddCourse.this, MyReceiver.class);
                            i.putExtra("title", "A course starts today!");
                            PendingIntent sender = PendingIntent.getBroadcast(AddCourse.this,0, i,0);
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

                            Intent i = new Intent(AddCourse.this, MyReceiver.class);
                            i.putExtra("title", "A course ends today!");
                            PendingIntent sender = PendingIntent.getBroadcast(AddCourse.this,0, i,0);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, millisEnd, sender);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                    Intent intent = new Intent (AddCourse.this, MainActivity.class);
                    intent.putExtra("EXTRA3", "backToCoursesFrag");

                    Bundle extras = new Bundle();
                    extras.putInt("termID", termID);

                    intent.putExtras(extras);

                    myHelper.close();

                    startActivity(intent);
                } else {
                    Toast.makeText(AddCourse.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
