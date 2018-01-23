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

public class QuestionFive extends AppCompatActivity {

    int failed_attempts;

    /* Variables for answer checkBoxes. */
    boolean q5a1;
    boolean q5a2;
    boolean q5a3;
    boolean q5a4;

    /* Variable for checking, if user proceeds to next question or will be redirected. */
    boolean correctChoiceQuestion5;

    /* Variable for toast message in case user picked wrong question(s). */
    String toastWrongAnswerQuestion5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_five);
        Intent nextQuestion = getIntent();
        failed_attempts = nextQuestion.getIntExtra("overall_failed_attempts", 0);

        if (savedInstanceState != null) {
            failed_attempts = savedInstanceState.getInt("FailedAttempts");
            q5a1 = savedInstanceState.getBoolean("Q5A1");
            q5a2 = savedInstanceState.getBoolean("Q5A2");
            q5a3 = savedInstanceState.getBoolean("Q5A3");
            q5a4 = savedInstanceState.getBoolean("Q5A4");
            correctChoiceQuestion5 = savedInstanceState.getBoolean("CorrectChoiceQuestion5");
            toastWrongAnswerQuestion5 = savedInstanceState.getString("ToastAnswerQuestion5");
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
    public void questionSix(View view) {
        CheckBox checkBoxQ5A1 = (CheckBox) findViewById(R.id.check_Box_Q_5_A_1);
        q5a1 = checkBoxQ5A1.isChecked();

        CheckBox checkBoxQ5A2 = (CheckBox) findViewById(R.id.check_Box_Q_5_A_2);
        q5a2 = checkBoxQ5A2.isChecked();

        CheckBox checkBoxQ5A3 = (CheckBox) findViewById(R.id.check_Box_Q_5_A_3);
        q5a3 = checkBoxQ5A3.isChecked();

        CheckBox checkBoxQ5A4 = (CheckBox) findViewById(R.id.check_Box_Q_5_A_4);
        q5a4 = checkBoxQ5A4.isChecked();

        /* This checks if all answers are correct or wrong and sets value of variable boolean correctChoice. */
        correctChoiceQuestion5 = checkAnswers(q5a1, q5a2, q5a3, q5a4);

        /* Creating toast message for possible wrong choice of answers. */
        toastWrongAnswerQuestion5 = toast_wrong_answer();

        /* In case the user picked one or more wrong answers ((i.e. boolean correctChoice = false)), the app will show a toast message which tells the user which answers are wrong and redirects him back to the question and his chosen answers*/
        if (correctChoiceQuestion5 == false) {
            failed_attempts += 1;
            AlertDialog.Builder reconsider = new AlertDialog.Builder(this);
            reconsider.setMessage(toastWrongAnswerQuestion5).setCancelable(false)
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
        if (correctChoiceQuestion5 == true) {
            AlertDialog.Builder proceedQuestion = new AlertDialog.Builder(this);
            proceedQuestion.setMessage(getString(R.string.correct_answer)).setCancelable(false)
                    .setNegativeButton(getString(R.string.nextQuestion), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent nextQuestion = new Intent(QuestionFive.this, QuestionSix.class);
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
        /* a1 must be true. */
        /* a2 must be true. */
        /* a3 must be true. */
        /* a4 must be true. */
        if ((a1 == false) || (a2 == false) || (a3 == false) || (a4  == false)) {
            correctChoiceQuestion5 = false;
        }
        else    {
            correctChoiceQuestion5 = true;
        }
        return correctChoiceQuestion5;
    }

    /**
     * Method for creating toast message, if one or more answer is wrong.
     */
    private String toast_wrong_answer()    {
        String toastWrongAnswer = "";
        /* a1 must be true. */
        /* a2 must be true. */
        /* a3 must be true. */
        /* a4 must be true. */
        /* No false answer so no 'if' in this case! */
        /* if (q4a2 == true) {
            toastWrongAnswer += getString(R.string.q_a4_wrong) + "\n";
        } */
        /* Here is checked, if the user did not click correct answers. */
        if ((q5a1 == false) | (q5a2 == false) | (q5a3 == false) | (q5a4 == false)) {
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
        savedInstanceState.putBoolean("Q5A1", q5a1);
        savedInstanceState.putBoolean("Q5A2", q5a2);
        savedInstanceState.putBoolean("Q5A3", q5a3);
        savedInstanceState.putBoolean("Q5A4", q5a4);
        savedInstanceState.putBoolean("CorrectChoiceQuestion5", correctChoiceQuestion5);
        savedInstanceState.putString("ToastAnswerQuestion5", toastWrongAnswerQuestion5);
    }
}