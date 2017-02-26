package com.mrmo.mimageloadlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * 图片加载。使用ImageLoader加载处理。
 * Created by moguangjian on 2017/2/26.
 */
public class MImageLoader implements MImageLoadAble {

    private static final String TAG = MImageLoader.class.getSimpleName();

    private MImageOptions mImageOptions;

    public MImageLoader(Context context, MImageOptions mImageOptions) {
        init(context, mImageOptions);
    }

    @Override
    public void init(Context context, MImageOptions mImageOptions) {
        if (mImageOptions == null) {
            mImageOptions = new MImageOptions();
        }
        this.mImageOptions = mImageOptions.clone();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(mImageOptions.getDefaultImageLoading())
                .showImageForEmptyUri(mImageOptions.getDefaultImageEmptyUri())
                .showImageOnFail(mImageOptions.getDefaultImageFailure())
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
//                    imageScaleType:
//                    EXACTLY:图像将完全按比例缩小的目标大小
//                    EXACTLY_STRETCHED:图片会缩放到目标大小完全
//                    IN_SAMPLE_INT:图像将被二次采样的整数倍
//                    IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
//                    NONE:图片不会调整
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new FadeInBitmapDisplayer(1000))
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
//                .memoryCacheExtraOptions(480, 800)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .diskCacheFileCount(1000) // 缓冲文件数目
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(options)
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)
                .threadPoolSize(3)
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);
        com.nostra13.universalimageloader.utils.L.writeDebugLogs(false);
        com.nostra13.universalimageloader.utils.L.writeLogs(false);
    }

    @Override
    public void cleanCache() {
        new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... params) {
                ImageLoader.getInstance().clearMemoryCache();
                ImageLoader.getInstance().clearDiskCache();

                return "ok";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("ok")) {
                    Log.i(TAG, "清理成功");
                }
            }
        }.execute();
    }

    @Override
    public void pause() {
        ImageLoader.getInstance().stop();
    }

    @Override
    public void resume() {
        ImageLoader.getInstance().resume();
    }

    @Override
    public String getCacheFilePath(String imageUrl) {
        try {
            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                return ImageLoader.getInstance().getDiskCache().get(imageUrl).getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public
    @Nullable
    MImageOptions getImageOptions() {
        return mImageOptions;
    }

    @Override
    public long getCacheSize() {
        Log.w(TAG, "没有实现方法getCacheSize");
        return 0;
    }

    @Override
    public String getCacheSizeFormat() {
        Log.w(TAG, "没有实现方法getCacheSizeFormat");
        return MImageLoadUtil.getFormatSize(getCacheSize());
    }

    @Override
    public void displayImage(ImageView imageView, String imageUrlOrPath) {
        displayImage(imageView, imageUrlOrPath, -1);
    }

    @Override
    public void displayImage(ImageView imageView, String imageUrlOrPath, int defaultImageResId) {
        if (defaultImageResId <= -1) {
            ImageLoader.getInstance().displayImage(imageUrlOrPath, imageView);
            return;
        }

        MImageOptions mImageOptions = new MImageOptions();
        mImageOptions.setDefaultImageLoading(defaultImageResId);
        mImageOptions.setDefaultImageEmptyUri(defaultImageResId);
        mImageOptions.setDefaultImageFailure(defaultImageResId);
        displayImage(imageView, imageUrlOrPath, mImageOptions);
    }

    @Override
    public void displayImage(ImageView imageView, String imageUrlOrPath, MImageOptions mImageOptions) {
        if (imageUrlOrPath == null) {
            imageUrlOrPath = "";
        }

        ImageLoader.getInstance().displayImage(imageUrlOrPath, imageView, getImageOptions(mImageOptions));
    }

    private DisplayImageOptions getImageOptions(MImageOptions mImageOptions) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(mImageOptions.getDefaultImageLoading())
                .showImageForEmptyUri(mImageOptions.getDefaultImageEmptyUri())
                .showImageOnFail(mImageOptions.getDefaultImageFailure())
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }

}
