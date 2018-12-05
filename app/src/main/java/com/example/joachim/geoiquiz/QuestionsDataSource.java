package com.example.joachim.geoiquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.joachim.geoiquiz.MySQLiteHelper.*;

public class QuestionsDataSource  {
    //Database Fields
    //private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String TABLE_QUESTIONS = "questions";
    private String[] allColumns = {COLUMN_ID,
            COLUMN_QUESTION,
            COLUMN_ANSWER};

    public QuestionsDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
        //open();
    }

    public void open(){
        //database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }


    public Question createQuestion(String question, String answer){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();



        values.put(COLUMN_QUESTION, question);
        values.put(COLUMN_ANSWER, answer);


        long insertId = db.insert(TABLE_QUESTIONS,null,values);
        Cursor cursor = db.query(TABLE_QUESTIONS, allColumns, COLUMN_ID + " = " + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        Question newQuestion = cursorToQuestion(cursor);
        cursor.close();
        db.close();
        return newQuestion;
    }

    public void deleteQuestion(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_QUESTIONS+" WHERE id="+id);
        db.close();
    }

    public void updateQuestion(int id, String question, String answer){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("update "+TABLE_QUESTIONS+" set question='"+question+"', answer='"+answer+"' where id='"+id+"'");
        db.close();
    }

    public List<Question> getAllQuestions(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Question> questions = new ArrayList<Question>();

        Cursor cursor = db.query(TABLE_QUESTIONS,allColumns,null,null,null,null,null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Question question = cursorToQuestion(cursor);
            questions.add(question);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return questions;
    }

    public String getAllQuestionsToDisplay() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor value = db.rawQuery("select * from " + TABLE_QUESTIONS, null);

        StringBuffer all_data = new StringBuffer();
        while(value.moveToNext()){
            all_data.append("Id :"+value.getInt(0)+"\n");
            all_data.append("Question :"+value.getString(1)+"\n");
            all_data.append("Answer :"+value.getString(2)+"\n");
            all_data.append("\n");
        }

        return all_data.toString();
    }


    public Question cursorToQuestion(Cursor cursor){
        int id = cursor.getInt(0);
        String questionText = cursor.getString(1);
        String answer = cursor.getString(2);

        return new Question(id, questionText, answer);

    }

    public Cursor getIdByQuestion(String question){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "select * from "+TABLE_QUESTIONS+" where question = '"+question+"'";

        Cursor value = db.rawQuery(query,null);

        return value;
    }

    public Cursor getQuestionByID(int question_id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from "+TABLE_QUESTIONS+" where id = '"+question_id+"'";

        Cursor value = db.rawQuery(query,null);


        return value;
    }

}
