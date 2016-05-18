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

/**
 * Created by Danamian on 10-05-2016.
 */
public class Brick extends GameObject
{
    private Bitmap bitmap;

    public Brick(Context context, float screenX, float screenY, float screenHeight, float screenWidth)
    {
        super(context, screenX, screenY, screenHeight, screenWidth);

        paint.setColor(0xFF00FF00);
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public void setBitmap(int id)
    {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), id);
    public void destroy()
    {
        GameWorld.getGameObjects().remove(this); // Remove the other object
    }

    @Override
    void onCollision(GameObject other)
    {
    }
}
