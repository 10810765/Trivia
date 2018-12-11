package com.example.marijn.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoresAdapter extends ArrayAdapter<Highscores> {

    private ArrayList scores;

    public HighscoresAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscores> objects) {
        super(context, resource, objects);
        this.scores = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.score_row, parent, false);
        }

        TextView Name = convertView.findViewById(R.id.name);
        TextView Score = convertView.findViewById(R.id.score);

        Highscores scoreList = (Highscores) scores.get(position);

        Name.setText(scoreList.getName());
        Score.setText(scoreList.getScore());

        return convertView;
    }
}
