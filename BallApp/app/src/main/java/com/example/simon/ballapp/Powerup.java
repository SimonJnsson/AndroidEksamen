package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import java.util.Random;

/**
 * Created by Patrick Q Jensen on 12-05-2016.
 */

public class Powerup extends GameObject
{
    public enum Type
    {
        EXTRABALL, LARGERPADDLE, SPEEDBOOST, PIERCING
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
            id = R.drawable.dark1;
        }
        if (r == 1)
        {
            powerType = Type.EXTRABALL;
            id = R.drawable.dark5;
        }
        if (r == 2)
        {
            powerType = Type.SPEEDBOOST;
            id = R.drawable.dark3;
        }
        if (r == 3)
        {
            powerType = Type.LARGERPADDLE;
            id = R.drawable.dark2;
        }

        setResizedBitmap(this, BitmapFactory.decodeResource(context.getResources(), id));
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
