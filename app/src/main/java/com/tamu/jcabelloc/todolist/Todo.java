package com.tamu.jcabelloc.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Todo extends AppCompatActivity {

    EditText todoTaskEditText;
    List<String> todoTaskNames;
    ArrayAdapter adapter;
    TodoTaskDBH db;
    int idGroup;
    public void addTodoTask(View view) {
        todoTaskNames.add(todoTaskEditText.getText().toString());
        int i = db.addTodoTask(new TodoTask(idGroup,todoTaskEditText.getText().toString(), 0));
        Log.i("Record inserted", String.valueOf(i));
        adapter.notifyDataSetChanged();
        todoTaskEditText.setText("");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Intent intent = getIntent();
        idGroup = Integer.valueOf(intent.getStringExtra("id"));
        //Log.i("id", intent.getStringExtra("id"));

        todoTaskEditText = (EditText)findViewById(R.id.todoTaskEditText);


        ListView todoTaskListView = (ListView) findViewById(R.id.todoTaskListView);
        todoTaskNames = new ArrayList<>();
        db = new TodoTaskDBH(this);
        //db.onUpgrade(db.getWritableDatabase(), 0, 0);
        db.onCreate(db.getWritableDatabase());
        List<TodoTask> todoTasks = db.getAllTodoTaskByIdGroup(idGroup);
        for (TodoTask todoTask:todoTasks){
            todoTaskNames.add(todoTask.getName());
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, todoTaskNames);
        todoTaskListView.setAdapter(adapter);
    }
}
