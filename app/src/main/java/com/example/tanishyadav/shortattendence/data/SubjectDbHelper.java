package com.example.tanishyadav.shortattendence.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SubjectDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "subject.db";
    private static final int DATABASE_VERSION = 1;

    public SubjectDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {





        String SQL_CREATE_SUBJECT_TABLE = "CREATE TABLE "+ SubjectContract.SubjectEntry.TABLE_NAME+"("
                + SubjectContract.SubjectEntry._id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SubjectContract.SubjectEntry.COLUMN_SUBJECT_NAME+" TEXT NOT NULL, "
                + SubjectContract.SubjectEntry.COLUMN_TOTAL_NO_OF_CLASSES+" INTEGER NOT NULL, "
                + SubjectContract.SubjectEntry.COLUMN_MISSED_CLASSES+" INTEGER NOT NULL DEFAULT 0, "
                + SubjectContract.SubjectEntry.COLUMN_EXTRA_CLASSES+" INTEGER NOT NULL DEFAULT 0, "
                + SubjectContract.SubjectEntry.COLUMN_PROF_NAME+" TEXT)";


        db.execSQL(SQL_CREATE_SUBJECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
