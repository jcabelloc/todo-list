package com.tamu.jcabelloc.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcabelloc on 2/7/2018.
 */

public class ToDoGroupDBH extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "Todo";

    // Contacts table name
    private static final String TABLE_TODOGROUP = "todoGroup";

    // Contacts Table Columns names
    private static final String ID = "id";
    private static final String NAME = "name";

    public ToDoGroupDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TODOGROUP + "(" + ID + " INTEGER PRIMARY KEY" + ", " + NAME + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOGROUP);
        onCreate(sqLiteDatabase);
    }
    public int addTodoGroup (TodoGroup todoGroup) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(ID, todoGroup.getId());
        values.put(NAME, todoGroup.getName());
        int id = (int)sqLiteDatabase.insert(TABLE_TODOGROUP, null, values);
        sqLiteDatabase.close();
        return id;

    }
    public TodoGroup getTodoGroup(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cur = sqLiteDatabase.query(TABLE_TODOGROUP,new String[]{ID, NAME}, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cur != null) {
            cur.moveToFirst();
        }
        TodoGroup todoGroup = new TodoGroup(cur.getInt(0), cur.getString(1));
        return todoGroup;
    }
    public List<TodoGroup> getAllTodoGroup() {
        List<TodoGroup> todoGroupList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cur = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TODOGROUP, null);
        int idIndex = cur.getColumnIndex(ID);
        int nameIndex = cur.getColumnIndex(NAME);
        if (cur.moveToFirst()) {
            do {
                TodoGroup todoGroup = new TodoGroup();
                todoGroup.setId(cur.getInt(idIndex));
                todoGroup.setName(cur.getString(nameIndex));
                todoGroupList.add(todoGroup);
            } while (cur.moveToNext());
        }
        return todoGroupList;
    }
}
