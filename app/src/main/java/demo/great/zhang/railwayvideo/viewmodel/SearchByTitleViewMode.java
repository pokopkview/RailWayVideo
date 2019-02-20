package demo.great.zhang.railwayvideo.viewmodel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import demo.great.zhang.railwayvideo.entity.SimpleMovie;
import demo.great.zhang.railwayvideo.net.URLConst;

public class SearchByTitleViewMode extends AbsViewModel {

    MutableLiveData<List<SimpleMovie>> mutableLiveData;


    public LiveData<List<SimpleMovie>> getSearchResult(){
        if(mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public void search(String title){
        value.clear();
        value.put("title",title);
        HttpGet(URLConst.GETMOVIEBYTITLE,value);
    }

    @Override
    protected void getCallBack(String response) {
        Type type = new TypeToken<List<SimpleMovie>>(){}.getType();
        List<SimpleMovie> simpleMovieList = new Gson().fromJson(response,type);
        mutableLiveData.setValue(simpleMovieList);
    }
}
