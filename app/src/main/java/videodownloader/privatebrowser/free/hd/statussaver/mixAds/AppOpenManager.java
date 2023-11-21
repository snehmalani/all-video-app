package videodownloader.privatebrowser.free.hd.statussaver.mixAds;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import videodownloader.privatebrowser.free.hd.statussaver.R;

public class AppOpenManager implements Application.ActivityLifecycleCallbacks, LifecycleObserver {

    private static final String LOG_TAG = "AppOpenManager";
    private static boolean isShowingAd = false;
    private final MyApplication myApplication;
    private AppOpenAd appOpenAd = null;
    private Activity currentActivity;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    public AppOpenManager(MyApplication myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    public void fetchAd() {
        // Have unused ad, no need to fetch another.
        if (isAdAvailable()) {
            return;
        }

        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            /**
             * Called when an app open ad has loaded.
             *
             * @param appOpenAd the loaded app open ad.
             */


            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                AppOpenManager.this.appOpenAd = appOpenAd;
            }

            /**
             * Called when an app open ad has failed to load.
             *
             * @param loadAdError the error.
             */
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }
        };
        AdRequest request = getAdRequest();
        AppOpenAd.load(myApplication, AdHelper.ID_GOOGLE_OPEN, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    /**
     * Creates and returns ad request.
     */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    public boolean isAdAvailable() {
        return appOpenAd != null;
    }


    @Override
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {

        currentActivity = null;
    }

    public void showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable()) {


            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Set the reference to null so isAdAvailable() returns false.
                    AppOpenManager.this.appOpenAd = null;
                    isShowingAd = false;
                    fetchAd();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAd = true;
                }
            };

            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);

            appOpenAd.show(currentActivity);

        } else {
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAd();
        }

    }

    /**
     * LifecycleObserver methods
     */
    @OnLifecycleEvent(ON_START)
    public void onStart() {
        if (AdHelper.IS_OPEN_AD_ON.matches("1")) {
            showAdIfAvailable();
        } else if (AdHelper.IS_OPEN_AD_ON.matches("2") && AdHelper.IS_QUREKA_OPEN_APP_ON.matches("1")) {
            AppOpen_Custom();
        }
    }

    public void AppOpen_Custom() {
        Dialog dialog = new Dialog(currentActivity, 16974120);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.qureka_app_open_ad);
        dialog.setCancelable(false);
        dialog.show();
        TextView description = dialog.findViewById(R.id.description);
        TextView shortdesc = dialog.findViewById(R.id.shortdes);
        TextView actionbutton = dialog.findViewById(R.id.action_btn);

        description.setText("" + AdHelper.disc_open);
        shortdesc.setText("" + AdHelper.short_disc_open);
        actionbutton.setText("" + AdHelper.button_titel_open);

        ImageView imageView = dialog.findViewById(R.id.imageview);
        Glide.with(currentActivity).load(AdHelper.image_open).into(imageView);
        TextView textView = dialog.findViewById(R.id.titel);
        textView.setText("" + AdHelper.title);
        LinearLayout btnQureka = dialog.findViewById(R.id.btnQureka);
        btnQureka.setOnClickListener(view -> {

            try {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor("#FFFFFF")).setShowTitle(true);
                CustomTabsIntent build = builder.build();
                build.intent.setPackage("com.android.chrome");
                build.launchUrl(currentActivity, Uri.parse(AdHelper.redirectLink_open));

            } catch (Exception e) {

            }

            dialog.dismiss();
        });

        RelativeLayout btnSkip = dialog.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(view -> {

            dialog.dismiss();
        });

    }
}