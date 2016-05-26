package com.example.simon.ballapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ScoreActivity extends Activity implements View.OnClickListener
{
    // Fields
    Button btnConfirm;
    EditText etUsername;
    TextView highscoreView;
    Toast toast;
    DBHandler db;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score);
        // Setup toast to show if user input is wrong
        toast = Toast.makeText(this, "Invalid name", Toast.LENGTH_SHORT);

        // Get reference to elements in activity
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        etUsername = (EditText) findViewById(R.id.etUsername);
        highscoreView = (TextView) findViewById(R.id.scoreView);

        // Get focus on the EditText to start activity focused here
        etUsername.requestFocus();

        // Get the score passed from the Gameactivity
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            score = extras.getInt("score");
        }

        // Setup AdView
        AdView mAdView = (AdView) findViewById(R.id.adView);
        // Request for ad
        AdRequest adRequest = new AdRequest.Builder().build();
        // Load the ad
        mAdView.loadAd(adRequest);

        // Setup database handler
        db = new DBHandler(this, null);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        // If the Confirm button is clicked
        if (v.equals(btnConfirm))
        {
            // Check if username is not empty
            if (!etUsername.getText().toString().equals(""))
            {
                // Create a new user with given username and the score passed from the GameActivity
                User user = new User(etUsername.getText().toString(), score);
                db.adduser(user);

                // Display the formatted content of the database
                highscoreView.setText(db.ToString(true));

                // Make the TextView visible
                highscoreView.setVisibility(View.VISIBLE);
                // Hide the other elements
                btnConfirm.setVisibility(View.INVISIBLE);
                etUsername.setVisibility(View.INVISIBLE);
            }
            else // If the username is empty
            {
                // Show a toast telling the uer that the username is empty
                toast.show();
            }
        }
    }

    // Called when a button on the phone is pressed
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // If the BACK button is pressed
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Load the Game activity again
            Intent i = new Intent(this, TiltBallActivity.class);
            // Start our GameActivity class via the Intent
            startActivity(i);
            // Now shut this activity down
            finish();
            return true;
        }
        return false;
    }
}
