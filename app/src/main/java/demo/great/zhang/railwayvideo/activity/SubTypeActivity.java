package demo.great.zhang.railwayvideo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.adapter.ItemClickListener;
import demo.great.zhang.railwayvideo.adapter.RecommendAdapter;
import demo.great.zhang.railwayvideo.base.BaseActivity;
import demo.great.zhang.railwayvideo.entity.ListObject;
import demo.great.zhang.railwayvideo.entity.SimpleMovie;
import demo.great.zhang.railwayvideo.viewmodel.GenresViewModel;


public class SubTypeActivity extends BaseActivity {

    @BindView(R.id.iv_subtype_icon)
    ImageView ivSubtypeIcon;
    @BindView(R.id.tv_sub_name)
    TextView tvSubName;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.rv_simple_movie)
    RecyclerView rvSimpleMovie;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.ic_back)
    ImageView icBack;

    GenresViewModel genresViewModel;
    RecommendAdapter adapter;

    int pageSize = 18;
    boolean canGetMore = true;
    boolean getingDate = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_sub_type;
    }

    @Override
    protected void initEvent() {
        String name = getIntent().getStringExtra("sub_name");
        genresViewModel = ViewModelProviders.of(this).get(GenresViewModel.class);
        tvSubName.setText(name);
        genresViewModel.getGenresMovie(name).observe(this, new Observer<ListObject<SimpleMovie>>() {
            @Override
            public void onChanged(ListObject<SimpleMovie> simpleMovies) {
                System.out.println("SIZE:"+simpleMovies.getList().size());
                if(adapter!=null){
                    if(simpleMovies.getList().size()<12){
                        canGetMore = false;
                    }
                    adapter.addData(simpleMovies.getList());
                    getingDate = false;
                    return;
                }

                if (!simpleMovies.getList().isEmpty()) {
                    initRec(simpleMovies.getList());
                } else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    rvSimpleMovie.setVisibility(View.GONE);
                    tvEmpty.setText("无资源～～");
                }
            }
        });
    }

    private void initRec(List<SimpleMovie> simpleMovies) {
        adapter = new RecommendAdapter(mContext, simpleMovies);
        rvSimpleMovie.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvSimpleMovie.setAdapter(adapter);
        adapter.setCliclListener(itemClickListener);
        rvSimpleMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                System.out.println("onScrolled");
                if(!rvSimpleMovie.canScrollVertically(1)){
                    //到底部
                    if(canGetMore && !getingDate) {
                        getingDate = true;
                        genresViewModel.getMore(tvSubName.getText().toString(),simpleMovies.size(), simpleMovies.size() + pageSize);
                    }else{
                        showMsg("已经加载所有资源！");
                    }
                }
            }
        });
    }

    private ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void ItemClick(int mainID, String subtype) {
            Intent intent = new Intent(mActivity, PlayVideoActivity.class);
            intent.putExtra("mainID", mainID);
            intent.putExtra("subtype", subtype);
            startActivity(intent);
        }
    };

    @OnClick(R.id.ic_back)
    public void back(View view){
        onBackPressed();
    }
}
