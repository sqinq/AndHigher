package com.shirleyqin.andhigher.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shirleyqin.andhigher.R;

public class MainActivity extends AppCompatActivity {
    Button silent_game, sound_game, climber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void startGame(View view) {
        Intent newInt = new Intent(MainActivity.this, GameActivity.class);
        startActivity(newInt);
    }
}
