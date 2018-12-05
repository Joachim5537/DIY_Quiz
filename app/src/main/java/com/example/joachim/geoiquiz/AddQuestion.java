package com.example.joachim.geoiquiz;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AddQuestion extends AppCompatActivity {

    QuestionsDataSource myDb;
    private EditText input_question;
    private Spinner input_answer;
    private Button add_button;
    private Button view_all;


    String answers[] = {"True", "False"};
    String selected_answer;
    String question;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);
        myDb = new QuestionsDataSource(this);


        //Setting answer drop down list
        input_answer = (Spinner)findViewById(R.id.input_add_answer);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,answers);
        input_answer.setAdapter(adapter);

        input_answer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        selected_answer ="True";
                        break;
                    case 1:
                        selected_answer="False";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Add question
        input_question = (EditText)findViewById(R.id.input_add_question);


        //Add button
        add_button = (Button)findViewById(R.id.add_button);
        addQuestion();

        //View all Question Button
        view_all = (Button)findViewById(R.id.view_question);
        getAllData();

        }

    //add question function, when summon this function, get the user input question and also answer, add in to database.
    public void addQuestion(){
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                question = input_question.getText().toString();
                if( question.equals("")){
                    Toast.makeText(AddQuestion.this,"Please enter something!",Toast.LENGTH_LONG).show();
                }
                else{
                    myDb.createQuestion(question,selected_answer);
                    Toast.makeText(AddQuestion.this,R.string.success_add,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddQuestion.this, QuizActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //when user press back, it will navigate to the main page.
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AddQuestion.this, QuizActivity.class);
        startActivity(intent);
    }

    //get all data function, get all the data from database, and show it out.
    public void getAllData(){
        view_all.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String all_question = myDb.getAllQuestionsToDisplay();

                showAllQuestion("All Question",all_question.toString());
            }
        });
    }

    //alert dialog function, pass in name and message and set dialog box.
    public void showAllQuestion(String name, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(name);
        builder.setMessage(Message);
        builder.show();
    }

}
