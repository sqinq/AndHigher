package com.shirleyqin.andhigher.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shirleyqin.andhigher.Model.NoteModel;
import com.shirleyqin.andhigher.R;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by shirleyqin on 2017-10-11.
 */

public class NoteView extends ImageView implements Observer {
    private AnimationDrawable animi;

    float width;
    float height;

    private NoteModel model;

    public NoteView(Context context) {
        super(context);

        Resources r = getResources();
        width = r.getDimension(R.dimen.item_width);
        height = r.getDimension(R.dimen.item_height);

        model = new NoteModel(width, height);
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
        NoteModel note = (NoteModel) observable;
        this.setY(note.getNoteY());
        this.setX(note.getNoteX());

        if (o != null) {
            boolean jumpUp = (boolean) o;
        }
    }

    public void jumpUp() {
        setBackgroundResource(R.drawable.eighth_note_jumpUp);
    }
}
