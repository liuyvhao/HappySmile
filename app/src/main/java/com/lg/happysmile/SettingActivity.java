package com.lg.happysmile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;

public class SettingActivity extends AppCompatActivity {
    private TextView tv_version, tv_cache;
    private CheckBox check_bt;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    //检测网络连接状态
    private ConnectivityManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
        showCache();
    }

    private void init() {
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_cache = (TextView) findViewById(R.id.tv_cache);
        check_bt = (CheckBox) findViewById(R.id.check_bt);
        // 获取SharedPerferences对象
        sp = getSharedPreferences("Happy", MODE_PRIVATE);
        editor = sp.edit();
        if (sp.getString("state", null).equals("true"))
            check_bt.setChecked(true);
        else if (sp.getString("state", null).equals("false"))
            check_bt.setChecked(false);
        tv_version.setText("v" + getVersion());
    }

    //checkBox点击事件
    public void check(View view) {
        if (check_bt.isChecked())
            editor.putString("state", "true");
        else
            editor.putString("state", "false");
        editor.commit();
        isNetworkAvailable();
    }

    /**
     * 网络已经连接，然后去判断是wifi连接还是GPRS连接
     */
    private void isNetworkAvailable() {
        //得到网络连接信息
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (sp.getString("state", null).equals("true")) {
            if (gprs == NetworkInfo.State.CONNECTED || gprs == NetworkInfo.State.CONNECTING) {
                MainActivity.state = false;
            }
            if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
                MainActivity.state = true;
            }
        } else if (sp.getString("state", null).equals("false"))
            MainActivity.state = true;

    }

    //清除缓存
    public void clearCache(View view) {
        new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("真的要消除缓存吗？")
                .setNegativeButton("算了", null)
                .setPositiveButton("是的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ImagePipeline imagePipeline = Fresco.getImagePipeline();
                        imagePipeline.clearCaches();
                        tv_cache.setText("0.0B");
                        showCache();
                    }
                }).show();
    }

    //显示缓存
    private void showCache() {
        float cacheSizeTemp1 = Math.round(getCache() / 1024);
        float cacheSizeTemp2 = Math.round((getCache() / 1024) / 1024);
        if (cacheSizeTemp1 < 1) {
            tv_cache.setText(getCache() + "B");
        } else if (((cacheSizeTemp1 >= 1) && (cacheSizeTemp2 < 1))) {
            tv_cache.setText(cacheSizeTemp1 + "K");
        } else if (cacheSizeTemp2 >= 1) {
            tv_cache.setText(cacheSizeTemp2 + "M");
        }
    }

    //获取当前缓存
    private long getCache() {
        Fresco.getImagePipelineFactory().getMainDiskStorageCache().trimToMinimum();
        long cacheSize = Fresco.getImagePipelineFactory().getMainDiskStorageCache().getSize();
        return cacheSize;
    }

    //获取当前版本
    private double getVersion() {
        PackageManager nPackageManager = getPackageManager();//得到包管理器
        double loadversion = 0;
        try {
            PackageInfo nPackageInfo = nPackageManager
                    .getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
            loadversion = nPackageInfo.versionCode;//得到现在app的版本号
        } catch (Exception e) {

        }
        return loadversion;
    }

    //返回
    public void back(View view) {
        finish();
    }

    //关于
    public void about(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }

    public void banBen(View view) {
        Toast.makeText(this, "点我也没用，快去看看是不是有新版本发布了！", Toast.LENGTH_SHORT).show();
    }
}
