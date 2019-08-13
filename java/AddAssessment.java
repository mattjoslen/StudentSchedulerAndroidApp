package com.example.mattjoslenproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class AddAssessment extends AppCompatActivity {

    DBHelper myHelper;

    Button submitButton;
    EditText etAssessmentName, etDueDate;
    Switch typeSwitch, addAssessmentAlertSwitch;
    int courseID;
    String type, mentorName, mentorPhone, mentorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        Intent intent = getIntent();
        courseID = intent.getIntExtra("courseID", -1);
        mentorName = intent.getStringExtra("mentorName");
        mentorPhone = intent.getStringExtra("mentorPhone");
        mentorEmail = intent.getStringExtra("mentorEmail");

        Toast.makeText(AddAssessment.this, "Course ID chosen: " + courseID, Toast.LENGTH_LONG).show();

        myHelper = new DBHelper(AddAssessment.this);

        etAssessmentName = findViewById(R.id.etAssessmentName);
        etDueDate = findViewById(R.id.etDueDate);
        typeSwitch = findViewById(R.id.typeSwitch);
        addAssessmentAlertSwitch = findViewById(R.id.addAssessmentAlertSwitch);
        submitButton = findViewById(R.id.submitButton);

        AddData();
    }

    public void AddData() {
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

                boolean insertData = myHelper.addAssessmentData(assessmentName, dueDate, type, courseID);

                if(insertData == true) {
                    //Toast.makeText(AddAssessment.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();

                    if (addAssessmentAlertSwitch.isChecked()) {
                        try {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            Date newDueDate = df.parse(dueDate);

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(newDueDate);
                            Long millis = calendar.getTimeInMillis();

                            System.out.println("Millis: " + millis);

                            Intent i = new Intent(AddAssessment.this, MyReceiver.class);
                            i.putExtra("title", "An assessment is due today!");
                            PendingIntent sender = PendingIntent.getBroadcast(AddAssessment.this,0, i,0);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, millis, sender);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    Intent intent = new Intent (AddAssessment.this, MainActivity.class);
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
                    Toast.makeText(AddAssessment.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
