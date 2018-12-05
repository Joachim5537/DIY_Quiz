package com.example.joachim.geoiquiz;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ShowAllQuestion extends AppCompatActivity {
    private QuestionsDataSource database = new QuestionsDataSource(this);
    private ListView question_list;
    private List<Question> all_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_question);

        all_question = database.getAllQuestions();
        Question[] list = new Question[all_question.size()];
        int counter = 0;

        for(int i =0; i<all_question.size(); i++){
            int id = all_question.get(counter).getId();
            String question = all_question.get(counter).getQuestion();
            String answer = all_question.get(counter).getAnswer();
            list[counter] = new Question(id,question,answer);
            counter ++;
        }


        question_list = (ListView) findViewById(R.id.question_list);
        ArrayAdapter<Question> itemsAdapter = new ArrayAdapter<Question>(this, android.R.layout.simple_list_item_1, list);
        question_list.setAdapter(itemsAdapter);

        question_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String val = parent.getItemAtPosition(position).toString();
//                Cursor cursor = database.getIdByQuestion(val);
                int question_id = -1;

                Cursor data = database.getIdByQuestion(val);
                while(data.moveToNext()){
                    question_id = data.getInt(0);
                }

                Intent intent = new Intent(ShowAllQuestion.this, DeleteEditQuestion.class);
                intent.putExtra("question_id",question_id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ShowAllQuestion.this, QuizActivity.class);
        startActivity(intent);
    }

}
