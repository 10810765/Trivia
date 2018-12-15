package com.example.marijn.trivia;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Parcelable;
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

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class GamePlayActivity extends AppCompatActivity implements TriviaRequest.Callback {

    // Instantiate various variables
    private ArrayList<Trivia> triviaQuestions; // Variable used to hold the questions
    private ArrayList<String> wrongAns;
    private int questionCount, pointsCount, correctAnsNum, chosenAnsNum;
    private String totalQuestions, name, categoryID;
    private Button[] answerButtons;
    private TextView points, questionsLeft, question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // Get intent with the name of the user and the chosen category id
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        categoryID = intent.getStringExtra("category_id");

        // Get TextView id's
        points = findViewById(R.id.pointsView);
        questionsLeft = findViewById(R.id.questionsLeftView);
        question = findViewById(R.id.questionView);

        // Create a list with all the answer button ID's
        answerButtons = new Button[] {
                findViewById(R.id.answerA),
                findViewById(R.id.answerB),
                findViewById(R.id.answerC),
                findViewById(R.id.answerD),
        };

        // If there is no saved state, request the trivia questions
        if (savedInstanceState == null) {
            TriviaRequest request = new TriviaRequest(this);
            request.getTrivia(this, categoryID);
            return;
        } else {
            // If there is a previously saved trivia game, restore this game
            triviaQuestions = savedInstanceState.getParcelableArrayList("trivia");
            pointsCount = savedInstanceState.getInt("points");
            questionCount = savedInstanceState.getInt("questions");
            totalQuestions = savedInstanceState.getString("totalQuestions");
            chosenAnsNum = 5;

            // Restore all the TextViews and buttons
            update();
        }
    }

    // Save the state of the current Trivia game
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("trivia", triviaQuestions);
        outState.putInt("points", pointsCount);
        outState.putInt("questions", questionCount);
        outState.putString("totalQuestions", totalQuestions);
    }


    @Override // Method that handles a successful call to the API
    public void gotTrivia(ArrayList<Trivia> trivia) {

        // Remember the requested Trivia ArrayList
        triviaQuestions = trivia;

        // Initialize the starting points and question count
        pointsCount = 0;
        questionCount = 0;
        totalQuestions = Integer.toString(triviaQuestions.size());

        // Set chosenAnsNum to an invalid option
        chosenAnsNum = 5;

        // Update the user interface (ask the first question)
        update();

    }

    @Override // Method that handles an unsuccessful to the the API
    public void gotTriviaError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Update the user interface
    public void update() {

        // Display a question counter and the difficulty of the question
        questionsLeft.setText(Html.fromHtml("Question " + Integer.toString(questionCount + 1) + " out of " + totalQuestions + ". " +
                "   <b> Difficulty: </b> " + triviaQuestions.get(questionCount).getDifficulty() ));

        // Ask the question
        question.setText(Html.fromHtml(triviaQuestions.get(questionCount).getQuestion()));

        // Retrieve three wrong answers to the question
        wrongAns = triviaQuestions.get(questionCount).getIncorrectAnswers();

        // Show earned the points
        points.setText("Total points: " + Integer.toString(pointsCount));

        // Generate a random number to be used for the correct answer
        correctAnsNum = ThreadLocalRandom.current().nextInt( 4);

        // Make a copy of the wrongAnswer ArrayList (that we can edit)
        ArrayList<String> wrongAnsCopy = new ArrayList<>(wrongAns);

        // Reset all the button backgrounds
        for (int i = 0, n = answerButtons.length; i < n; i++) {
            answerButtons[i].getBackground().setColorFilter(null);
        }

        // Randomly set the answers on the buttons
        for (int i = 0, n = answerButtons.length; i < n; i++) {
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

    // On click get the clicked answer number
    public void onAnswerClick(View view) {

        // Grey-out all the button
        for (int i = 0, n = answerButtons.length; i < n; i++) {
            greyOutButton(answerButtons[i]);
        }

        // Undo the grey-out of the clicked button and store the button number
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

    // When the confirm button is clicked award points and display the next question
    public void onConfirmClick(View view) {

        // If no answer is clicked toast a message
        if (chosenAnsNum == 5) {
            Toast.makeText(this, "Select an answer before confirming!", Toast.LENGTH_SHORT).show();
            return;
        // If the correct answer was clicked, award points
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
        } else { // If the wrong answer was clicked, toast the right answer
            Toast.makeText(this, "Wrong answer. The correct answer was " +
                    (Html.fromHtml(triviaQuestions.get(questionCount).getCorrectAnswer())) + ".", Toast.LENGTH_LONG).show();
        }


        // Reset chosenAnsNum to an invalid option
        chosenAnsNum = 5;

        // Add 1 to questionCount to get the next question
        questionCount += 1;

        // If the last question has been answered, put highscore in database and go to HighscoreActivity
        if (questionCount == 10) {
            HighscoresRequest request = new HighscoresRequest(this);
            request.putHighscore(name, Integer.toString(pointsCount));

            Intent intent = new Intent(this, HighscoresActivity.class);
            startActivity(intent);

        } else { // If it's not the last question display the next one
            update();
        }
    }

    // Go to main activity when the reset button is clicked
    public void onRestartClicked(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override // On back pressed go to MainActivity
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    // Method to grey out a button
    // Source: https://stackoverflow.com/questions/38186885/
    private void greyOutButton(Button button) {
        final ColorMatrix grayscaleMatrix = new ColorMatrix();
        grayscaleMatrix.setSaturation(0);

        final ColorMatrixColorFilter greyFilter = new ColorMatrixColorFilter(grayscaleMatrix);
        button.getBackground().setColorFilter(greyFilter);
    }
}
