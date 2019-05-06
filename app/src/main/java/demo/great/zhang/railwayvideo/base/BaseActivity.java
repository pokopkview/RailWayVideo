package demo.great.zhang.railwayvideo.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import demo.great.zhang.railwayvideo.MainActivity;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.Utils.Density;
import hugo.weaving.DebugLog;
import okhttp3.Call;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected Activity mActivity;
    protected Context appContext;
    protected Application mApplication;

    protected static Toast toast;
    protected Map<String,String> params;

    protected ProgressDialog mProgress;

    @DebugLog
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        hideStatusBar();
        mContext = this;
        mActivity = this;
        appContext = getApplicationContext();
        mApplication = getApplication();
        params = new HashMap<>();
        setOrientation();
        setContentView(getLayout());
        ButterKnife.bind(this);
        initEvent();
    }

    @Override
    protected void onDestroy() {
        if(mProgress!=null) {
            mProgress.cancel();
        }
        super.onDestroy();
    }

    public void showMsg(String msg) {
        toast = Toast.makeText(appContext, "", Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    //获取请求失败得到的code,并针对处理
    public void showMistake(int code, String msg) {
        showMsg(msg);
        switch (code) {
            case 100:

                break;
            default:
                break;
        }
    }

    public void HttpGet(String url, Map<String,String> params,int flag){
        OkHttpUtils.get()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showMsg("未知错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        getCallBack(response,flag);
                    }
                });
    }

    protected void getCallBack(String response,int flag){};

    public void setOrientation() {
        Density.setDefault(mActivity);
    }

    protected abstract int getLayout();

    protected abstract void initEvent();

    private void hideStatusBar() {
//        WindowManager.LayoutParams attrs = getWindow().getAttributes();
////        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN|;
//        getWindow().setAttributes(attrs);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  //该参数指布局能延伸到navigationbar，我们场景中不应加这个参数
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT); //设置navigationbar颜色为透明
        }
    }

    public void showProgress(){
        showProgress(false, "加载中～～");
    }

    public void showProgress(boolean cancel,String msg){
        if(mProgress==null) {
            mProgress = new ProgressDialog(mActivity);
        }
        if(mProgress.isShowing()){
            return;
        }
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setMessage(msg);
        //是否可以通过返回按钮退出对话框
        mProgress.setCancelable(cancel);
        mProgress.show();
    }


    public void dismissProgress(){
        if(mProgress!=null) {
            mProgress.dismiss();
        }
    }



    public void showNormalDialog(Context context,String title){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        normalDialog.setIcon(R.mipmap.wifi_icon);
        normalDialog.setTitle("提示");
        normalDialog.setCancelable(false);
        normalDialog.setMessage(title);
        normalDialog.setPositiveButton("退出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        // 显示
        normalDialog.show();
    }
}
