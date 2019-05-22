package com.example.mattjoslenproject;

import android.provider.BaseColumns;

public class Assessments {

    public static final class AssessmentEntry implements BaseColumns {

        private AssessmentEntry() {}

        public static final String COLUMN_ID = "assessmentID";
        public static final String TABLE_NAME = "assessment_tbl";
        public static final String COLUMN_COURSE_ID = "courseID";
        public static final String COLUMN_NAME = "assessmentName";
        public static final String COLUMN_DUE = "dueDate";
        public static final String COLUMN_TYPE = "type";
    }

}
