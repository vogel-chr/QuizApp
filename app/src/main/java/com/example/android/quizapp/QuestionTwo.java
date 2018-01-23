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

public class QuestionTwo extends AppCompatActivity {

    int failed_attempts;

    /* Variables for answer checkBoxes. */
    boolean q2a1;
    boolean q2a2; /* Wrong Answer */
    boolean q2a3; /* Wrong Answer */
    boolean q2a4;

    /* Variable for checking, if user proceeds to next question or will be redirected. */
    boolean correctChoiceQuestion2;

    /* Variable for toast message in case user picked wrong question(s). */
    String toastWrongAnswerQuestion2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_two);
        Intent nextQuestion = getIntent();
        failed_attempts = nextQuestion.getIntExtra("overall_failed_attempts", 0);

        if (savedInstanceState != null) {
            failed_attempts = savedInstanceState.getInt("FailedAttempts");
            q2a1 = savedInstanceState.getBoolean("Q2A1");
            q2a2 = savedInstanceState.getBoolean("Q2A2");
            q2a3 = savedInstanceState.getBoolean("Q2A3");
            q2a4 = savedInstanceState.getBoolean("Q2A4");
            correctChoiceQuestion2 = savedInstanceState.getBoolean("CorrectChoiceQuestion2");
            toastWrongAnswerQuestion2 = savedInstanceState.getString("ToastAnswerQuestion2");
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
    public void questionThree(View view) {
        CheckBox checkBoxQ2A1 = (CheckBox) findViewById(R.id.check_Box_Q_2_A_1);
        q2a1 = checkBoxQ2A1.isChecked();

        CheckBox checkBoxQ2A2 = (CheckBox) findViewById(R.id.check_Box_Q_2_A_2);
        q2a2 = checkBoxQ2A2.isChecked();

        CheckBox checkBoxQ2A3 = (CheckBox) findViewById(R.id.check_Box_Q_2_A_3);
        q2a3 = checkBoxQ2A3.isChecked();

        CheckBox checkBoxQ2A4 = (CheckBox) findViewById(R.id.check_Box_Q_2_A_4);
        q2a4 = checkBoxQ2A4.isChecked();

        /* This checks if all answers are correct or wrong and sets value of variable boolean correctChoice. */
        correctChoiceQuestion2 = checkAnswers(q2a1, q2a2, q2a3, q2a4);

        /* Creating toast message for possible wrong choice of answers. */
        toastWrongAnswerQuestion2 = toast_wrong_answer();

        /* In case the user picked one or more wrong answers ((i.e. boolean correctChoice = false)), the app will show a toast message which tells the user which answers are wrong and redirects him back to the question and his chosen answers*/
        if (correctChoiceQuestion2 == false) {
            failed_attempts += 1;
            AlertDialog.Builder reconsider = new AlertDialog.Builder(this);
            reconsider.setMessage(toastWrongAnswerQuestion2).setCancelable(false)
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
        if (correctChoiceQuestion2 == true) {
            AlertDialog.Builder proceedQuestion = new AlertDialog.Builder(this);
            proceedQuestion.setMessage(getString(R.string.correct_answer)).setCancelable(false)
                    .setNegativeButton(getString(R.string.nextQuestion), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent nextQuestion = new Intent(QuestionTwo.this, QuestionThree.class);
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
        /* a2 must be false. */
        /* a3 must be false. */
        /* a4 must be true. */
        if (a2 || a3 || (a1 == false) || (a4 == false)) {
            correctChoiceQuestion2 = false;
        }
        else    {
            correctChoiceQuestion2 = true;
        }
        return correctChoiceQuestion2;
    }

    /**
     * Method for creating toast message, if one or more answer is wrong.
     */
    private String toast_wrong_answer()    {
        String toastWrongAnswer = "";
        /* a1 must be true. */
        /* a2 must be false. */
        /* a3 must be false. */
        /* a4 must be true. */
        if (q2a2 == true) {
            toastWrongAnswer += getString(R.string.q_a2_wrong) + "\n";
        }
        if (q2a3 == true) {
            toastWrongAnswer += getString(R.string.q_a3_wrong) + "\n";
        }
        /* Here is checked, if the user did not click correct answers. */
        if ((q2a1 == false) | (q2a4 == false)) {
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
        savedInstanceState.putBoolean("Q2A1", q2a1);
        savedInstanceState.putBoolean("Q2A2", q2a2);
        savedInstanceState.putBoolean("Q2A3", q2a3);
        savedInstanceState.putBoolean("Q2A4", q2a4);
        savedInstanceState.putBoolean("CorrectChoiceQuestion2", correctChoiceQuestion2);
        savedInstanceState.putString("ToastAnswerQuestion2", toastWrongAnswerQuestion2);
    }
}