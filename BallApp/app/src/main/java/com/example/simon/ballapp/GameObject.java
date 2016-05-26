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

    //X, Y coordinates for our gameobjects
    protected float x, y;

    //objRect handles the rectangle. It's parameters is: Left, Top, Right, Bottom which defines how big the object is gonna be
    protected RectF objRect;

    public RectF getObjRect()
    {
        return objRect;
    }
    //Current state / information of the application
    protected Context context;

    public Paint getPaint()
    {
        return paint;
    }
    //Paint is used for drawing the game objects.
    protected Paint paint;
    //Returns resized bitmap
    public Bitmap getBitmap()
    {
        return resizedBitmap;
    }

    protected Bitmap bitmap;
    //Takes a GameObject and Bitmap and creates a rescaled bitmap.
    public void setResizedBitmap(GameObject object, Bitmap bm)
    {
        this.resizedBitmap = Bitmap.createScaledBitmap(bm, Math.round(object.getObjRect().right) - Math.round(object.getObjRect().left), Math.round(object.getObjRect().bottom) - Math.round(object.getObjRect().top), false);
    }

    protected Bitmap resizedBitmap;
    //Gets the ID of the objects drawable.
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
    //Checks for Collision on every GameObject
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
    //Rescale is used for rescaling the bitmap when were instantiating the object
    public void reScale(GameObject object)
    {
        resizedBitmap = Bitmap.createScaledBitmap(bitmap,
                Math.round(object.getObjRect().right) - Math.round(object.getObjRect().left),
                Math.round(object.getObjRect().bottom) - Math.round(object.getObjRect().top),
                false);
    }

    //Returns true if collision and false if not.
    public boolean hasCollision(GameObject go)
    {
        // Return true if the 2 rectangles intersect
        return RectF.intersects(objRect, go.getObjRect());
    }

    //Abstract method so everyone who inherited this class must implement it.
    abstract void onCollision(GameObject other);
}
