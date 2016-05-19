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
    DisplayMetrics metrics;
    int scrWidth, scrHeight, r;
    float speedY, speedX;
    float radius;
    Random rnd = new Random();

    public Powerup(Context context, float left, float top, float right, float bottom, int r, int id)
    {
        super(context, left, top, right, bottom, id);

        metrics = Resources.getSystem().getDisplayMetrics();
        scrHeight = metrics.heightPixels;
        scrWidth = metrics.widthPixels;
        speedY = 10;
        radius = objRect.right - objRect.left;
        r = rnd.nextInt(3);
        if (r == 1)
        {
            paint.setColor(0xFF00FF00);
        }
        if (r == 2)
        {
            paint.setColor(0xFFFF0000);
        }
        if (r == 3)
        {
            paint.setColor(0xFF0000FF);
        }

    }

    @Override
    public void update()
    {
        super.update();

        Move();
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
