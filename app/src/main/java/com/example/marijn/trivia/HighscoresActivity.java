package com.example.marijn.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HighscoresActivity extends AppCompatActivity implements HighscoresRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        HighscoresRequest request = new HighscoresRequest(this);
        request.getHighscores(this);

    }

    @Override
    public void gotHighscores(ArrayList<Highscores> highscores) {

        HighscoresAdapter scoreAdapter = new HighscoresAdapter(this, R.layout.score_row, highscores);

        ListView scoreList = findViewById(R.id.scoreList);

        scoreList.setAdapter(scoreAdapter);

    }

    @Override
    public void gotHighscoresError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
