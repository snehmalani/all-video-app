package videodownloader.privatebrowser.free.hd.statussaver.mixAds;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;

import com.facebook.ads.NativeAdLayout;
import com.facebook.shimmer.ShimmerFrameLayout;

import videodownloader.privatebrowser.free.hd.statussaver.R;

public class AllInOneAds {

    public static Dialog dialog;
    String TAG = "AllInOneAds";
    Activity activity;

    public static AllInOneAds getInstance() {
        return new AllInOneAds();
    }


    public static void initIdsCheck(Activity activity) {
        if (AdHelper.TEMP_CHECK_VAR == null || AdHelper.TEMP_CHECK_VAR.isEmpty()) {
            new AdHelper(activity, null, () -> { }).execute();
        }
    }


    public void showInterWithId(Activity activity, AdsId adsId, InterAdsClick adsClick) {
        initIdsCheck(activity);

        if (AdHelper.INTER_COUNT < AdHelper.INTER_RANGE) {
            AdHelper.INTER_COUNT++;
            adsClick.onInterDismiss();
            return;
        }
        AdHelper.INTER_COUNT = 1;


        if (adsId.getInterType() == 0) {
            if (adsId.getFbInterID()==null||adsId.getFbInterID().isEmpty()) {
                adsClick.onInterDismiss();
            }else {
                showLoading(activity, false);
                FacebookAds.getInstance().showInterWithId(activity, adsId.getFbInterID(), adsClick::onInterDismiss);
            }
        } else if (adsId.getInterType() == 1) {
            if (adsId.getGoogleInterID()==null||adsId.getGoogleInterID().isEmpty()) {
                adsClick.onInterDismiss();
                return;
            }
            showLoading(activity, false);
            GoogleAds.getInstance().loadAndShowInter(activity, adsId.getGoogleInterID(), new GoogleAds.GoogleAdsInterClick() {
                @Override
                public void onInterDismiss() {
                    adsClick.onInterDismiss();
                }

                @Override
                public void onInterFailedToLoad() {
                    QurekaAds.getInstance().loadQurekaInter(activity);
                    adsClick.onInterDismiss();
                }
            });
        } else if (adsId.interType == 2) {
            showLoading(activity, false);
            QurekaAds.getInstance().loadQurekaInter(activity);
            adsClick.onInterDismiss();
        } else {
            adsClick.onInterDismiss();
        }

    }


    public void showInterWithIdWithoutLoading(Activity activity, AdsId adsId, InterAdsClick adsClick) {
//        initIdsCheck(activity);


        if (adsId.getInterType() == 0) {
            FacebookAds.getInstance().showInterWithId(activity, adsId.getFbInterID(), adsClick::onInterDismiss);
        } else if (adsId.getInterType() == 1) {
            GoogleAds.getInstance().loadAndShowInter(activity, adsId.getGoogleInterID(), new GoogleAds.GoogleAdsInterClick() {
                @Override
                public void onInterDismiss() {
                    adsClick.onInterDismiss();
                }

                @Override
                public void onInterFailedToLoad() {
                    adsClick.onInterDismiss();
                }
            });
        } else {
            adsClick.onInterDismiss();
        }
    }


    public void showBackInter(Activity activity, InterAdsClick adsClick) {
        Log.e(TAG, "showBackInter: " );
        initIdsCheck(activity);
        Log.e(TAG, "showBackInter: " );
        if (AdHelper.BACK_INTER_ON.matches("0")) {
            Log.e(TAG, "showBackInter: " );
            adsClick.onInterDismiss();
            return;
        }
        Log.e(TAG, "showBackInter: " );
        if (AdHelper.BACK_INTER_COUNT < AdHelper.BACK_INTER_RANGE) {
            Log.e(TAG, "showBackInter: " );
            AdHelper.BACK_INTER_COUNT++;
            adsClick.onInterDismiss();
            return;
        }

        Log.e(TAG, "showBackInter: 2" );
        AdHelper.BACK_INTER_COUNT = 1;
        Log.e(TAG, "showBackInter: 3" );
        if (AdHelper.BACK_INTER_ADS_TYPE.matches("0")) {
            showLoading(activity, false);
            Log.e(TAG, "showBackInter: 4" );
            FacebookAds.getInstance().showBackInter(activity, adsClick::onInterDismiss);
            Log.e(TAG, "showBackInter:5 " );
        } else if (AdHelper.BACK_INTER_ADS_TYPE.matches("1")) {
            showLoading(activity, false);
            Log.e(TAG, "showBackInter: 6" );
            GoogleAds.getInstance().showBackInter(activity, adsClick);
        } else if (AdHelper.BACK_INTER_ADS_TYPE.matches("3")) {
            showLoading(activity, false);
            Log.e(TAG, "showBackInter: 7" );
            QurekaAds.getInstance().loadQurekaInter(activity);
            adsClick.onInterDismiss();
        } else {
            Log.e(TAG, "showBackInter: 8" );
            adsClick.onInterDismiss();
        }
    }

