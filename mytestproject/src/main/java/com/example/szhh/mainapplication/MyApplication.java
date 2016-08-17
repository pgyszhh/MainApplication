package com.example.szhh.mainapplication;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @ Created by szhh on 2016/8/14.
 * @ Date   : 2016/8/14.
 * @ Auther    : suzhiheng
 * @ Description    :TODDO
 */

public class MyApplication extends Application {
    private Context mContextm;
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        //线程优先级
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        //不设置多种存储格式在内存当中
        config.denyCacheImageMultipleSizesInMemory();
        //文件名生成规则
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        //SD卡最大缓存数
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        //加载次序
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //是否打印imageloader日志
        // config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
