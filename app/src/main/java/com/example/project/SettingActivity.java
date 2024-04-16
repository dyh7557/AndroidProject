package com.example.project;

import static com.example.project.DBOpenHelper.TABLE_NAME;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    private String time;
    private static final String DB_NAME = "AddressDB";
    private static final int DB_VERSION = 1;
    private DBOpenHelper openHelper;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        TextView textFirstName = (TextView)findViewById(R.id.textFirstName);
        TextView textViewName = (TextView)findViewById(R.id.textViewName);
        TextView textViewMessage = (TextView)findViewById(R.id.textViewMessage);
        TextView textViewPhone = (TextView)findViewById(R.id.textViewPhone);

        Intent intent = getIntent();
        time = intent.getStringExtra("time");

        openHelper = new DBOpenHelper(this, DB_NAME, null, DB_VERSION);
        Cursor cursor= SearchDB(time);

        while(cursor.moveToNext()) {
            textFirstName.setText(cursor.getString(1).substring(0, 1));
            textViewName.setText(cursor.getString(1));
            textViewPhone.setText(cursor.getString(2));
            textViewMessage.setText(cursor.getString(3));
            imageView.setBackgroundColor(Color.parseColor(cursor.getString(4)));
        }

        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        Button updateButton = (Button)findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UpdateActivity.class);
                intent.putExtra("time", time);
                finish();
                startActivity(intent);
            }
        });

        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog = new Dialog(SettingActivity.this);
                dialog.setContentView(R.layout.delete_check);
                dialog.show();

                Button noButton = dialog.findViewById(R.id.noButton);
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button yesButton = dialog.findViewById(R.id.yesButton);
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = openHelper.getReadableDatabase();
                        db.delete(TABLE_NAME, "time = '" + time + "'", null);
                        db.close();
                        dialog.dismiss();
                        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });

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