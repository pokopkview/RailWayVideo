package demo.great.zhang.railwayvideo.application;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import demo.great.zhang.railwayvideo.Utils.Density;
import demo.great.zhang.railwayvideo.net.HttpInterceptor;
import okhttp3.OkHttpClient;

public class RailWayVideoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpInterceptor interceptor = new HttpInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(interceptor)
                .build();
        OkHttpUtils.initClient(okHttpClient);
        Density.setDensity(this);
    }
}
