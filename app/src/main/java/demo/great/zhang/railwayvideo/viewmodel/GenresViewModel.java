package demo.great.zhang.railwayvideo.viewmodel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import demo.great.zhang.railwayvideo.entity.ListObject;
import demo.great.zhang.railwayvideo.entity.SimpleMovie;
import demo.great.zhang.railwayvideo.net.URLConst;

public class GenresViewModel extends AbsViewModel {

    private MutableLiveData<ListObject<SimpleMovie>> mutableLiveData;


    public LiveData<ListObject<SimpleMovie>> getGenresMovie(String gen){
        if(mutableLiveData==null){
            mutableLiveData = new MutableLiveData<>();
            value.clear();
            value.put("gen",gen);
            value.put("start","0");
            value.put("end","18");
            HttpGet(URLConst.GETGEN(),value);
        }
        return mutableLiveData;
    }

    public void getMore(String gen,int start,int end){
        value.clear();
        value.put("gen",gen);
        value.put("start",String.valueOf(start));
        value.put("end",String.valueOf(end));
        HttpGet(URLConst.GETGEN(),value);
    }


    @Override
    protected void getCallBack(String response) {
        System.out.println(response);
        Type type = new TypeToken<ListObject<SimpleMovie>>(){}.getType();
        ListObject<SimpleMovie> simpleMovieList = new Gson().fromJson(response,type);
        mutableLiveData.setValue(simpleMovieList);
    }

}
