package com.example.marijn.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class HighscoresAdapter extends ArrayAdapter<Highscores> {

    private ArrayList scores;

    public HighscoresAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscores> objects) {
        super(context, resource, objects);
        this.scores = objects;
    }

    @NonNull
    @Override // Method that will be called every time a new score is to be displayed
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        // If the convert view is null, inflate a new one
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.score_row, parent, false);
        }

        // Get the ID's of the name and score (TextView)
        TextView Name = convertView.findViewById(R.id.name);
        TextView Score = convertView.findViewById(R.id.score);

        // Get the index of the score that we want to display
        Highscores scoreList = (Highscores) scores.get(position);

        // Set the name and score
        Name.setText(scoreList.getName());
        Score.setText(scoreList.getScore());

        return convertView;
    }
}
