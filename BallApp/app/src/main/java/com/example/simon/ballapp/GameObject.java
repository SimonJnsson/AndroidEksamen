package com.example.simon.ballapp;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

abstract public class GameObject
{
    protected float x, y, width, height;
    protected RectF objRect;

    public RectF getObjRect()
    {
        return objRect;
    }

    protected Context context;

    public Paint getPaint()
    {
        return paint;
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


    // Constructor
    public GameObject(Context context, float left, float top, float right, float bottom)
    {
        objRect = new RectF(left, top, right, bottom);
        x = left;
        y = top;

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
            }
        }
    }

    public boolean hasCollision(GameObject go)
    {
        double xDiff = x - go.getX();
        double yDiff = y - go.getY();

        double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));

        return false;
//        return distance < (r + go.getR());
    }
}
