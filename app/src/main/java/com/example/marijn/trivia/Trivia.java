package com.example.marijn.trivia;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class Trivia implements Parcelable {
    private String difficulty, question, correctAnswer, ID;
    private ArrayList incorrectAnswers;

    // Trivia questions constructor
    public Trivia(String difficulty, String question, String correctAnswer, ArrayList incorrectAnswers, String ID) {
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.ID = ID;
    }

    // Getters and setters
    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList getIncorrectAnswers() {
        return incorrectAnswers;
    }


    // The code below is created with help from: https://stackoverflow.com/questions/12503836/
    // It is used to create custom Parcable objects from the Trivia ArrayList
    // This enables the use of outState.putParcelableArrayList(), so we can save the state of the Trivia game
    private Trivia(Parcel in) {
        difficulty = in.readString();
        question = in.readString();
        correctAnswer = in.readString();
        ID = in.readString();
        incorrectAnswers = in.readArrayList(Trivia.class.getClassLoader());

    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(difficulty);
        out.writeString(question);
        out.writeString(correctAnswer);
        out.writeString(ID);
        out.writeList(incorrectAnswers);
    }

    public static final Parcelable.Creator<Trivia> CREATOR = new Parcelable.Creator<Trivia>() {
        public Trivia createFromParcel(Parcel in) {
            return new Trivia(in);
        }

        public Trivia[] newArray(int size) {
            return new Trivia[size];
        }
    };
}
