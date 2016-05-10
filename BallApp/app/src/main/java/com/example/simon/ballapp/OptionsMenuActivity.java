package com.example.simon.ballapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class OptionsMenuActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    Button btnBack;
    Switch switchSound;
    boolean sound = true;
    public static final String myPref = "myPref";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu);
        btnBack = (Button) findViewById(R.id.btnPlay);
        switchSound = (Switch) findViewById(R.id.switchSound);

        btnBack.setOnClickListener(this);
        switchSound.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnBack))
        {
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        }

        if (v.equals(switchSound))
        {
            sound=!sound;
            editor.putBoolean("soundBool",sound);
        }

    }
}
