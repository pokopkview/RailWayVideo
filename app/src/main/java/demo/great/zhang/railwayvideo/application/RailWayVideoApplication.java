package demo.great.zhang.railwayvideo.application;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import demo.great.zhang.railwayvideo.Utils.CrashHandler;
import demo.great.zhang.railwayvideo.Utils.Density;
import demo.great.zhang.railwayvideo.net.HttpInterceptor;
import okhttp3.OkHttpClient;

public class RailWayVideoApplication extends Application {

    private static RailWayVideoApplication instance = null;

    public static boolean connect = false;

    public static RailWayVideoApplication getInstance(){
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        CrashHandler.getInstance().initCrashHandler(this);
        HttpInterceptor interceptor = new HttpInterceptor();
        interceptor.setmContext(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .writeTimeout(10000l,TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(interceptor)
                .build();
        OkHttpUtils.initClient(okHttpClient);
        Density.setDensity(this);
    }
}
