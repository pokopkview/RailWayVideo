package demo.great.zhang.railwayvideo.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import androidx.annotation.RequiresPermission;

public class DeviceUtils {


    /**
     * Get uuid
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider
                .Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();

        return uniqueId;
    }

    /**
     * Get screen brightness mode，must declare the
     * {@link android.Manifest.permission#WRITE_SETTINGS} permission in its manifest.
     *
     * @param context
     * @return
     */
    public static int getScreenBrightnessMode(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    /**
     * Judge screen brightness mode is auto mode，must declare the
     * {@link android.Manifest.permission#WRITE_SETTINGS} permission in its manifest.
     *
     * @param context
     * @return true:auto;false: manual;default:true
     */
    public static boolean isScreenBrightnessModeAuto(Context context) {
        return getScreenBrightnessMode(context) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC ? true
                : false;
    }

    /**
     * Set screen brightness mode，must declare the
     * {@link android.Manifest.permission#WRITE_SETTINGS} permission in its manifest.
     *
     * @param context
     * @param auto
     * @return
     */
    public static boolean setScreenBrightnessMode(Context context, boolean auto) {
        boolean result = true;
        if (isScreenBrightnessModeAuto(context) != auto) {
            result = Settings.System.putInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    auto ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                            : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }
        return result;
    }

    /**
     * Get screen brightness, must declare the
     * {@link android.Manifest.permission#WRITE_SETTINGS} permission in its manifest.
     *
     * @param context
     * @return brightness:0-255; default:255
     */
    public static int getScreenBrightness(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, 255);
    }

