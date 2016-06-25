package com.lg.happysmile;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.cache.common.CacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.lg.util.FrescoUtil;

import java.io.File;

public class ImageActivity extends AppCompatActivity {
    private SimpleDraweeView img_faceimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        init();
    }

    private void init() {
        img_faceimg = (SimpleDraweeView) findViewById(R.id.img_faceimg);
        //获取传来的图片
        Uri uri = Uri.parse(this.getIntent().getExtras().getString("uri"));
        if (MainActivity.state) {
            //访问图片
            DraweeController draweeController = Fresco.newDraweeControllerBuilder().setUri(uri).setAutoPlayAnimations(true).build();
            img_faceimg.setController(draweeController);
        } else {
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(uri));
            File cacheFile = FrescoUtil.getCachedImageOnDisk(cacheKey);
            if (cacheFile != null) {      //有缓存
                //访问图片
                DraweeController draweeController = Fresco.newDraweeControllerBuilder().setUri(uri).setAutoPlayAnimations(true).build();
                img_faceimg.setController(draweeController);
            } else {
                img_faceimg.setImageResource(R.drawable.error);
                img_faceimg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
        }
    }

    //保存
    public void save(View view) {
        if (MainActivity.state) {
            FrescoUtil.savePicture(this.getIntent().getExtras().getString("uri"), this);
            Toast.makeText(this, "已帮你收藏啦，快去图库看看吧！", Toast.LENGTH_SHORT).show();
        }else{
            //获取传来的图片
            Uri uri = Uri.parse(this.getIntent().getExtras().getString("uri"));
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(uri));
            File cacheFile = FrescoUtil.getCachedImageOnDisk(cacheKey);
            if (cacheFile!=null){   //有缓存
                FrescoUtil.savePicture(this.getIntent().getExtras().getString("uri"), this);
                Toast.makeText(this, "已帮你收藏啦，快去图库看看吧！", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "打开Wi-Fi再保存吧！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
