package com.example.simon.ballapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.Random;

/**
 * Created by Simon on 21-04-2016.
 */
public class Target extends View
{

    Random rand;
    public float x;
    public float y;

    public float getR()
    {
        return r;
    }

    public void setR(float r)
    {
        this.r = r;
    }

    private float r;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //construct new ball object
    public Target(Context context)
    {
        super(context);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        int h = dm.heightPixels;

        //color hex is [transparency][red][green][blue]
        mPaint.setColor(0xFFFF0000);  //not transparent. color is green
        this.r = randNum(10, 50);  //radius
        this.x = randNum(0 + r, w - r);
        this.y = randNum(0 + r, h - r);
    }

    public static float randNum(float min, float max)
    {
        Random rand = new Random();

        // nextInt is normally exclusive of the top value, so add 1 to make it inclusive
        float randomNum = rand.nextFloat() * (max - min) + min;

        return randomNum;
    }

    //called by invalidate()
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, mPaint);
        if (r - 0.01f > 0)
        {
            r -= 0.01f;
            android.util.Log.d("TiltBall", "Drawing");
        }
    }


}
