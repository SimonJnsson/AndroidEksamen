package com.example.simon.ballapp;

public class User
{
    private String username;
    private int score;

    public String getUsername()
    {
        return username;
    }

    public int getScore()
    {
        return score;
    }

    public User(String username, int score)
    {
        // A user contains a string and int for username and score
        this.username = username;
        this.score = score;
    }
}
