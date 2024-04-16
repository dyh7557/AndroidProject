package com.example.project;

import static com.example.project.DBOpenHelper.TABLE_NAME;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class FindActivity extends AppCompatActivity {

    private AddressListView listView;
    private AddressAdapter adapter;
    private static final String DB_NAME = "AddressDB";
    private static final int DB_VERSION = 1;
    private DBOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        EditText editText = (EditText)findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = editText.getText().toString();
                filterWord(str);
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    private void filterWord(String str) {
        adapter.clearItem();

        Cursor cursor = SearchDB(str);
        while(cursor.moveToNext()) {
            adapter.addItem(cursor.getString(0), cursor.getString(1), cursor.getString(3), cursor.getString(4));
        }

        adapter.notifyDataSetChanged();
    }

    private Cursor ReadDB() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        startManagingCursor(cursor);
        return cursor;
    }

    private Cursor SearchDB(String str) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "name LIKE '%"+str+"%'", null, null, null, null);
        startManagingCursor(cursor);
        return cursor;
    }

    private void clickItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemData item = (ItemData) parent.getItemAtPosition(position);
                Intent intent = new Intent(FindActivity.this, SettingActivity.class);

                intent.putExtra("time", item.getTime());
                startActivity(intent);
                finish();
            }
        });
    }
}