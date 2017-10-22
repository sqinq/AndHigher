package com.shirleyqin.andhigher.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.shirleyqin.andhigher.R;

/**
 * Created by shirleyqin on 2017-10-04.
 */

public class NoteView extends View {
    public float bitmapX;
    public float bitmapY;

    private AnimationDrawable animi;

    public NoteView(Context context) {
        super(context);

        bitmapX = 0;
        bitmapY = GroundView.BASELINE-120;

        WalkingNoteView note = new WalkingNoteView(context);
        animi = note.getAnimi();
    }

    

    public void startAnimi() {
        animi.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

     /*   Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.eighth_note_normal_1);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint);

        if (bitmap.isRecycled()) {
            bitmap.recycle();
        }*/
    }


}
