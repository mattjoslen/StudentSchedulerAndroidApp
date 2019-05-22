package com.example.mattjoslenproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTerm extends AppCompatActivity {

    DBHelper myHelper;

    Button submitButton;
    EditText etTermName, etStartDate, etEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        myHelper = new DBHelper(AddTerm.this);

        etTermName = findViewById(R.id.etTermName);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        submitButton = findViewById(R.id.submitButton);

        AddData();
    }



    public void AddData() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String termName = etTermName.getText().toString();
                String startDate = etStartDate.getText().toString();
                String endDate = etEndDate.getText().toString();

                boolean insertData = myHelper.addTermData(termName, startDate, endDate);

                if(insertData == true) {
                    Toast.makeText(AddTerm.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent (AddTerm.this, MainActivity.class);
                    intent.putExtra("EXTRA", "backToTermsFrag");

                    myHelper.close();

                    startActivity(intent);
                } else {
                    Toast.makeText(AddTerm.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}
