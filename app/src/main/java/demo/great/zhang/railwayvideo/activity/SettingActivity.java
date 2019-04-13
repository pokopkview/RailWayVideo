package demo.great.zhang.railwayvideo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.great.zhang.railwayvideo.MainActivity;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.application.Consts;
import demo.great.zhang.railwayvideo.net.URLConst;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.et_server_ip)
    EditText etServerIp;
    @BindView(R.id.et_wifi_name)
    EditText etWifiName;
    @BindView(R.id.et_wifi_pwd)
    EditText etWifiPwd;
    @BindView(R.id.bt_confrim)
    Button btConfrim;

    private String serverIP,wifiName,wifiPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_confrim)
    public void onViewClicked() {
        if(etServerIp.getText().toString().isEmpty()){
            serverIP = etServerIp.getHint().toString();
        }else{
            serverIP = etServerIp.getText().toString();
        }
        if(etWifiName.getText().toString().isEmpty()){
            wifiName = etWifiName.getHint().toString();
        }else{
            wifiName = etWifiName.getText().toString();
        }
        if(etWifiPwd.getText().toString().isEmpty()){
            wifiPwd = etWifiPwd.getHint().toString();
        }else{
            wifiPwd = etWifiPwd.getText().toString();
        }
        URLConst.staticURL = serverIP;
        Consts.STATICPWD = wifiPwd;
        Consts.STATICSSID = wifiName;
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    long firstTime = 0;
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(SettingActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else{
            finish();
        }
    }
}
