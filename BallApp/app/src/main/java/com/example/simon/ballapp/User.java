package com.example.simon.ballapp;

public class User
{
    private int _id;
    private String username;
    private int score;

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public User()
    {
    }

    public User(String username, int score)
    {
        this.username = username;
        this.score = score;
    }
}
