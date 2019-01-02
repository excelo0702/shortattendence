package com.example.tanishyadav.shortattendence.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.tanishyadav.shortattendence.R;

public class ToDoCursorAdapter extends CursorAdapter {
    public ToDoCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.view,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView)view.findViewById(R.id.subject_name_for_display);
        TextView attendence = (TextView)view.findViewById(R.id.attendence);

        String Name = cursor.getString(cursor.getColumnIndexOrThrow(SubjectContract.SubjectEntry.COLUMN_SUBJECT_NAME));
        String T_Classes = cursor.getString(cursor.getColumnIndexOrThrow(SubjectContract.SubjectEntry.COLUMN_TOTAL_NO_OF_CLASSES));
        String M_Classes = cursor.getString(cursor.getColumnIndexOrThrow(SubjectContract.SubjectEntry.COLUMN_MISSED_CLASSES));
        String E_Classes = cursor.getString(cursor.getColumnIndexOrThrow(SubjectContract.SubjectEntry.COLUMN_EXTRA_CLASSES));
        int EClasses = Integer.parseInt(E_Classes);
        int TClasses = Integer.parseInt(T_Classes)+EClasses;
        int MClasses = Integer.parseInt(M_Classes);

        int AClasses = TClasses - MClasses;
        double Attendence = (AClasses*100)/TClasses;

        String Result = new Double(Attendence).toString();

        //String Breed = cursor.getString(cursor.getColumnIndexOrThrow(SubjectContract.SubjectEntry.COLUMN_TOTAL_NO_OF_CLASSES));
        name.setText(Name);
        attendence.setText(Result);

    }
}
