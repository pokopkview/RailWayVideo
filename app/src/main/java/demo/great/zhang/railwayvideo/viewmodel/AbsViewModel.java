package demo.great.zhang.railwayvideo.viewmodel;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModel;
import okhttp3.Call;

public abstract class AbsViewModel extends ViewModel {

    protected Map<String,String> value = new HashMap<>();

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
                        getError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        getCallBack(response);
                    }
                });
    }

    protected abstract void getError();

}
