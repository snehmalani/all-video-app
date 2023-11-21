package videodownloader.privatebrowser.free.hd.statussaver.mixAds;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi.StackAppIPDownloadApiClient;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi.StackAppIPDownloadApiInterface;

public class FacebookAds {

    String TAG = "FacebookAds";
    Dialog dialog;

    public interface FacebookAdsInterClick {
        void onInterDismiss();
    }

    public static FacebookAds getInstance() {
        return new FacebookAds();
    }

    public void showTopBannerOrNative(Activity activity, AdsId adsId, NativeAdLayout placeView) {
        if (AdHelper.NATIVE_SIZE.matches("1")) {
            showSmallNative(activity, adsId.topFBNativeID, placeView, true);
        } else if (AdHelper.NATIVE_SIZE.matches("2")) {
            showMidNative(activity, adsId.topFBNativeID, placeView, true);
        } else if (AdHelper.NATIVE_SIZE.matches("3")) {
            showBigNative(activity, adsId.topFBNativeID, placeView, true);
        }
    }

    public void showMiddleBannerOrNative(Activity activity, AdsId adsId, NativeAdLayout placeView) {
        if (AdHelper.MID_NATIVE_SIZE.matches("1")) {
            showSmallNative(activity, adsId.middleFBNativeID, placeView, true);
        } else if (AdHelper.MID_NATIVE_SIZE.matches("2")) {
            showMidNative(activity, adsId.middleFBNativeID, placeView, true);
        } else if (AdHelper.MID_NATIVE_SIZE.matches("3")) {
            showBigNative(activity, adsId.middleFBNativeID, placeView, true);
        }
    }

    public void showBottomBannerOrNative(Activity activity, AdsId adsId, NativeAdLayout placeView) {
        if (AdHelper.BANNER_SIZE.matches("1")) {
            showBanner(activity, adsId.fbBannerID, placeView);
        } else if (AdHelper.BANNER_SIZE.matches("2")) {
            showSmallNative(activity, adsId.bottomFBNativeID, placeView, false);
        } else if (AdHelper.BANNER_SIZE.matches("3")) {
            showMidNative(activity, adsId.bottomFBNativeID, placeView, false);
        } else if (AdHelper.BANNER_SIZE.matches("4")) {
            showBigNative(activity, adsId.bottomFBNativeID, placeView, false);
        }
    }


    public void showBanner(Activity activity, String adsId, NativeAdLayout placeView) {
        loadBanner(activity, adsId, placeView);
    }

