package com.example.marijn.trivia;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GamePlayActivity extends AppCompatActivity implements TriviaRequest.Callback {

    private ArrayList<Trivia> triviaQuestions;
    private ArrayList<String> wrongAns;
    private int questionCount, pointsCount, correctAnsNum, chosenAnsNum;
    private String totalQuestions, name;
    private Button[] answerButtons;
    private TextView points, questionsLeft, question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // Get intent with the name of the user
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        TriviaRequest request = new TriviaRequest(this);
        request.getTrivia(this);

        points = findViewById(R.id.pointsView);
        questionsLeft = findViewById(R.id.questionsLeftView);
        question = findViewById(R.id.questionView);

        answerButtons = new Button[] {
                findViewById(R.id.answerA),
                findViewById(R.id.answerB),
                findViewById(R.id.answerC),
                findViewById(R.id.answerD),
        };

    }

    @Override
    public void gotTrivia(ArrayList<Trivia> trivia) {

        // Remember the requested Trivia ArrayList
        triviaQuestions = trivia;

        // Initialize the starting points and question count
        pointsCount = 0;
        questionCount = 0;
        totalQuestions = Integer.toString(triviaQuestions.size());

        // Set chosenAnsNum to an invalid option
        chosenAnsNum = 5;

        // Update the user interface in order to ask the first question
        update();

    }

    @Override
    public void gotTriviaError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void update() {

        questionsLeft.setText(Html.fromHtml("Question " + Integer.toString(questionCount + 1) + " out of " + totalQuestions + ". " +
                " <b> Difficulty: </b> " + triviaQuestions.get(questionCount).getDifficulty() ));

        question.setText(Html.fromHtml(triviaQuestions.get(questionCount).getQuestion()));

        wrongAns = triviaQuestions.get(questionCount).getIncorrectAnswers();

        points.setText("Total points: " + Integer.toString(pointsCount));


        // Generate a random number to be used for the correct answer
        correctAnsNum = ThreadLocalRandom.current().nextInt( 4);


        // Make a copy of the wrongAnswer ArrayList
        ArrayList<String> wrongAnsCopy = new ArrayList<>(wrongAns);

        for (int i = 0, n = 4; i < n; i++) {
            answerButtons[i].getBackground().setColorFilter(null);
        }

        for (int i = 0, n = 4; i < n; i++) {
            if (i == correctAnsNum) {
                answerButtons[i].setText(Html.fromHtml(triviaQuestions.get(questionCount).getCorrectAnswer()));
            } else {
                // Generate a random number to be used for an incorrect answer
                int wrongAnsNum = ThreadLocalRandom.current().nextInt(wrongAnsCopy.size());
                // Pick wrong answer from wrongAns ArrayList and then delete that answer from the ArrayList
                answerButtons[i].setText(Html.fromHtml(wrongAnsCopy.get(wrongAnsNum)));
                wrongAnsCopy.remove(wrongAnsNum);
            }
        }
    }

    public void onAnswerClick(View view) {

        final ColorMatrix grayscaleMatrix = new ColorMatrix();
        grayscaleMatrix.setSaturation(0);

        final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(grayscaleMatrix);

        for (int i = 0, n = 4; i < n; i++) {
            answerButtons[i].getBackground().setColorFilter(filter);
        }

        switch (view.getId()) {
            case R.id.answerA:
                answerButtons[0].getBackground().setColorFilter(null);
                chosenAnsNum = 0;
                break;
            case R.id.answerB:
                answerButtons[1].getBackground().setColorFilter(null);
                chosenAnsNum = 1;
                break;
            case R.id.answerC:
                answerButtons[2].getBackground().setColorFilter(null);
                chosenAnsNum = 2;
                break;
            case R.id.answerD:
                answerButtons[3].getBackground().setColorFilter(null);
                chosenAnsNum = 3;
                break;
        }
    }

    public void onConfirmClick(View view) {

        if (chosenAnsNum == 5) {
            Toast.makeText(this, "Select an answer before confirming!", Toast.LENGTH_SHORT).show();
            return;
        } else if (chosenAnsNum == correctAnsNum) {
            Toast.makeText(this, "That was the correct answer!", Toast.LENGTH_SHORT).show();
            switch (triviaQuestions.get(questionCount).getDifficulty()) {
                case "easy":
                    pointsCount += 10;
                    break;
                case "medium":
                    pointsCount += 20;
                    break;
                case "hard":
                    pointsCount += 30;
                    break;
            }
        } else {
            Toast.makeText(this, "Wrong answer! The correct answer was " +
                    (Html.fromHtml(triviaQuestions.get(questionCount).getCorrectAnswer())) + ".", Toast.LENGTH_LONG).show();
        }


        // Reset chosenAnsNum to an invalid option
        chosenAnsNum = 5;

        // Add 1 to questionCount to get the next questionTest
        questionCount += 1;


        if (questionCount == 10) {
            HighscoresRequest request = new HighscoresRequest(this);
            request.putHighscore(name, Integer.toString(pointsCount));

            Intent intent = new Intent(this, HighscoresActivity.class);
            startActivity(intent);

        } else {
            update();
        }
    }

    // Go to main activity when the reset button is clicked
    public void onRestartClicked(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
