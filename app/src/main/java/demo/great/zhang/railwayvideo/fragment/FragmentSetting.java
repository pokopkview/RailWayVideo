package demo.great.zhang.railwayvideo.fragment;

import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.Utils.FileUtils;
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
//        FileUtils.openAssignFolder(getAppActivity(),Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
    }
}
