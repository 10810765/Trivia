package com.example.marijn.trivia;

import java.util.ArrayList;

public class Trivia {
    private String difficulty, question, correctAnswer, ID;
    private ArrayList incorrectAnswers;

    public Trivia(String difficulty, String question, String correctAnswer, ArrayList incorrectAnswers, String ID) {
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.ID = ID;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(ArrayList incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
