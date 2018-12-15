package com.example.marijn.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // On start button click
    public void onStartClicked(View view) {

        // Get the name filled in by the user
        TextView Name = findViewById(R.id.nameInput);
        String name = Name.getText().toString();

        // If textfield is empty, toast a message
        if (name.equals("")) {
            Toast.makeText(this, "You must fill in your name!", Toast.LENGTH_LONG).show();
            return;
        } else {
            // Go to the next activity, remember the name
            Intent intent = new Intent(this, CategoriesActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        }
    }

    // On button click, show Highscores
    public void showHighscores(View view) {
        startActivity(new Intent(this, HighscoresActivity.class));
    }
}
