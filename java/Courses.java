package com.example.mattjoslenproject;

import android.provider.BaseColumns;

public class Courses {

    public static final class CourseEntry implements BaseColumns {

        private CourseEntry() {}

        public static final String COLUMN_ID = "courseID";
        public static final String TABLE_NAME = "course_tbl";
        public static final String COLUMN_TERM_ID = "termID";
        public static final String COLUMN_NAME = "courseName";
        public static final String COLUMN_START = "startDate";
        public static final String COLUMN_END = "endDate";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_MENTOR_NAME = "mentorName";
        public static final String COLUMN_MENTOR_PHONE = "mentorPhone";
        public static final String COLUMN_MENTOR_EMAIL = "mentorEmail";
    }


}
