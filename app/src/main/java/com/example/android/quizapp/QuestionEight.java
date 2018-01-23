package com.example.android.quizapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

public class QuestionEight extends AppCompatActivity {

    int failed_attempts;

    /* Variables for answer checkBoxes. */
    boolean q8a1; /* Wrong answer. */
    boolean q8a2;
    boolean q8a3;
    boolean q8a4;

    /* Variable for checking, if user proceeds to next question or will be redirected. */
    boolean correctChoiceQuestion8;

    /* Variable for toast message in case user picked wrong question(s). */
    String toastWrongAnswerQuestion8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_eight);
        Intent nextQuestion = getIntent();
        failed_attempts = nextQuestion.getIntExtra("overall_failed_attempts", 0);

        if (savedInstanceState != null) {
            failed_attempts = savedInstanceState.getInt("FailedAttempts");
            q8a1 = savedInstanceState.getBoolean("Q8A1");
            q8a2 = savedInstanceState.getBoolean("Q8A2");
            q8a3 = savedInstanceState.getBoolean("Q8A3");
            q8a4 = savedInstanceState.getBoolean("Q8A4");
            correctChoiceQuestion8 = savedInstanceState.getBoolean("CorrectChoiceQuestion8");
            toastWrongAnswerQuestion8 = savedInstanceState.getString("ToastAnswerQuestion8");
        }
    }

    /**
     * User clicks this button after picking her/his answers.
     * The method checks, if answers are correct or wrong.
     * Result will be shown via toast.
     * If all answers are correct, app proceeds to next question.
     * If one or more answers are wrong, user has to click the toast and will be redirected to the
     * question and her/his answers to reconsider.
     * @param view
     */
    public void questionNine(View view) {
        CheckBox checkBoxQ8A1 = (CheckBox) findViewById(R.id.check_Box_Q_8_A_1);
        q8a1 = checkBoxQ8A1.isChecked();

        CheckBox checkBoxQ8A2 = (CheckBox) findViewById(R.id.check_Box_Q_8_A_2);
        q8a2 = checkBoxQ8A2.isChecked();

        CheckBox checkBoxQ8A3 = (CheckBox) findViewById(R.id.check_Box_Q_8_A_3);
        q8a3 = checkBoxQ8A3.isChecked();

        CheckBox checkBoxQ8A4 = (CheckBox) findViewById(R.id.check_Box_Q_8_A_4);
        q8a4 = checkBoxQ8A4.isChecked();

        /* This checks if all answers are correct or wrong and sets value of variable boolean correctChoice. */
        correctChoiceQuestion8 = checkAnswers(q8a1, q8a2, q8a3, q8a4);

        /* Creating toast message for possible wrong choice of answers. */
        toastWrongAnswerQuestion8 = toast_wrong_answer();

        /* In case the user picked one or more wrong answers ((i.e. boolean correctChoice = false)), the app will show a toast message which tells the user which answers are wrong and redirects him back to the question and his chosen answers*/
        if (correctChoiceQuestion8 == false) {
            failed_attempts += 1;
            AlertDialog.Builder reconsider = new AlertDialog.Builder(this);
            reconsider.setMessage(toastWrongAnswerQuestion8).setCancelable(false)
                    .setNegativeButton(getString(R.string.goBack), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alert = reconsider.create();
            alert.setTitle(getString(R.string.wrong_answer));
            alert.show();
        }
        if (correctChoiceQuestion8 == true) {
            AlertDialog.Builder proceedQuestion = new AlertDialog.Builder(this);
            proceedQuestion.setMessage(getString(R.string.correct_answer)).setCancelable(false)
                    .setNegativeButton(getString(R.string.nextQuestion), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent nextQuestion = new Intent(QuestionEight.this, QuestionNine.class);
                            /* Creating Intent for storing global variable of overall failed attempts across all activities. */
                            nextQuestion.putExtra("overall_failed_attempts", failed_attempts);
                            startActivity(nextQuestion);
                        }
                    });

            AlertDialog alert = proceedQuestion.create();
            alert.setTitle(getString(R.string.title_correct_answer));
            alert.show();
        }
    }

    /**
     * This method checks what answers have been chosen by user.
     * Sets variable correctChoice;
     * if true then user proceeds to next question;
     * if false then user gets toast message and is redirected back to question and answers.
     */
    private boolean checkAnswers (boolean a1, boolean a2, boolean a3, boolean a4)   {
        /* a1 must be false. */
        /* a2 must be true. */
        /* a3 must be true. */
        /* a4 must be true. */
        if (a1 || (a2 == false) || (a3 == false) || (a4  == false)) {
            correctChoiceQuestion8 = false;
        }
        else    {
            correctChoiceQuestion8 = true;
        }
        return correctChoiceQuestion8;
    }

    /**
     * Method for creating toast message, if one or more answer is wrong.
     */
    private String toast_wrong_answer()    {
        String toastWrongAnswer = "";
        /* a1 must be false. */
        /* a2 must be true. */
        /* a3 must be true. */
        /* a4 must be true. */
        if (q8a1 == true) {
            toastWrongAnswer += getString(R.string.q_a1_wrong) + "\n";
        }
        /* Here is checked, if the user did not click correct answers. */
        if ((q8a2 == false) | (q8a3 == false) | (q8a4 == false)) {
            toastWrongAnswer += getString(R.string.forgotten_answer) + "\n";
        }
        toastWrongAnswer += " " + getString(R.string.reconsider);
        return toastWrongAnswer;
    }

    /**
     * Method for saving data.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
        // Save the user's current game state
        savedInstanceState.putInt("FailedAttempts", failed_attempts);
        savedInstanceState.putBoolean("Q8A1", q8a1);
        savedInstanceState.putBoolean("Q8A2", q8a2);
        savedInstanceState.putBoolean("Q8A3", q8a3);
        savedInstanceState.putBoolean("Q8A4", q8a4);
        savedInstanceState.putBoolean("CorrectChoiceQuestion8", correctChoiceQuestion8);
        savedInstanceState.putString("ToastAnswerQuestion8", toastWrongAnswerQuestion8);
    }
}