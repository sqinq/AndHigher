package com.shirleyqin.andhigher;

import android.content.Context;
import android.content.res.Resources;

import com.shirleyqin.andhigher.Model.groundLevel;

/**
 * Created by shirleyqin on 2017-11-02.
 */

public class TileHeightTranslater {
    static Context mContext;
    static float baseLine;
    static float tileHeight;

    public static void init(Context context) {
        mContext = context;

        Resources r = mContext.getResources();
        baseLine = r.getDimension(R.dimen.baseline);
        tileHeight = r.getDimension(R.dimen.tile_height);
    }

    public static int levelToHeight(groundLevel g) {
        {
            int height;
            switch (g) {
                case UG:
                    height = (int)(baseLine + tileHeight);
                    break;
                case GD:
                    height = (int)baseLine;
                    break;
                case L1:
                    height = (int)(baseLine - tileHeight);
                    break;
                case L2:
                    height = (int)(baseLine - tileHeight * 2);
                    break;
                default:
                    height = (int)baseLine;
            }
            return height;
        }
    }
}
