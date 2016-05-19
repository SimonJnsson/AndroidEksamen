package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import java.util.Random;

/**
 * Created by Danamian on 10-05-2016.
 */
public class Brick extends GameObject
{
    public Brick(Context context, float screenX, float screenY, float screenHeight, float screenWidth)
    {
        super(context, screenX, screenY, screenHeight, screenWidth);

        paint.setColor(0xFF00FF00);
    }

    public void destroy()
    {
        Random rnd = new Random();
        int r = rnd.nextInt(10);
        if (r==5)
        {
            int r2 = rnd.nextInt(GameWorld.getScreenX());
            GameWorld.gameObjects.add(new Powerup(context, r2-10,20,100,r2+10));
        }
        GameWorld.getGameObjects().remove(this); // Remove the other object
    }

    @Override
    void onCollision(GameObject other)
    {
    }
}
