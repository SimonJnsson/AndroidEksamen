package com.example.simon.ballapp;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;

abstract public class GameObject
{
    protected float x, y;
    private float r;
    protected Context context;

    public Paint getPaint()
    {
        return paint;
    }

    public void setPaint(Paint paint)
    {
        this.paint = paint;
    }

    protected Paint paint;

    public float getX()

    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public float getR()
    {
        return r;
    }

    // Constructor
    public GameObject(Context context, int screenX, int screenY, float radius)
    {
        x = screenX;
        y = screenY;
        r = radius;

        paint = new Paint();
        this.context = context;
    }

    public void update()
    {
        for (GameObject go : GameWorld.getGameObjects())
        {
            if (hasCollision(go) && go != this && go instanceof Player)
            {
                // Collision with an other game object happened of type Player
                // Handle collision here
                Log.v("Log Collision", "The Player collided with something");
                GameWorld.pause();
            }
        }
    }

    public boolean hasCollision(GameObject go)
    {
        double xDiff = x - go.getX();
        double yDiff = y - go.getY();

        double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));

        return distance < (r + go.getR());
    }
}
