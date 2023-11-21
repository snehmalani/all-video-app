package videodownloader.privatebrowser.free.hd.statussaver;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import videodownloader.privatebrowser.free.hd.statussaver.mixAds.MyApplication;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

public class DclassApp extends MyApplication implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    public static List<String> UnwantedDomains = null;
    public static String[] allUrlRegx = null;
    public static boolean isBlockFunLoaded = false;
    public static String localparsdata;
    private static DclassApp vObj;
    private SharedPreferences preferences;
    public boolean isRateDisplay = false;


    public static DclassApp getInstance() {
        return vObj;
    }


    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public String getIdFromPref(String prefKey) {
        if (this.preferences == null) {
            this.preferences = PreferenceManager.getDefaultSharedPreferences(getInstance());
        }
        return this.preferences.getString(prefKey, "");
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        vObj = this;
        isBlockFunLoaded = false;

        this.isRateDisplay = false;
        boolean adCurrentState = true;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getInstance());
        FirebaseApp.initializeApp(this);
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);
        UtilsForApp.FillRegexers();
        UtilsForApp.FillUnwanted();
        String str = localparsdata;
        if (str == null || str.isEmpty()) {
            UtilsForApp.FillLocalPardata();
        }
        UtilsForApp.writeEmpty("", "wbproxdata");
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onMoveToForeground() {

    }


}
