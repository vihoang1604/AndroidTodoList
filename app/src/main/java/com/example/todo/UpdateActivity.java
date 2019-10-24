package com.example.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    AppDatabase db;
    EditText edtTitle;
    EditText edtDate;
    Button btnUpdate;
    int todoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        edtTitle = findViewById(R.id.edit_title);
        edtDate = findViewById(R.id.edit_date);
        btnUpdate = findViewById(R.id.button_update);

        int id = getIntent().getIntExtra("id", 0);
        todoId = id;
        String title = getIntent().getStringExtra("title");
        String date = getIntent().getStringExtra("date");

        edtTitle.setText(title);
        edtDate.setText(date);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTodoToDatabase();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void updateTodoToDatabase() {
        final String title = edtTitle.getText().toString();
        final String date = edtDate.getText().toString();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Todo newTodo = new Todo(title, date);
                newTodo.setId(todoId); // thinking about why we need to set id here
                db.todoDao().update(newTodo);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                showSuccessDialog();
            }
        }.execute();
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage("Update Success")
                .setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }
}
