package demo.great.zhang.railwayvideo.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.Utils.OpenFileUtils;
import demo.great.zhang.railwayvideo.Utils.SpacesCheck;
import demo.great.zhang.railwayvideo.adapter.LocalFileAdapter;
import demo.great.zhang.railwayvideo.base.BaseFragment;
import demo.great.zhang.railwayvideo.entity.LocalFileEntity;

public class FragmentSetting extends BaseFragment {

    @BindView(R.id.iv_subtype_icon)
    ImageView ivSubtypeIcon;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_detaile_loca)
    TextView tvDetaileLoca;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.rl_local_file)
    RecyclerView rlLocalFile;
    @BindView(R.id.tv_showmsg)
    TextView tvShowmsg;

    @Override
    protected Object getContentLayout() {
        return R.layout.setting_fragment_layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume");
    }

    @Override
    protected void initView(View contentView) {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File dFile = new File(file.getAbsolutePath() + "/RDownload");
        if (!dFile.exists() && dFile.listFiles().length<1) {
            tvShowmsg.setText("无下载文件");
            return;
        }
        LocalFileEntity entity;
        List<LocalFileEntity> list = new ArrayList<>();
        for(File files : dFile.listFiles()){
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date(files.lastModified()));
            entity = new LocalFileEntity(files.getName(), SpacesCheck.getAutoFileOrFilesSize(files.getAbsolutePath()),time,files.getAbsolutePath());
            list.add(entity);
        }
        rlLocalFile.setLayoutManager(new LinearLayoutManager(getAppActivity()));
        LocalFileAdapter adapter = new LocalFileAdapter(getAppActivity(),list);
        rlLocalFile.addItemDecoration(new DividerItemDecoration(getAppActivity(),DividerItemDecoration.VERTICAL));
        adapter.setListener(new LocalFileAdapter.fileClickListener() {
            @Override
            public void onClickItem(int position) {
                OpenFileUtils.openFile(getAppActivity(),dFile.listFiles()[position]);
            }
        });
        rlLocalFile.setAdapter(adapter);
    }

    @OnClick(R.id.tv_open)
    public void openFile(View view) {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File dFile = new File(file.getAbsolutePath() + "/RDownload");
//        File file = Environment.getExternalStorageDirectory();
        System.out.println(dFile.getAbsoluteFile());
        Uri uri = null;

        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(getAppActivity(), "demo.great.zhang.railwayvideo.android7.fileprovider", dFile);
        } else {
            uri = Uri.fromFile(dFile);
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        System.out.println(uri.toString());
        intent.setDataAndType(uri, "*/*");
        try {
            startActivity(intent);
//            startActivity(Intent.createChooser(intent,"选择浏览工具"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

//        FileUtils.openAssignFolder(getAppActivity(),Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
//        OpenFileUtils.openFile(getAppActivity(),new File(path));

    }
}
