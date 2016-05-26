package com.example.simon.ballapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class howToActivity extends AppCompatActivity implements View.OnClickListener
{
    // Field for button
    private Button btnBackHelp;

    // When activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set the content view to How to activity
        setContentView(R.layout.activity_how_to);
        // Setup referenceto the back button
        btnBackHelp = (Button) findViewById(R.id.btnBackHelp);
        // Create an onClickListener for the button to capture clicks
        btnBackHelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        // If the clicked element is our button
        if (v.equals(btnBackHelp))
        {
            // Setup intent to open the Menu activity
            Intent i = new Intent(this, MenuActivity.class);
            // Start the new activity
            startActivity(i);
        }
    }
}
