package com.example.marijn.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class CategoriesActivity extends AppCompatActivity {

    // Variable to hold the users name
    private static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Retrieved the name from MainActivity
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
    }

    // On button click, go to GamePlayActivty
    public void onCategoryClicked(View view){
        Button pressedButton = findViewById(view.getId());
        String categoryId = String.valueOf(pressedButton.getTag());

        // Pass the chosen category and the users name to the next activity
        Intent intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra("category_id", categoryId);
        intent.putExtra("name", name);
        startActivity(intent);

    }
}
