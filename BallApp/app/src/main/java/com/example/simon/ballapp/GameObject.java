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

    public void setResizedBitmap(GameObject object, Bitmap bm)
    {
        this.resizedBitmap = Bitmap.createScaledBitmap(bm, Math.round(object.getObjRect().right) - Math.round(object.getObjRect().left), Math.round(object.getObjRect().bottom) - Math.round(object.getObjRect().top), false);
    }

    protected Bitmap resizedBitmap;

    public int getId() {
        return id;
    }

    protected int id;
    // Constructor
    public GameObject(Context context, float left, float top, float right, float bottom, int id)
    {
        objRect = new RectF(left, top, right, bottom);
        x = left;
        y = top;
        this.id = id;
        bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        resizedBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(right) - Math.round(left), Math.round(bottom) - Math.round(top), false);

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
                break;
            }
        }
    }

    public boolean hasCollision(GameObject go)
    {
        // Return true if the 2 rectangles intersect
        return RectF.intersects(objRect, go.getObjRect());
    }

    public void reScale(GameObject object)
    {
        resizedBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(object.getObjRect().right) - Math.round(object.getObjRect().left), Math.round(object.getObjRect().bottom) - Math.round(object.getObjRect().top), false);
    }

    abstract void onCollision(GameObject other);
}
