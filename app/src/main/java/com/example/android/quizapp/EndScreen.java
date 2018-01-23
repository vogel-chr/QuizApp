package com.example.android.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        Intent nextQuestion = getIntent();
        int failed_attempts = nextQuestion.getIntExtra("overall_failed_attempts", 0);

        TextView quantityTextView = (TextView) findViewById(R.id.failed_attempts_text_view);
        quantityTextView.setText(getString(R.string.summary) + failed_attempts + getString(R.string.mistakes));
    }

    public void question_me_again (View view) {
        Intent questionMeAgain = new Intent(EndScreen.this, QuestionOne.class);
        startActivity(questionMeAgain);
    }
}
