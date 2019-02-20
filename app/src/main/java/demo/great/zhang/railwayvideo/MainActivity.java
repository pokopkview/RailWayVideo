package demo.great.zhang.railwayvideo;

import android.Manifest;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.great.zhang.railwayvideo.Utils.MPermissionUtils;
import demo.great.zhang.railwayvideo.Utils.WIFIUtils;
import demo.great.zhang.railwayvideo.application.Consts;
import demo.great.zhang.railwayvideo.base.BaseActivity;
import demo.great.zhang.railwayvideo.base.BaseFragment;
import demo.great.zhang.railwayvideo.fragment.FragmentIndex;
import demo.great.zhang.railwayvideo.fragment.FragmentSearch;
import demo.great.zhang.railwayvideo.fragment.FragmentSetting;
import demo.great.zhang.railwayvideo.fragment.FragmentSubtype;

public class MainActivity extends BaseActivity {


    @BindView(R.id.navigation)
    BottomNavigationView navigation;


    private FragmentIndex fragmentIndex;
    private FragmentSubtype fragmentSubtype;
    private FragmentSearch fragmentSearch;
    private FragmentSetting fragmentSetting;
    private BaseFragment[] fragments;

    private int lastfragment;

    public BottomNavigationView getNavigation() {
        return navigation;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvent() {
        MPermissionUtils.requestPermissionsResult(this, 1, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        showProgress();
                        setNettest();
                    }

                    @Override
                    public void onPermissionDenied() {
                    }
                });
        navigation.setItemIconTintList(null);
    }

    private void setNettest() {
        WIFIUtils wifiUtils = new WIFIUtils(this);
        List<String> ssidList = new ArrayList<>();
        for(ScanResult scanResult : wifiUtils.getWifiList()){
            ssidList.add(scanResult.SSID);
        }

//        if(!ssidList.contains(Consts.STATICSSID)){
//            dismissProgress();
//            showNormalDialog(this,"请在对应Wi-Fi区域内使用！");
//            return;
//        }
        wifiUtils.openWifi();
        boolean isConnect = wifiUtils.connectWifi(Consts.STATICSSID, Consts.STATICPWD);
        System.out.println(isConnect);
        if (isConnect) {
            initFragment();
            dismissProgress();
        } else {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    setNettest();
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 2000);
        }
    }


    private void initFragment() {
        fragmentIndex = new FragmentIndex();
        fragmentSubtype = new FragmentSubtype();
        fragmentSearch = new FragmentSearch();
        fragmentSetting = new FragmentSetting();
        fragments = new BaseFragment[]{fragmentIndex, fragmentSubtype, fragmentSearch, fragmentSetting};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview, fragmentIndex).show(fragmentIndex).commit();

        navigation.setOnNavigationItemSelectedListener(changeFragment);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home: {
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;

                    }
                    return true;
                }
                case R.id.navigation_subtype: {
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;

                    }
                    return true;
                }
                case R.id.navigation_search: {
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;

                    }
                    return true;
                }
                case R.id.navigation_setting: {
                    if (lastfragment != 3) {
                        switchFragment(lastfragment, 3);
                        lastfragment = 3;

                    }
                    return true;
                }
            }
            return false;
        }
    };


    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainview, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

    }
    long firstTime = 0;
    /**
     * 第三种方法
     */
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else{
            finish();
        }
    }

}
