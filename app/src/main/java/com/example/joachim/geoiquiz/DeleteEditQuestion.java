package com.example.joachim.geoiquiz;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import static java.lang.Integer.parseInt;

public class DeleteEditQuestion extends AppCompatActivity {
    private QuestionsDataSource database = new QuestionsDataSource(this);
    private ListView question_list;
    private List<Question> all_question;
    private int question_id;
    private EditText input_edit;
    private Spinner input_answer;
    private Button edit_button;
    private Button delete_button;

    String selected_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_question);

        all_question = database.getAllQuestions();
        question_id = getIntent().getIntExtra("question_id", 0);

        Cursor value = database.getQuestionByID(question_id);

        value.move(1);
        int id = value.getInt(0);
        String question_text = value.getString(1);
        String answer_text = value.getString(2);

        final Question question = new Question(id, question_text, answer_text);

        //preset the question before edit
        input_edit = (EditText) findViewById(R.id.input_edit_question);
        input_edit.setText(question_text);

        //preset the answer before edit.
        String[] answers = {"True", "False"};


        input_answer = (Spinner) findViewById(R.id.input_add_answer);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answers);
        input_answer.setAdapter(adapter2);

        input_answer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        selected_answer = "True";
                        break;
                    case 1:
                        selected_answer = "False";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //delete question function goes here
        delete_button = (Button) findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteQuestion(question.getId());
                Toast.makeText(DeleteEditQuestion.this,
                        R.string.success_delete,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeleteEditQuestion.this, ShowAllQuestion.class);
                startActivity(intent);
            }
        });

        //edit question function goes here
        edit_button = (Button) findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String edit_question = input_edit.getText().toString();

                if(edit_question.equals("")){
                    Toast.makeText(DeleteEditQuestion.this,
                            "Please enter something!",
                            Toast.LENGTH_SHORT).show();
                }else{
                    database.updateQuestion(question.getId(),edit_question,selected_answer );

                    Toast.makeText(DeleteEditQuestion.this,
                            R.string.success_edit,
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DeleteEditQuestion.this, ShowAllQuestion.class);
                    startActivity(intent);
                }

            }
        });

    }



}
