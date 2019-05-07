package demo.great.zhang.railwayvideo.viewmodel;

import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModel;

import demo.great.zhang.railwayvideo.application.RailWayVideoApplication;
import okhttp3.Call;

public abstract class AbsViewModel extends ViewModel {

    protected Map<String,String> value = new HashMap<>();
    private static final int retry = 3;
    private int count = 0;

    protected abstract void getCallBack(String response);

    protected void HttpGet(String url,Map<String,String> params){
        System.out.println(url);
        OkHttpUtils.get()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if(count<retry) {
                            count++;
                            HttpGet(url, params);
                        }else{
                            getError();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        getCallBack(response);
                    }
                });
    }

    private void getError(){
        Toast.makeText(RailWayVideoApplication.getInstance(),"网络错误！",Toast.LENGTH_LONG).show();
    }

}
