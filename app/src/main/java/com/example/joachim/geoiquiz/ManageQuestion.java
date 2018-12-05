package com.example.joachim.geoiquiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ManageQuestion extends AppCompatActivity {
    private EditText input_password;
    private Button submit_password_button;
    private Button add_button;
    private Button delete_button;
    private LinearLayout action_button;
    private String password = "123123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_question);

        input_password = (EditText)findViewById(R.id.input_password);
        action_button = (LinearLayout) findViewById(R.id.action_button);



        submit_password_button = (Button) findViewById(R.id.submit_password);
        submit_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();
            }
        });


        add_button = (Button) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ManageQuestion.this, AddQuestion.class);
                startActivity(intent);
            }
        });


        delete_button = (Button) findViewById(R.id.delete);
        delete_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ManageQuestion.this, ShowAllQuestion.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ManageQuestion.this, QuizActivity.class);
        startActivity(intent);
    }

    private void checkPassword(){
        String passwordToCheck = input_password.getText().toString();

        if(passwordToCheck.equals(password)){
            Toast.makeText(ManageQuestion.this,
                    R.string.password_correct,
                    Toast.LENGTH_SHORT).show();
            action_button.setVisibility(View.VISIBLE);

        }
        else{
            Toast.makeText(ManageQuestion.this,
                            R.string.password_incorrect,
                            Toast.LENGTH_SHORT).show();
        }
    }
}
