package com.example.simon.ballapp;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameWorld extends SurfaceView implements Runnable
{
    static volatile boolean playing;
    static private boolean gameEnded;

    static Thread gameThread = null;
    private int screenX;
    private int screenY;
    private Context context;

    // Sound
    private SoundPool soundPool;
    int start = -1;
    int bump = -1;
    int destroyed = -1;
    int win = -1;

    //Game objects
    private Player player;
    static ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

    // For drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    static ArrayList<GameObject> getGameObjects()
    {
        return gameObjects;
    }

    public GameWorld(Context context, int x, int y)
    {
        super(context);
        this.context = context;

        // Attempt to load sounds
        loadSounds();

        // Initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        // Start the game
        startGame();
    }

    private void loadSounds()
    {
        // This SoundPool is deprecated but don't worry
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try
        {
            //Create objects of the 2 required classes
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            //create our three fx in memory ready for use
            descriptor = assetManager.openFd("start.ogg");
            start = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("win.ogg");
            win = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("bump.ogg");
            bump = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("destroyed.ogg");
            destroyed = soundPool.load(descriptor, 0);
        }
        catch (IOException e)
        {
            //Print an error message to the console
            Log.e("error", "failed to load sound files");
        }
    }

    public static float randNum(float min, float max)
    {
        Random rand = new Random();

        // nextInt is normally exclusive of the top value, so add 1 to make it inclusive
        float randomNum = rand.nextFloat() * (max - min) + min;

        return randomNum;
    }

    private void startGame()
    {
        gameEnded = false;
        playing = true;

        //Initialize game objects
        player = new Player(context, screenX / 2, screenY / 2, 10);

        gameObjects.add(player);

        //soundPool.play(start, 1, 1, 0, 0, 1);
    }

    @Override
    public void run()
    {
        while (playing)
        {
            if (!gameEnded)
            {
                update();
            }

            draw();
            control();
        }
    }

    static public void pause()
    {
        playing = false;
        gameEnded = true;
        try
        {
            gameThread.join();
        }
        catch (InterruptedException e)
        {

        }
    }

    // Make a new thread and start it
    // Execution moves to our R
    public void resume()
    {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void update()
    {
        // Collision detection on new positions
        // Before move because we are testing last frames
        // position which has just been drawn
        boolean hitDetected = false;

        if (hitDetected)
        {
            soundPool.play(bump, 1, 1, 0, 0, 1);
        }

        // Update the GameObjects
        for (GameObject go : gameObjects)
        {
            go.update();
        }

        //Completed the game!
//        if (won)
//        {
//            // Now end the game
//            gameEnded = true;
//        }
    }

    private void draw()
    {
        if (ourHolder.getSurface().isValid())
        {
            //First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();


            // Rub out the last frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // Test draw circle
            //paint.setColor(0xFF00FF00);

            for (GameObject go : gameObjects)
            {
                canvas.drawCircle(go.x, go.y, go.getR(), go.getPaint());
            }

            if (gameEnded)
            {
                //this happens when the game is ended
                // Show pause screen
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over", screenX / 2, 100, paint);
            }


            // For debugging
            // Switch to white pixels
//            paint.setColor(Color.argb(255, 255, 255, 255));
//
//            // Draw Hit boxes
//            canvas.drawRect(player.getHitbox().left,
//                    player.getHitbox().top,
//                    player.getHitbox().right,
//                    player.getHitbox().bottom,
//                    paint);
//

            // Draw the player
            //canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);

            // Draw the enemies
//            for (GameObject go : gameObjects)
//            {
//                canvas.drawBitmap(go.getBitmap(), go.getX(), go.getY(), paint);
//            }

            {
//                if (!gameEnded)
//                {
//                    // Draw the hud
//                    paint.setTextAlign(Paint.Align.LEFT);
//                    paint.setColor(Color.argb(255, 255, 255, 255));
//                    paint.setTextSize(25);
//                    canvas.drawText("Fastest: s", 10, 20, paint);
//                    //canvas.drawText("Time: " + timeTaken + "s", screenX / 2, 20, paint);
//                    canvas.drawText("Distance: KM", screenX / 3, screenY - 20, paint);
//                }
//                else if (gameEnded && timeTaken > 0)
//                {
//                    //this happens when the game is ended
//                    // Show pause screen
//                    paint.setTextSize(80);
//                    paint.setTextAlign(Paint.Align.CENTER);
//                    canvas.drawText("Game Over", screenX / 2, 100, paint);
//                    paint.setTextSize(25);
//                    canvas.drawText("Fastest: s", screenX / 2, 160, paint);
//                }
            }


            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control()
    {
        try
        {
            gameThread.sleep(17);
        }
        catch (InterruptedException e)
        {
        }
    }

    // SurfaceView allows us to handle the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        // There are many different events in MotionEvent
        // We care about just 2 - for now.

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {

            // Has the player lifted their finger up?
            case MotionEvent.ACTION_UP:
                break;
            // Has the player touched the screen?
            case MotionEvent.ACTION_DOWN:
                // If we are currently on the pause screen, start a new game
                if (gameEnded)
                {
                    startGame();
                }
                break;
        }
        return true;
    }
}

