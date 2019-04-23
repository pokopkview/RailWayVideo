package demo.great.zhang.railwayvideo.viewmodel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import demo.great.zhang.railwayvideo.entity.ListObject;
import demo.great.zhang.railwayvideo.entity.SimpleMovie;
import demo.great.zhang.railwayvideo.net.URLConst;

public class AutoViewModel extends AbsViewModel {


    private MutableLiveData<ListObject<SimpleMovie>> mutableLiveData;

    public LiveData<ListObject<SimpleMovie>> getAutoResource(){
        if(mutableLiveData==null){
            mutableLiveData = new MutableLiveData<>();
            value.clear();
            value.put("gen","自定义");
            value.put("start","0");
            value.put("end","6");
            HttpGet(URLConst.GETGEN(),value);
        }
        return mutableLiveData;
    }



    public void reGet(){
        value.clear();
        value.put("gen","自定义");
        value.put("start","0");
        value.put("end","6");
        HttpGet(URLConst.GETHOT(),value);
    }


    @Override
    protected void getCallBack(String response) {
        Type type = new TypeToken<ListObject<SimpleMovie>>(){}.getType();
        ListObject<SimpleMovie> simpleMovieList = new Gson().fromJson(response,type);
        mutableLiveData.setValue(simpleMovieList);
    }

    @Override
    protected void getError() {

    }
}
