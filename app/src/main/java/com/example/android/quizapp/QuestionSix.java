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

public class QuestionSix extends AppCompatActivity {

    int failed_attempts;

    /* Variables for answer checkBoxes. */
    boolean q6a1;
    boolean q6a2; /* Wrong answer */
    boolean q6a3;
    boolean q6a4;

    /* Variable for checking, if user proceeds to next question or will be redirected. */
    boolean correctChoiceQuestion6;

    /* Variable for toast message in case user picked wrong question(s). */
    String toastWrongAnswerQuestion6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_six);
        Intent nextQuestion = getIntent();
        failed_attempts = nextQuestion.getIntExtra("overall_failed_attempts", 0);

        if (savedInstanceState != null) {
            failed_attempts = savedInstanceState.getInt("FailedAttempts");
            q6a1 = savedInstanceState.getBoolean("Q6A1");
            q6a2 = savedInstanceState.getBoolean("Q6A2");
            q6a3 = savedInstanceState.getBoolean("Q6A3");
            q6a4 = savedInstanceState.getBoolean("Q6A4");
            correctChoiceQuestion6 = savedInstanceState.getBoolean("CorrectChoiceQuestion6");
            toastWrongAnswerQuestion6 = savedInstanceState.getString("ToastAnswerQuestion6");
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
    public void questionSeven(View view) {
        CheckBox checkBoxQ6A1 = (CheckBox) findViewById(R.id.check_Box_Q_6_A_1);
        q6a1 = checkBoxQ6A1.isChecked();

        CheckBox checkBoxQ6A2 = (CheckBox) findViewById(R.id.check_Box_Q_6_A_2);
        q6a2 = checkBoxQ6A2.isChecked();

        CheckBox checkBoxQ6A3 = (CheckBox) findViewById(R.id.check_Box_Q_6_A_3);
        q6a3 = checkBoxQ6A3.isChecked();

        CheckBox checkBoxQ6A4 = (CheckBox) findViewById(R.id.check_Box_Q_6_A_4);
        q6a4 = checkBoxQ6A4.isChecked();

        /* This checks if all answers are correct or wrong and sets value of variable boolean correctChoice. */
        correctChoiceQuestion6 = checkAnswers(q6a1, q6a2, q6a3, q6a4);

        /* Creating toast message for possible wrong choice of answers. */
        toastWrongAnswerQuestion6 = toast_wrong_answer();

        /* In case the user picked one or more wrong answers ((i.e. boolean correctChoice = false)), the app will show a toast message which tells the user which answers are wrong and redirects him back to the question and his chosen answers*/
        if (correctChoiceQuestion6 == false) {
            failed_attempts += 1;
            AlertDialog.Builder reconsider = new AlertDialog.Builder(this);
            reconsider.setMessage(toastWrongAnswerQuestion6).setCancelable(false)
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
        if (correctChoiceQuestion6 == true) {
            AlertDialog.Builder proceedQuestion = new AlertDialog.Builder(this);
            proceedQuestion.setMessage(getString(R.string.correct_answer)).setCancelable(false)
                    .setNegativeButton(getString(R.string.nextQuestion), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent nextQuestion = new Intent(QuestionSix.this, QuestionSeven.class);
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
        /* a3 must be true. */
        /* a4 must be true. */
        if ((a1 == false) || a2 || (a3 == false) || (a4  == false)) {
            correctChoiceQuestion6 = false;
        }
        else    {
            correctChoiceQuestion6 = true;
        }
        return correctChoiceQuestion6;
    }

    /**
     * Method for creating toast message, if one or more answer is wrong.
     */
    private String toast_wrong_answer()    {
        String toastWrongAnswer = "";
        /* a1 must be true. */
        /* a2 must be false. */
        /* a3 must be true. */
        /* a4 must be true. */
        if (q6a2 == true) {
            toastWrongAnswer += getString(R.string.q_a2_wrong) + "\n";
        }
        /* Here is checked, if the user did not click correct answers. */
        if ((q6a1 == false) | (q6a3 == false) | (q6a4 == false)) {
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
        savedInstanceState.putBoolean("Q6A1", q6a1);
        savedInstanceState.putBoolean("Q6A2", q6a2);
        savedInstanceState.putBoolean("Q6A3", q6a3);
        savedInstanceState.putBoolean("Q6A4", q6a4);
        savedInstanceState.putBoolean("CorrectChoiceQuestion6", correctChoiceQuestion6);
        savedInstanceState.putString("ToastAnswerQuestion6", toastWrongAnswerQuestion6);
    }
}