package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by Patrick Q Jensen on 10-05-2016.
 */
public class Ball extends GameObject
{

    private int color;
    DisplayMetrics metrics;
    int scrWidth, scrHeight;
    float speedY, speedX;
    float radius;
    private RectF startRect;

    public boolean isCanMove()
    {
        return canMove;
    }

    public void setCanMove(boolean canMove)
    {
        this.canMove = canMove;
    }

    private boolean canMove;

    public Ball(Context context, float left, float top, float right, float bottom)
    {
        super(context, left, top, right, bottom);

        paint.setColor(0xFF00FF00);

        metrics = Resources.getSystem().getDisplayMetrics();
        scrHeight = metrics.heightPixels;
        scrWidth = metrics.widthPixels;
        speedX = -8;
        speedY = -8;
        radius = objRect.right - objRect.left;
        canMove = false;

        startRect = objRect;
    }

    @Override
    public void update()
    {
        super.update();

        if (objRect.right >= scrWidth || objRect.left <= 0)
        {
            speedX *= -1;
        }

        if (objRect.top <= 0)
        {
            speedY *= -1;
        }


        if (objRect.bottom >= scrHeight)
        {
            resetBall();
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

        if (canMove)
        {
            Move();
        }
    }

    private void resetBall()
    {
        canMove = false;
        objRect = new RectF(955, 970, 965, 980);
        speedX = -8;
        speedY = -8;
        GameWorld.getPlayer().lives--;
        GameWorld.getPlayer().resetPlayer();
    }

    public void Move()
    {
        objRect.left += speedX;
        objRect.right += speedX;

        objRect.top += speedY;
        objRect.bottom += speedY;
    }

    public void RevertX()
    {
        speedX *= -1;
    }

    public void RevertY()
    {
        speedY *= -1;
    }


    @Override
    void onCollision(GameObject other)
    {
        if (!(other instanceof Player) && other instanceof Brick)
        {
            if (objRect.bottom >= other.objRect.bottom) // if the ball hits from below
            {
                RevertY();
            }
            else if (objRect.right >= other.objRect.right) // If the ball hits the right side
            {
                RevertX();
            }
            else if (objRect.top <= other.objRect.top) // if the ball hits from above
            {
                RevertY();
            }
            else if (objRect.left <= other.objRect.left) // If the ball hits the left side
            {
                RevertX();
            }

            ((Brick) other).destroy();
        }
        else
        {
            RevertY();
        }
    }
}
