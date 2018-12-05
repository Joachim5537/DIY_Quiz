package com.example.joachim.geoiquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.joachim.geoiquiz.MySQLiteHelper.*;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mManageQuestion;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private List<Question> mQuestionsBank;
    private QuestionsDataSource mQuestionsDataSource = new QuestionsDataSource(this);
    private int mCurrentIndex = 0;



    //update question function, get current index, and display it based on index.
    private void updateQuestion(){
        String question = mQuestionsBank.get(mCurrentIndex).getQuestion();
        mQuestionTextView.setText(question);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        mQuestionsBank = mQuestionsDataSource.getAllQuestions();
        //getting question form database.
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        //String question = mQuestions[0].getQuestion();
        String question = mQuestionsBank.get(mCurrentIndex).getQuestion();
        mQuestionTextView.setText(question);


        //setting the true button
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("true");
            }
        });

        //setting the false button
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 checkAnswer("false");
            }
        });

        //setting the manage question button
        mManageQuestion = (Button) findViewById(R.id.manage_question);
        mManageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent, navigate the page to manage question activity;
                Intent intent = new Intent(QuizActivity.this, ManageQuestion.class);
                startActivity(intent);
            }
        });

        //setting the next button
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //every time user click next button, the current index will +1, and will go to next question.
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.size();
                updateQuestion();
            }
        });

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }

    //check answer function, pass in the user input, check whether it is correct or not.
    private void checkAnswer(String answerToCheck){
        String answerToCheck2 = answerToCheck.toLowerCase();
        String correctAnswer = mQuestionsBank.get(mCurrentIndex).getAnswer().toLowerCase();


        if(answerToCheck2.equals(correctAnswer)){
            Toast.makeText(QuizActivity.this,
                R.string.correct_toast,
                Toast.LENGTH_SHORT).show();
    }
        else{
            Toast.makeText(QuizActivity.this,
                    R.string.incorrect_toast,
                    Toast.LENGTH_SHORT).show();
        }


    }




}