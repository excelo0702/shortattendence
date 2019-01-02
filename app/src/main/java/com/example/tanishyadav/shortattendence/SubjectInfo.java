package com.example.tanishyadav.shortattendence;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tanishyadav.shortattendence.data.SubjectContract;
import com.example.tanishyadav.shortattendence.data.SubjectDbHelper;
import com.example.tanishyadav.shortattendence.data.ToDoCursorAdapter;

public class SubjectInfo extends AppCompatActivity {

    private EditText mSubject;
    private EditText mTotal_No_Of_Classes;
    private EditText mExtra_Classes;
    private EditText mMissed_Classes;
    private EditText mProf_Name;

    private Uri currentUri;
    private SubjectDbHelper mDbHelper;
    ToDoCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_info);


        mSubject = (EditText)findViewById(R.id.subject);
        mTotal_No_Of_Classes = (EditText)findViewById(R.id.total_Classes);
        mExtra_Classes = (EditText)findViewById(R.id.Extra_Classes);
        mMissed_Classes = (EditText)findViewById(R.id.Missed_Classes);
        mProf_Name = (EditText)findViewById(R.id.Prof_Name);

        Log.v("this","2223");

    }



    private void insertSubjects(){

        String subject = mSubject.getText().toString().trim();
        String TNoOfClasses = mTotal_No_Of_Classes.getText().toString().trim();
        String EClasses = mExtra_Classes.getText().toString().trim();
        String MClasses = mMissed_Classes.getText().toString().trim();
        String prof = mProf_Name.getText().toString().trim();
        int Total_Classes = Integer.parseInt(TNoOfClasses);
        int Extra_Classes = Integer.parseInt(EClasses);
        int Missed_Classes = Integer.parseInt(MClasses);

        mDbHelper = new SubjectDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubjectContract.SubjectEntry.COLUMN_SUBJECT_NAME,subject);
        values.put(SubjectContract.SubjectEntry.COLUMN_TOTAL_NO_OF_CLASSES,Total_Classes);

        values.put(SubjectContract.SubjectEntry.COLUMN_EXTRA_CLASSES,Extra_Classes);
        values.put(SubjectContract.SubjectEntry.COLUMN_MISSED_CLASSES,Missed_Classes);

        Uri newUri = getContentResolver().insert(SubjectContract.SubjectEntry.CONTENT_URI,values);

        if(newUri == null){
            Toast.makeText(this, "1111",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.v("this","1111");
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, "111000",
                    Toast.LENGTH_SHORT).show();
        }


    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.subject_info,menu);
        return true;
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.save_info:
                Log.v("this","2222");
                insertSubjects();
                finish();
                //displayDatabaseInfo();
                Log.v("this","1111");
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
