package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
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
    int scrWidth, scrHeight;

    public Player(Context context, int screenX, int screenY, float radius)
    {
        super(context, screenX, screenY, radius);

        paint.setColor(0xFF00FF00);

        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accel = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);

        metrics = Resources.getSystem().getDisplayMetrics();
        scrHeight = metrics.heightPixels;
        scrWidth = metrics.widthPixels;
    }

    @Override
    public void update()
    {
        super.update();

        // Player update logic

        // If the player moves outside of screen view
        // Move it to the opposite side
        if (x > scrWidth)
        {
            x = 0;
        }
        if (y > scrHeight)
        {
            y = 0;
        }
        if (x < 0)
        {
            x = scrWidth;
        }
        if (y < 0)
        {
            y = scrHeight;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        x += -event.values[0] * 2;
        y += event.values[1] * 2;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
