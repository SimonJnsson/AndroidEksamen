package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Random;

/**
 * Created by Patrick Q Jensen on 12-05-2016.
 */

public class Powerup extends GameObject
{
    public enum Type
    {
        EXTRABALL, LARGERPADDLE, SPEEDBOOST,PIERCING
    }

    DisplayMetrics metrics;
    int scrWidth, scrHeight, r;
    float speedY, speedX;
    float radius;
    Random rnd = new Random();

    public Type getPowerType()
    {
        return powerType;
    }

    private Type powerType;

    public Powerup(Context context, float left, float top, float right, float bottom, int id)
    {
        super(context, left, top, right, bottom, id);

        metrics = Resources.getSystem().getDisplayMetrics();
        scrHeight = metrics.heightPixels;
        scrWidth = metrics.widthPixels;
        speedY = 10;
        radius = objRect.right - objRect.left;
        r = rnd.nextInt(4);
        if (r == 0)
        {
            powerType = Type.PIERCING;
            paint.setColor(0xFFFFFF00); //yellow
        }
        if (r == 1)
        {
            powerType = Type.EXTRABALL;
            paint.setColor(0xFF00FF00); //green
        }
        if (r == 2)
        {
            powerType = Type.SPEEDBOOST;
            paint.setColor(0xFFFF0000); //red
        }
        if (r == 3)
        {
            powerType = Type.LARGERPADDLE;
            paint.setColor(0xFF0000FF);//blue
        }

    }

    @Override
    public void update()
    {
        super.update();

        Move();

        if (objRect.top > GameWorld.getScreenY())
        {
            GameWorld.getGameObjects().remove(this);
        }
    }

    public void Move()
    {
        objRect.top += speedY;
        objRect.bottom += speedY;
    }

    @Override
    void onCollision(GameObject other)
    {

    }
}
