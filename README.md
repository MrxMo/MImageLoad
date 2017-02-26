#MImageLoad

MImageLoad是一个第三方图片加载框架兼容库，能方便快速切换不同的图片加载框架。本项目默认集成[Universal-Image-Loader](https://github.com/nostra13/Android-Universal-Image-Loader)和[Glide](https://github.com/bumptech/glide)
<br/>

## Gradle [下载aar](https://github.com/MrxMo/MImageLoad/raw/master/release/mimageloadlib-1.0_2017_02_26.aar)
 ```
 compile 'com.github.MrxMo:MImageLoad:v1.0'
 ```
 <br/>
 
## How to use
```
MImageLoad.init(new MImageLoader(this, mImageOptions));
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        String imageUrl = "http://g.hiphotos.baidu.com/image/pic/item/730e0cf3d7ca7bcbbcac7afeba096b63f724a808.jpg";
        MImageLoad.getInstance().displayImage(ivIcon, imageUrl);
```

## Author

MrMo <br/>
Created on 2017/2/26. 
