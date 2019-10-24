package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTodoActivity extends AppCompatActivity {

    AppDatabase db;
    EditText edtTitle;
    EditText edtDate;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        edtTitle = findViewById(R.id.edit_title);
        edtDate = findViewById(R.id.edit_date);
        btnAdd = findViewById(R.id.button_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodoToDatabase();
                finish();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void addTodoToDatabase() {
        final String title = edtTitle.getText().toString();
        final String date = edtDate.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this, "Title must not null", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Todo newTodo = new Todo(title, date);
                db.todoDao().insert(newTodo);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(AddTodoActivity.this, "New todo added", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}
