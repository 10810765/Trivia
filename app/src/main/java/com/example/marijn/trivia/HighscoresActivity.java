package com.example.marijn.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class HighscoresActivity extends AppCompatActivity implements HighscoresRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        // Make a request for the highscores
        HighscoresRequest request = new HighscoresRequest(this);
        request.getHighscores(this);
    }

    @Override // Method that handles a successful call to the API
    public void gotHighscores(ArrayList<Highscores> highscores) {

        // Sort the highscores in descending order
        Collections.sort(highscores);

        // Instantiate the adapter
        HighscoresAdapter scoreAdapter = new HighscoresAdapter(this, R.layout.score_row, highscores);

        // Get list view ID and attach the adapter to it
        ListView scoreList = findViewById(R.id.scoreList);
        scoreList.setAdapter(scoreAdapter);
    }

    @Override // Method that handles an unsuccessful to the the API
    public void gotHighscoresError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Refresh the highscores by making an new API call
    public void refreshHighscores(View view) {
        HighscoresRequest request = new HighscoresRequest(this);
        request.getHighscores(this);
    }

    // On play restart pressed, go to MainActivity
    public void playAgain(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }



    @Override // On back pressed go to MainActivity
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}


