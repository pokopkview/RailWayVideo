package demo.great.zhang.railwayvideo.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.Utils.JZAutoNextStd;
import demo.great.zhang.railwayvideo.Utils.JZMediaIjkplayer;
import demo.great.zhang.railwayvideo.Utils.OpenFileUtils;
import demo.great.zhang.railwayvideo.Utils.URLChange;
import demo.great.zhang.railwayvideo.adapter.EpisodesAdapter;
import demo.great.zhang.railwayvideo.adapter.ItemClickListener;
import demo.great.zhang.railwayvideo.adapter.RecommendAdapter;
import demo.great.zhang.railwayvideo.base.BaseActivity;
import demo.great.zhang.railwayvideo.entity.DetailMovie;
import demo.great.zhang.railwayvideo.entity.ListObject;
import demo.great.zhang.railwayvideo.entity.SimpleMovie;
import demo.great.zhang.railwayvideo.eventbus.Message;
import demo.great.zhang.railwayvideo.net.URLConst;
import demo.great.zhang.railwayvideo.viewmodel.GenresViewModel;
import okhttp3.Call;

public class PlayVideoActivity extends BaseActivity {


    @BindView(R.id.jz_video)
    JZAutoNextStd jzVideo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_descripte)
    TextView tvDescripte;
    @BindView(R.id.rl_other_movie)
    RecyclerView rlOtherMovie;

    @BindView(R.id.iv_file_icon)
    ImageView ivFileIcon;
    @BindView(R.id.bt_download)
    Button btDownload;


    DetailMovie detailMovie;
    String subtype;

    private static final int GETDETAIL = 1;
    private static final int SETWATCHED = 2;

    private static int REQUEST_PERMISSION_CODE = 101;
    @BindView(R.id.tv_show_more)
    TextView tvShowMore;
    @BindView(R.id.tv_episodes)
    RecyclerView tvEpisodes;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.ic_back)
    ImageView icBack;
    @BindView(R.id.tv_sub_name)
    TextView tvSubName;

    private ProgressDialog progressDialog;

    private String defultgen = "";

    private boolean hasShow = false;


    private File download;

    private List<String> esp;
    EpisodesAdapter adapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_play_video;
    }

    @OnClick(R.id.bt_download)
    public void downloadClick(View view) {
        checkPermission();

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEspide(Message messageEvent) {
        adapter.setSelectItem(messageEvent.message);
    }

    @Override
    protected void initEvent() {
        EventBus.getDefault().register(this);
        int id = getIntent().getIntExtra("mainID", -1);
        subtype = getIntent().getStringExtra("subtype");
        params.clear();
        params.put("id", String.valueOf(id));
        showProgress(true, "等待加载～");
        HttpGet(URLConst.GETDETAIL(), params, GETDETAIL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setViewByType() {
        tvTitle.setText(detailMovie.getTitle());
        tvRating.setText(null == detailMovie.getRating() ? "0 分" : detailMovie.getRating().getAverage() + " 分");
        tvDescripte.setText(String.format(getResources().getString(R.string.descripte), detailMovie.getSummary()));
        String gen = detailMovie.getGenres().toString();
        gen = gen.substring(1, gen.length() - 1);
        defultgen = gen.split(",")[0];
        GenresViewModel genresViewModel = ViewModelProviders.of(this).get(GenresViewModel.class);
        genresViewModel.getGenresMovie(defultgen).observe(this, new Observer<ListObject<SimpleMovie>>() {
            @Override
            public void onChanged(ListObject<SimpleMovie> simpleMovies) {
                initRecommend(simpleMovies.getList());
            }
        });
        tvType.setText(String.format(getResources().getString(R.string.type), gen));
        tvDescripte.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这个监听的回调是异步的，在监听完以后一定要把绘制监听移除，不然这个会一直回调，导致界面错乱
                tvDescripte.getViewTreeObserver().removeOnPreDrawListener(this);
                int line = tvDescripte.getLineCount();
                if (line < 7) {
                    tvShowMore.setVisibility(View.INVISIBLE);
                    tvShowMore.setTag(line);
                } else {
                    tvShowMore.setVisibility(View.VISIBLE);
                    tvShowMore.setTag(line);
                }
                return true;
            }
        });
        System.out.println("subtype:" + subtype);
        switch (subtype) {
            case "movie":


                break;
            case "file":
                jzVideo.setVisibility(View.GONE);
                ivFileIcon.setVisibility(View.VISIBLE);
                btDownload.setVisibility(View.VISIBLE);
                break;
            case "tv":
                tvEpisodes.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                tvEpisodes.setLayoutManager(linearLayoutManager);
                System.out.println(detailMovie.toString());
                adapter = new EpisodesAdapter(this, detailMovie.getResourse());
                adapter.setSelectListener(new EpisodesAdapter.EpisodeListener() {
                    @Override
                    public void ItemSelect(int position) {
                        adapter.notifyDataSetChanged();
                        String encode = detailMovie.getResourse().get(position);
                        System.out.println("encode:" + encode);
                        encode = URLConst.baseurl() + encode.split("://")[1].substring(encode.split("://")[1].indexOf("/"));
                        JZDataSource jzDataSource = new JZDataSource(URLChange.decodeURL(encode), detailMovie.getTitle());
                        Object[] objects = new Object[]{position, esp};
                        jzDataSource.objects = objects;
                        jzVideo.changeUrl(jzDataSource, 0l);
                        jzVideo.startButton.performClick();
                    }
                });
                tvEpisodes.setAdapter(adapter);
                break;
        }
    }

    private void initRecommend(List<SimpleMovie> simpleMovies) {
        RecommendAdapter adapter = new RecommendAdapter(this, simpleMovies);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlOtherMovie.setLayoutManager(linearLayoutManager);
//        rlOtherMovie.setLayoutManager(new GridLayoutManager(this,3));
        rlOtherMovie.setAdapter(adapter);
        adapter.setCliclListener(itemClickListener);
    }

    @OnClick(R.id.tv_show_more)
    public void setTvShowMore(View view) {
        System.out.println("line2:" + tvShowMore.getTag());
        if ((int) tvShowMore.getTag() < tvDescripte.getMaxLines() && hasShow) {
            tvShowMore.setText("展开");
            tvDescripte.setMaxLines(7);
        } else {
            hasShow = true;
            tvShowMore.setText("收起");
            tvDescripte.setMaxLines(99);
        }
    }

    @Override
    protected void getCallBack(String response, int flag) {
        switch (flag) {
            case GETDETAIL:
                dismissProgress();
                detailMovie = new Gson().fromJson(response, DetailMovie.class);
                tvSubName.setText(detailMovie.getTitle());
                String encode = detailMovie.getResourse().get(0);
                encode = URLConst.baseurl() + encode.split("://")[1].substring(encode.split("://")[1].indexOf("/"));
//                encode = URLConst.baseurl +"/video/21克.mkv";
                System.out.println("encode:" + encode);
                params.clear();
                params.put("id", String.valueOf(detailMovie.getMainID()));
                HttpGet(URLConst.SETWATCHED(), params, SETWATCHED);//提交已观看
                JzvdStd.setMediaInterface(new JZMediaIjkplayer());
                JZDataSource jzDataSource = new JZDataSource(URLChange.decodeURL(encode), detailMovie.getTitle());
                System.out.println(URLChange.decodeURL(encode));
                if (subtype.equals("tv")) {
                    esp = new ArrayList<>();
                    for (int i = 0; i < detailMovie.getResourse().size(); i++) {
                        String encodes = detailMovie.getResourse().get(i);
                        encodes = URLConst.baseurl() + encodes.split("://")[1].substring(encodes.split("://")[1].indexOf("/"));
                        esp.add(URLChange.decodeURL(encodes));
                    }
                    Object[] objects = new Object[]{0, esp};
                    jzDataSource.objects = objects;
                }
                jzVideo.setUp(jzDataSource, Jzvd.SCREEN_WINDOW_NORMAL);
                jzVideo.startButton.performClick();

                setViewByType();
                break;
            case SETWATCHED:

                break;
        }
    }


    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常下载！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);

        } else {
            File str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File dFile = new File(str.getAbsolutePath()+"/RDownload");
            if(!dFile.exists()){
                dFile.mkdirs();
            }
            if (detailMovie != null) {
                String url = detailMovie.getResourse().get(0);
                url = URLConst.baseurl() + url.split("8080")[1];
                System.out.println(url);
                String name = url.substring(url.lastIndexOf("/"));
                showDownloadProgress();
                OkHttpUtils.get()
                        .url(url)
                        .build()
                        .execute(new FileCallBack(dFile.getAbsolutePath(), name) {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                showMsg("下载失败！");
                                dismissPro();
                            }

                            @Override
                            public void onResponse(File response, int id) {
                                download = response;
                                showMsg("下载成功！");
                            }

                            @Override
                            public void inProgress(float progress, long total, int id) {
                                progress = progress * 100;
                                uploadProgress((int) progress);
                            }
                        });
            }
        }
    }

    private void uploadProgress(int pro) {
        if (progressDialog != null) {
            progressDialog.setProgress(pro);
        }
    }


    private void showDownloadProgress() {
        //progressDialog 的属性
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("文件下载");
        progressDialog.setMessage(detailMovie.getTitle());
        progressDialog.setIcon(R.mipmap.yellow_folders_10);
        //progressDialog 进度条的属性
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.setIndeterminate(false);//明确显示进度
        progressDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "打开", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OpenFileUtils.openFile(getApplicationContext(), download);
            }
        });
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //是否可以通过返回按钮退出对话框
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    private void dismissPro() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
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


    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        dismissProgress();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ic_back)
    public void onViewClicked() {
        onBackPressed();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    File str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    if (detailMovie != null) {
                        String url = detailMovie.getResourse().get(0);
                        url = URLConst.baseurl() + url.split("8080")[1];
                        System.out.println(url);
                        String name = url.substring(url.lastIndexOf("/"));
                        showDownloadProgress();
                        OkHttpUtils.get()
                                .url(url)
                                .build()
                                .execute(new FileCallBack(str.getAbsolutePath(), name) {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        showMsg("下载失败！");
                                        dismissPro();
                                    }

                                    @Override
                                    public void onResponse(File response, int id) {
                                        download = response;
                                        showMsg("下载成功！");
                                    }

                                    @Override
                                    public void inProgress(float progress, long total, int id) {
                                        progress = progress * 100;
                                        uploadProgress((int) progress);
                                    }
                                });
                    }
                } else {
                    Toast.makeText(this, "权限被限，文件将无法下载", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
