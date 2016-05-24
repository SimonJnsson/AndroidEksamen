package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.graphics.RectF;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Random;

public class Ball extends GameObject
{
    private int color;
    DisplayMetrics metrics;
    int scrWidth, scrHeight;
    float startSpeed, speedY, speedX;
    float radius;
    final MediaPlayer mp = MediaPlayer.create(context, R.raw.batsound);
    final MediaPlayer mp2 = MediaPlayer.create(context, R.raw.bricksound);
    final MediaPlayer batSound = MediaPlayer.create(context, R.raw.batsound);
    final MediaPlayer brickSound = MediaPlayer.create(context, R.raw.bricksound);
    final MediaPlayer deathSound = MediaPlayer.create(context, R.raw.deathsound);

    Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);

    public boolean piercePowerup = false;

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

        startSpeed = 12;
        speedX = -startSpeed;
        speedY = -startSpeed;

        radius = objRect.right - objRect.left;
        canMove = false;
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
            piercePowerup = false;
        }

        if (objRect.top >= scrHeight)
        {
            resetBall();
            deathSound.start();
            v.vibrate(100);
        }

        if (canMove)
        {
            Move();
        }
    }

    public void fireBall()
    {
        if (!canMove)
        {
            canMove = true;
            Random rnd = new Random();
            if (rnd.nextInt(2) == 0)
            {
                speedX *= -1;
            }
        }
    }

    private void resetBall()
    {
        canMove = false;
        positionBall();
        speedX = -startSpeed;
        speedY = -startSpeed;
        GameWorld.getPlayer().lives--;
    }

    public void positionBall()
    {
        RectF playerRect = GameWorld.getPlayer().getObjRect();
        objRect.left = playerRect.left - (radius / 2) + ((playerRect.right - playerRect.left) / 2);
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
            if (!piercePowerup)
            {
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
            }

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
