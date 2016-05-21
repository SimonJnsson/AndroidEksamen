package com.example.simon.ballapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld extends SurfaceView implements Runnable
{
    static volatile boolean playing;
    static private boolean gameEnded;
    private boolean levelCleared;

    static Thread gameThread = null;

    static int getScreenX()
    {
        return screenX;
    }

    static int screenX;

    static int getScreenY()
    {
        return screenY;
    }

    static int screenY;
    private Context context;
    private MapChanger mapChanger;

    // Sound
    private SoundPool soundPool;
    int start = -1;
    int bump = -1;
    int destroyed = -1;
    int win = -1;

    static Player getPlayer()
    {
        return player;
    }
   // static Ball getBall(){return ball;}

    //Game objects
    static Player player;
    static Ball ball;
    static CopyOnWriteArrayList<GameObject> gameObjects = new CopyOnWriteArrayList<GameObject>();

    // For drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;
    private Brick brick;
    private Bitmap bmp;

    static CopyOnWriteArrayList<GameObject> getGameObjects()
    {
        return gameObjects;
    }

    GestureDetector gestureDetector;

    public GameWorld(Context context, int x, int y)
    {
        super(context);
        this.context = context;

        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.bglight);

        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/game_over.ttf");
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener());

        // Initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();
        paint.setTypeface(typeFace);
        mapChanger = new MapChanger(context);

        screenX = x;
        screenY = y;

        levelCleared = false;

        // Start the game
        startGame();
    }

    private void startGame()
    {
        gameObjects.clear();
        gameEnded = false;
        playing = true;

        int distFromBottom = Math.round(screenY * 0.02f);

        //Initialize game objects
        player = new Player(context,
                screenX / 2 - Math.round(screenX * 0.06f),
                screenY - Math.round(screenY * 0.017f) - distFromBottom,
                screenX / 2 + Math.round(screenX * 0.06f),
                screenY + Math.round(screenY * 0.017f) - distFromBottom,
                R.drawable.paddle);


        ball = new Ball(context,
                screenX / 2 - Math.round(screenX * 0.016f),
                screenY - Math.round(screenX * 0.016f),
                screenX / 2 + Math.round(screenX * 0.016f),
                screenY + Math.round(screenX * 0.016f),
                R.drawable.ball);

        gameObjects.add(ball);
        ball.positionBall();
        gameObjects.add(player);
        Spawnbrick();
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
        if (player.lives <= 0)
        {
            gameEnded = true;
        }

        // Update the GameObjects
        for (GameObject go : gameObjects)
        {
            go.update();
        }

        if (bricksCleared())
        {
            levelCleared = true;
            Spawnbrick();
        }
    }

    // Check if all bricks have been removed from the game
    private boolean bricksCleared()
    {
        for (GameObject obj : gameObjects)
        {
            // If the obj is of type Brick
            if (obj instanceof Brick)
            {
                // Bricks still remain in the game,
                // so return false
                return false;
            }
        }

        // If no object of type Brick was found
        // Return true (all bricks have been cleared)
        return true;
    }

    private void draw()
    {
        if (ourHolder.getSurface().isValid())
        {
            //First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            // Rub out the last frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            canvas.drawBitmap(bmp, 0, 0, paint);

            for (GameObject go : gameObjects)
            {
                if (go instanceof Ball)
                {
                    canvas.drawBitmap(go.getBitmap(), go.getObjRect().left, go.getObjRect().top, paint);
                }
                else if (go instanceof Brick)
                {
                    canvas.drawBitmap(go.getBitmap(), go.getObjRect().left, go.getObjRect().top, paint);
                }
                else if (go instanceof Player)
                {
                    canvas.drawBitmap(go.getBitmap(), go.getObjRect().left, go.getObjRect().top, paint);
                }
                else
                {
                    canvas.drawRect(go.getObjRect(), go.getPaint());
                }
            }

            if (gameEnded)
            {
                //this happens when the game is ended
                // Show pause screen
                paint.setTextSize(160);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over", screenX / 2, screenY / 2, paint);
                paint.setTextSize(100);
                canvas.drawText("Tap to view score", screenX / 2, screenY / 2 + 50, paint);
                canvas.drawText("Long press to retry", screenX / 2, screenY / 2 + 100, paint);
            }
            else
            {
                paint.setColor(0xFFFFFFFF);

                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("SCORE: " + player.getScore(), 15, 50, paint);

                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("LIVES: " + player.lives, screenX, 50, paint);
            }

            if (levelCleared)
            {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(100);
                canvas.drawText("Level cleared", screenX / 2, screenY / 2 + 50, paint);

                // Stop displaying level cleared text after time has passed
                new java.util.Timer().schedule(
                        new java.util.TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                // your code here
                                levelCleared = false;
                            }
                        },
                        2500
                );
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
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            // Has the player lifted their finger up?
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                if (gameEnded)
                {
                    Intent i = new Intent(context, ScoreActivity.class);
                    i.putExtra("score", player.getScore());
                    context.startActivity(i);
                }

                ball.fireBall();
                break;
        }
        return true;
    }

    public void Spawnbrick()
    {
        int brickWidth = screenX / 15;
        int brickHeight = screenY / 20;
        float distanceToTop = brickHeight * 3;
        int id = 0;
        for (int column = 0; column < 15; column++)
        {
            for (int row = 0; row < 6; row++)
            {
                switch (row)
                {
                    case 0:
                        id = R.drawable.b1;
                        break;
                    case 1:
                        id = R.drawable.b2;
                        break;
                    case 2:
                        id = R.drawable.b3;
                        break;
                    case 3:
                        id = R.drawable.b4;
                        break;
                    case 4:
                        id = R.drawable.b5;
                        break;
                    case 5:
                        id = R.drawable.b6;
                        break;
                    default:
                        id = R.drawable.b1;
                        break;
                }
                brick = new Brick(context, column * brickWidth, row * brickHeight + distanceToTop, brickWidth * (column + 1), brickHeight * (row + 1) + distanceToTop, id);
                gameObjects.add(brick);
            }
        }
    }
}

