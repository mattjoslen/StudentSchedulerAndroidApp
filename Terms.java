package com.example.mattjoslenproject;

import android.provider.BaseColumns;

public class Terms {

    public static final class TermEntry implements BaseColumns {

        private TermEntry() {}

        public static final String COLUMN_ID = "termID";
        public static final String TABLE_NAME = "term_tbl";
        public static final String COLUMN_NAME = "termName";
        public static final String COLUMN_START = "startDate";
        public static final String COLUMN_END = "endDate";
    }


}
