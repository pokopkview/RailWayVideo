package demo.great.zhang.railwayvideo.viewmodel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import demo.great.zhang.railwayvideo.entity.ListObject;
import demo.great.zhang.railwayvideo.entity.SimpleMovie;
import demo.great.zhang.railwayvideo.net.URLConst;

public class RecommendViewModel extends AbsViewModel {

    private MutableLiveData<ListObject<SimpleMovie>> mutableLiveData;


    public LiveData<ListObject<SimpleMovie>> getRecommend(){
        if(mutableLiveData==null){
            mutableLiveData = new MutableLiveData<>();
            HttpGet(URLConst.GETRECOMMEND(),value);
        }
        return mutableLiveData;
    }

    public void reGet(){
        HttpGet(URLConst.GETRECOMMEND(),value);
    }

    @Override
    protected void getCallBack(String response) {
        System.out.println(response);
        Type type = new TypeToken<ListObject<SimpleMovie>>(){}.getType();
        ListObject<SimpleMovie> simpleMovieList = new Gson().fromJson(response,type);
        mutableLiveData.setValue(simpleMovieList);

    }

    @Override
    protected void getError() {

    }


}
