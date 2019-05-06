package demo.great.zhang.railwayvideo.net;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;

import demo.great.zhang.railwayvideo.application.RailWayVideoApplication;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {

    private Context mContext;

    public void setmContext(Context context){
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        System.out.println("intercept:");
        Request uest = chain.request();
        Response response;
        response = chain.proceed(uest);
        System.out.println("ResponseCode:"+response.code());
        if(response.code()!= HttpURLConnection.HTTP_OK){
            Toast.makeText(RailWayVideoApplication.getInstance(),"网络错误",Toast.LENGTH_LONG).show();
        }
        return response;
    }
}
