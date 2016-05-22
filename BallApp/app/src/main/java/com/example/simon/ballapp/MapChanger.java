package com.example.simon.ballapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by Danamian on 12-05-2016.
 */
public class MapChanger implements SensorEventListener
{
    private SensorManager mSensorManager;
    private Sensor mLight;

    public boolean isNight() {
        return isNight;
    }

    private boolean isNight;
    private Context context;


    public MapChanger(Context context)
    {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, mLight, mSensorManager.SENSOR_DELAY_NORMAL);
        isNight = false;
        this.context = context;
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark1);
        if (event.sensor.getType() == Sensor.TYPE_LIGHT && event.values[0] <= 150 && !isNight)
        {
            isNight = true;
            for (GameObject obj : GameWorld.getGameObjects())
            {
                if (obj instanceof Brick)
                {
                    switch (obj.getId())
                    {
                        case 2130837589:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark1);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                        case 2130837590:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark2);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                        case 2130837591:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark3);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                        case 2130837592:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark4);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                        case 2130837593:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark5);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                        case 2130837594:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark6);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                    }
                }
            }
        }
        else if (event.sensor.getType() == Sensor.TYPE_LIGHT && event.values[0] >= 400 && isNight)
        {
            isNight = false;
            for (GameObject obj : GameWorld.getGameObjects())
            {
                if (obj instanceof Brick)
                {
                    switch (obj.getId())
                    {
                        case 2130837589:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.light1);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                        case 2130837590:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.light2);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                        case 2130837591:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.light3);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                        case 2130837592:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.light4);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                        case 2130837593:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.light5);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                        case 2130837594:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.light6);
                            obj.setResizedBitmap(obj, bitmap);
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
