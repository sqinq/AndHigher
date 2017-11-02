package com.shirleyqin.andhigher.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
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
        width = r.getDimension(R.dimen.item_width);
        height = r.getDimension(R.dimen.item_height);
        levelHeight = r.getDimension(R.dimen.tile_height);
        initSpeed = r.getDimension(R.dimen.jump_speed);

        model = new NoteModel();
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
    }

    public AnimationDrawable getAnimi() {
        return animi;
    }

    public NoteModel getModel() {
        return model;
    }

    public void setAnim(AnimationDrawable anim){
        this.animi = anim;
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
                verticalSpeed -= 0.45;

                final float Y = getY();
                //check if there is a tile to land on
                float height = model.landOnHeight(Y, verticalSpeed, levelHeight);
                if (height > 0) {
                    setY(height);
                    verticalSpeed = 0;
                    postInvalidate();

                    model.isJumping = false;
                    model.jumpCount = 0;
                    this.cancel();
                }

                //if the note is jumping out of the screen
                if (Y < 0) {
                    setY(0);
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
                    postInvalidate();
                }
            }
        };
        new Timer().schedule(task, 0, 1000 / 30);
    }
}
