package videodownloader.privatebrowser.free.hd.statussaver.mixAds;

import android.os.StrictMode;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;


public class MyApplication extends MultiDexApplication {

    public static AppOpenManager appOpenManager;
    private static MyApplication _instance;

    public static void initMobileAds() {
        MobileAds.initialize(getInstance(), initializationStatus -> {
            if (appOpenManager == null) {
                if (!AdHelper.IS_OPEN_AD_ON.matches("0")) {
                    appOpenManager = new AppOpenManager(getInstance());
                }
            }
        });
    }

    public static synchronized MyApplication getInstance() {

        MyApplication cricketExpert;
        synchronized (MyApplication.class) {
            synchronized (MyApplication.class) {
                synchronized (MyApplication.class) {
                    cricketExpert = _instance;
                }
                return cricketExpert;
            }

        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;

        AudienceNetworkAds.initialize(this);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        MultiDex.install(this);

        initMobileAds();

//        if (BuildConfig.DEBUG) {
        AdSettings.setTestMode(true);
//        }

    }


}
