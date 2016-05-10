package com.example.simon.ballapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPlay,btnOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnOptions = (Button) findViewById(R.id.btnOptions);

        btnPlay.setOnClickListener(this);
        btnOptions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnPlay))
        {
            Intent i = new Intent(this, TiltBallActivity.class);
            startActivity(i);
        }

        if (v.equals(btnOptions))
        {
            Intent i = new Intent(this, OptionsMenuActivity.class);
            startActivity(i);
        }
    }
}
