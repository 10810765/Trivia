package com.example.marijn.trivia;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class Highscores implements Comparable<Highscores> {

    private String name, score;

    // Highscores constructor
    public Highscores(String name, String score) {
        this.name = name;
        this.score = score;
    }

    // Method that compares two values (enables the use of Collections.sort() in HighscoresActivity)
    // With help from: https://stackoverflow.com/questions/9109890/
    @Override
        public int compareTo(Highscores o) {

        // Turn the strings into integers
        Integer scoreInt = Integer.parseInt(score);
        Integer oInt = Integer.parseInt(o.score);

        // Compare the integers
        if (scoreInt.compareTo(oInt) < 0) {
            return 1;
        } else if (scoreInt.compareTo(oInt)> 0) {
            return -1;
        } else {
            return 0;
        }
    }

    // Getters and setters
    public String getName () {
        return name;
    }

    public void setName (String name){
        this.name = name;
    }

    public String getScore () {
        return score;
    }

    public void setScore (String score){
        this.score = score;
    }
}
