package com.example.simon.ballapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class TiltBallActivity extends Activity
{
    // Our object to handle the View
    private GameWorld gameView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getRealSize(size);

        gameView = new GameWorld(this, size.x, size.y);

        // Make our gameView the view for the Activity
        setContentView(gameView);
    } //OnCreate

    @Override
    protected void onResume()
    {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        gameView.pause();
    }
}
