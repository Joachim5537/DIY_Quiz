package com.example.joachim.geoiquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_QUESTIONS = "questions";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_ANSWER = "answer";

    private static final String DATABASE_NAME="question.db";
    private static final int DATABASE_VERSION=1;

    //Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_QUESTIONS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_QUESTION
            + " text not null, " +COLUMN_ANSWER
            + " text not null) ";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL("create table questions (id INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT, answer TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version" + oldVersion + " to" + newVersion+ ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_QUESTIONS);
        onCreate(db);
    }



    }



