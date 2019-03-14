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

import java.io.File;

import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.OnClick;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.Utils.FileUtils;
import demo.great.zhang.railwayvideo.Utils.OpenFileUtils;
import demo.great.zhang.railwayvideo.base.BaseFragment;

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

    @Override
    protected Object getContentLayout() {
        return R.layout.setting_fragment_layout;
    }

    @Override
    protected void initView(View contentView) {
        String location = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        tvDetaileLoca.setText(location);
    }

    @OnClick(R.id.tv_open)
    public void openFile(View view){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        System.out.println(file.getAbsoluteFile());
        Uri uri = null;

//        if (Build.VERSION.SDK_INT >= 24) {
//            uri = FileProvider.getUriForFile(getAppActivity(), "demo.great.zhang.railwayvideo.android7.fileprovider", file);
//        } else {
            uri = Uri.fromFile(file);
//        }
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
