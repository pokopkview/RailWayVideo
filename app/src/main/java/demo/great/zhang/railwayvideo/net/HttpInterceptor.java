package demo.great.zhang.railwayvideo.net;

import android.content.Context;

import java.io.IOException;

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
        Request uest = chain.request();
        Response response;
        response = chain.proceed(uest);
        return response;
    }
}
