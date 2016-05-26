package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
//inherited GameObject
public class Brick extends GameObject
{
    private int chanceToSpawn = 20;
    Random rnd = new Random();
    int entropyNum = rnd.nextInt(100);
    //Constructor
    public Brick(Context context, float screenX, float screenY, float screenHeight, float screenWidth, int id)
    {
        super(context, screenX, screenY, screenHeight, screenWidth, id);
    }
    //Every time a brick gets destroyed this method has a chance to spawn a powerup
    public void destroy()
    {
        entropyNum += chanceToSpawn;
        if (entropyNum > 100)
        {
            GameWorld.gameObjects.add(new Powerup(context, objRect.left, objRect.top, objRect.left + 50, objRect.top + 50, R.drawable.dark1));
            entropyNum -= 100;
        }

        GameWorld.getGameObjects().remove(this); // Remove the other object
    }
    //Must implement onCollision
    @Override
    void onCollision(GameObject other)
    {
    }
}
