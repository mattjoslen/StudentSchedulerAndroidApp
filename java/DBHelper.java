package com.example.mattjoslenproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student DB.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void createTermTable(String tableName) {
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " +
                tableName +
                "(termID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "termName TEXT NOT NULL, " +
                "startDate DATE NOT NULL, " +
                "endDate DATE NOT NULL)");
    }

    public void createCourseTable(String tableName) {
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " +
                tableName +
                "(courseID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "courseName TEXT NOT NULL, " +
                "termID INTEGER NOT NULL, " +
                "startDate DATE NOT NULL, " +
                "endDate DATE NOT NULL, " +
                "status TEXT NOT NULL, " +
                "mentorName TEXT, " +
                "mentorPhone INTEGER, " +
                "mentorEmail TEXT, " +
                "FOREIGN KEY(termID) REFERENCES term_tbl(termID))");
    }

    public void createAssessmentTable(String tableName) {
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " +
                tableName +
                "(assessmentID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "assessmentName TEXT NOT NULL, " +
                "courseID INTEGER NOT NULL, " +
                "dueDate DATE NOT NULL, " +
                "FOREIGN KEY(courseID) REFERENCES course_tbl(courseID))");
    }

    public boolean addTermData(String termName, String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("termName", termName);
        contentValues.put("startDate", startDate);
        contentValues.put("endDate", endDate);

        long result = db.insert("term_tbl", null, contentValues);

        return result != -1;
    }

    public boolean addCourseData(String courseName, int termID, String startDate, String endDate, String mentorName,
                                 String mentorPhone, String mentorEmail, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("courseName", courseName);
        contentValues.put("termID", termID);
        contentValues.put("startDate", startDate);
        contentValues.put("endDate", endDate);
        contentValues.put("mentorName", mentorName);
        contentValues.put("mentorPhone", mentorPhone);
        contentValues.put("mentorEmail", mentorEmail);
        contentValues.put("status", status);

        long result = db.insert("course_tbl", null, contentValues);

        return result != -1;
    }

    public boolean editCourseData(int courseID, String courseName, int termID, String startDate, String endDate, String mentorName, String mentorPhone, String mentorEmail, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        String courseIDString = Integer.toString(courseID);

        ContentValues contentValues = new ContentValues();
        contentValues.put("courseName", courseName);
        contentValues.put("termID", termID);
        contentValues.put("startDate", startDate);
        contentValues.put("endDate", endDate);
        contentValues.put("mentorName", mentorName);
        contentValues.put("mentorPhone", mentorPhone);
        contentValues.put("mentorEmail", mentorEmail);
        contentValues.put("status", status);

        long result = db.update("course_tbl", contentValues, "courseID = " + courseIDString, null);

        return result != -1;
    }

    public boolean addAssessmentData(String assessmentName, String dueDate, String type, int courseID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("assessmentName", assessmentName);
        contentValues.put("courseID", courseID);
        contentValues.put("dueDate", dueDate);
        contentValues.put("type", type);

        long result = db.insert("assessment_tbl", null, contentValues);

        return result != -1;
    }

    public boolean editAssessmentData(int assessmentID, String assessmentName, String dueDate, String type, int courseID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String assessmentIDString = Integer.toString(assessmentID);

        ContentValues contentValues = new ContentValues();
        contentValues.put("assessmentName", assessmentName);
        contentValues.put("courseID", courseID);
        contentValues.put("dueDate", dueDate);
        contentValues.put("type", type);

        long result = db.update("assessment_tbl", contentValues, "assessmentID = " + assessmentIDString, null);

        return result != -1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
