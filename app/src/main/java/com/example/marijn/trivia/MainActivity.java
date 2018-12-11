package com.example.marijn.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartClicked(View view) {
        TextView Name = findViewById(R.id.nameInput);
        String name = Name.getText().toString();

        if (name.equals("")) {
            Toast.makeText(this, "You must fill in your name!", Toast.LENGTH_LONG).show();
            return;

        } else {
            Intent intent = new Intent(MainActivity.this, GamePlayActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        }
    }
}
