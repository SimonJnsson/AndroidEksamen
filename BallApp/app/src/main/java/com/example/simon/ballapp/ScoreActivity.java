package com.example.simon.ballapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ScoreActivity extends Activity implements View.OnClickListener
{
    Button btnConfirm;
    EditText etUsername;
    TextView highscoreView;
    DBHandler db;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score);

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        etUsername = (EditText) findViewById(R.id.etUsername);

        etUsername.requestFocus();
        highscoreView = (TextView) findViewById(R.id.scoreView);


        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            score = extras.getInt("score");
        }

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Setup database handler
        db = new DBHandler(this, null);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.equals(btnConfirm))
        {
            User user = new User(etUsername.getText().toString(), score);
            db.adduser(user);

            highscoreView.setText(db.ToString(true));

            highscoreView.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.INVISIBLE);
            etUsername.setVisibility(View.INVISIBLE);
        }
    }
}
