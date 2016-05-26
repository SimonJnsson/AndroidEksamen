package com.example.simon.ballapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.graphics.RectF;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Random;

public class Ball extends GameObject
{
    // Fields
    DisplayMetrics metrics;
    private boolean soundDisabled;
    SharedPreferences prefs;
    private int scrWidth, scrHeight;
    float startSpeed, speedY, speedX;
    float radius;

    final MediaPlayer batSound;
    final MediaPlayer brickSound;
    final MediaPlayer deathSound;

    Vibrator v;

    public boolean piercePowerup = false;

    private boolean canMove;

    public Ball(Context context, float left, float top, float right, float bottom, int id)
    {
        super(context, left, top, right, bottom, id);

        paint.setColor(0xFF00FF00);
        // Setup the vibrator for use
        v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);

        // Get the shared connetion to use saved setting from options menu
        prefs = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        // Get boolean from SharedPreference
        soundDisabled = prefs.getBoolean("soundBool", true);

        // Get metrics of the display (height and width)
        metrics = Resources.getSystem().getDisplayMetrics();
        scrHeight = metrics.heightPixels;
        scrWidth = metrics.widthPixels;

        // Set startSpeed and X - Y speed
        startSpeed = 12;
        speedX = -startSpeed;
        speedY = -startSpeed;

        radius = objRect.right - objRect.left;

        canMove = false;

        // Load soundfiles
        batSound = MediaPlayer.create(context, R.raw.batsound);
        brickSound = MediaPlayer.create(context, R.raw.bricksound);
        deathSound = MediaPlayer.create(context, R.raw.deathsound);
    }

    @Override
    public void update()
    {
        super.update();

        // If the ball isn't supposed to move
        if (!canMove)
        {
            // Position the ball above the player
            positionBall();
        }
        else
        {
            // Move the ball
            Move();
        }

        // If the ball hits the left or right border
        if (objRect.right >= scrWidth || objRect.left <= 0)
        {
            // Revert the
            RevertX();
        }

        // If the ball hits the top
        if (objRect.top <= 0)
        {
            RevertY();
            piercePowerup = false;
        }

        // if the ball falls below the bottom
        if (objRect.top >= scrHeight)
        {
            // Reset the ball
            resetBall();
            // If sound is enabled
            if (!soundDisabled)
            {
                // Play the sound
                deathSound.start();
            }
            // Vibrate for 100ms
            v.vibrate(100);
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

    // Used to reset the ball to proper position and subtract a life from player
    private void resetBall()
    {
        // Stop the ball from moving
        canMove = false;
        // Position the ball above the player
        positionBall();
        // Reset speed
        speedX = -startSpeed;
        speedY = -startSpeed;
        // Subtract life
        GameWorld.getPlayer().lives--;
    }

    // Used to keep the ball above the player
    public void positionBall()
    {
        // Setup a RectF corresponding to the player rect
        RectF playerRect = GameWorld.getPlayer().getObjRect();
        // Position in center & above
        objRect.left = playerRect.left - (radius / 2) + ((playerRect.right - playerRect.left) / 2);
        objRect.top = playerRect.top - radius;
        objRect.right = playerRect.left + radius + ((playerRect.right - playerRect.left) / 2);
        objRect.bottom = playerRect.top;
    }

    // Used to move the ball
    public void Move()
    {
        // Add speedX to left and right
        objRect.left += speedX;
        objRect.right += speedX;

        // Add speedY to top and bottom
        objRect.top += speedY;
        objRect.bottom += speedY;
    }

    // Used to revers the speed
    public void RevertX()
    {
        speedX *= -1;
    }

    public void RevertY()
    {
        speedY *= -1;
    }


    // Called when two gameobjects collide
    @Override
    void onCollision(GameObject other)
    {
        // if the ball collides with a Brick
        if (other instanceof Brick)
        {
            // Add to player score
            GameWorld.getPlayer().setScore(GameWorld.getPlayer().getScore() + 1);
            // if the piercing powerup is not active
            if (!piercePowerup)
            {
                // Check where the ball hits the Brick
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

            // If sound is not disabled
            if (brickSound.isPlaying())
            {
                // Play sound
                brickSound.stop();
            }
            if (!soundDisabled)
            {
                brickSound.start();
            }

            // Destroy the brick
            ((Brick) other).destroy();
        }
        else if (other instanceof Player) // If the ball collides with the Player
        {
            // Play sound if enabled
            if (batSound.isPlaying())
            {
                batSound.stop();
            }
            if (!soundDisabled)
            {
                batSound.start();
            }

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
