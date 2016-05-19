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
import android.view.Display;

public class Player extends GameObject implements SensorEventListener
{
    private int color;
    DisplayMetrics metrics;
    int lives;
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

    @Override
    void onCollision(GameObject other)
    {
        if (other instanceof Powerup)
        {
            if (((Powerup) other).r == 1)
            {
                speed = 6;
                speedPowerup = true;
            }
            else if (((Powerup) other).r == 2)
            {
                GameWorld.gameObjects.add(new Ball(context, GameWorld.getScreenX() / 2 - 5, GameWorld.getScreenY() - 110, GameWorld.getScreenX() / 2 + 5, GameWorld.getScreenY() - 100, R.drawable.ball));

            }
            else if (((Powerup) other).r == 3)
            {

            }

        }

    }
}
