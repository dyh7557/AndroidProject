package com.example.project;

import static com.example.project.DBOpenHelper.COLOR;
import static com.example.project.DBOpenHelper.MESSAGE;
import static com.example.project.DBOpenHelper.NAME;
import static com.example.project.DBOpenHelper.PHONE;
import static com.example.project.DBOpenHelper.TABLE_NAME;
import static com.example.project.DBOpenHelper.TIME;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;
import java.lang.Integer;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    private static final String DB_NAME = "AddressDB";
    private static final int DB_VERSION = 1;
    private DBOpenHelper openHelper;
    private Toast toast;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        openHelper = new DBOpenHelper(this, DB_NAME, null, DB_VERSION);

        EditText editTextName = (EditText)findViewById(R.id.editTextName);
        EditText editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        EditText editTextMessage = (EditText)findViewById(R.id.editTextMessage);
        Button buttonSubmit = (Button)findViewById(R.id.submitButton);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().length() > 8) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast.makeText(EditActivity.this, "이름은 8자 이내로 써주세요", Toast.LENGTH_SHORT).show();
                }
                
                else if(editTextMessage.getText().length() > 30) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast.makeText(EditActivity.this, "메세지는 30자 이내로 해주세요", Toast.LENGTH_SHORT).show();
                }

                else if(editTextName.getText().length() == 0 || editTextPhone.getText().length() == 0 || editTextMessage.getText().length() == 0) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast.makeText(EditActivity.this, "모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                else{
                    Random random = new Random();
                    String r = Integer.toHexString(random.nextInt(235) + 17);
                    String g = Integer.toHexString(random.nextInt(235) + 17);
                    String b = Integer.toHexString(random.nextInt(235) + 17);
                    StringBuilder builder = new StringBuilder("#");
                    builder.append(r).append(g).append(b);

                    writeDB(editTextName.getText().toString(), editTextPhone.getText().toString(), editTextMessage.getText().toString(), builder.toString());

                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);


                }


            }
        });

        Button buttonBack = (Button)findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    private void writeDB(String name, String phone, String message, String color) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        time = System.currentTimeMillis();
        String timeStr = Long.toString(time);
        values.put(TIME, timeStr);
        values.put(NAME, name);
        values.put(PHONE, phone);
        values.put(COLOR, color);
        values.put(MESSAGE, message);
        if (toast != null) {
            toast.cancel();
        }

        db.insertOrThrow(TABLE_NAME, null, values);
    }
}