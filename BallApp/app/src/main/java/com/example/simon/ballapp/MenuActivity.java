package com.example.simon.ballapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener
{
    // Fields for needed button
    Button btnPlay, btnOptions, btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Get references for buttons
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnOptions = (Button) findViewById(R.id.btnOptions);
        btnHelp = (Button) findViewById(R.id.btnHelp);

        // Apply onClickListeners to the buttons
        btnPlay.setOnClickListener(this);
        btnOptions.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        // Check which button is clicked and start the matching Activity
        if (v.equals(btnPlay))
        {
            Intent i = new Intent(this, TiltBallActivity.class);
            startActivity(i);
            finish();
        }
        else if (v.equals(btnOptions))
        {
            Intent i = new Intent(this, OptionsMenuActivity.class);
            startActivity(i);
            finish();
        }
        else if (v.equals(btnHelp))
        {
            Intent i = new Intent(this, howToActivity.class);
            startActivity(i);
            finish();
        }

    }
}