    public void loadBanner(Activity activity, String adsId, NativeAdLayout adView1) {
        Log.e(TAG, "loadBanner: FacebookAds");

        if (adsId == null || adsId.isEmpty()) {
            return;
        }

        AdView adView;
        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                QurekaAds.getInstance().loadQurekaBanner(activity, adView1);
            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };
        adView = new AdView(activity, adsId, AdSize.BANNER_HEIGHT_50);
        adView1.removeAllViews();
        adView1.addView(adView);
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
    }


    public void showInterWithId(Activity activity, String interUnitId, FacebookAdsInterClick clicker) {
        Log.e(TAG, "showInter: ");
        InterstitialAd fbInterstitialAd = new InterstitialAd(activity, interUnitId);
        fbInterstitialAd.buildLoadAdConfig().withAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.e(TAG, "onInterstitialDisplayed: ");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.e(TAG, "onInterstitialDismissed: ");
                AllInOneAds.hideLoading();
                clicker.onInterDismiss();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "onError: " + adError.getErrorMessage());
                QurekaAds.getInstance().loadQurekaInter(activity);
                AllInOneAds.hideLoading();
                clicker.onInterDismiss();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e(TAG, "onAdLoaded: ");
                AllInOneAds.hideLoading();
                impressionCounter();
                fbInterstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e(TAG, "onAdClicked: ");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e(TAG, "onLoggingImpression: ");
            }
        });
        fbInterstitialAd.loadAd();

    }

    public void showBackInter(Activity activity, FacebookAdsInterClick clicker) {
        Log.e(TAG, "showInter: ");
        if (AdHelper.BACKAPPOPEN_ON.matches("1")) {
            AppOpenAd.AppOpenAdLoadCallback onShowAdCompleteListener;
            onShowAdCompleteListener = new AppOpenAd.AppOpenAdLoadCallback() {
                public void onAdLoaded(AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    appOpenAd.show(activity);
                    appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            backInterShow(activity, clicker);
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            backInterShow(activity, clicker);
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    backInterShow(activity, clicker);
                }
            };
            try {
                AppOpenAd.load(activity, AdHelper.ID_GOOGLE_OPEN, new AdRequest.Builder().build(), 1, onShowAdCompleteListener);
            } catch (Exception e) {
            }

        } else {
            backInterShow(activity, clicker);
        }
    }

    private void backInterShow(Activity activity, FacebookAdsInterClick clicker) {
        InterstitialAd fbInterstitialAd = new InterstitialAd(activity, AdHelper.ID_FACEBOOK_BACK_INTER);
        fbInterstitialAd.buildLoadAdConfig().withAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.e(TAG, "onInterstitialDisplayed: ");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.e(TAG, "onInterstitialDismissed: ");
                AllInOneAds.hideLoading();
                clicker.onInterDismiss();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "onError: " + adError.getErrorMessage());
                QurekaAds.getInstance().loadQurekaInter(activity);
                AllInOneAds.hideLoading();
                clicker.onInterDismiss();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e(TAG, "onAdLoaded: ");
                AllInOneAds.hideLoading();
                impressionCounter();
                fbInterstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e(TAG, "onAdClicked: ");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e(TAG, "onLoggingImpression: ");
            }
        });
        fbInterstitialAd.loadAd();
    }

    public void showSmallNative(Activity activity, String adsId, NativeAdLayout placeView, boolean isTopAd) {
        if (placeView == null) {
            return;
        }

        if (adsId == null || adsId.isEmpty()) {
            return;
        }
        NativeBannerAd nativeAd = new NativeBannerAd(activity, adsId);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onMediaDownloaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // showing Toast message
                //Toast.makeText(activity, "onError", Toast.LENGTH_SHORT).show();
                /**show google ads if fb was failed!*/
                QurekaAds.getInstance().loadQurekaNative(activity, placeView);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onAdLoaded", Toast.LENGTH_SHORT).show();
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                inflateAdSmall(activity, nativeAd, placeView);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onAdClicked", Toast.LENGTH_SHORT).show();
                placeView.removeAllViews();
                placeView.setVisibility(View.GONE);
                new Handler().postDelayed(() -> {
                    showSmallNative(activity, adsId, placeView, isTopAd);
                }, 2000);
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onLoggingImpression", Toast.LENGTH_SHORT).show();
            }
        };
        // Load an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());

    }

    public void showMidNative(Activity activity, String adsId, NativeAdLayout viewById, boolean isTopAd) {
        if (viewById == null) {
            return;
        }

        if (adsId == null || adsId.isEmpty()) {
            return;
        }
        NativeBannerAd nativeAd = new NativeBannerAd(activity, adsId);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onMediaDownloaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // showing Toast message
                //Toast.makeText(activity, "onError", Toast.LENGTH_SHORT).show();
                /**show google ads if fb was failed!*/
                Log.e(TAG, "onError: 1212 ");
                QurekaAds.getInstance().loadQurekaNativeSmall(activity, viewById);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onAdLoaded", Toast.LENGTH_SHORT).show();
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                inflateAdMid(activity, nativeAd, viewById);

            }

            @Override
            public void onAdClicked(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onAdClicked", Toast.LENGTH_SHORT).show();
                viewById.removeAllViews();
                viewById.setVisibility(View.GONE);
                new Handler().postDelayed(() -> {
                    showMidNative(activity, adsId, viewById, isTopAd);
                }, 2000);
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onLoggingImpression", Toast.LENGTH_SHORT).show();
            }
        };
        // Load an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());


    }


    public void showBigNative(Activity activity, String adsId, NativeAdLayout viewById, boolean isTopAd) {
        if (viewById == null) {
            return;
        }

        if (adsId == null || adsId.isEmpty()) {
            return;
        }
        nativeAd = new NativeAd(activity, adsId);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onMediaDownloaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // showing Toast message
                //Toast.makeText(activity, "onError", Toast.LENGTH_SHORT).show();
                /**show google ads if fb was failed!*/
                QurekaAds.getInstance().loadQurekaNative(activity, viewById);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onAdLoaded", Toast.LENGTH_SHORT).show();
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                inflateAdBig(activity, nativeAd, viewById);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onAdClicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // showing Toast message
                //Toast.makeText(activity, "onLoggingImpression", Toast.LENGTH_SHORT).show();
            }
        };
        // Load an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());

    }

    LinearLayout adView;
    NativeAd nativeAd;

    void inflateAdBig(Activity activity, NativeAd nativeAd, NativeAdLayout nativeAdLayout) {

        // unregister the native Ad View
        nativeAd.unregisterView();
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.
        if (AdHelper.NATIVE_COLOR.matches("1")) {
            adView = (LinearLayout) inflater.inflate(R.layout.videosaver_instatools_gb_fb_ad_big_trn, nativeAdLayout, false);
        } else {
            adView = (LinearLayout) inflater.inflate(R.layout.videosaver_instatools_gb_fb_ad_big, nativeAdLayout, false);
        }

        // adding view
        nativeAdLayout.removeAllViews();
        nativeAdLayout.addView(adView);
        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        TextView nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        if (AdHelper.IS_NATIVE_BLINK.matches("1")) {
            YoYo.with(Techniques.FadeIn).duration(AdHelper.BLINK_TIME).repeat(10000).playOn(nativeAdCallToAction);
        }

        if (AdHelper.NATIVE_BTN_COLOR.matches("1")) {
            nativeAdCallToAction.setTextColor(activity.getResources().getColor(R.color.white));
            nativeAdCallToAction.setBackgroundResource(R.drawable.first_button_chage);
        } else {
            nativeAdCallToAction.setTextColor(activity.getResources().getColor(R.color.white));
            nativeAdCallToAction.setBackgroundResource(R.drawable.first_button_color);
        }

        // Setting  the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and  button to listen for clicks.
        nativeAd.registerViewForInteraction(adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    public static void inflateAdSmall(Activity activity, NativeBannerAd nativeAd_banner, ViewGroup viewGroup) {

        nativeAd_banner.unregisterView();
        NativeAdLayout nativeAdLayout = new NativeAdLayout(activity);
        int i2 = 0;
        View inflate;
        if (AdHelper.NATIVE_COLOR.matches("1")) {
            inflate = LayoutInflater.from(activity).inflate(R.layout.videosaver_instatools_gb_fb_ad_small_trn, (ViewGroup) null, false);
        } else {
            inflate = LayoutInflater.from(activity).inflate(R.layout.videosaver_instatools_gb_fb_ad_small, (ViewGroup) null, false);
        }

        viewGroup.setVisibility(View.VISIBLE);
        viewGroup.removeAllViews();
        viewGroup.addView(inflate);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd_banner, nativeAdLayout);
        linearLayout.removeAllViews();
        linearLayout.addView(adOptionsView, 0);
        TextView textView = (TextView) inflate.findViewById(R.id.native_ad_title);
        TextView textView2 = (TextView) inflate.findViewById(R.id.native_ad_social_context);
        TextView textView3 = (TextView) inflate.findViewById(R.id.native_ad_sponsored_label);
        com.facebook.ads.MediaView mediaView = (com.facebook.ads.MediaView) inflate.findViewById(R.id.native_ad_icon);
        TextView button = (TextView) inflate.findViewById(R.id.native_ad_call_to_action);
        button.setText(nativeAd_banner.getAdCallToAction());

        if (AdHelper.IS_NATIVE_BLINK.matches("1")) {
            YoYo.with(Techniques.FadeIn).duration(AdHelper.BLINK_TIME).repeat(10000).playOn(button);
        }

        if (AdHelper.NATIVE_BTN_COLOR.matches("1")) {
            button.setTextColor(activity.getResources().getColor(R.color.white));
            button.setBackgroundResource(R.drawable.first_button_chage);
        } else {
            button.setTextColor(activity.getResources().getColor(R.color.white));
            button.setBackgroundResource(R.drawable.first_button_color);
        }

        if (!nativeAd_banner.hasCallToAction()) {
            i2 = 4;
        }
        button.setVisibility(i2);
        textView.setText(nativeAd_banner.getAdvertiserName());
        textView2.setText(nativeAd_banner.getAdSocialContext());
        textView3.setText(nativeAd_banner.getSponsoredTranslation());
        ArrayList arrayList = new ArrayList();
        arrayList.add(textView);
        arrayList.add(button);
        nativeAd_banner.registerViewForInteraction(inflate, mediaView, arrayList);
    }


    void inflateAdMid(Activity activity, NativeBannerAd nativeAd_banner, NativeAdLayout viewGroup) {


        nativeAd_banner.unregisterView();

        int i2 = 0;
        View inflate;
        if (AdHelper.NATIVE_COLOR.matches("1")) {
            inflate = LayoutInflater.from(activity).inflate(R.layout.native_fb_ad_mid_trn, (ViewGroup) null, false);
        } else {
            inflate = LayoutInflater.from(activity).inflate(R.layout.native_fb_ad_mid, (ViewGroup) null, false);
        }

        viewGroup.setVisibility(View.VISIBLE);
        viewGroup.removeAllViews();
        viewGroup.addView(inflate);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.ad_choices_container);
        NativeAdLayout nativeAdLayout = new NativeAdLayout(activity);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd_banner, nativeAdLayout);
        linearLayout.removeAllViews();
        linearLayout.addView(adOptionsView, 0);
        TextView textView = (TextView) inflate.findViewById(R.id.native_ad_title);
        TextView textView2 = (TextView) inflate.findViewById(R.id.native_ad_social_context);
        TextView textView3 = (TextView) inflate.findViewById(R.id.native_ad_sponsored_label);
        com.facebook.ads.MediaView mediaView = (com.facebook.ads.MediaView) inflate.findViewById(R.id.native_ad_icon);
        TextView button = (TextView) inflate.findViewById(R.id.native_ad_call_to_action);
        button.setText(nativeAd_banner.getAdCallToAction());

        if (AdHelper.IS_NATIVE_BLINK.matches("1")) {
            YoYo.with(Techniques.FadeIn).duration(AdHelper.BLINK_TIME).repeat(10000).playOn(button);
        }

        if (AdHelper.NATIVE_BTN_COLOR.matches("1")) {
            button.setTextColor(activity.getResources().getColor(R.color.white));
            button.setBackgroundResource(R.drawable.first_button_chage);
        } else {
            button.setTextColor(activity.getResources().getColor(R.color.white));
            button.setBackgroundResource(R.drawable.first_button_color);
        }

        if (!nativeAd_banner.hasCallToAction()) {
            i2 = 4;
        }
        button.setVisibility(i2);
        textView.setText(nativeAd_banner.getAdvertiserName());
        textView2.setText(nativeAd_banner.getAdSocialContext());
        textView3.setText(nativeAd_banner.getSponsoredTranslation());
        ArrayList arrayList = new ArrayList();
        arrayList.add(textView);
        arrayList.add(button);
        nativeAd_banner.registerViewForInteraction(inflate, mediaView, arrayList);




     /*   // unregister the native Ad View
        nativeAd.unregisterView();
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.
        if (AdHelper.NATIVE_COLOR.matches("1")) {
            adView = (LinearLayout) inflater.inflate(R.layout.native_fb_ad_mid_trn, nativeAdLayout, false);
        } else {
            adView = (LinearLayout) inflater.inflate(R.layout.native_fb_ad_mid, nativeAdLayout, false);
        }
        // adding view
        nativeAdLayout.removeAllViews();
        nativeAdLayout.addView(adView);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        TextView nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        if (AdHelper.IS_NATIVE_BLINK.matches("1")) {
            YoYo.with(Techniques.FadeIn).duration(AdHelper.BLINK_TIME).repeat(10000).playOn(nativeAdCallToAction);
        }

        if (AdHelper.NATIVE_BTN_COLOR.matches("1")) {
            nativeAdCallToAction.setTextColor(activity.getResources().getColor(R.color.white));
            nativeAdCallToAction.setBackgroundResource(R.drawable.first_button_chage);
        } else {
            nativeAdCallToAction.setTextColor(activity.getResources().getColor(R.color.white));
            nativeAdCallToAction.setBackgroundResource(R.drawable.first_button_color);
        }
        // Setting  the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and  button to listen for clicks.
        nativeAd.registerViewForInteraction(adView, nativeAdMedia,  clickableViews);*/
    }


    void inflateAdMidRev(Activity activity, NativeAd nativeAd, NativeAdLayout nativeAdLayout) {

        // unregister the native Ad View
        nativeAd.unregisterView();
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.
        if (AdHelper.NATIVE_COLOR.matches("1")) {
            adView = (LinearLayout) inflater.inflate(R.layout.native_fb_ad_mid_trn_rev, nativeAdLayout, false);
        } else {
            adView = (LinearLayout) inflater.inflate(R.layout.native_fb_ad_mid_rev, nativeAdLayout, false);
        }
        // adding view
        nativeAdLayout.removeAllViews();
        nativeAdLayout.addView(adView);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        TextView nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        if (AdHelper.IS_NATIVE_BLINK.matches("1")) {
            YoYo.with(Techniques.FadeIn).duration(AdHelper.BLINK_TIME).repeat(10000).playOn(nativeAdCallToAction);
        }

        if (AdHelper.NATIVE_BTN_COLOR.matches("1")) {
            nativeAdCallToAction.setTextColor(activity.getResources().getColor(R.color.white));
            nativeAdCallToAction.setBackgroundResource(R.drawable.first_button_chage);
        } else {
            nativeAdCallToAction.setTextColor(activity.getResources().getColor(R.color.white));
            nativeAdCallToAction.setBackgroundResource(R.drawable.first_button_color);
        }
        // Setting  the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and  button to listen for clicks.
        nativeAd.registerViewForInteraction(adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }


    public void impressionCounter() {
        StackAppIPDownloadApiInterface client = StackAppIPDownloadApiClient.getClient().create(StackAppIPDownloadApiInterface.class);
        client.countImpression(MyApplication.getInstance().getPackageName(), 1).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
            }
        });
    }

}
