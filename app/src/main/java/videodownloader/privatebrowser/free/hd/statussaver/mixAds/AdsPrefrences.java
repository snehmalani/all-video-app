package videodownloader.privatebrowser.free.hd.statussaver.mixAds;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.nio.charset.StandardCharsets;

public class AdsPrefrences {

    public static String TAG_ADS_JSON = "tag_ads_json";
    public static String TAG_HOST_URL_KEY = "tag_host_url_key";
    public static String TAG_BASE_HOST = "tag_base_host";
    public static String TAG_CARRIER_ID_KEY = "tag_carrier_id_key";
    public static String TAG_VPN_ID = "tag_vpn_id";
    public static String TAG_SELECTED_COUNTRY = "tag_selected_country";
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    Activity activity;

    public AdsPrefrences(Activity activity) {
        this.activity = activity;
        initPref();
    }

    public static AdsPrefrences getInstance(Activity activity) {
        return new AdsPrefrences(activity);
    }

    private void initPref() {
        if (editor == null) {
            sharedPreferences = activity.getSharedPreferences("tempPrefSave", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    public void saveString(String tag, String str) {
        initPref();
        editor.putString(tag, encodeString(str));
        editor.apply();
    }

    public String getString(String tag) {
        initPref();
        return decodeString(sharedPreferences.getString(tag, ""));
    }

    public String getString(String tag, String defaultStr) {
        initPref();
        String str1 = decodeString(sharedPreferences.getString(tag, null));
        if (str1 != null) {
            return str1;
        } else {
            return defaultStr;
        }
    }

    public String encodeString(String str) {
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public String decodeString(String str) {
        byte[] data = Base64.decode(str, Base64.DEFAULT);
        return new String(data, StandardCharsets.UTF_8);
    }


}
