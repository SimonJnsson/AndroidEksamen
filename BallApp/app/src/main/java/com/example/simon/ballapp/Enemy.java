package com.example.simon.ballapp;

import android.content.Context;

import java.util.Random;

public class Enemy extends GameObject
{
    public float speed;
    private GameObject target;

    public Enemy(Context context, int screenX, int screenY, float radius)
    {
        super(context, screenX, screenY, radius);

        paint.setColor(0xFFFF0000);

        speed = 1f;
    }

    @Override
    public void update()
    {
        super.update();

        // Enemy update logic
        Move();
    }

    public void Move()
    {
        findTarget();

        if (target != null)
        {
            float deltaX = target.getX() - x;
            float deltaY = target.getY() - y;

            double angle = Math.atan2(deltaY, deltaX) * 180 / Math.PI;

            x += Math.cos(angle * Math.PI / 180) * speed;
            y += Math.sin(angle * Math.PI / 180) * speed;
        }
    }

    public void findTarget()
    {
        double smallestDist;

        if (target == null)
        {
            smallestDist = 10000;
        }
        else
        {
            smallestDist = distToTarget(target);
        }

        GameObject tmpTarget = GameWorld.getGameObjects().get(0);

//        for (GameObject obj : GameWorld.getGameObjects())
//        {
//            if (distToTarget(obj) < smallestDist && obj != this)
//            {
//                tmpTarget = obj;
//                smallestDist = distToTarget(obj);
//            }
//        }

        target = tmpTarget;
    }

    private double distToTarget(GameObject target)
    {
        return Math.sqrt((target.getX() - x) * (target.getX() - x) + (target.getX() - y) * (target.getY() - y));
    }
}
