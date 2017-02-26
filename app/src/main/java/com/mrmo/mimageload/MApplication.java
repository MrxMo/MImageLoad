package com.mrmo.mimageload;

import android.app.Application;

import com.mrmo.mimageloadlib.MGlide;
import com.mrmo.mimageloadlib.MImageLoad;
import com.mrmo.mimageloadlib.MImageOptions;

/**
 * Created by moguangjian on 2017/2/26.
 */

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MImageOptions mImageOptions = new MImageOptions();
        mImageOptions.setDefaultImageLoading(R.mipmap.ic_launcher);
        mImageOptions.setDefaultImageEmptyUri(R.mipmap.ic_launcher);
        mImageOptions.setDefaultImageFailure(R.mipmap.ic_launcher);
//        MImageLoad.init(new MImageLoader(this, mImageOptions));
        MImageLoad.init(new MGlide(this, mImageOptions));
    }
}
