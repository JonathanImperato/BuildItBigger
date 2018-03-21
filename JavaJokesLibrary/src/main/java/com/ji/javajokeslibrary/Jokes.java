package com.ji.javajokeslibrary;

import java.util.Random;

public class Jokes {

    private String[] jokesList;
    private Random randomInt;

    public Jokes() {
        jokesList = new String[3];
        jokesList[0] = "A SQL query goes into a bar, walks up to two tables and asks, \"Can I join you?\"";
        jokesList[1] = "Q: How many prolog programmers does it take to change a lightbulb? A: Yes.";
        jokesList[2] = "Why do Java developers wear glasses? Because they can't C#";
        randomInt = new Random();
    }

    public String[] getJokesList() {
        return jokesList;
    }

    public String getRandomJoke() {
        return jokesList[randomInt.nextInt(jokesList.length)];
    }
}
