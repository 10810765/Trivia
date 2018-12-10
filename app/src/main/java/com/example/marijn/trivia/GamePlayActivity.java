package com.example.marijn.trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GamePlayActivity extends AppCompatActivity implements TriviaRequest.Callback {

    public ArrayList<Trivia> triviaQuestions;
    private int questionCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        TriviaRequest request = new TriviaRequest(this);
        request.getTrivia(this);

    }

    @Override
    public void gotTrivia(ArrayList<Trivia> trivia) {
        triviaQuestions = trivia;
        questionCount = 0;

        update();

        // Do Something

    }

    @Override
    public void gotTriviaError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void update() {

        ArrayList<String> wrongAns;

        TextView points = findViewById(R.id.pointsView);
        TextView questionsLeft = findViewById(R.id.questionsLeftView);
        TextView question = findViewById(R.id.questionView);
        Button reset = findViewById(R.id.resetButton);

        question.setText(triviaQuestions.get(0).getQuestion());

        wrongAns = triviaQuestions.get(0).getIncorrectAnswers();

        ArrayList<String> l2 = new ArrayList<String>(wrongAns);

        Button[] answerButtons = new Button[] {
                findViewById(R.id.answerA),
                findViewById(R.id.answerB),
                findViewById(R.id.answerC),
                findViewById(R.id.answerD),
        };



        for (int i = 0, n = answerButtons.length; i < n; i++) {
            if (i == 2) {
                answerButtons[2].setText(triviaQuestions.get(0).getCorrectAnswer());
            } else {
                answerButtons[i].setText(l2.get(0));
                l2.remove(0);

            }

            }


                // Pick wrong answer from list and then delete that answer from the list.



            }


}
