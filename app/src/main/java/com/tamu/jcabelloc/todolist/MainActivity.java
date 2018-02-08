package com.tamu.jcabelloc.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText todoGroupEditText;
    List<String> groupNames;
    List<Integer> groupId;
    ArrayAdapter adapter;
    ToDoGroupDBH db;

    public void addGroupName(View view) {
        //Log.i("input", groupNameText.getText().toString());

        int id = db.addTodoGroup(new TodoGroup(todoGroupEditText.getText().toString()));
        groupNames.add(todoGroupEditText.getText().toString());
        groupId.add(id);
        adapter.notifyDataSetChanged();
        todoGroupEditText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoGroupEditText = (EditText)findViewById(R.id.todoGroupEditText);
        ListView groupListView = (ListView)findViewById(R.id.groupListView);

        groupNames = new ArrayList<>();
        groupId = new ArrayList<>();
        db = new ToDoGroupDBH(this);
        db.onCreate(db.getWritableDatabase());
        List<TodoGroup> todoGroupList =  db.getAllTodoGroup();
        for (TodoGroup todoGroup: todoGroupList) {
            groupNames.add(todoGroup.getName());
            groupId.add(todoGroup.getId());
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groupNames);
        groupListView.setAdapter(adapter);
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.i("Text", groupNames.get(i).toString());
                Intent intent = new Intent(getApplicationContext(), Todo.class);
                intent.putExtra("id", String.valueOf(groupId.get(i)));
                startActivity(intent);

            }
        });


    }
}
