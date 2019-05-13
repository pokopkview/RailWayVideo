package demo.great.zhang.railwayvideo.net;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;

import demo.great.zhang.railwayvideo.application.RailWayVideoApplication;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {

    private static Context mContext;

    public static void setmContext(Context context){
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        System.out.println("intercept:");
        Request uest = chain.request();
        Response response;
        response = chain.proceed(uest);
        System.out.println("ResponseCode:"+response.code());
        if(response.code()!= HttpURLConnection.HTTP_OK){
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext,"网络错误",Toast.LENGTH_LONG).show();
                }
            });
        }
        return response;
    }
}
