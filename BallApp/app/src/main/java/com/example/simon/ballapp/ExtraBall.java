package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;

public class ExtraBall extends GameObject
{


    DisplayMetrics metrics;
    int scrWidth, scrHeight;
    float startSpeed, speedY, speedX;
    static float radius;
    private RectF startRect;

    final MediaPlayer batSound = MediaPlayer.create(context, R.raw.batsound);
    final MediaPlayer brickSound = MediaPlayer.create(context, R.raw.bricksound);

    public ExtraBall(Context context, float left, float top, float right, float bottom, int id)
    {
        super(context, left, top, right, bottom, id);
        paint.setColor(0xFF000000); //black

        metrics = Resources.getSystem().getDisplayMetrics();
        scrHeight = metrics.heightPixels;
        scrWidth = metrics.widthPixels;

        startSpeed = 12;
        speedX = -startSpeed;
        speedY = -startSpeed;

        radius = objRect.right - objRect.left;

        RectF playerRect = GameWorld.getPlayer().getObjRect();
        objRect.left = playerRect.left - (radius / 2) + ((playerRect.right - playerRect.left) / 2 * 1.15f);
        objRect.top = playerRect.top - radius;
        objRect.right = playerRect.left + radius + ((playerRect.right - playerRect.left) / 2 / 1.15f);
        objRect.bottom = playerRect.top;

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
            // piercePowerup = false;
        }

        if (objRect.top >= scrHeight)
        {
            GameWorld.getGameObjects().remove(this);
        }

        Move();
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
            brickSound.start();
            GameWorld.getPlayer().setScore(GameWorld.getPlayer().getScore() + 1);
            //  if (!piercePowerup)
            // {
            if (objRect.top - speedY >= other.objRect.bottom) // if the ball hits from below
            {
                RevertY();
            }
            else if (objRect.right - speedX >= other.objRect.right) // If the ball hits the right side
            {
                RevertX();
            }
            else if (objRect.top + speedY <= other.objRect.top) // if the ball hits from above
            {
                RevertY();
            }
            else if (objRect.left + speedX <= other.objRect.left) // If the ball hits the left side
            {
                RevertX();
            }
            //  }

            if (brickSound.isPlaying())
            {
                brickSound.stop();
            }
            brickSound.start();
            ((Brick) other).destroy();
        }
        else if (other instanceof Player)
        {
            if (batSound.isPlaying())
            {
                batSound.stop();
            }
            batSound.start();

            // Check which side of he player is hit
            if (objRect.centerX() > other.objRect.centerX())
            {
                // If the ball hits the right side and is approaching from the right side
                if (speedX < 0) // Hit from right
                {
                    RevertX();
                    RevertY();
                }
                else
                {
                    RevertY();
                }
            }
            else
            {
                // If the ball hits the left side and is approaching from the left side
                if (speedX > 0) // Hit from left
                {
                    RevertX();
                    RevertY();
                }
                else
                {
                    RevertY();
                }
            }
        }


    }
}
