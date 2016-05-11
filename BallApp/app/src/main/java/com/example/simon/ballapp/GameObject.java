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
            if (go instanceof Ball && go != this && hasCollision(go))
            {
                // Collision with an other game object happened of type Player
                // Handle collision here
            }
        }
    }

    public boolean hasCollision(GameObject go)
    {
        return RectF.intersects(objRect, go.getObjRect());
    }
}
