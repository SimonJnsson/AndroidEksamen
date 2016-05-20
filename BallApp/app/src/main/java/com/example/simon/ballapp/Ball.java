package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.graphics.RectF;
import android.util.DisplayMetrics;

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
    final MediaPlayer mp = MediaPlayer.create(context, R.raw.batsound);
    final MediaPlayer mp2 = MediaPlayer.create(context, R.raw.bricksound);
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

    public Ball(Context context, float left, float top, float right, float bottom, int id)
    {
        super(context, left, top, right, bottom, id);

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

        if (!canMove)
        {
            positionBall();
        }

        if (objRect.right >= scrWidth || objRect.left <= 0)
        {
            speedX *= -1;
        }

        if (objRect.top <= 0)
        {
            speedY *= -1;
        }

        if (objRect.top >= scrHeight)
        {
            resetBall();
        }

        if (canMove)
        {
            Move();
        }
    }

    private void resetBall()
    {
        canMove = false;
        positionBall();
        speedX = -8;
        speedY = -8;
        GameWorld.getPlayer().lives--;
    }

    public void positionBall()
    {
        int distToPlayer = 50;
        RectF playerRect = GameWorld.getPlayer().getObjRect();
        objRect.left = playerRect.left + ((playerRect.right - playerRect.left) / 2);
        objRect.top = playerRect.top - radius;
        objRect.right = playerRect.left + radius + ((playerRect.right - playerRect.left) / 2);
        objRect.bottom = playerRect.top;
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
        if (other instanceof Brick)
        {
            mp2.start();
            GameWorld.getPlayer().setScore(GameWorld.getPlayer().getScore() + 1);

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
        else if (other instanceof Player)
        {
            mp.start();
            RevertY();
        }
    }
}
