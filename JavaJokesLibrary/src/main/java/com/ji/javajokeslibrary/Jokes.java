package com.ji.javajokeslibrary;

import java.util.Random;

public class Jokes {

    private String[] jokesList;
    private Random randomInt;

    public Jokes() {
        jokesList = new String[3];
        jokesList[0] = "A guy walks into a bar and asks for 1.4 root beers. The bartender says \"I'll have to charge you extra, that's a root beer float\". The guy says \"In that case, better make it a double.\"";
        jokesList[1] = "A programmer is told to \"go to hell\", he finds the worst part of that statement is the \"go to\"";
        jokesList[2] = "What is a programmer's favourite hangout place? \"Foo Bar\"";
        randomInt = new Random();
    }

    public String[] getJokesList() {
        return jokesList;
    }

    public String getJoke() {
        return getJokesList()[randomInt.nextInt(jokesList.length)];
    }
}
