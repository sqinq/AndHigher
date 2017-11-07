package com.shirleyqin.andhigher.Activity;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.shirleyqin.andhigher.Model.Model;
import com.shirleyqin.andhigher.Model.NoteModel;
import com.shirleyqin.andhigher.NoteListener;
import com.shirleyqin.andhigher.R;
import com.shirleyqin.andhigher.TileHeightTranslater;
import com.shirleyqin.andhigher.View.GroundView;
import com.shirleyqin.andhigher.View.NoteView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends AppCompatActivity implements NoteListener {

    NoteView note;
    GroundView ground;

    Model model;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout frame = (FrameLayout) findViewById(R.id.frameLayout);


        note = new NoteView(this);
        note.setX(200);
        note.setupAnimi(R.drawable.walking_note);

        final NoteModel noteModel = note.getModel();
        noteModel.setGameInterface(this);

        frame.addView(note);

         frame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                noteModel.jump();
                return false;
            }
        });


        ground = new GroundView(GameActivity.this, 200);
        ground.setNoteModel(noteModel);
        model = Model.getInstance();
        frame.addView(ground);

        TileHeightTranslater.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        note.startAnimi();
        ground.runBackground();
    }


    @Override
    protected void onPause() {
        super.onPause();

        ground.stopGame();
    }

    @Override
    public void stopGame() {
        ground.stopGame();
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("Game Over")
                .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GameActivity.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean offScreen(float height) {
        return height>=ground.getHeight();
    }
}
