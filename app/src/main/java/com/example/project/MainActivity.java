package com.example.project;

import static com.example.project.DBOpenHelper.COLOR;
import static com.example.project.DBOpenHelper.MESSAGE;
import static com.example.project.DBOpenHelper.NAME;
import static com.example.project.DBOpenHelper.PHONE;
import static com.example.project.DBOpenHelper.TABLE_NAME;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AddressListView listView;
    private AddressAdapter adapter;
    private static final String DB_NAME = "AddressDB";
    private static final int DB_VERSION = 1;
    private DBOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        Button findButton = (Button)findViewById(R.id.findButton);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FindActivity.class);
                startActivity(intent);
            }
        });

        openHelper = new DBOpenHelper(this, DB_NAME, null, DB_VERSION);
        listView = (AddressListView)findViewById(R.id.listView);

        adapter = new AddressAdapter(this);

        Cursor cursor = ReadDB();
        while(cursor.moveToNext()) {
            adapter.addItem(cursor.getString(0), cursor.getString(1), cursor.getString(3), cursor.getString(4));
        }

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        clickItem();

        openHelper.close();
    }

    private Cursor ReadDB() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        startManagingCursor(cursor);
        return cursor;
    }

    private void clickItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemData item = (ItemData) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);

                intent.putExtra("time", item.getTime());
                startActivity(intent);
            }
        });
    }
}