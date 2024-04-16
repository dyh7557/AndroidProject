package com.example.project;

import static com.example.project.DBOpenHelper.MESSAGE;
import static com.example.project.DBOpenHelper.NAME;
import static com.example.project.DBOpenHelper.PHONE;
import static com.example.project.DBOpenHelper.TABLE_NAME;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class UpdateActivity extends AppCompatActivity {

    private static final String DB_NAME = "AddressDB";
    private static final int DB_VERSION = 1;
    private DBOpenHelper openHelper;
    private Toast toast;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        time = intent.getStringExtra("time");

        openHelper = new DBOpenHelper(this, DB_NAME, null, DB_VERSION);
        Cursor cursor = SearchDB(time);

        EditText editTextName = (EditText)findViewById(R.id.editTextName);
        EditText editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        EditText editTextMessage = (EditText)findViewById(R.id.editTextMessage);

        while(cursor.moveToNext()) {
            editTextName.setText(cursor.getString(1));
            editTextPhone.setText(cursor.getString(2));
            editTextMessage.setText(cursor.getString(3));
        }

        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, SettingActivity.class);
                intent.putExtra("time", time);
                finish();
                startActivity(intent);
            }
        });

        Button submitButton = (Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().length() > 8) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast.makeText(UpdateActivity.this, "이름은 8자 이내로 써주세요", Toast.LENGTH_SHORT).show();
                }

                else if(editTextMessage.getText().length() > 30) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast.makeText(UpdateActivity.this, "메세지는 30자 이내로 해주세요", Toast.LENGTH_SHORT).show();
                }

                else if(editTextName.getText().length() == 0 || editTextPhone.getText().length() == 0 || editTextMessage.getText().length() == 0) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast.makeText(UpdateActivity.this, "모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                else {
                    SQLiteDatabase db = openHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(NAME, editTextName.getText().toString());
                    contentValues.put(PHONE, editTextPhone.getText().toString());
                    contentValues.put(MESSAGE, editTextMessage.getText().toString());

                    db.update(TABLE_NAME, contentValues, "time = '" + time + "'", null);
                    db.close();
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast.makeText(UpdateActivity.this, "데이터 수정 완료", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);

                }


            }
        });
    }

    private Cursor SearchDB(String time) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "time = '" + time + "'", null, null, null, null);
        startManagingCursor(cursor);
        return cursor;
    }
}