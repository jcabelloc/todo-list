package com.tamu.jcabelloc.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Todo extends AppCompatActivity {

    EditText todoTaskEditText;
    List<String> todoTaskNames;
    List<Integer> todoTaskIds;
    List<String> doneTaskNames;
    List<Integer> doneTaskIds;
    ArrayAdapter todoAdapter;
    ArrayAdapter doneAdapter;
    TodoTaskDBH db;
    int idGroup;
    public void addTodoTask(View view) {
        int i = db.addTodoTask(new TodoTask(idGroup,todoTaskEditText.getText().toString(), 0));
        todoTaskNames.add(todoTaskEditText.getText().toString());
        todoTaskIds.add(i);
        Log.i("Record inserted", String.valueOf(i));
        todoAdapter.notifyDataSetChanged();
        todoTaskEditText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Intent intent = getIntent();
        idGroup = Integer.valueOf(intent.getStringExtra("id"));
        todoTaskEditText = (EditText)findViewById(R.id.todoTaskEditText);
        final ListView todoTaskListView = (ListView) findViewById(R.id.todoTaskListView);
        final ListView doneTaskListView = (ListView) findViewById(R.id.doneTaskLisView);

        todoTaskListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        doneTaskListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        todoTaskNames = new ArrayList<>();
        todoTaskIds = new ArrayList<>();
        doneTaskNames = new ArrayList<>();
        doneTaskIds = new ArrayList<>();

        db = new TodoTaskDBH(this);
        //db.onUpgrade(db.getWritableDatabase(), 0, 0);
        db.onCreate(db.getWritableDatabase());
        final List<TodoTask> todoTasks = db.getAllTodoTaskByIdGroupStatus(idGroup, 0);
        List<TodoTask> doneTasks = db.getAllTodoTaskByIdGroupStatus(idGroup, 1);
        for (TodoTask todoTask:todoTasks){
            todoTaskNames.add(todoTask.getName());
            todoTaskIds.add(todoTask.getId());
        }
        for (TodoTask doneTask:doneTasks){
            doneTaskNames.add(doneTask.getName());
            doneTaskIds.add(doneTask.getId());
        }
        todoAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, todoTaskNames);
        doneAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, doneTaskNames);
        todoTaskListView.setAdapter(todoAdapter);
        doneTaskListView.setAdapter(doneAdapter);

        for (int i =0; i < doneTaskIds.size(); i++) {
            doneTaskListView.setItemChecked(i, true);
        }

        todoTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                todoTaskListView.setItemChecked(i, false);
                int recordsUpdated = db.updateTodoTask(new TodoTask(todoTaskIds.get(i), idGroup, todoTaskNames.get(i), 1));
                if (recordsUpdated == 1) {
                    doneTaskNames.add(todoTaskNames.get(i));
                    doneTaskIds.add(todoTaskIds.get(i));
                    todoTaskNames.remove(i);
                    todoTaskIds.remove(i);
                    todoAdapter.notifyDataSetChanged();
                    doneAdapter.notifyDataSetChanged();
                    doneTaskListView.setItemChecked(doneTaskIds.size() - 1, true);
                }
            }
        });
        todoTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemIndex = i;
                new AlertDialog.Builder(Todo.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Sure to Delete?")
                        .setMessage("Do you want to delete that task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(MainActivity.this, "Its done!", Toast.LENGTH_LONG).show();
                                int deletedRows = db.deleteTodoTask(todoTaskIds.get(itemIndex));
                                if (deletedRows == 1) {
                                    todoTaskNames.remove(itemIndex);
                                    todoTaskIds.remove(itemIndex);
                                    todoAdapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });
        doneTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                doneTaskListView.setItemChecked(i, true);
                int recordsUpdated = db.updateTodoTask(new TodoTask(doneTaskIds.get(i), idGroup, doneTaskNames.get(i), 0));
                if (recordsUpdated == 1) {
                    todoTaskNames.add(doneTaskNames.get(i));
                    todoTaskIds.add(doneTaskIds.get(i));
                    doneTaskNames.remove(i);
                    doneTaskIds.remove(i);
                    todoAdapter.notifyDataSetChanged();
                    doneAdapter.notifyDataSetChanged();

                }

            }
        });
    }
}
