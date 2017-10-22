package com.shirleyqin.andhigher.View;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shirleyqin.andhigher.Model.NoteModel;
import com.shirleyqin.andhigher.R;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shirleyqin on 2017-10-11.
 */

public class WalkingNoteView extends ImageView implements Observer {
    private AnimationDrawable animi;

    final int width = 100;
    final int height = 120;



    public WalkingNoteView(Context context) {
        super(context);
    }

    public WalkingNoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WalkingNoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setupAnimi(int id) {
        this.setBackgroundResource(id);
        this.setLayoutParams(new ViewGroup.LayoutParams(width, height));
    }

    public AnimationDrawable getAnimi() {
        return animi;
    }


    public void setAnim(AnimationDrawable anim){
        this.animi = anim;
    }


    @Override
    public void update(Observable observable, Object o) {
        NoteModel note = (NoteModel) observable;
        this.setY(note.getNoteY());
        this.setX(note.getNoteX());
    }
}