    public void showTopNativeWithId(Activity activity, AdsId adsId, NativeAdLayout nativeLay) {

        ShimmerFrameLayout shimmerFrameLayout = nativeLay.findViewById(R.id.shimmer_layout);
        if (shimmerFrameLayout!=null) {
            shimmerFrameLayout.setVisibility(View.VISIBLE);

            shimmerFrameLayout.findViewById(R.id.bannerProgress).setVisibility(View.GONE);
            shimmerFrameLayout.findViewById(R.id.smallNativeProgress).setVisibility(View.GONE);
            shimmerFrameLayout.findViewById(R.id.midNativeProgress).setVisibility(View.GONE);
            shimmerFrameLayout.findViewById(R.id.bigNativeProgress).setVisibility(View.GONE);

            if (AdHelper.NATIVE_SIZE.matches("1")) {
                shimmerFrameLayout.findViewById(R.id.smallNativeProgress).setVisibility(View.VISIBLE);
            } else if (AdHelper.NATIVE_SIZE.matches("2")) {
                shimmerFrameLayout.findViewById(R.id.midNativeProgress).setVisibility(View.VISIBLE);
            } else if (AdHelper.NATIVE_SIZE.matches("3")) {
                shimmerFrameLayout.findViewById(R.id.bigNativeProgress).setVisibility(View.VISIBLE);
            }

            shimmerFrameLayout.startShimmer();
        }

        initIdsCheck(activity);
        if (nativeLay == null) {
            return;
        }

        if (adsId.topNativeType == 0) {
            FacebookAds.getInstance().showTopBannerOrNative(activity, adsId, nativeLay);
        } else if (adsId.topNativeType == 1) {
            GoogleAds.getInstance().showTopBannerOrNative(activity, adsId, nativeLay);
        } else if (adsId.topNativeType == 2) {
            QurekaAds.getInstance().showTopNativeAds(activity, adsId, nativeLay);
        }
    }

    public void showMiddleNativeWithId(Activity activity, AdsId adsId, NativeAdLayout nativeLay) {

        ShimmerFrameLayout shimmerFrameLayout = nativeLay.findViewById(R.id.shimmer_layout);
        if (shimmerFrameLayout!=null) {
            shimmerFrameLayout.setVisibility(View.VISIBLE);

            shimmerFrameLayout.findViewById(R.id.bannerProgress).setVisibility(View.GONE);
            shimmerFrameLayout.findViewById(R.id.smallNativeProgress).setVisibility(View.GONE);
            shimmerFrameLayout.findViewById(R.id.midNativeProgress).setVisibility(View.GONE);
            shimmerFrameLayout.findViewById(R.id.bigNativeProgress).setVisibility(View.GONE);

            if (AdHelper.MID_NATIVE_SIZE.matches("1")) {
                shimmerFrameLayout.findViewById(R.id.smallNativeProgress).setVisibility(View.VISIBLE);
            } else if (AdHelper.MID_NATIVE_SIZE.matches("2")) {
                shimmerFrameLayout.findViewById(R.id.midNativeProgress).setVisibility(View.VISIBLE);
            } else if (AdHelper.MID_NATIVE_SIZE.matches("3")) {
                shimmerFrameLayout.findViewById(R.id.bigNativeProgress).setVisibility(View.VISIBLE);
            }

            shimmerFrameLayout.startShimmer();
        }

        initIdsCheck(activity);
        if (nativeLay == null) {
            return;
        }

        if (adsId.middleNativeType == 0) {
            FacebookAds.getInstance().showMiddleBannerOrNative(activity, adsId, nativeLay);
        } else if (adsId.middleNativeType == 1) {
            GoogleAds.getInstance().showMiddleBannerOrNative(activity, adsId, nativeLay);
        } else if (adsId.topNativeType == 2) {
            QurekaAds.getInstance().showMiddleNativeAds(activity, adsId, nativeLay);
        }
    }


    public void showBottomNativeWithId(Activity activity, AdsId adsId, NativeAdLayout nativeLay) {

        ShimmerFrameLayout shimmerFrameLayout = nativeLay.findViewById(R.id.shimmer_layout);
        if (shimmerFrameLayout!=null) {
            shimmerFrameLayout.setVisibility(View.VISIBLE);

            shimmerFrameLayout.findViewById(R.id.bannerProgress).setVisibility(View.GONE);
            shimmerFrameLayout.findViewById(R.id.smallNativeProgress).setVisibility(View.GONE);
            shimmerFrameLayout.findViewById(R.id.midNativeProgress).setVisibility(View.GONE);
            shimmerFrameLayout.findViewById(R.id.bigNativeProgress).setVisibility(View.GONE);

            if (AdHelper.BANNER_SIZE.matches("1")) {
                shimmerFrameLayout.findViewById(R.id.bannerProgress).setVisibility(View.VISIBLE);
            } else if (AdHelper.BANNER_SIZE.matches("2")) {
                activity.findViewById(R.id.smallNativeProgress).setVisibility(View.VISIBLE);
            } else if (AdHelper.BANNER_SIZE.matches("3")) {
                shimmerFrameLayout.findViewById(R.id.midNativeProgress).setVisibility(View.VISIBLE);
            } else if (AdHelper.BANNER_SIZE.matches("4")) {
                shimmerFrameLayout.findViewById(R.id.bigNativeProgress).setVisibility(View.VISIBLE);
            }

            shimmerFrameLayout.startShimmer();
        }
        initIdsCheck(activity);
        if (nativeLay == null) {
            return;
        }

        if (adsId.bottomNativeType == 0) {
            FacebookAds.getInstance().showBottomBannerOrNative(activity, adsId, nativeLay);
        } else if (adsId.bottomNativeType == 1) {
            GoogleAds.getInstance().showBottomBannerOrNative(activity, adsId, nativeLay);
        } else if (adsId.topNativeType == 2) {
            QurekaAds.getInstance().showBottomNativeAds(activity, adsId, nativeLay);
        }
    }

    public static void showLoading(Activity activity, boolean cancelable) {
        if (AdHelper.IS_INTER_DIALOG_SHOW.matches("1")) {
            dialog = new Dialog(activity, android.R.style.Theme_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.loading_dialog);
            dialog.setCancelable(cancelable);

            if (!dialog.isShowing() && !activity.isFinishing()) {
                dialog.show();
            }
        }
    }

    public static void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }

}
