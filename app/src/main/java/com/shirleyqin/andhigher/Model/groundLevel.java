package com.shirleyqin.andhigher.Model;

import android.content.res.Resources;

/**
 * Created by shirleyqin on 2017-10-05.
 */

public enum groundLevel {
    UG, GD, L1, L2;

    public int getHeight(float baseLine, float height) {
        int h;
        switch (this) {
            case UG:
                h = (int)(baseLine + height);
                break;
            case GD:
                h = (int)baseLine;
                break;
            case L1:
                h = (int)(baseLine - height);
                break;
            case L2:
                h = (int)(baseLine - height * 2);
                break;
            default:
                h = (int)baseLine;
        }
        return h;
    }
}
