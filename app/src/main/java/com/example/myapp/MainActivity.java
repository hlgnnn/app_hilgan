package com.example.myapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText nameEditText;
    private EditText cityEditText;
    private ListView listView;
    private PersonAdapter personAdapter;
    private ArrayList<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        nameEditText = findViewById(R.id.nameEditText);
        cityEditText = findViewById(R.id.cityEditText);
        listView = findViewById(R.id.listView);
        Button addButton = findViewById(R.id.addButton);

        personList = new ArrayList<>();
        personAdapter = new PersonAdapter(this, personList);
        listView.setAdapter(personAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPerson();
            }
        });

        loadPeople();
    }

    private void addPerson() {
        String name = nameEditText.getText().toString();
        String city = cityEditText.getText().toString();

        if (!name.isEmpty() && !city.isEmpty()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_NAME, name);
            values.put(DatabaseHelper.COLUMN_CITY, city);
            db.insert(DatabaseHelper.TABLE_NAME, null, values);
            db.close();

            nameEditText.setText("");
            cityEditText.setText("");
            loadPeople();
        }
    }

    private void loadPeople() {
        personList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                String city = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CITY));
                Person person = new Person(id, name, city);
                personList.add(person);
            } while (cursor.moveToNext());

            cursor.close();
        }

        personAdapter.notifyDataSetChanged();
    }
}

