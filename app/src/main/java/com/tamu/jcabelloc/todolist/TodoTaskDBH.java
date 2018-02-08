package com.tamu.jcabelloc.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcabelloc on 2/7/2018.
 */

public class TodoTaskDBH extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "Todo";

    // Contacts table name
    private static final String TABLE_TODOTASK = "todoTask";

    // Contacts Table Columns names
    private static final String ID = "id";
    private static final String ID_GROUP = "idGroup";
    private static final String NAME = "name";
    private static final String STATUS = "status";

    public TodoTaskDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TODOTASK + " (" + ID + " INTEGER PRIMARY KEY" + ", " + ID_GROUP + " INTEGER" + ", " +  NAME + " TEXT" + ", " + STATUS + " INTEGER" + ")");
        //sqLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS todoTask (id INTEGER PRIMARY KEY, idGroup INTEGER, name TEXT, status INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOTASK);
        onCreate(sqLiteDatabase);
    }
    public int addTodoTask (TodoTask todoTask) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_GROUP, todoTask.getIdGroup());
        values.put(NAME, todoTask.getName());
        values.put(STATUS, todoTask.getStatus());
        int id = (int)sqLiteDatabase.insert(TABLE_TODOTASK, null, values);
        sqLiteDatabase.close();
        return id;
    }
    public TodoTask getTodotask(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cur = sqLiteDatabase.query(TABLE_TODOTASK,new String[]{ID, ID_GROUP, NAME, STATUS}, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cur != null) {
            cur.moveToFirst();
        }
        TodoTask todoTask = new TodoTask(cur.getInt(0), cur.getInt(1), cur.getString(2), cur.getInt(3));
        return todoTask;
    }
    public List<TodoTask> getAllTodoTask() {
        List<TodoTask> todoTaskList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cur = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TODOTASK, null);
        int idIndex = cur.getColumnIndex(ID);
        int idGroupIndex = cur.getColumnIndex(ID_GROUP);
        int nameIndex = cur.getColumnIndex(NAME);
        int statusIndex = cur.getColumnIndex(STATUS);
        if (cur.moveToFirst()) {
            do {
                TodoTask todoTask = new TodoTask();
                todoTask.setId(cur.getInt(idIndex));
                todoTask.setIdGroup(cur.getInt(idGroupIndex));
                todoTask.setName(cur.getString(nameIndex));
                todoTask.setStatus(cur.getInt(statusIndex));
                todoTaskList.add(todoTask);
            } while (cur.moveToNext());
        }
        return todoTaskList;
    }
    public List<TodoTask> getAllTodoTaskByIdGroup(int idGroup){
        List<TodoTask> todoTaskList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cur = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TODOTASK + " WHERE " + ID_GROUP + " = " + idGroup , null);
        int idIndex = cur.getColumnIndex(ID);
        int idGroupIndex = cur.getColumnIndex(ID_GROUP);
        int nameIndex = cur.getColumnIndex(NAME);
        int statusIndex = cur.getColumnIndex(STATUS);
        if (cur.moveToFirst()) {
            do {
                TodoTask todoTask = new TodoTask();
                todoTask.setId(cur.getInt(idIndex));
                todoTask.setIdGroup(cur.getInt(idGroupIndex));
                todoTask.setName(cur.getString(nameIndex));
                todoTask.setStatus(cur.getInt(statusIndex));
                todoTaskList.add(todoTask);
            } while (cur.moveToNext());
        }
        return todoTaskList;
    }
}
