package com.example.tanishyadav.shortattendence.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class SubjectProvider extends ContentProvider {

    public static final String LOG_TAG = SubjectProvider.class.getSimpleName();

    private SubjectDbHelper mDbHelper;

    private static final int SUBJECTS = 100;
    private static final int SUBJECTS_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(SubjectContract.CONTENT_AUTHORITY,SubjectContract.PATH_SUBJECTS,SUBJECTS);

        sUriMatcher.addURI(SubjectContract.CONTENT_AUTHORITY,SubjectContract.PATH_SUBJECTS+"/#",SUBJECTS_ID);

    }
    @Override
    public boolean onCreate() {
        mDbHelper = new SubjectDbHelper(getContext());
        return true;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Log.v("this","petprovider1234567");
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Log.v("this","petprovider12345678");

        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match)
        {
            case SUBJECTS:
                cursor = database.query(SubjectContract.SubjectEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case SUBJECTS_ID:
                selection = SubjectContract.SubjectEntry._id+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(SubjectContract.SubjectEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Query is not supported"+uri);
                //
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        int c;
        switch (match)
        {
            case SUBJECTS:
                return insertSubject(uri,values);
            default:
                throw new IllegalArgumentException("Insertion is not available"+ uri);
        }
    }

    private Uri insertSubject(Uri uri,ContentValues values)
    {

        String name = values.getAsString(SubjectContract.SubjectEntry.COLUMN_SUBJECT_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }

        // Check that the gender is valid
        Integer TClasses = values.getAsInteger(SubjectContract.SubjectEntry.COLUMN_TOTAL_NO_OF_CLASSES);
        if (TClasses == null) {
            throw new IllegalArgumentException("Pet requires valid number of classes");
        }

        // If the weight is provided, check that it's greater than or equal to 0 kg
        Integer weight = values.getAsInteger(SubjectContract.SubjectEntry.COLUMN_MISSED_CLASSES);
        if (weight != null && weight < 0) {
            throw new IllegalArgumentException("Pet requires valid weight");
        }


        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(SubjectContract.SubjectEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1)
        {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);

    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        SQLiteDatabase db =mDbHelper.getWritableDatabase();
        int c;
        switch (match)
        {
            case SUBJECTS:
                return deletePet(uri,selection,selectionArgs);
            case SUBJECTS_ID:
                selection = SubjectContract.SubjectEntry._id+"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return deletePet(uri,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Delete");


        }

    }
    private int deletePet(Uri uri,String selection,String[] selectionArgs)
    {

    }



    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        switch (match)
        {
            case SUBJECTS:
                return updatePet(uri,values,selection,selectionArgs);
            case SUBJECTS_ID:
                selection = SubjectContract.SubjectEntry._id+"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri,values,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Update");

        }
    }
    private int updatePet(Uri uri,ContentValues values,String selection,String[] selectionArgs)
    {
        if(values.size()==0)
            return 0;

        SQLiteDatabase db =mDbHelper.getWritableDatabase();

        int rowsUpdated = db.update(SubjectContract.SubjectEntry.TABLE_NAME,values,selection,selectionArgs);
        if(rowsUpdated!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }


}
