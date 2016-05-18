package com.example.simon.ballapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by Danamian on 12-05-2016.
 */
public class MapChanger implements SensorEventListener
{
    private SensorManager mSensorManager;
    private Sensor mLight;

    public MapChanger(Context context)
    {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, mLight, mSensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        int column = 0;
        int row = 0;

        for (GameObject go : GameWorld.getGameObjects())
        {
            if (go instanceof Brick)
            {
                if (event.sensor.getType() == Sensor.TYPE_LIGHT && event.values[0] <= 250)
                {
                   /* if (go.getPaint().getColor() == ((Brick) go).getDarkColor())
                    {
                        break;
                    }

                    go.getPaint().setColor(((Brick) go).darker(((Brick) go).getLightColor(), 25));
                } else
                {
                    if (go.getPaint().getColor() == ((Brick) go).getLightColor())
                    {
                        break;
                    }

                    go.getPaint().setColor(((Brick) go).getLightColor());
                }*/
                }
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
