package com.shirleyqin.andhigher.Activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.shirleyqin.andhigher.Model.Model;
import com.shirleyqin.andhigher.Model.NoteModel;
import com.shirleyqin.andhigher.Model.groundLevel;
import com.shirleyqin.andhigher.R;
import com.shirleyqin.andhigher.View.GroundView;
import com.shirleyqin.andhigher.View.NoteView;
import com.shirleyqin.andhigher.View.WalkingNoteView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends AppCompatActivity {

    NoteModel note;
    WalkingNoteView noteView;
    GroundView ground;


    private AnimationDrawable anim = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout frame = (FrameLayout) findViewById(R.id.frameLayout);




        noteView = new WalkingNoteView(this);
        noteView.setupAnimi(R.drawable.walking_note);
        note = new NoteModel();
        note.addObserver(noteView);
        anim = (AnimationDrawable) noteView.getBackground();
        noteView.setAnim(anim);
        frame.addView(noteView);

         frame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                note.jump(NoteModel.initSpeed);

                return false;
            }
        });



        ground = new GroundView(GameActivity.this, 200);
        ground.setNoteModel(note);
        frame.addView(ground);


    }

    @Override
    protected void onResume() {
        super.onResume();
        anim.start();
        ground.runBackground();
    }


    @Override
    protected void onPause() {
        super.onPause();

        ground.stopGame();
    }
}
