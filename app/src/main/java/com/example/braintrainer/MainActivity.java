package com.example.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity {

    TextView timeRemaining;
    TextView question;
    TextView scoreView;
    TextView result;
    int seconds = 30;
    CountDownTimer time;
    LinearLayout gameOver;
    int a;
    int b;
    int answer;
    int questionsAsked = 0;
    int questionsAnswered = 0;
    String[] myTags = {"a","b","c","d"};


    public  void setClickable(boolean ans){
        TextView a = findViewById(R.id.a);
        TextView b = findViewById(R.id.b);
        TextView c = findViewById(R.id.c);
        TextView d = findViewById(R.id.d);
        a.setClickable(ans);
        b.setClickable(ans);
        c.setClickable(ans);
        d.setClickable(ans);
    }
    public void ask(){
        a = (int)(Math.random()*101);
        b = (int)(Math.random()*101);
        answer = a + b;

        question = findViewById(R.id.question);
        question.setText(a + " + " + b);

        int index = (int)(Math.random()* 4);
        TextView choice = findViewById(android.R.id.content).findViewWithTag(myTags[index]);
        choice.setText(String.valueOf(answer));

        for(int i = 0; i < 4; i++){
            if(i == index){
                continue;
            }

            TextView assign = findViewById(android.R.id.content).findViewWithTag(myTags[i]);
            assign.setText(String.valueOf((int)(Math.random()*101)));
        }


    }

    public void choice(View view){
        TextView wrong = findViewById(R.id.wrong);
        TextView text = (TextView) view;
        Log.i("answer: ", text.getText() + " and " + String.valueOf(answer));
        if(Integer.parseInt(text.getText().toString()) == answer)
        {
            Log.i("answer: ", "correct");
            questionsAnswered += 1;
            wrong.setText("Correct");
            wrong.setVisibility(View.VISIBLE);

        }
        else{
            wrong.setText("Incorrect");
            wrong.setVisibility(View.VISIBLE);
        }
        questionsAsked += 1;
         scoreView = findViewById(R.id.score);
        scoreView.setText(String.format("%02d/%02d",questionsAnswered,questionsAsked));
        ask();

    }
    public void exitGame(View view){
        finishAffinity();
        System.exit(0);
    }

    public void startGame(View view){
        Button myBtn = findViewById(R.id.myBtn);
        myBtn.setVisibility(View.INVISIBLE);
        gameOver = findViewById(R.id.gameOver);
        gameOver.setVisibility(View.INVISIBLE);
        timeRemaining = findViewById(R.id.timeRemaining);
        result = findViewById(R.id.result);
        TextView wrong = findViewById(R.id.wrong);
        wrong.setVisibility(View.INVISIBLE);
        ask();
        setClickable(true);
        time = new CountDownTimer((seconds * 1000)+20, 1000) {
            @Override
            public void onTick(long l) {
                seconds = (int) l / 1000;
                timeRemaining.setText(seconds + "S");

            }

            @Override
            public void onFinish() {
                seconds = 30;
                timeRemaining.setText("30S");
                result.setText("Score " + questionsAnswered + "/" + questionsAsked);
                questionsAsked = 0;
                questionsAnswered = 0;
                scoreView = findViewById(R.id.score);
                scoreView.setText("0/0");
                TextView wrong = findViewById(R.id.wrong);
                wrong.setVisibility(View.INVISIBLE);
                setClickable(false);

                gameOver = findViewById(R.id.gameOver);

                gameOver.setVisibility(View.VISIBLE);


            }
        }.start();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setClickable(false);



    }
}