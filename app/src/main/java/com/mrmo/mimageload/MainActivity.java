package com.mrmo.mimageload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mrmo.mimageloadlib.MImageLoad;

/**
 * Created by moguangjian on 2017/2/26.
 */
public class MainActivity extends AppCompatActivity {

    private ImageView ivIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        String imageUrl = "http://g.hiphotos.baidu.com/image/pic/item/730e0cf3d7ca7bcbbcac7afeba096b63f724a808.jpg";
        MImageLoad.getInstance().displayImage(ivIcon, imageUrl);

    }
}
