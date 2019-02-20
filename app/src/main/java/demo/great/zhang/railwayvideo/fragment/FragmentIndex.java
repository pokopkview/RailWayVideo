package demo.great.zhang.railwayvideo.fragment;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import demo.great.zhang.railwayvideo.MainActivity;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.Utils.ConnectionUtils;
import demo.great.zhang.railwayvideo.activity.PlayVideoActivity;
import demo.great.zhang.railwayvideo.adapter.ItemClickListener;
import demo.great.zhang.railwayvideo.adapter.RecommendAdapter;
import demo.great.zhang.railwayvideo.base.BaseFragment;
import demo.great.zhang.railwayvideo.entity.SimpleMovie;
import demo.great.zhang.railwayvideo.viewmodel.HotViewModel;
import demo.great.zhang.railwayvideo.viewmodel.RecommendViewModel;
import demo.great.zhang.railwayvideo.widget.GlideImageLoader;
import demo.great.zhang.railwayvideo.widget.HeaderLayout;

public class FragmentIndex extends BaseFragment {


    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.bt_search)
    Button btSearch;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rl_show_hot)
    RecyclerView rlShowHot;
    @BindView(R.id.rl_header)
    HeaderLayout rlHeader;


    private Timer timer;
    private TimerTask timerTask;


    @Override
    protected Object getContentLayout() {
        return R.layout.index_fragment_layout;
    }


    @Override
    protected void initView(View contentView) {


    }


    @OnClick(R.id.rl_header)
    public void SearchClick(View view) {
        SkipToSearch();
    }

    private void SkipToSearch() {
        BottomNavigationView navigation = ((MainActivity) getAppActivity()).getNavigation();
        navigation.setSelectedItemId(navigation.getMenu().getItem(2).getItemId());
    }

    @Override
    protected void initNet() {
        if (ConnectionUtils.ping()) {
            if(timer!=null){
                timer.cancel();
                timer = null;
                timerTask.cancel();
                timerTask = null;
            }
            getAppActivity().dismissProgress();
            HotViewModel hotViewModel = ViewModelProviders.of(this).get(HotViewModel.class);
            hotViewModel.getHotResource().observe(this, new Observer<List<SimpleMovie>>() {
                @Override
                public void onChanged(List<SimpleMovie> simpleMovies) {
                    initBanner(simpleMovies);
                    initRecycle(simpleMovies);
                }
            });

            RecommendViewModel recommendViewModel = ViewModelProviders.of(this).get(RecommendViewModel.class);
            recommendViewModel.getRecommend().observe(this, new Observer<List<SimpleMovie>>() {
                @Override
                public void onChanged(List<SimpleMovie> simpleMovies) {

                }
            });
        } else {
            if(timer==null) {
                timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        getAppActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initNet();
                            }
                        });
                    }
                };
            }
            timer.schedule(timerTask, 2500);
            getAppActivity().showProgress();
        }

    }

    private void initRecycle(List<SimpleMovie> simpleMovies) {
        RecommendAdapter adapter = new RecommendAdapter(getAppActivity(), simpleMovies);
        rlShowHot.setLayoutManager(new GridLayoutManager(getAppActivity(), 3));
        rlShowHot.setAdapter(adapter);
        adapter.setCliclListener(itemClickListener);
    }

    private void initBanner(List<SimpleMovie> simpleMovies) {
        System.out.println("initBanner");
        banner.setImageLoader(new GlideImageLoader());
        List<String> urlList = new ArrayList<>();
        List<String> titleType = new ArrayList<>();
        for (SimpleMovie simpleMovie : simpleMovies) {
            urlList.add(simpleMovie.getImage());
            titleType.add(simpleMovie.getTitle());
        }
        banner.setImages(urlList);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerTitles(titleType);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getAppActivity(), PlayVideoActivity.class);
                intent.putExtra("mainID", simpleMovies.get(position).getMainID());
                intent.putExtra("subtype", simpleMovies.get(position).getSubtype());
                getAppActivity().startActivity(intent);
            }
        });
        banner.start();
    }

    private ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void ItemClick(int mainID, String subtype) {
            Intent intent = new Intent(getAppActivity(), PlayVideoActivity.class);
            intent.putExtra("mainID", mainID);
            intent.putExtra("subtype", subtype);
            getAppActivity().startActivity(intent);
        }
    };
}
