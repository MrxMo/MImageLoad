package com.mrmo.mimageloadlib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by moguangjian on 2017/2/26.
 */

public interface MImageLoadAble {

    String IMAGE_TYPE_HTTP = "http://";
    String IMAGE_TYPE_HTTPS = "https://";
    String IMAGE_TYPE_FILE = "file://";

    /**
     * 初始化
     *
     * @param context
     * @param mImageOptions 图片加载器设置置项
     */
    void init(Context context, MImageOptions mImageOptions);

    /**
     * 清除缓存
     */
    void cleanCache();

    /**
     * 暂停加载图片
     */
    void pause();

    /**
     * 恢复加载图片
     */
    void resume();

    /**
     * 获取缓存图片地址
     *
     * @param imageUrl
     * @return
     */
    String getCacheFilePath(String imageUrl);

    /**
     * 获取默认配置项
     * @return
     */
    @Nullable MImageOptions getImageOptions();

    /**
     * 获取缓存大小
     * @return
     */
    long getCacheSize();

    /**
     * 获取缓存大小.格式成b,kb,m ...
     * @return
     */
    String getCacheSizeFormat();

    void displayImage(ImageView imageView, String imageUrlOrPath);

    /**
     * 加载图片
     *
     * @param imageView
     * @param imageUrlOrPath    网络图片地址 / 本地图片地址
     * @param defaultImageResId 默认图片图片资源
     */
    void displayImage(ImageView imageView, String imageUrlOrPath, int defaultImageResId);

    void displayImage(ImageView imageView, String imageUrlOrPath, MImageOptions mImageOptions);

}