    /**
     * Set screen brightness, cannot change window brightness.must declare the
     * {@link android.Manifest.permission#WRITE_SETTINGS} permission in its manifest.
     *
     * @param context
     * @param screenBrightness 0-255
     * @return
     */
    public static boolean setScreenBrightness(Context context,
                                              int screenBrightness) {
        int brightness = screenBrightness;
        if (screenBrightness < 1) {
            brightness = 1;
        } else if (screenBrightness > 255) {
            brightness = screenBrightness % 255;
            if (brightness == 0) {
                brightness = 255;
            }
        }
        boolean result = Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, brightness);
        return result;
    }

    /**
     * Set window brightness, cannot change system brightness.
     *
     * @param activity
     * @param screenBrightness 0-255
     */
    public static void setWindowBrightness(Activity activity,
                                           float screenBrightness) {
        float brightness = screenBrightness;
        if (screenBrightness < 1) {
            brightness = 1;
        } else if (screenBrightness > 255) {
            brightness = screenBrightness % 255;
            if (brightness == 0) {
                brightness = 255;
            }
        }
        Window window = activity.getWindow();
        WindowManager.LayoutParams localLayoutParams = window.getAttributes();
        localLayoutParams.screenBrightness = brightness / 255.0f;
        window.setAttributes(localLayoutParams);
    }

    /**
     * Set screen brightness and change system brightness, must declare the
     * {@link android.Manifest.permission#WRITE_SETTINGS} permission in its manifest.
     *
     * @param activity
     * @param screenBrightness 0-255
     * @return
     */
    public static boolean setScreenBrightnessAndApply(Activity activity,
                                                      int screenBrightness) {
        boolean result = setScreenBrightness(activity, screenBrightness);
        if (result) {
            setWindowBrightness(activity, screenBrightness);
        }
        return result;
    }

    /**
     * Get screen dormant time, must declare the
     * {@link android.Manifest.permission#WRITE_SETTINGS} permission in its manifest.
     *
     * @param context
     * @return dormantTime:ms, default:30s
     */
    public static int getScreenDormantTime(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT, 30000);
    }

    /**
     * Set screen dormant time by millis, must declare the
     * {@link android.Manifest.permission#WRITE_SETTINGS} permission in its manifest.
     *
     * @param context
     * @return
     */
    public static boolean setScreenDormantTime(Context context, int millis) {
        return Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT, millis);
    }

    /**
     * Get airplane mode, must declare the
     * {@link android.Manifest.permission#WRITE_APN_SETTINGS} permission in its manifest.
     *
     * @param context
     * @return 1:open, 0:close, default:close
     */
    public static int getAirplaneMode(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0);
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0);
        }
    }

    /**
     * Judge whether airplane is open, must declare the
     * {@link android.Manifest.permission#WRITE_APN_SETTINGS} permission in its manifest.
     *
     * @param context
     * @return true:open, false:close, default:close
     */
    public static boolean isAirplaneModeOpen(Context context) {
        return getAirplaneMode(context) == 1 ? true : false;
    }

    /**
     * Set airplane mode, must declare the
     * {@link android.Manifest.permission#WRITE_APN_SETTINGS} permission in its manifest.
     *
     * @param context
     * @param enable
     * @return
     */
    public static boolean setAirplaneMode(Context context, boolean enable) {
        boolean result = true;
        if (isAirplaneModeOpen(context) != enable) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                result = Settings.System.putInt(context.getContentResolver(),
                        Settings.System.AIRPLANE_MODE_ON, enable ? 1 : 0);
            } else {
                result = Settings.Global.putInt(context.getContentResolver(),
                        Settings.Global.AIRPLANE_MODE_ON, enable ? 1 : 0);
            }
            context.sendBroadcast(new Intent(
                    Intent.ACTION_AIRPLANE_MODE_CHANGED));
        }
        return result;
    }

    /**
     * Get bluetooth state
     *
     * @return STATE_OFF, STATE_TURNING_OFF, STATE_ON, STATE_TURNING_ON, NONE
     * @throws Exception
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static Integer getBluetoothState() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return null;
        } else {
            return bluetoothAdapter.getState();
        }
    }

    /**
     * Judge bluetooth is open
     *
     * @return true:open, false:close.
     */
    public static boolean isBluetoothOpen() {
        Integer bluetoothStateCode = getBluetoothState();
        if (bluetoothStateCode == null) {
            return false;
        }
        return bluetoothStateCode == BluetoothAdapter.STATE_ON
                || bluetoothStateCode == BluetoothAdapter.STATE_TURNING_ON ? true
                : false;
    }

    /**
     * Set bluetooth
     *
     * @param enable
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    public static void setBluetooth(boolean enable) throws Exception {
        if (isBluetoothOpen() != enable) {
            if (enable) {
                BluetoothAdapter.getDefaultAdapter().enable();
            } else {
                BluetoothAdapter.getDefaultAdapter().disable();
            }
        }
    }

    /**
     * Get media volume
     *
     * @param context
     * @return volume:0-15
     */
    public static int getMediaVolume(Context context) {
        return ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).getStreamVolume(AudioManager
                .STREAM_MUSIC);
    }

    /**
     * Set media volume
     *
     * @param context
     * @return volume:0-15
     */
    public static void setMediaVolume(Context context, int mediaVloume) {
        if (mediaVloume < 0) {
            mediaVloume = 0;
        } else if (mediaVloume > 15) {
            mediaVloume = mediaVloume % 15;
            if (mediaVloume == 0) {
                mediaVloume = 15;
            }
        }
        ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).setStreamVolume(AudioManager.STREAM_MUSIC,
                mediaVloume, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
    }

    /**
     * Get ring volume
     *
     * @param context
     * @return volume:0-7
     */
    public static int getRingVolume(Context context) {
        return ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).getStreamVolume(AudioManager
                .STREAM_RING);
    }

    /**
     * Set ring volume
     *
     * @param context
     * @return volume:0-7
     */
    public static void setRingVolume(Context context, int ringVloume) {
        if (ringVloume < 0) {
            ringVloume = 0;
        } else if (ringVloume > 7) {
            ringVloume = ringVloume % 7;
            if (ringVloume == 0) {
                ringVloume = 7;
            }
        }
        ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).setStreamVolume(AudioManager.STREAM_RING,
                ringVloume, AudioManager.FLAG_PLAY_SOUND);
    }

    public static boolean isMiui(Context context) {

        if ("xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
            return true;
        }

        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        i.setClassName("com.android.settings", "com.miui.securitycenter.permission.AppPermissionsEditor");
        if (isIntentAvailable(context, i)) {
            return true;
        }
        //双重判断
        boolean isMiUi = "miui".equalsIgnoreCase(Build.ID);

        if ("xiaomi".equalsIgnoreCase(Build.BRAND)) {
            isMiUi = true;
        }
        if (Build.MODEL != null) {
            String str = Build.MODEL.toLowerCase();
            if (str.contains("xiaomi")) {
                isMiUi = true;
            }
            if (str.contains("miui")) {
                isMiUi = true;
            }
        }
        return isMiUi;
    }

    public static boolean isFlyme() {
        return Build.DISPLAY.startsWith("Flyme");
    }

    private static boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        @SuppressLint("WrongConstant") List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
        return list.size() > 0;
    }

    public static boolean setMIUIStatusBarDarkMode(Activity activity, boolean darkMode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkMode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean setFlymeStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }
}
