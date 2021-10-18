package com.example.contentproviders1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void onClickAddDetails(View view) {
        //Class to add values in teh database
        ContentValues values = new ContentValues();

        //Fetching text from user
        values.put(MyContentProvider.name, ((EditText) findViewById(R.id.name_edit_tv)).getText().toString());

        //inserting into database through content URI
        getContentResolver().insert(MyContentProvider.CONTENT_URI, values);

        //displaying a toast message
        Toast.makeText(getBaseContext(), "New Record inserted", Toast.LENGTH_LONG).show();
    }

    @SuppressLint("Range")
    public void onClickShowDetails(View view) {
        //Inserting complete table details in this test field
        TextView resultTv = (TextView) findViewById(R.id.res_tv);

        //Creating a cursor object of the content URI
        Cursor cursor = getContentResolver().query(Uri.parse("conent://com.demo.user.provider/users"), null, null, null, null);

        //Iterations of the cursor to print the whole table
        //FIXME: Check if the db is empty first
        if (cursor.moveToFirst()) {
            StringBuilder stringBuilder = new StringBuilder();
            while ((!cursor.isAfterLast())) {
                stringBuilder.append("\n" + cursor.getString(cursor.getColumnIndex("id")) + "-" + cursor.getString(cursor.getColumnIndex("name")));
                cursor.moveToNext();
            }
            resultTv.setText(stringBuilder);
        } else {
            resultTv.setText("No Records Found");
        }
    }
}