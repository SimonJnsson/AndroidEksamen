package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

public class Player extends GameObject implements SensorEventListener
{
    private int color;
    DisplayMetrics metrics;
    int lives;

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    int score;
    private RectF startRect;
    int scrWidth, scrHeight, powerupTimer;
    private float speed;
    private boolean speedPowerup = false, ballPowerup = false;


    public Player(Context context, int left, int top, int right, int bottom, int id)
    {
        super(context, left, top, right, bottom, id);

        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accel = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);

        metrics = Resources.getSystem().getDisplayMetrics();
        scrHeight = metrics.heightPixels;
        scrWidth = metrics.widthPixels;
        paint.setColor(0xFF00FF00);

        lives = 3;
        score = 0;
        speed = 3;
    }

    @Override
    public void update()
    {
        super.update();

        if (objRect.left < 0)
        {
            objRect.left = 0;
        }
        else if (objRect.right > scrWidth)
        {
            objRect.right = scrWidth;
        }

        if (speedPowerup)
        {
            powerupTimer++;
            if (powerupTimer <= 300)
            {
                speedPowerup = false;
                speed = 3;
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (objRect.left + event.values[1] * speed > 0 && objRect.right + event.values[1] * speed < scrWidth)
        {
            objRect.left += event.values[1] * speed;
            objRect.right += event.values[1] * speed;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

    @Override
    void onCollision(GameObject other)
    {
        if (other instanceof Powerup)
        {
            if (((Powerup) other).getPowerType() == Powerup.Type.SPEEDBOOST)
            {
                Log.v("LOG", "Speed boost powerup collected");
                speed = 6;
                speedPowerup = true;
            }
            else if (((Powerup) other).getPowerType() == Powerup.Type.EXTRABALL)
            {
                Log.v("LOG", "Extra ball powerup collected");
//                Ball newBall = new Ball(context, GameWorld.getScreenX() / 2 - 30, GameWorld.getScreenY() - 100, GameWorld.getScreenX() / 2 + 30, GameWorld.getScreenY() - 40, R.drawable.ball);
//                GameWorld.getGameObjects().add(newBall);
//                newBall.setCanMove(true);
            }
            else if (((Powerup) other).getPowerType() == Powerup.Type.LARGERPADDLE)
            {
                Log.v("LOG", "Larger paddle powerup collected");
            }

            GameWorld.getGameObjects().remove(other);
        }

    }
}
