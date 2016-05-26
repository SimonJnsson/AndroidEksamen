package com.example.simon.ballapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class OptionsMenuActivity extends Activity implements View.OnClickListener
{
    // Fields
    SharedPreferences sharedPreferences;
    Button btnBack;
    Switch switchSound;
    boolean sound;
    public static final String myPref = "myPref";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu);
        // Get reference to buttons
        btnBack = (Button) findViewById(R.id.btnBack);
        switchSound = (Switch) findViewById(R.id.switchSound);

        // Set onClickListeners
        btnBack.setOnClickListener(this);
        switchSound.setOnClickListener(this);

        // Setup sharedpreference to store the value of the Switch
        sharedPreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
        // Create editor to allow editing the sharedPreference
        editor = sharedPreferences.edit();

        // Set the boolean to the stored value in the sharedpreference
        // Default value is 'true'
        sound = sharedPreferences.getBoolean("soundBool", true);
        // Set the value of the Switch to the boolean
        switchSound.setChecked(sound);
    }

    @Override
    public void onClick(View v)
    {
        // Check which element is clicked
        if (v.equals(btnBack))
        {
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        }

        // If the switch is clicked
        if (v.equals(switchSound))
        {
            // Swap value of sound boolean
            sound = !sound;
            // edit the value of 'soundBool'
            // in the sharedPreference
            editor.putBoolean("soundBool", sound);
            // Commit the edit for change to take effect
            editor.commit();
        }

    }
}
