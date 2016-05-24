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
    int scrWidth, scrHeight;
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
        switch (rnd.nextInt(4))
        {
            case 0:
                powerType = Type.PIERCING;
                id = R.drawable.powerupyellow;
                break;
            case 1:
                powerType = Type.EXTRABALL;
                id = R.drawable.powerupgreen;
                break;
            case 2:
                powerType = Type.SPEEDBOOST;
                id = R.drawable.powerupred;
                break;
            case 3:
                powerType = Type.LARGERPADDLE;
                id = R.drawable.powerupblue;
                break;
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
