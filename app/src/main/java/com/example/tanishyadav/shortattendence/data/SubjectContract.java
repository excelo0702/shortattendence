package com.example.tanishyadav.shortattendence.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class SubjectContract {

    private SubjectContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.tanishyadav.shortattendence";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_SUBJECTS = "shortattendence";

    public static final class SubjectEntry implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_SUBJECTS);

        public static final String TABLE_NAME = "subject";
        public static final String _id = BaseColumns._ID;
        public static final String COLUMN_SUBJECT_NAME = "Name";
        public static final String COLUMN_TOTAL_NO_OF_CLASSES = "TotalClasses";
        public static final String COLUMN_MISSED_CLASSES = "MissedClasses";
        public static final String COLUMN_EXTRA_CLASSES = "ExtraClasses";
        public static final String COLUMN_PROF_NAME = "ProfName";
    }
}
