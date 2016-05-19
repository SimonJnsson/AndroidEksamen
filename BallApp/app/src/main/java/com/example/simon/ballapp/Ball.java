package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.graphics.RectF;
import android.os.Vibrator;
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
    final MediaPlayer batSound = MediaPlayer.create(context,R.raw.batsound);
    final MediaPlayer brickSound = MediaPlayer.create(context,R.raw.bricksound);
    final MediaPlayer deathSound = MediaPlayer.create(context,R.raw.deathsound);
    Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
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
            deathSound.start();
            v.vibrate(100);
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
            if(brickSound.isPlaying())
            {
               brickSound.stop();
            }
            brickSound.start();
            ((Brick) other).destroy();
        }
        else
        {
            if (batSound.isPlaying())
            {
                batSound.stop();
            }
            batSound.start();
            RevertY();
        }
    }
}
