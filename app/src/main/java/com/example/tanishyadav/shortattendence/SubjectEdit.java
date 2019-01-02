package com.example.tanishyadav.shortattendence;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tanishyadav.shortattendence.data.SubjectContract;
import com.example.tanishyadav.shortattendence.data.SubjectDbHelper;
import com.example.tanishyadav.shortattendence.data.ToDoCursorAdapter;

public class SubjectEdit extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{


    private EditText mSubject;
    private EditText mTotal_No_Of_Classes;
    private EditText mExtra_Classes;
    private EditText mMissed_Classes;
    private EditText mProf_Name;

    private Uri currentUri;
    private SubjectDbHelper mDbHelper;
    ToDoCursorAdapter mCursorAdapter;
    private static final int EXISTING_SUBJECT_LOADER = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_edit);

        Intent intent = getIntent();
        currentUri = intent.getData();
        Log.v("this","77778");
        mDbHelper = new SubjectDbHelper(this);

        mSubject = (EditText)findViewById(R.id.subject);
        mTotal_No_Of_Classes = (EditText)findViewById(R.id.TClassesUpdate);
        mExtra_Classes = (EditText)findViewById(R.id.EClassesUpdate);
        mMissed_Classes = (EditText)findViewById(R.id.MClassesUpdate);
        mProf_Name = (EditText)findViewById(R.id.ProfUpdate);




        getSupportLoaderManager().initLoader(EXISTING_SUBJECT_LOADER,null,this);



    }

    private void updateSubject()
    {
        Log.v("this","22220");


        Log.v("this","22221");
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
       // values.put(SubjectContract.SubjectEntry.COLUMN_SUBJECT_NAME,subject);
        values.put(SubjectContract.SubjectEntry.COLUMN_TOTAL_NO_OF_CLASSES,Total_Classes);
        values.put(SubjectContract.SubjectEntry.COLUMN_PROF_NAME,prof);
        values.put(SubjectContract.SubjectEntry.COLUMN_EXTRA_CLASSES,Extra_Classes);
        values.put(SubjectContract.SubjectEntry.COLUMN_MISSED_CLASSES,Missed_Classes);
        Log.v("this","2222");
        int rowsAffected = getContentResolver().update(currentUri,values,null,null);
        Log.v("this","2222");

        // Show a toast message depending on whether or not the update was successful.
        if (rowsAffected == 0) {
            Log.v("this","333");
            // If no rows were affected, then there was an error with the update.
            Toast.makeText(this, "fail",
                    Toast.LENGTH_SHORT).show();
        } else {
            Log.v("this","4444");
            // Otherwise, the update was successful and we can display a toast.
            Toast.makeText(this, "pass",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this message");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deletePet();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deletePet() {
        // Only perform the delete if this is an existing pet.
        if (currentUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(currentUri, null, null);
        }
        getContentResolver().notifyChange(currentUri, null);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.subject_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                //insertPets();
                updateSubject();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                //if (!mPethasChanged) {
                  //  NavUtils.navigateUpFromSameTask(EditorActivity.this);
                   // return true;
                //}
                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(SubjectEdit.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
              //  showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] projection = {
                SubjectContract.SubjectEntry._id,
                SubjectContract.SubjectEntry.COLUMN_SUBJECT_NAME,
                SubjectContract.SubjectEntry.COLUMN_TOTAL_NO_OF_CLASSES,
                SubjectContract.SubjectEntry.COLUMN_MISSED_CLASSES,
                SubjectContract.SubjectEntry.COLUMN_EXTRA_CLASSES

        };
        return new CursorLoader(this, currentUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {




        if(cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.COLUMN_SUBJECT_NAME);
            int TClassColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.COLUMN_TOTAL_NO_OF_CLASSES);
            int EClassColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.COLUMN_EXTRA_CLASSES);
            int MClassColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.COLUMN_MISSED_CLASSES);
            //int ProfColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.COLUMN_PROF_NAME);

            String subject = cursor.getString(nameColumnIndex);
          //  String prof = cursor.getString(ProfColumnIndex);
            int TClass = cursor.getInt(TClassColumnIndex);
            int EClass = cursor.getInt(EClassColumnIndex);
            int MClass = cursor.getInt(MClassColumnIndex);
            Log.v("this",Integer.toString(TClassColumnIndex));
            setTitle(subject);
            Log.v("this","0000");
            mTotal_No_Of_Classes.setText(""+TClass);
            mExtra_Classes.setText(""+EClass);
            mMissed_Classes.setText(""+MClass);
            Log.v("this","0000");

              //   mProf_Name.setText(ProfColumnIndex);
        }


        //mCursorAdapter.swapCursor(cursor);
    }



    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mTotal_No_Of_Classes.setText("");
        mExtra_Classes.setText("");
        mMissed_Classes.setText("");
        mProf_Name.setText("");

    }
}
