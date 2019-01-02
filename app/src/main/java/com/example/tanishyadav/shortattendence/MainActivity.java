package com.example.tanishyadav.shortattendence;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tanishyadav.shortattendence.data.SubjectContract;
import com.example.tanishyadav.shortattendence.data.SubjectDbHelper;
import com.example.tanishyadav.shortattendence.data.ToDoCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{



    private Uri currentUri;
    private SubjectDbHelper mDbHelper;
    private static final int SUBJECT_LOADER =0;
    ToDoCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView add = (ImageView)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SubjectInfo.class);
                startActivity(intent);
            }
        });
        Log.v("this","112");

        mDbHelper = new SubjectDbHelper(this);

        ListView subjectview = (ListView)findViewById(R.id.list_item);
        View Emptyview = findViewById(R.id.empty_view);
        subjectview.setEmptyView(Emptyview);

        subjectview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, SubjectEdit.class);
                Uri currentUri = ContentUris.withAppendedId(SubjectContract.SubjectEntry.CONTENT_URI,id);
                intent.setData(currentUri);
                startActivity(intent);
            }
        });

        mCursorAdapter = new ToDoCursorAdapter(this,null);
        subjectview.setAdapter(mCursorAdapter);
        getSupportLoaderManager().initLoader(SUBJECT_LOADER,null,this);

    }

    @Override
    protected void onStart(){
        super.onStart();

        //displayDatabaseInfo();
        Toast.makeText(this,"toast",Toast.LENGTH_SHORT).show();

    }

    private void insertSubjects(){
        Log.v("this","112");
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubjectContract.SubjectEntry.COLUMN_SUBJECT_NAME,"Subject");
        values.put(SubjectContract.SubjectEntry.COLUMN_TOTAL_NO_OF_CLASSES,"30");
        values.put(SubjectContract.SubjectEntry.COLUMN_EXTRA_CLASSES,"0");
        values.put(SubjectContract.SubjectEntry.COLUMN_MISSED_CLASSES,"2");
        values.put(SubjectContract.SubjectEntry.COLUMN_PROF_NAME,"fuck");

        Uri newUri = getContentResolver().insert(SubjectContract.SubjectEntry.CONTENT_URI,values);
        //long newRowId = db.insert(SubjectContract.SubjectEntry.TABLE_NAME,null,values);

    }

    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(SubjectContract.SubjectEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_activity,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                insertSubjects();

                //displayDatabaseInfo();
                return true;

            case R.id.action_delete_all_entries:
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
        return new CursorLoader(this, SubjectContract.SubjectEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }



    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mCursorAdapter.swapCursor(null);

    }
}
