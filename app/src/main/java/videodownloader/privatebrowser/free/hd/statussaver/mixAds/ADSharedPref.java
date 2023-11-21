package videodownloader.privatebrowser.free.hd.statussaver.mixAds;

import android.content.Context;
import android.content.SharedPreferences;


public class ADSharedPref {


    public static final String PREF_NAME = "PREF_PROFILE";
    public static final int MODE = Context.MODE_PRIVATE;
    public static final String SHOW = "SHOW";


    public static final String IND_USER = "IND_USER";
    public static final String NATIVE1ADS = "NATIVE1ADS";
    public static final String NATIVE2ADS = "NATIVE2ADS";
    public static final String INTERINCLICK = "INTERINCLICK";


    public static final String INTERBACKCLICK = "INTERBACKCLICK";
    public static final String INTERDIALOGIN = "INTERDIALOGIN";
    public static final String INTERDIALOGBACK = "INTERDIALOGBACK";
    public static final String INTERSTITIAL1ADS = "INTERSTITIAL1ADS";
    public static final String INTERSTITIAL2ADS = "INTERSTITIAL2ADS";
    public static final String INTERSTITIAL3ADS = "INTERSTITIAL3ADS";
    public static final String APPOPENSPLASHONOFF = "APPOPENSPLASHONOFF";

    public static final String APPOPEN1ADS = "APPOPEN1ADS";
    public static final String APPOPEN2ADS = "APPOPEN2ADS";
    public static final String ADSBACKONOFF = "ADSBACKONOFF";

    public static final String PRIVACY = "PRIVACY";
    public static final String NATIVE_BANNER_ON_OFF = "Native_Banner_On_OF"; // 1 google ,2 fb , 3 google after fb
    public static final String STATUS = "STATUS"; // 1 google ,2 fb , 3 google after fb

    public static final String FB_INTER = "FB_INTER";
    public static final String FB_BANNER = "FB_BANNER";
    public static final String FB_NATIVE = "FB_NATIVE";


    public static final String BACKAPPOPENANDINTERSTITIAL = "BACKAPPOPENANDINTERSTITIAL";
    public static final String INAPPOPENANDINTERSTITIAL = "INAPPOPENANDINTERSTITIAL";
    public static final String BLINK_TIME = "BLINK_TIME";

    public static final String NATIVETOP_ON_OFF = "NATIVETOP_ON_OFF";
    public static final String EXTRAPAGE = "EXTRAPAGE";

    public static final String NATIVET = "NATIVET";
    public static final String NATIVECOLOR = "NATIVECOLOR";

    public static final String NATIVESIZE = "NATIVESIZE";
    public static final String BLINK = "BLINK";
    public static final String fb_twitter_on_off = "fb_twitter_on_off";


    public static void setBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void setInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int getInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void setString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String getString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void setFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float getFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void setLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long getLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void clearlogin(Context context) {
        getPreferences(context).edit().clear().commit();
    }

}

