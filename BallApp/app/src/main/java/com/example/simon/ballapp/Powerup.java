package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by Patrick Q Jensen on 12-05-2016.
 */
public class Powerup extends GameObject
{
    DisplayMetrics metrics;
    int scrWidth, scrHeight;
    float speedY, speedX;
    float radius;

    public Powerup(Context context, float left, float top, float right, float bottom)
    {
        super(context, left, top, right, bottom);

        paint.setColor(0xFF00FF00);

        metrics = Resources.getSystem().getDisplayMetrics();
        scrHeight = metrics.heightPixels;
        scrWidth = metrics.widthPixels;
        speedX = 0;
        speedY = -10;
        radius = objRect.right - objRect.left;
    }

    @Override
    public void update()
    {
        super.update();

        Move();
    }

    public void Move()
    {
        y += speedY;
        x += speedX;
    }

    @Override
    void onCollision(GameObject other)
    {

    }
}
