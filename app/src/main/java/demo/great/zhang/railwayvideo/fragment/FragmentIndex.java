package demo.great.zhang.railwayvideo.fragment;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import demo.great.zhang.railwayvideo.MainActivity;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.Utils.ConnectionUtils;
import demo.great.zhang.railwayvideo.activity.PlayVideoActivity;
import demo.great.zhang.railwayvideo.adapter.ItemClickListener;
import demo.great.zhang.railwayvideo.adapter.RecommendAdapter;
import demo.great.zhang.railwayvideo.application.RailWayVideoApplication;
import demo.great.zhang.railwayvideo.base.BaseFragment;
import demo.great.zhang.railwayvideo.entity.ListObject;
import demo.great.zhang.railwayvideo.entity.SimpleMovie;
import demo.great.zhang.railwayvideo.net.URLConst;
import demo.great.zhang.railwayvideo.viewmodel.AutoViewModel;
import demo.great.zhang.railwayvideo.viewmodel.HotViewModel;
import demo.great.zhang.railwayvideo.viewmodel.RecentViewModel;
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
    @BindView(R.id.tv_auto)
    TextView tvAuto;
    @BindView(R.id.rl_show_auto)
    RecyclerView rlShowAuto;
    @BindView(R.id.tv_recent)
    TextView tvRecent;
    @BindView(R.id.rl_show_recent)
    RecyclerView rlShowRecent;
    @BindView(R.id.tv_recomend)
    TextView tvRecomend;

    private boolean hasConnect = false;

    HotViewModel hotViewModel;
    RecommendViewModel recommendViewModel;
    RecentViewModel recentViewModel;
    AutoViewModel autoViewModel;


    @Override
    protected Object getContentLayout() {
        return R.layout.index_fragment_layout;
    }


    @Override
    protected void initView(View contentView) {


    }

    private CountDownTimer netDown = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            System.out.println("hasConnect:" + hasConnect + hotViewModel);
            if (!hasConnect) {
                if (hotViewModel != null) {
                    hotViewModel.reGet();
                }
                if (recommendViewModel != null) {
                    recommendViewModel.reGet();
                }
                if (autoViewModel != null) {
                    autoViewModel.reGet();
                }
                if (recentViewModel != null) {
                    recentViewModel.reGet();
                }
            }
        }
    };


    private CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            initNet();
        }
    };


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
        System.out.println("initNet" + ConnectionUtils.ping(getAppActivity()));
        if (RailWayVideoApplication.connect) {
            getAppActivity().dismissProgress();
            hotViewModel = ViewModelProviders.of(this).get(HotViewModel.class);
            netDown.start();
            hotViewModel.getHotResource().observe(this, new Observer<ListObject<SimpleMovie>>() {
                @Override
                public void onChanged(ListObject<SimpleMovie> simpleMovies) {
                    hasConnect = true;
                    initBanner(simpleMovies.getList());
                }
            });

            recommendViewModel = ViewModelProviders.of(this).get(RecommendViewModel.class);
            recommendViewModel.getRecommend().observe(this, new Observer<ListObject<SimpleMovie>>() {
                @Override
                public void onChanged(ListObject<SimpleMovie> simpleMovies) {
                    hasConnect = true;
                    initRecycle(simpleMovies.getList());
                }
            });

            recentViewModel = ViewModelProviders.of(this).get(RecentViewModel.class);
            recentViewModel.getRecentlyResource().observe(this, new Observer<ListObject<SimpleMovie>>() {
                @Override
                public void onChanged(ListObject<SimpleMovie> simpleMovieListObject) {
                    hasConnect = true;
                    initRecentRecycle(simpleMovieListObject.getList());

                }
            });
            autoViewModel = ViewModelProviders.of(this).get(AutoViewModel.class);
            autoViewModel.getAutoResource().observe(this, new Observer<ListObject<SimpleMovie>>() {
                @Override
                public void onChanged(ListObject<SimpleMovie> simpleMovieListObject) {
                    hasConnect = true;
                    initAutoRecycle(simpleMovieListObject.getList());
                }
            });
        } else {
            getAppActivity().showProgress();
            countDownTimer.start();
        }
    }

    private void initRecycle(List<SimpleMovie> simpleMovies) {
        getAppActivity().dismissProgress();
        if (simpleMovies.size() < 1) {
            tvRecomend.setVisibility(View.GONE);
            rlShowHot.setVisibility(View.GONE);
            return;
        }
        RecommendAdapter adapter = new RecommendAdapter(getAppActivity(), simpleMovies);
        rlShowHot.setLayoutManager(new GridLayoutManager(getAppActivity(), 3));
        rlShowHot.setNestedScrollingEnabled(false);
        rlShowHot.setAdapter(adapter);
        adapter.setCliclListener(itemClickListener);
    }

    private void initRecentRecycle(List<SimpleMovie> simpleMovies) {
        if (simpleMovies.size() < 1) {
            tvRecent.setVisibility(View.GONE);
            rlShowRecent.setVisibility(View.GONE);
            return;
        }
        System.out.println("SIZE:"+simpleMovies.size());
        rlShowRecent.setNestedScrollingEnabled(false);
        RecommendAdapter adapter = new RecommendAdapter(getAppActivity(), simpleMovies);
        rlShowRecent.setLayoutManager(new GridLayoutManager(getAppActivity(), 3));
        rlShowRecent.setAdapter(adapter);
        adapter.setCliclListener(itemClickListener);
    }

    private void initAutoRecycle(List<SimpleMovie> simpleMovies) {
        if (simpleMovies.size() < 1) {
            tvAuto.setVisibility(View.GONE);
            rlShowAuto.setVisibility(View.GONE);
            return;
        }
        rlShowAuto.setNestedScrollingEnabled(false);
        RecommendAdapter adapter = new RecommendAdapter(getAppActivity(), simpleMovies);
        rlShowAuto.setLayoutManager(new GridLayoutManager(getAppActivity(), 3));
        rlShowAuto.setAdapter(adapter);
        adapter.setCliclListener(itemClickListener);
    }


    private void initBanner(List<SimpleMovie> simpleMovies) {
        System.out.println("initBanner");
        banner.setImageLoader(new GlideImageLoader());
        List<Object> urlList = new ArrayList<>();
        List<String> titleType = new ArrayList<>();
        for (SimpleMovie simpleMovie : simpleMovies) {
            if (!simpleMovie.getImage().equals("custom_image.png")) {
                urlList.add(URLConst.IMAGEPRE() + simpleMovie.getImage());
            } else {

                if (!simpleMovie.getSubtype().equals("file")) {
                    urlList.add(R.mipmap.index_video);
                } else {
                    urlList.add(R.mipmap.index_file);
                }
            }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        countDownTimer.cancel();
        netDown.cancel();
    }
}
