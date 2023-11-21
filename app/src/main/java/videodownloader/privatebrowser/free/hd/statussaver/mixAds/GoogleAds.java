package videodownloader.privatebrowser.free.hd.statussaver.mixAds;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.ads.NativeAdLayout;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi.StackAppIPDownloadApiClient;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi.StackAppIPDownloadApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
        AllInOneAds.getInstance().showNative(this);
        GoogleAds.getInstance().showBannerOrNative(this);


     GoogleAds.getInstance().showInter(this, () ->


     GoogleAds.getInstance().showInter(this,()->{

                });


    @Override
    public void onBackPressed() {
        GoogleAds.getInstance().showBackInter(this,()->{
            finish();
        });
    }

 */

public class GoogleAds {

    String TAG = "GoogleAds";
    NativeAd nativeAdfirst;
    NativeAd nativeAdBottom;
    AdView adView;
    static Activity activity;
    Dialog dialog;


    public interface GoogleAdsInterClick {
        void onInterDismiss();

        void onInterFailedToLoad();
    }

    public static void initIdsCheck(Activity activity) {
        if (AdHelper.TEMP_CHECK_VAR == null || AdHelper.TEMP_CHECK_VAR.isEmpty()) {
            new AdHelper(activity, null, () -> {
            }).execute();
        }
    }

    public static GoogleAds getInstance() {
        return new GoogleAds();
    }

    public GoogleAds(Activity activity) {
        initIdsCheck(activity);
        this.activity = activity;
    }

    public GoogleAds() {

    }

    public void showTopBannerOrNative(Activity activity, AdsId adsId, NativeAdLayout placeView) {
        if (AdHelper.NATIVE_SIZE.matches("1")) {
            showSmallNative(activity, adsId.topGoogleNativeID, placeView, true);
        } else if (AdHelper.NATIVE_SIZE.matches("2")) {
            showMidNative(activity, adsId.topGoogleNativeID, placeView, true);
        } else if (AdHelper.NATIVE_SIZE.matches("3")) {
            showBigNative(activity, adsId.topGoogleNativeID, placeView, true);
        }
    }

    public void showMiddleBannerOrNative(Activity activity, AdsId adsId, NativeAdLayout placeView) {
        if (AdHelper.MID_NATIVE_SIZE.matches("1")) {
            showSmallNative(activity, adsId.middleGoogleNativeID, placeView, true);
        } else if (AdHelper.MID_NATIVE_SIZE.matches("2")) {
            showMidNative(activity, adsId.middleGoogleNativeID, placeView, true);
        } else if (AdHelper.MID_NATIVE_SIZE.matches("3")) {
            showBigNative(activity, adsId.middleGoogleNativeID, placeView, true);
        }
    }

    public void showBottomBannerOrNative(Activity activity, AdsId adsId, NativeAdLayout placeView) {
        Log.e(TAG, "showBottomBannerOrNative: 1");
        if (AdHelper.BANNER_SIZE.matches("1")) {
            Log.e(TAG, "showBottomBannerOrNative: 2");
            showBanner(activity, adsId.googleBannerID, placeView);
        } else if (AdHelper.BANNER_SIZE.matches("2")) {
            Log.e(TAG, "showBottomBannerOrNative: 3");
            showSmallNative(activity, adsId.bottomGoogleNativeID, placeView, false);
        } else if (AdHelper.BANNER_SIZE.matches("3")) {
            Log.e(TAG, "showBottomBannerOrNative: 4");
            showMidNative(activity, adsId.bottomGoogleNativeID, placeView, false);
        } else if (AdHelper.BANNER_SIZE.matches("4")) {
            Log.e(TAG, "showBottomBannerOrNative: 5");
            showBigNative(activity, adsId.bottomGoogleNativeID, placeView, false);
        }
        Log.e(TAG, "showBottomBannerOrNative: 6");
    }

    public void showBanner(Activity activity, String adsId, NativeAdLayout placeView) {
        loadBanner(activity, adsId, placeView);
    }

    public void loadBanner(Activity activity, String adsId, NativeAdLayout placeView) {
        if (adsId == null || adsId.isEmpty()) {
            return;
        }
        this.activity = activity;
        adView = new AdView(activity);
        // adView.setAdSize(AdSize.BANNER);
        adView.setAdSize(getAdSize(activity));
        adView.setAdUnitId(adsId);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
//                    ((ViewGroup) placeView).setForegroundGravity(Gravity.CENTER);
                ((ViewGroup) placeView).removeAllViews();
                ((ViewGroup) placeView).addView(adView);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                QurekaAds.getInstance().loadQurekaBanner(activity, placeView);
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdClosed() {
            }
        });
    }

    private AdSize getAdSize(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, (int) (((float) displayMetrics.widthPixels) / displayMetrics.density));
    }


    public void showBackInter(Activity activity, InterAdsClick adsClick) {
        this.activity = activity;
        if (AdHelper.BACKAPPOPEN_ON.matches("1")) {
            AppOpenAd.AppOpenAdLoadCallback onShowAdCompleteListener;
            onShowAdCompleteListener = new AppOpenAd.AppOpenAdLoadCallback() {
                public void onAdLoaded(AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    appOpenAd.show(activity);
                    appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            loadAndShowInter(activity, AdHelper.ID_GOOGLE_BACK_INTER, new GoogleAdsInterClick() {
                                @Override
                                public void onInterDismiss() {
                                    adsClick.onInterDismiss();
                                }

                                @Override
                                public void onInterFailedToLoad() {

                                    AllInOneAds.hideLoading();
                                    adsClick.onInterDismiss();

                                }
                            });
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            loadAndShowInter(activity, AdHelper.ID_GOOGLE_BACK_INTER, new GoogleAdsInterClick() {
                                @Override
                                public void onInterDismiss() {
                                    adsClick.onInterDismiss();
                                }

                                @Override
                                public void onInterFailedToLoad() {

                                    AllInOneAds.hideLoading();
                                    adsClick.onInterDismiss();

                                }
                            });
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    loadAndShowInter(activity, AdHelper.ID_GOOGLE_BACK_INTER, new GoogleAdsInterClick() {
                        @Override
                        public void onInterDismiss() {
                            adsClick.onInterDismiss();
                        }

                        @Override
                        public void onInterFailedToLoad() {

                            AllInOneAds.hideLoading();
                            adsClick.onInterDismiss();

                        }
                    });
                }
            };
            try {
                AppOpenAd.load(activity, AdHelper.ID_GOOGLE_OPEN, new AdRequest.Builder().build(), 1, onShowAdCompleteListener);
            } catch (Exception e) {
            }

        } else {
            loadAndShowInter(activity, AdHelper.ID_GOOGLE_BACK_INTER, new GoogleAdsInterClick() {
                @Override
                public void onInterDismiss() {
                    adsClick.onInterDismiss();
                }

                @Override
                public void onInterFailedToLoad() {
                    QurekaAds.getInstance().loadQurekaInter(activity);
                    AllInOneAds.hideLoading();
                    adsClick.onInterDismiss();

                }
            });
        }

    }

    public void loadAndShowInter(Activity activity, String adsId, GoogleAdsInterClick googleAdsInter) {

        InterstitialAd.load(activity, adsId, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                if (interstitialAd != null) {

                    impressionCounter();

                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdClicked() {
                            // Called when a click is recorded for an ad.

                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            // Set the ad reference to null so you don't show the ad a second time.
                            AllInOneAds.hideLoading();
                            googleAdsInter.onInterDismiss();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when ad fails to show.


                        }

                        @Override
                        public void onAdImpression() {
                            // Called when an impression is recorded for an ad.

                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when ad is shown.

                        }
                    });
                    AllInOneAds.hideLoading();
                    interstitialAd.show(activity);
                } else {
                    AllInOneAds.getInstance().hideLoading();
                    googleAdsInter.onInterFailedToLoad();
//                    Toast.makeText(activity, "Ad not loaded", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                AllInOneAds.hideLoading();
                googleAdsInter.onInterFailedToLoad();
//                    adsClick.onInterDismiss();
            }
        });
    }

    public void showSmallNative(Activity activity, String adsId, NativeAdLayout nativeLay, boolean isTopAd) {
        this.activity = activity;

        if (nativeLay == null) {
            return;
        }

        if (adsId == null || adsId.isEmpty()) {
            return;
        }

        AdLoader adLoader = new AdLoader.Builder(activity, adsId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {

            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                // Show ad here.
                NativeAdView adViewPre;
                if (AdHelper.NATIVE_COLOR.matches("1")) {
                    adViewPre = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.videosaver_instatools_gb_wa_small_native_trn, null);
                } else {
                    adViewPre = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.videosaver_instatools_gb_wa_small_native, null);
                }
                populateUnifiedNativeAdView100(activity, nativeAd, adViewPre);
                setUpNativeViwe(nativeLay, adViewPre);


            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Handle the failure by logging, altering the UI, and so on.
                /**load second native if first id failed*/
                QurekaAds.getInstance().loadQurekaNativeSmall(activity, nativeLay);

            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();
        adLoader.loadAd(new AdRequest.Builder().build());

    }

    public void showBigNative(Activity activity, String adsId, NativeAdLayout nativeLay, boolean isTopAd) {
        initIdsCheck(activity);
        this.activity = activity;

        if (adsId == null || adsId.isEmpty()) {
            return;
        }

        AdLoader adLoader = new AdLoader.Builder(activity, adsId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                // Show the ad.
                NativeAdView adViewPre;
                if (AdHelper.NATIVE_COLOR.matches("1")) {
                    adViewPre = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.videosaver_instatools_gb_wa_big_native_ads_trn, null);
                } else {
                    adViewPre = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.videosaver_instatools_gb_wa_big_native_ads, null);
                }
                populateUnifiedNativeAdView(activity, nativeAd, adViewPre);
                setUpNativeViwe(nativeLay, adViewPre);

            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Handle the failure by logging, altering the UI, and so on.
                QurekaAds.getInstance().loadQurekaNative(activity, nativeLay);
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder()
                // Methods in the NativeAdOptions.Builder class can be
                // used here to specify individual options settings.
                .build()).build();
        adLoader.loadAd(new AdRequest.Builder().build());


    }

    public void showMidNative(Activity activity, String adsId, NativeAdLayout nativeLay, boolean isTopAd) {
        initIdsCheck(activity);
        this.activity = activity;

        if (adsId == null || adsId.isEmpty()) {
            return;
        }

        AdLoader adLoader = new AdLoader.Builder(activity, adsId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                // Show the ad.
                NativeAdView adViewPre;
                if (AdHelper.NATIVE_COLOR.matches("1")) {
                    adViewPre = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.bottom_medium_native_layout_trn, null);
                } else {
                    adViewPre = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.bottom_medium_native_layout, null);
                }
                populateUnifiedNativeAdView100(activity, nativeAd, adViewPre);
                setUpNativeViwe(nativeLay, adViewPre);

            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Handle the failure by logging, altering the UI, and so on.
                /**load second native if first id failed*/
                QurekaAds.getInstance().loadQurekaNativeSmall(activity, nativeLay);
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder()
                // Methods in the NativeAdOptions.Builder class can be
                // used here to specify individual options settings.
                .build()).build();
        adLoader.loadAd(new AdRequest.Builder().build());


    }

    private void setUpNativeViwe(View placeView, NativeAdView adView) {

        if (placeView instanceof RelativeLayout) {
            ((RelativeLayout) placeView).removeAllViews();
            ((RelativeLayout) placeView).addView(adView);
        } else if (placeView instanceof LinearLayout) {
            ((LinearLayout) placeView).removeAllViews();
            ((LinearLayout) placeView).addView(adView);
        } else if (placeView instanceof FrameLayout) {
            ((FrameLayout) placeView).removeAllViews();
            ((FrameLayout) placeView).addView(adView);
        } else {
            ((ViewGroup) placeView).removeAllViews();
            ((ViewGroup) placeView).addView(adView);
        }
    }

    public NativeAdView getNativeAdView(Activity activity) {
        if (AdHelper.NATIVE_SIZE.matches("1")) {
            if (AdHelper.NATIVE_COLOR.matches("1")) {
                return (NativeAdView) activity.getLayoutInflater().inflate(R.layout.videosaver_instatools_gb_wa_small_native_trn, null);
            } else {
                return (NativeAdView) activity.getLayoutInflater().inflate(R.layout.videosaver_instatools_gb_wa_small_native, null);
            }
        } else if (AdHelper.NATIVE_SIZE.matches("2")) {
            if (AdHelper.NATIVE_COLOR.matches("1")) {
                return (NativeAdView) activity.getLayoutInflater().inflate(R.layout.videosaver_instatools_gb_wa_big_native_ads_trn, null);
            } else {
                return (NativeAdView) activity.getLayoutInflater().inflate(R.layout.videosaver_instatools_gb_wa_big_native_ads, null);
            }
        } else {
            return null;
        }
    }

    private void populateUnifiedNativeAdView100(Activity activity, NativeAd nativeAd, NativeAdView adView) {
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        //   adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        TextView button = adView.findViewById(R.id.ad_call_to_action);
        adView.setCallToActionView(button);
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

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

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }

    private void populateUnifiedNativeAdView(Activity activity, NativeAd nativeAd, @NonNull NativeAdView adView) {

        adView.setMediaView((com.google.android.gms.ads.nativead.MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        //    adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        TextView button = adView.findViewById(R.id.ad_call_to_action);

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

        adView.setCallToActionView(button);
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
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
