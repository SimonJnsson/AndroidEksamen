package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by Patrick Q Jensen on 10-05-2016.
 */
public class Ball extends GameObject {

    private int color;
    DisplayMetrics metrics;
    int scrWidth, scrHeight;
    float speedY, speedX;

    public Ball(Context context, float left, float top, float right, float bottom) {
        super(context,left,top,right,bottom);

        paint.setColor(0xFF00FF00);

        metrics = Resources.getSystem().getDisplayMetrics();
        scrHeight = metrics.heightPixels;
        scrWidth = metrics.widthPixels;
        speedX = 20;
        speedY = 20;

    }

    @Override
    public void update()
    {
        super.update();

        if (objRect.right >= scrWidth)
        {
            speedX *= -1;
        }
         if (objRect.left <= 0)
        {
            speedX *= -1;
        }

        if (objRect.top <= 0)
        {
            speedY *= -1;
        }

        if (objRect.bottom >= scrHeight)
        {
            speedY *= -1;
        }

        // if (y > ?player.y?)
        //{player loses 1 life, respawn ball}

        /*
        * if (ball collides with top of brick)
        * speedY * -1;
        * */

        /*
        * if (ball collides with bottom of brick)
        * speedY * -1;
        * */

        /*
        * if (ball collides with right side of brick)
        * speedX * -1;
        * */

        /*
        * if (ball collides with left side of brick)
        * speedX * -1;
        * */

        Move();

    }

    public void Move()
    {
        //x += speedX;
      //  y += speedY;

        objRect.left += speedX;
        objRect.right += speedX;

        objRect.top += speedY;
        objRect.bottom += speedY;


    }



}