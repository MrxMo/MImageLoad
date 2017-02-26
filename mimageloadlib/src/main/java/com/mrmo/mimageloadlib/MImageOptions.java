package com.mrmo.mimageloadlib;

/**
 * Created by moguangjian on 2017/2/26.
 */

public class MImageOptions implements Cloneable{
    private int defaultImageLoading = R.mipmap.m_default_image_loading;
    private int defaultImageEmptyUri = R.mipmap.m_default_image_loading;
    private int defaultImageFailure = R.mipmap.m_default_image_loading;

    @Override
    public MImageOptions clone(){
        MImageOptions model = null;
        try {
            model = (MImageOptions) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return model;
    }

    public int getDefaultImageLoading() {
        return defaultImageLoading;
    }

    public void setDefaultImageLoading(int defaultImageLoading) {
        this.defaultImageLoading = defaultImageLoading;
    }

    public int getDefaultImageEmptyUri() {
        return defaultImageEmptyUri;
    }

    public void setDefaultImageEmptyUri(int defaultImageEmptyUri) {
        this.defaultImageEmptyUri = defaultImageEmptyUri;
    }

    public int getDefaultImageFailure() {
        return defaultImageFailure;
    }

    public void setDefaultImageFailure(int defaultImageFailure) {
        this.defaultImageFailure = defaultImageFailure;
    }
}
