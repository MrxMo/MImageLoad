package com.mrmo.mimageloadlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * * 图片加载。Glide加载处理。
 * Created by moguangjian on 2017/2/26.
 */

public class MGlide implements MImageLoadAble {

    private static final String TAG = MGlide.class.getSimpleName();

    private MImageOptions mImageOptions;
    private Context context;

    public MGlide(Context context, MImageOptions mImageOptions) {
        init(context, mImageOptions);
    }

    @Override
    public void init(Context context, MImageOptions mImageOptions) {
        this.context = context;
        if (mImageOptions == null) {
            mImageOptions = new MImageOptions();
        }
        this.mImageOptions = mImageOptions.clone();

    }

    @Override
    public void cleanCache() {
        clearCacheMemory();
        clearCacheDisk();
    }

    @Override
    public void pause() {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void resume() {
        Glide.with(context).resumeRequests();
    }

    /**
     * 注意必须在子线程几获取
     * @param imageUrl
     * @return
     */
    @Override
    public String getCacheFilePath(String imageUrl) {
        String path = "";
        FutureTarget<File> future = Glide.with(context)
                .load(imageUrl)
                .downloadOnly(500, 500);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();

        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return path;
    }

    @Override
    public MImageOptions getImageOptions() {
        return mImageOptions;
    }

    @Override
    public long getCacheSize() {
        try {
            File file = new File(context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR);
            MImageLoadUtil.getFolderSize(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public String getCacheSizeFormat() {

        return MImageLoadUtil.getFormatSize(getCacheSize());
    }

    @Override
    public void displayImage(ImageView imageView, String imageUrlOrPath) {
        displayImage(imageView, imageUrlOrPath, -1);
    }

    @Override
    public void displayImage(ImageView imageView, String imageUrlOrPath, int defaultImageResId) {
        MImageOptions mImageOptions = null;
        if (defaultImageResId <= -1) {
            mImageOptions = getImageOptions();

        } else {
            mImageOptions = new MImageOptions();
            mImageOptions.setDefaultImageLoading(defaultImageResId);
            mImageOptions.setDefaultImageEmptyUri(defaultImageResId);
            mImageOptions.setDefaultImageFailure(defaultImageResId);
        }

        displayImage(imageView, imageUrlOrPath, mImageOptions);
    }

    @Override
    public void displayImage(ImageView imageView, String imageUrlOrPath, MImageOptions mImageOptions) {
        Glide.with(imageView.getContext())
                .load(imageUrlOrPath)
//                .fitCenter()
//                .skipMemoryCache( true ) //跳过内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.NONE) // 跳过硬盘缓存
//                .override(300, 200) // 设置大小
//                .asGif() // 判断加载的url资源是否为gif格式的资源
                .placeholder(mImageOptions.getDefaultImageFailure())
                .error(mImageOptions.getDefaultImageFailure())
                .into(imageView);
    }

    /**
     * 清除Glide内存缓存
     * @return
     */
    private boolean clearCacheMemory() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 清除图片磁盘缓存
     * @return
     */
    private boolean clearCacheDisk() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Nullable
    public Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        if (imageUrl == null) {
            return bitmap;
        }

        try {
            bitmap = Glide.with(context)
                    .load(imageUrl)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
