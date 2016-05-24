package com.example.simon.ballapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class howToActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btnBackHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        btnBackHelp = (Button) findViewById(R.id.btnBackHelp);
        btnBackHelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.equals(btnBackHelp))
        {
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        }
    }
}
