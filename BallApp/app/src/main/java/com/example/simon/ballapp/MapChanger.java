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
    //Lets us acces the sensors availble on the phone
    private SensorManager mSensorManager;
    //Light sensor
    private Sensor mLight;

    public boolean isNight()
    {
        return isNight;
    }

    private boolean isNight;
    private Context context;

    //Constructor
    public MapChanger(Context context)
    {
        //Tells the sensormanager what the current context is.
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        //Declares that we want to use the light sensor
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //We limit the light sensor update so it wont be trying to get information faster than we really need. For performance.
        mSensorManager.registerListener(this, mLight, mSensorManager.SENSOR_DELAY_NORMAL);
        isNight = false;
        this.context = context;
    }
    //Checks with the light sensor. If its under 50 were gonna change the map to dark. If not then light.
    // f it's changing were gonna change the texture/drawable and resize the object to make sure they fit.
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        Bitmap bitmap;
        if (event.sensor.getType() == Sensor.TYPE_LIGHT && event.values[0] <= 50 && !isNight)
        {
            isNight = true;
            for (GameObject obj : GameWorld.getGameObjects())
            {
                int test = obj.getId();
                if (obj instanceof Brick)
                {
                    switch (obj.getId())
                    {
                        case 2130837589:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark1);
                            break;
                        case 2130837590:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark2);
                            break;
                        case 2130837591:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark3);
                            break;
                        case 2130837592:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark4);
                            break;
                        case 2130837593:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark5);
                            break;
                        case 2130837594:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark6);
                            break;
                        default:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark1);
                            break;
                    }
                    obj.setResizedBitmap(obj, bitmap);
                }
            }
        }
        else if (event.sensor.getType() == Sensor.TYPE_LIGHT && event.values[0] >= 70 && isNight)
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
                            break;
                        case 2130837590:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.light2);
                            break;
                        case 2130837591:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.light3);
                            break;
                        case 2130837592:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.light4);
                            break;
                        case 2130837593:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.light5);
                            break;
                        case 2130837594:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.light6);
                            break;
                        default:
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark1);
                            break;
                    }
                    obj.setResizedBitmap(obj, bitmap);
                }
            }
        }
    }
    //Abstract method from SensorEventListener
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
