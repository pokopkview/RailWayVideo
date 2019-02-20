package demo.great.zhang.railwayvideo.Utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

public class WIFIUtils {
    // 定义WifiManager对象
    private WifiManager mWifiManager;
    // 定义WifiInfo对象
    private WifiInfo mWifiInfo;
    // 网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;
    // 定义一个WifiLock
    WifiManager.WifiLock mWifiLock;

    // 构造器
    public WIFIUtils(Context context) {
        // 取得WifiManager对象
        mWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 取得WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    // 打开WIFI
    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    // 关闭WIFI
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    // 锁定WifiLock
    public void acquireWifiLock() {
        mWifiLock.acquire();
    }

    // 解锁WifiLock
    public void releaseWifiLock() {
        // 判断时候锁定
        if (mWifiLock.isHeld()) {
            mWifiLock.release();
        }
    }

    // 创建一个WifiLock
    public void creatWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("Test");
    }

    // 得到配置好的网络
    public List<WifiConfiguration> getConfiguration() {
        return mWifiConfiguration;
    }

    // 指定配置好的网络进行连接
    public void connectConfiguration(int index) {
        // 索引大于配置好的网络索引返回
        if (index > mWifiConfiguration.size()) {
            return;
        }
        // 连接配置好的指定ID的网络
        mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
                true);
    }

    public void startScan() {
        mWifiManager.startScan();
        // 得到配置好的网络连接
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
    }


    // 得到网络列表
    public List<ScanResult> getWifiList() {
        return mWifiManager.getScanResults();
    }

    // 得到MAC地址
    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    // 得到接入点的BSSID
    public String getBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }

    // 得到IP地址
    public int getIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    // 得到连接的ID
    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    // 得到WifiInfo的所有信息包
    public String getWifiInfo() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    }

    // 添加一个网络并连接
    public void addNetwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);
        mWifiManager.enableNetwork(wcgID, true);
    }

    // 断开指定ID的网络
    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

    /**
     * 连接wifi
     * @param ssid
     * @param psw
     * @return
     */
    public boolean connectWifi(String ssid, String psw) {
        WifiConfiguration config = isExsits(ssid);
        if(null != config){
//            mWifiManager.removeNetwork(config.networkId);
            //如果已经连接过择直接连接wifi
            return mWifiManager.enableNetwork(config.networkId,true);

        }
        int type = getCipherType(ssid);
        config = createWifiInfo(ssid, psw, type);
        System.out.println(config.toString());
        if(null != config){
            //如果没连接过则添加网络配置并连接wifi
            mWifiManager.disconnect();
            int id = mWifiManager.addNetwork(config);
            return mWifiManager.enableNetwork(id, true);

        }
        return false;
    }



    private static final int TYPE_NO_PSW = 1;
    private static final int TYPE_WEP = 2;
    private static final int TYPE_WPA = 3;
    private static final int TYPE_EAP = 4;

    /**
     * 创建连接配置
     *
     * @param SSID
     * @param password
     * @param type
     * @return
     */
    private WifiConfiguration createWifiInfo(String SSID, String password,
                                             int type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";
        if (type == TYPE_NO_PSW) {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == TYPE_WEP) {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + password + "\"";
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == TYPE_WPA) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
//          config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
//          config.allowedPairwiseCiphers
//                  .set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    /**
     * 检测是否配置过该wifi
     * @param SSID
     * @return
     */
    private WifiConfiguration isExsits(String SSID) {
        //获取所有配置过的WiFi列表
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    /**
     * 基于wifi配置获取加密方式
     * @param config
     * @return
     */
    private int getSecurity(WifiConfiguration config) {
        if (config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_PSK)) {
            return TYPE_WPA;
        } else if (config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_EAP)
                || config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.IEEE8021X)) {
            return TYPE_EAP;
        } else {
            return (config.wepKeys[0] != null) ? TYPE_WEP : TYPE_NO_PSW;
        }
    }

    /**
     * 基于ScanResult获取加密方式
     * @param ssid
     * @return
     */
    private int getCipherType(String ssid) {
        //获取所有的扫描结果列表
        List<ScanResult> list = mWifiManager.getScanResults();
        System.out.println(list.size());
        for (ScanResult scResult : list) {
            if (!TextUtils.isEmpty(scResult.SSID) && scResult.SSID.equals(ssid)) {
                //加密方式属性
                String capabilities = scResult.capabilities;
                Log.e("m_tag","capabilities=" + capabilities);
                if (!TextUtils.isEmpty(capabilities)) {
                    if (capabilities.contains("WPA") || capabilities.contains("wpa")) {
                        return TYPE_WPA;
                    } else if (capabilities.contains("WEP") || capabilities.contains("wep")) {
                        return TYPE_WEP;
                    } else {
                        return TYPE_NO_PSW;
                    }
                }
            }
        }
        return -1;
    }
}
