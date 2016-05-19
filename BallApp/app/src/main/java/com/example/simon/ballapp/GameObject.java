package com.example.simon.ballapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

abstract public class GameObject
{
    protected float x, y;
    protected RectF objRect;

    public RectF getObjRect()
    {
        return objRect;
    }

    protected Context context;

    public Paint getPaint()
    {
        return paint;
    }

    protected Paint paint;

    public Bitmap getBitmap()
    {
        return resizedBitmap;
    }

    protected Bitmap bitmap;
    protected Bitmap resizedBitmap;

    // Constructor
    public GameObject(Context context, float left, float top, float right, float bottom, int id)
    {
        objRect = new RectF(left, top, right, bottom);
        x = left;
        y = top;

        bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int)Math.round(right) - (int)Math.round(left), (int)Math.round(bottom) - (int)Math.round(top), false);

        paint = new Paint();
        this.context = context;
    }

    public void update()
    {
        for (GameObject go : GameWorld.getGameObjects())
        {
            if (go != this && hasCollision(go))
            {
                onCollision(go);
            }
        }
    }

    public boolean hasCollision(GameObject go)
    {
        // Return true if the 2 rectangles intersect
        return RectF.intersects(objRect, go.getObjRect());
    }

    abstract void onCollision(GameObject other);
}
