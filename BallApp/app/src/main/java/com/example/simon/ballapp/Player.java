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
    private float speed;

    public Player(Context context, int left, int top, int right, int bottom)
    {
        super(context, left, top, right, bottom);

        paint.setColor(0xFF00FF00);

        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accel = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);

        metrics = Resources.getSystem().getDisplayMetrics();
        scrHeight = metrics.heightPixels;
        scrWidth = metrics.widthPixels;

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

    }
}
