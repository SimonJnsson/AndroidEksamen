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
    int scrWidth, scrHeight, speedPowerupTimer, paddlePowerupTimer;
    private float speed;
    private boolean speedPowerup = false, ballPowerup = false, paddlePowerup = false;

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
        speed = 5;
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
            speedPowerupTimer++;
            if (speedPowerupTimer >= 150)
            {
                speedPowerup = false;
                speed = 5;
                speedPowerupTimer = 0;
            }
        }
        if (paddlePowerup)
        {
            paddlePowerupTimer++;
            if (paddlePowerupTimer >= 150)
            {
                GameWorld.player.objRect.right -= 80;
                GameWorld.player.objRect.left += 80;
                GameWorld.player.reScale(this);
                paddlePowerup = false;
                paddlePowerupTimer = 0;
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (objRect.left + (event.values[1] * speed) > 0 && objRect.right + (event.values[1] * speed) < scrWidth)
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
            if (((Powerup) other).getPowerType() == Powerup.Type.SPEEDBOOST) //red
            {
                speed = 10;
                speedPowerup = true;
            }
            else if (((Powerup) other).getPowerType() == Powerup.Type.EXTRABALL) //green
            {
                Log.v("LOG", "Extra ball powerup collected");
                GameWorld.gameObjects.add(new ExtraBall(context,
                        GameWorld.getScreenX() / 2 - Math.round(GameWorld.getScreenX() * 0.016f),
                        GameWorld.getScreenY() - Math.round(GameWorld.getScreenX() * 0.016f),
                        GameWorld.getScreenX() / 2 + Math.round(GameWorld.getScreenX() * 0.016f),
                        GameWorld.getScreenY() + Math.round(GameWorld.getScreenX() * 0.016f),
                        R.drawable.extraball
                ));
            }
            else if (((Powerup) other).getPowerType() == Powerup.Type.LARGERPADDLE) //blue
            {
                if (!paddlePowerup)
                {
                    GameWorld.player.objRect.right += 80;
                    GameWorld.player.objRect.left -= 80;
                    GameWorld.player.reScale(this);
                    paddlePowerup = true;
                }
            }
            else if (((Powerup) other).getPowerType() == Powerup.Type.PIERCING) //yellow
            {
                GameWorld.ball.piercePowerup = true;
            }


            GameWorld.getGameObjects().remove(other);
        }

    }
}
