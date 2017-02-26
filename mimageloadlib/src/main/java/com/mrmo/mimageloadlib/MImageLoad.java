package com.mrmo.mimageloadlib;

import android.util.Log;

/**
 * Created by moguangjian on 2017/2/26.
 */
public class MImageLoad {

    private static final String TAG = MImageLoad.class.getSimpleName();
    private static MImageLoadAble mImageLoadAble;

    public static void init(MImageLoadAble imageLoadAble) {
        if (mImageLoadAble != null) {
            mImageLoadAble = null;
        }

        mImageLoadAble = imageLoadAble;
    }

    public static synchronized MImageLoadAble getInstance() {
        if (mImageLoadAble == null) {
            Log.e(TAG, "请在使用之前先调用 init(MImageLoadAble imageLoadAble)");
        }

        return mImageLoadAble;
    }

    private MImageLoad() {
    }
}
