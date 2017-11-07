package com.shirleyqin.andhigher.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shirleyqin.andhigher.Model.NoteModel;
import com.shirleyqin.andhigher.Model.Tile;
import com.shirleyqin.andhigher.R;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shirleyqin on 2017-10-11.
 */

public class NoteView extends ImageView implements Observer {
    private AnimationDrawable animi;

    float width;
    float height;
    float levelHeight;
    private float initSpeed;

    float verticalSpeed = 0;

    private NoteModel model;

    public NoteView(Context context) {
        super(context);

        Resources r = getResources();
        width = r.getDimension(R.dimen.note_width);
        height = r.getDimension(R.dimen.note_height);
        levelHeight = r.getDimension(R.dimen.tile_height);
        initSpeed = r.getDimension(R.dimen.jump_speed);

        model = new NoteModel();
        model.setNoteHeight(height);
        model.setItemHeight(r.getDimension(R.dimen.item_height));
        model.addObserver(this);
    }

    public NoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setupAnimi(int id) {
        this.setBackgroundResource(id);
        this.setLayoutParams(new ViewGroup.LayoutParams((int)width, (int)height));
        animi = (AnimationDrawable) this.getBackground();
    }

    public void startAnimi() {
        animi.start();
    }

    public NoteModel getModel() {
        return model;
    }


    @Override
    public void update(Observable observable, Object o) {
        float speed;
        if (o == null)
            speed = initSpeed;
        else
            speed = (float) o;
        if (model.isJumping) {
            verticalSpeed = speed;
        } else {
            model.isJumping = true;
            jump(speed);
        }
    }


    private void jump(final float initSpeed) {
        verticalSpeed = initSpeed;
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                verticalSpeed -= 0.35;

                final float Y = getY();
                //check if there is a tile to land on
                float landOn = model.landOnHeight(Y, verticalSpeed, height);
                if (landOn > 0) {
                    setY(landOn);
                    model.refreshLocation(getX(), getY());
                    verticalSpeed = 0;
                    postInvalidate();

                    model.isJumping = false;
                    model.jumpCount = 0;
                    this.cancel();
                }

                //if the note is jumping out of the screen
                if (Y < 0) {
                    setY(0);
                    model.refreshLocation(getX(), getY());
                    verticalSpeed = 0;
                    postInvalidate();

                    //if the note falls off the screen
                } else if (model.checkOffScreen(Y)) {
                    Looper.prepare();
                    Handler mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            model.stopGame();
                        }
                    };
                    mHandler.sendEmptyMessage(1);
                    Looper.loop();
                    this.cancel();
                } else {
                    setY(Y-verticalSpeed);
                    model.refreshLocation(getX(), getY());
                    postInvalidate();
                }
            }
        };
        new Timer().schedule(task, 0, 1000 / 30);
    }
}
