package videodownloader.privatebrowser.free.hd.statussaver.mixAds;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class AdHelper extends AsyncTask {

    public static String scrapUrl = "";
    OnShowAdCompleteListener onShowAdCompleteListener;

    /**
     * Always remember empty test id's after complete testing app.
     */

    public static AdsId introId;
    public static AdsId settingId;
    public static AdsId fbSaverId;
    public static AdsId whatsappId;
    public static AdsId twitterId;
    public static AdsId igSaverId;
    public static AdsId pintrestId;
    public static AdsId vimeId;
    public static AdsId googleId;
    public static AdsId fbVideoDownloaderId;
    public static AdsId instaVideoDownloaderId;
    public static AdsId hashTagId;
    public static AdsId captionId;
    public static AdsId bioId;
    public static AdsId allVideoDownloadId;
    public static AdsId languageId;


    public static int splashScreen = 2;
    public static String splashInterFb = "";
    public static String splashInterGoogle = "";
    public static String splashOpenGoogle = "";

    public static String ID_GOOGLE_BACK_INTER = "";
    public static String ID_GOOGLE_OPEN = "";
    public static String ID_FACEBOOK_BACK_INTER = "";


    /*Qureka*/
    public static String IS_QUREKA_NATIVE_ON = "1";// 0, 1
    public static String IS_QUREKA_BANNER_ON = "1";// 0, 1
    public static String IS_QUREKA_OPEN_APP_ON = "1";// 0, 1
    public static String IS_QUREKA_INTER_ON = "1";// 0, 1

    public static String QurekaBanner = "https://helloindianstar.co/apiV2/aupload/adsBanner/2.jpg,https://helloindianstar.co/apiV2/aupload/adsBanner/3.jpg,https://helloindianstar.co/apiV2/aupload/adsBanner/4.jpg,https://helloindianstar.co/apiV2/aupload/adsBanner/5.jpg,https://helloindianstar.co/apiV2/aupload/adsBanner/6.jpg";
    public static String redirectLink_banner = "https://play395.atmequiz.com";
    public static String inter_redirectLink = "https://play395.atmequiz.com";
    public static String button_titel = "Play Now";
    public static String disc = "Play&Win Coin";
    public static String image = "https://helloindianstar.co/apiV2/aupload/adsBanner/1.png";
    public static String image2 = "https://helloindianstar.co/apiV2/aupload/adsBanner/1.gif";
    public static String short_disc = "win 5,00,000  Coin..";
    public static String redirectLink_native = "https://play395.atmequiz.com";
    public static String button_titel_open = "Play Now";
    public static String disc_open = "Prize  Quize:5,00,000";
    public static String image_open = "https://helloindianstar.co/apiV2/aupload/adsBanner/1.png";
    public static String short_disc_open = "No Install Required";
    public static String title = "win 5,00,000  Coin..";
    public static String redirectLink_open = "https://play395.atmequiz.com";

    /**
     * OLD
     */


    public static String IS_OPEN_AD_ON = "0";// 0, 1
    public static String BACK_INTER_ADS_TYPE = "0";  // 0,1,2
    public static String BACKAPPOPEN_ON = "0";  // 0,1,2
    public static String BACK_INTER_ON = "0";  // 0,1,2
    public static int INTER_RANGE = 0;  // 0,1
    public static int BACK_INTER_RANGE = 0;  // 0,1
    //    public static String IS_NATIVE_PRELOAD = "0";   //0,1
    public static String NATIVE_SIZE = "0";  // 0 = OFF, 1 = SMALL,2 = BIG
    public static String MID_NATIVE_SIZE = "0";  // 0 = OFF, 1 = SMALL,2 = BIG
    public static String BANNER_SIZE = "0";  // 0 = OFF, 1 = SMALL,2 = BIG
    public static String IS_INTER_DIALOG_SHOW = "0"; //0,1
    public static String NATIVE_COLOR = "0"; //0 = COLORED,1 = TRANSPARENT
    public static String NATIVE_BTN_COLOR = "0"; //0 = BLACK,1 = APP COLOR
    public static String IS_NATIVE_BLINK = "0"; //0,1
    //    public static String IS_GALLERY_NATIVE = "0"; //0,1
    public static String IS_SPLASH_OPEN_AD_ON = "0";// 0 = DIRECT NEXT, 1 = APP OPEN, 2 = INTER
    public static int BLINK_TIME = 0;
    public static int INTER_COUNT = 0;  // 0,1
    public static int BACK_INTER_COUNT = 0;  // 0,1
    public static int PAGE_RANGE = 0;  // 0,1,2,3,4,5,6
    public static String PRIVACY_POLICY = "";
    public static String VPN_DIALOG = "1";


    public static String TEMP_CHECK_VAR = ""; //0,1

    /*special for one app var's*/
    public static String HOME_CALLER_VIEW = "0"; //0,1
    Activity activity;
    String adJson;


    public AdHelper(Activity activity, String adJson, OnShowAdCompleteListener onShowAdCompleteListener) {
        this.activity = activity;
        this.adJson = adJson;
        this.onShowAdCompleteListener = onShowAdCompleteListener;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (adJson == null || adJson.isEmpty()) {
            adJson = AdsPrefrences.getInstance(activity).getString(AdsPrefrences.TAG_ADS_JSON);
        }

        /*
        {"IS_QUREKA_NATIVE_ON":"0","IS_QUREKA_BANNER_ON":"0","IS_QUREKA_OPEN_APP_ON":"0","IS_QUREKA_INTER_ON":"0","id_google_back_inter":"\/6499\/example\/interstitial","back_inter_on":"1","back_inter_ads_type":"1","is_open_ad_on":"1","native_size":"3","mid_native_size":"2","banner_size":"1","is_inter_dialog_show":"1","native_color":"0","native_btn_color":"1","is_native_blink":"0","blink_time":"1000","inter_range":"0","back_inter_range":"0","Privacypolicy":"https:\/\/divineinfotech.website\/","vpnD":"1","inter_redirectLink":"https:\/\/play395.atmequiz.com","BannerAds":{"image":"https:\/\/helloindianstar.co\/apiV2\/aupload\/adsBanner\/2.jpg,https:\/\/helloindianstar.co\/apiV2\/aupload\/adsBanner\/3.jpg,https:\/\/helloindianstar.co\/apiV2\/aupload\/adsBanner\/4.jpg,https:\/\/helloindianstar.co\/apiV2\/aupload\/adsBanner\/5.jpg,https:\/\/helloindianstar.co\/apiV2\/aupload\/adsBanner\/6.jpg","redirectLink":"https:\/\/play395.atmequiz.com"},"nativeads":{"button_titel":"Play Now","disc":"Play&Win Coin","image":"https:\/\/helloindianstar.co\/apiV2\/aupload\/adsBanner\/1.png","image2":"https:\/\/helloindianstar.co\/apiV2\/aupload\/adsBanner\/1.gif","redirectLink":"https:\/\/play395.atmequiz.com","short_disc":"win 5,00,000 Coin.."},"appopen":{"button_titel":"Play Now","disc":"Prize Quize:5,00,000","image":"https:\/\/helloindianstar.co\/apiV2\/aupload\/adsBanner\/1.png","short_disc":"No Install Required","redirectLink":"https:\/\/play395.atmequiz.com","title":"win 5,00,000 Coin.."},"splashScreen":"0","splashInterFb":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","splashInterGoogle":"\/6499\/example\/interstitial","splashOpenGoogle":"\/6499\/example\/app-open","introInterType":"0","introInterGoogleId":"\/6499\/example\/interstitial","introIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","introTopNativeType":"0","introTopNativeGoogleId":"\/6499\/example\/native","introTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","introMiddleNativeType":"0","introMiddleNativeGoogleId":"\/6499\/example\/native","introMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","introBottomNativeType":"0","introBottomNativeGoogleId":"\/6499\/example\/native","introBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","settingInterType":"0","settingInterGoogleId":"\/6499\/example\/interstitial","settingIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","settingTopNativeType":"0","settingTopNativeGoogleId":"\/6499\/example\/native","settingTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","settingMiddleNativeType":"0","settingMiddleNativeGoogleId":"\/6499\/example\/native","settingMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","settingBottomNativeType":"0","settingBottomNativeGoogleId":"\/6499\/example\/native","settingBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","fbSaverInterType":"0","fbSaverInterGoogleId":"\/6499\/example\/interstitial","fbSaverIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","fbSaverTopNativeType":"0","fbSaverTopNativeGoogleId":"\/6499\/example\/native","fbSaverTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","fbSaverMiddleNativeType":"0","fbSaverMiddleNativeGoogleId":"\/6499\/example\/native","fbSaverMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","fbSaverBottomNativeType":"0","fbSaverBottomNativeGoogleId":"\/6499\/example\/native","fbSaverBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","whatsappInterType":"0","whatsappInterGoogleId":"\/6499\/example\/interstitial","whatsappIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","whatsappTopNativeType":"0","whatsappTopNativeGoogleId":"\/6499\/example\/native","whatsappTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","whatsappMiddleNativeType":"0","whatsappMiddleNativeGoogleId":"\/6499\/example\/native","whatsappMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","whatsappBottomNativeType":"0","whatsappBottomNativeGoogleId":"\/6499\/example\/native","whatsappBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","twitterInterType":"0","twitterInterGoogleId":"\/6499\/example\/interstitial","twitterIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","twitterTopNativeType":"0","twitterTopNativeGoogleId":"\/6499\/example\/native","twitterTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","twitterMiddleNativeType":"0","twitterMiddleNativeGoogleId":"\/6499\/example\/native","twitterMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","twitterBottomNativeType":"0","twitterBottomNativeGoogleId":"\/6499\/example\/native","twitterBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","igSaverInterType":"0","igSaverInterGoogleId":"\/6499\/example\/interstitial","igSaverIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","igSaverTopNativeType":"0","igSaverTopNativeGoogleId":"\/6499\/example\/native","igSaverTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","igSaverMiddleNativeType":"0","igSaverMiddleNativeGoogleId":"\/6499\/example\/native","igSaverMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","igSaverBottomNativeType":"0","igSaverBottomNativeGoogleId":"\/6499\/example\/native","igSaverBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","pintrestInterType":"0","pintrestInterGoogleId":"\/6499\/example\/interstitial","pintrestIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","pintrestTopNativeType":"0","pintrestTopNativeGoogleId":"\/6499\/example\/native","pintrestTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","pintrestMiddleNativeType":"0","pintrestMiddleNativeGoogleId":"\/6499\/example\/native","pintrestMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","pintrestBottomNativeType":"0","pintrestBottomNativeGoogleId":"\/6499\/example\/native","pintrestBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","vimeInterType":"0","vimeInterGoogleId":"\/6499\/example\/interstitial","vimeIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","vimeTopNativeType":"0","vimeTopNativeGoogleId":"\/6499\/example\/native","vimeTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","vimeMiddleNativeType":"0","vimeMiddleNativeGoogleId":"\/6499\/example\/native","vimeMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","vimeBottomNativeType":"0","vimeBottomNativeGoogleId":"\/6499\/example\/native","vimeBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","googleInterType":"0","googleInterGoogleId":"\/6499\/example\/interstitial","googleIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","googleTopNativeType":"0","googleTopNativeGoogleId":"\/6499\/example\/native","googleTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","googleMiddleNativeType":"0","googleMiddleNativeGoogleId":"\/6499\/example\/native","googleMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","googleBottomNativeType":"0","googleBottomNativeGoogleId":"\/6499\/example\/native","googleBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","fbVideoDownloaderInterType":"0","fbVideoDownloaderInterGoogleId":"\/6499\/example\/interstitial","fbVideoDownloaderIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","fbVideoDownloaderTopNativeType":"0","fbVideoDownloaderTopNativeGoogleId":"\/6499\/example\/native","fbVideoDownloaderTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","fbVideoDownloaderMiddleNativeType":"0","fbVideoDownloaderMiddleNativeGoogleId":"\/6499\/example\/native","fbVideoDownloaderMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","fbVideoDownloaderBottomNativeType":"0","fbVideoDownloaderBottomNativeGoogleId":"\/6499\/example\/native","fbVideoDownloaderBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","instaVideoDownloaderInterType":"0","instaVideoDownloaderInterGoogleId":"\/6499\/example\/interstitial","instaVideoDownloaderIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","instaVideoDownloaderTopNativeType":"0","instaVideoDownloaderTopNativeGoogleId":"\/6499\/example\/native","instaVideoDownloaderTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","instaVideoDownloaderMiddleNativeType":"0","instaVideoDownloaderMiddleNativeGoogleId":"\/6499\/example\/native","instaVideoDownloaderMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","instaVideoDownloaderBottomNativeType":"0","instaVideoDownloaderBottomNativeGoogleId":"\/6499\/example\/native","instaVideoDownloaderBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","hashTagInterType":"0","hashTagInterGoogleId":"\/6499\/example\/interstitial","hashTagIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","hashTagTopNativeType":"0","hashTagTopNativeGoogleId":"\/6499\/example\/native","hashTagTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","hashTagMiddleNativeType":"0","hashTagMiddleNativeGoogleId":"\/6499\/example\/native","hashTagMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","hashTagBottomNativeType":"0","hashTagBottomNativeGoogleId":"\/6499\/example\/native","hashTagBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","captionInterType":"0","captionInterGoogleId":"\/6499\/example\/interstitial","captionIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","captionTopNativeType":"0","captionTopNativeGoogleId":"\/6499\/example\/native","captionTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","captionMiddleNativeType":"0","captionMiddleNativeGoogleId":"\/6499\/example\/native","captionMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","captionBottomNativeType":"0","captionBottomNativeGoogleId":"\/6499\/example\/native","captionBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","bioInterType":"0","bioInterGoogleId":"\/6499\/example\/interstitial","bioIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","bioTopNativeType":"0","bioTopNativeGoogleId":"\/6499\/example\/native","bioTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","bioMiddleNativeType":"0","bioMiddleNativeGoogleId":"\/6499\/example\/native","bioMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","bioBottomNativeType":"0","bioBottomNativeGoogleId":"\/6499\/example\/native","bioBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","allVideoDownloadInterType":"0","allVideoDownloadInterGoogleId":"\/6499\/example\/interstitial","allVideoDownloadIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","allVideoDownloadTopNativeType":"0","allVideoDownloadTopNativeGoogleId":"\/6499\/example\/native","allVideoDownloadTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","allVideoDownloadMiddleNativeType":"0","allVideoDownloadMiddleNativeGoogleId":"\/6499\/example\/native","allVideoDownloadMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","allVideoDownloadBottomNativeType":"0","allVideoDownloadBottomNativeGoogleId":"\/6499\/example\/native","allVideoDownloadBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","languageInterType":"0","languageInterGoogleId":"\/6499\/example\/interstitial","languageIinterFBId":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","languageTopNativeType":"0","languageTopNativeGoogleId":"\/6499\/example\/native","languageTopNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","languageMiddleNativeType":"0","languageMiddleNativeGoogleId":"\/6499\/example\/native","languageMiddleNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID","languageBottomNativeType":"0","languageBottomNativeGoogleId":"\/6499\/example\/native","languageBottomNativeFBID":"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"}
         */

        adJson = "{\"IS_QUREKA_NATIVE_ON\":\"0\"," +
                "\"IS_QUREKA_BANNER_ON\":\"0\"," +
                "\"IS_QUREKA_OPEN_APP_ON\":\"0\"," +
                "\"IS_QUREKA_INTER_ON\":\"0\"," +
                "\"id_google_back_inter\":\"\\/6499\\/example\\/interstitial\"," +
                "\"back_inter_on\":\"0\"," +
                "\"back_inter_ads_type\":\"0\"," +
                "\"is_open_ad_on\":\"0\"," +
                "\"native_size\":\"0\"," +
                "\"mid_native_size\":\"0\"," +
                "\"banner_size\":\"0\"," +
                "\"is_inter_dialog_show\":\"1\"," +
                "\"native_color\":\"0\"," +
                "\"native_btn_color\":\"1\"," +
                "\"is_native_blink\":\"0\"," +
                "\"blink_time\":\"1000\"," +
                "\"inter_range\":\"0\"," +
                "\"back_inter_range\":\"0\"," +
                "\"Privacypolicy\":\"https:\\/\\/divineinfotech.website\\/\"," +
                "\"vpnD\":\"1\"," +
                "\"inter_redirectLink\":\"https:\\/\\/play395.atmequiz.com\"," +
                "\"BannerAds\":{\"image\":\"https:\\/\\/helloindianstar.co\\/apiV2\\/aupload\\/adsBanner\\/2.jpg," +
                "https:\\/\\/helloindianstar.co\\/apiV2\\/aupload\\/adsBanner\\/3.jpg," +
                "https:\\/\\/helloindianstar.co\\/apiV2\\/aupload\\/adsBanner\\/4.jpg," +
                "https:\\/\\/helloindianstar.co\\/apiV2\\/aupload\\/adsBanner\\/5.jpg," +
                "https:\\/\\/helloindianstar.co\\/apiV2\\/aupload\\/adsBanner\\/6.jpg\"," +
                "\"redirectLink\":\"https:\\/\\/play395.atmequiz.com\"}," +
                "\"nativeads\":{\"button_titel\":\"Play Now\"," +
                "\"disc\":\"Play&Win Coin\"," +
                "\"image\":\"https:\\/\\/helloindianstar.co\\/apiV2\\/aupload\\/adsBanner\\/1.png\"," +
                "\"image2\":\"https:\\/\\/helloindianstar.co\\/apiV2\\/aupload\\/adsBanner\\/1.gif\"," +
                "\"redirectLink\":\"https:\\/\\/play395.atmequiz.com\"," +
                "\"short_disc\":\"win 5," +
                "00," +
                "000 Coin..\"}," +
                "\"appopen\":{\"button_titel\":\"Play Now\"," +
                "\"disc\":\"Prize Quize:5," +
                "00," +
                "000\"," +
                "\"image\":\"https:\\/\\/helloindianstar.co\\/apiV2\\/aupload\\/adsBanner\\/1.png\"," +
                "\"short_disc\":\"No Install Required\"," +
                "\"redirectLink\":\"https:\\/\\/play395.atmequiz.com\"," +
                "\"title\":\"win 5," +
                "00," +
                "000 Coin..\"}," +
                "\"splashScreen\":\"0\"," +
                "\"splashInterFb\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"splashInterGoogle\":\"\\/6499\\/example\\/interstitial\"," +
                "\"splashOpenGoogle\":\"\\/6499\\/example\\/app-open\"," +
                "\"introInterType\":\"0\"," +
                "\"introInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"introIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"introTopNativeType\":\"0\"," +
                "\"introTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"introTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"introMiddleNativeType\":\"0\"," +
                "\"introMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"introMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"introBottomNativeType\":\"0\"," +
                "\"introBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"introBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"settingInterType\":\"0\"," +
                "\"settingInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"settingIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"settingTopNativeType\":\"0\"," +
                "\"settingTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"settingTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"settingMiddleNativeType\":\"0\"," +
                "\"settingMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"settingMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"settingBottomNativeType\":\"0\"," +
                "\"settingBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"settingBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"fbSaverInterType\":\"0\"," +
                "\"fbSaverInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"fbSaverIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"fbSaverTopNativeType\":\"0\"," +
                "\"fbSaverTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"fbSaverTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"fbSaverMiddleNativeType\":\"0\"," +
                "\"fbSaverMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"fbSaverMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"fbSaverBottomNativeType\":\"0\"," +
                "\"fbSaverBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"fbSaverBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"whatsappInterType\":\"0\"," +
                "\"whatsappInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"whatsappIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"whatsappTopNativeType\":\"0\"," +
                "\"whatsappTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"whatsappTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"whatsappMiddleNativeType\":\"0\"," +
                "\"whatsappMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"whatsappMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"whatsappBottomNativeType\":\"0\"," +
                "\"whatsappBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"whatsappBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"twitterInterType\":\"0\"," +
                "\"twitterInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"twitterIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"twitterTopNativeType\":\"0\"," +
                "\"twitterTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"twitterTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"twitterMiddleNativeType\":\"0\"," +
                "\"twitterMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"twitterMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"twitterBottomNativeType\":\"0\"," +
                "\"twitterBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"twitterBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"igSaverInterType\":\"0\"," +
                "\"igSaverInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"igSaverIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"igSaverTopNativeType\":\"0\"," +
                "\"igSaverTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"igSaverTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"igSaverMiddleNativeType\":\"0\"," +
                "\"igSaverMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"igSaverMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"igSaverBottomNativeType\":\"0\"," +
                "\"igSaverBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"igSaverBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"pintrestInterType\":\"0\"," +
                "\"pintrestInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"pintrestIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"pintrestTopNativeType\":\"0\"," +
                "\"pintrestTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"pintrestTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"pintrestMiddleNativeType\":\"0\"," +
                "\"pintrestMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"pintrestMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"pintrestBottomNativeType\":\"0\"," +
                "\"pintrestBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"pintrestBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"vimeInterType\":\"0\"," +
                "\"vimeInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"vimeIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"vimeTopNativeType\":\"0\"," +
                "\"vimeTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"vimeTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"vimeMiddleNativeType\":\"0\"," +
                "\"vimeMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"vimeMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"vimeBottomNativeType\":\"0\"," +
                "\"vimeBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"vimeBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"scrapUrl\":\"https:\\/\\/allsocialsaver.xyz\\/allsocialsave\\/scrpdta\"," +
                "\"googleInterType\":\"0\"," +
                "\"googleInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"googleIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"googleTopNativeType\":\"0\"," +
                "\"googleTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"googleTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"googleMiddleNativeType\":\"0\"," +
                "\"googleMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"googleMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"googleBottomNativeType\":\"0\"," +
                "\"googleBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"googleBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"fbVideoDownloaderInterType\":\"0\"," +
                "\"fbVideoDownloaderInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"fbVideoDownloaderIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"fbVideoDownloaderTopNativeType\":\"0\"," +
                "\"fbVideoDownloaderTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"fbVideoDownloaderTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"fbVideoDownloaderMiddleNativeType\":\"0\"," +
                "\"fbVideoDownloaderMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"fbVideoDownloaderMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"fbVideoDownloaderBottomNativeType\":\"0\"," +
                "\"fbVideoDownloaderBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"fbVideoDownloaderBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"instaVideoDownloaderInterType\":\"0\"," +
                "\"instaVideoDownloaderInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"instaVideoDownloaderIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"instaVideoDownloaderTopNativeType\":\"0\"," +
                "\"instaVideoDownloaderTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"instaVideoDownloaderTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"instaVideoDownloaderMiddleNativeType\":\"0\"," +
                "\"instaVideoDownloaderMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"instaVideoDownloaderMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"instaVideoDownloaderBottomNativeType\":\"0\"," +
                "\"instaVideoDownloaderBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"instaVideoDownloaderBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"hashTagInterType\":\"0\"," +
                "\"hashTagInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"hashTagIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"hashTagTopNativeType\":\"0\"," +
                "\"hashTagTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"hashTagTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"hashTagMiddleNativeType\":\"0\"," +
                "\"hashTagMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"hashTagMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"hashTagBottomNativeType\":\"0\"," +
                "\"hashTagBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"hashTagBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"captionInterType\":\"0\"," +
                "\"captionInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"captionIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"captionTopNativeType\":\"0\"," +
                "\"captionTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"captionTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"captionMiddleNativeType\":\"0\"," +
                "\"captionMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"captionMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"captionBottomNativeType\":\"0\"," +
                "\"captionBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"captionBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"bioInterType\":\"0\"," +
                "\"bioInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"bioIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"bioTopNativeType\":\"0\"," +
                "\"bioTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"bioTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"bioMiddleNativeType\":\"0\"," +
                "\"bioMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"bioMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"bioBottomNativeType\":\"0\"," +
                "\"bioBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"bioBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +

                "\"allVideoDownloadInterType\":\"0\"," +
                "\"allVideoDownloadInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"allVideoDownloadIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"allVideoDownloadTopNativeType\":\"1\"," +
                "\"allVideoDownloadTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"allVideoDownloadTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"allVideoDownloadMiddleNativeType\":\"1\"," +
                "\"allVideoDownloadMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"allVideoDownloadMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"allVideoDownloadBottomNativeType\":\"0\"," +
                "\"allVideoDownloadBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"allVideoDownloadBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +

                "\"languageInterType\":\"0\"" +
                ",\"languageInterGoogleId\":\"\\/6499\\/example\\/interstitial\"," +
                "\"languageIinterFBId\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"languageTopNativeType\":\"0\"," +
                "\"languageTopNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"languageTopNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"languageMiddleNativeType\":\"0\"," +
                "\"languageMiddleNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"languageMiddleNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"," +
                "\"languageBottomNativeType\":\"0\"," +
                "\"languageBottomNativeGoogleId\":\"\\/6499\\/example\\/native\"," +
                "\"languageBottomNativeFBID\":\"IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID\"}";

        try {
            JSONObject jsonObject = new JSONObject(adJson);

            ID_GOOGLE_BACK_INTER = jsonObject.has("id_google_back_inter") ? jsonObject.getString("id_google_back_inter") : "0";
            ID_FACEBOOK_BACK_INTER = jsonObject.has("id_facebook_back_inter") ? jsonObject.getString("id_facebook_back_inter") : "0";
            ID_GOOGLE_OPEN = jsonObject.has("id_google_open") ? jsonObject.getString("id_google_open") : "0";

            BACK_INTER_ADS_TYPE = jsonObject.has("back_inter_ads_type") ? jsonObject.getString("back_inter_ads_type") : "0";
            IS_OPEN_AD_ON = jsonObject.has("is_open_ad_on") ? jsonObject.getString("is_open_ad_on") : "0";
            BACKAPPOPEN_ON = jsonObject.has("backappopen") ? jsonObject.getString("backappopen") : "0";
            BACK_INTER_ON = jsonObject.has("back_inter_on") ? jsonObject.getString("back_inter_on") : "0";

            NATIVE_SIZE = jsonObject.has("native_size") ? jsonObject.getString("native_size") : "0";
            MID_NATIVE_SIZE = jsonObject.has("mid_native_size") ? jsonObject.getString("mid_native_size") : "0";
            BANNER_SIZE = jsonObject.has("banner_size") ? jsonObject.getString("banner_size") : "0";

            IS_INTER_DIALOG_SHOW = jsonObject.has("is_inter_dialog_show") ? jsonObject.getString("is_inter_dialog_show") : "0";
            NATIVE_COLOR = jsonObject.has("native_color") ? jsonObject.getString("native_color") : "0";
            NATIVE_BTN_COLOR = jsonObject.has("native_btn_color") ? jsonObject.getString("native_btn_color") : "0";
            IS_NATIVE_BLINK = jsonObject.has("is_native_blink") ? jsonObject.getString("is_native_blink") : "0";
            IS_SPLASH_OPEN_AD_ON = jsonObject.has("splash_open_ad") ? jsonObject.getString("splash_open_ad") : "0";
            BLINK_TIME = Integer.parseInt(jsonObject.has("blink_time") ? jsonObject.getString("blink_time") : "0");
            INTER_RANGE = Integer.parseInt(jsonObject.has("inter_range") ? jsonObject.getString("inter_range") : "0");
            BACK_INTER_RANGE = Integer.parseInt(jsonObject.has("back_inter_range") ? jsonObject.getString("back_inter_range") : "0");
            PAGE_RANGE = Integer.parseInt(jsonObject.has("page_range") ? jsonObject.getString("page_range") : "0");
            PRIVACY_POLICY = jsonObject.has("Privacypolicy") ? jsonObject.getString("Privacypolicy") : "0";
            VPN_DIALOG = jsonObject.has("vpnD") ? jsonObject.getString("vpnD") : "1";

            /*url*/
            scrapUrl = jsonObject.has("scrapUrl") ? jsonObject.getString("scrapUrl") : "0";


            introId = new AdsId(0,0,0,0,"","","","","","","","","","");
            settingId = new AdsId(0,0,0,0,"","","","","","","","","","");
            fbSaverId = new AdsId(0,0,0,0,"","","","","","","","","","");
            whatsappId = new AdsId(0,0,0,0,"","","","","","","","","","");
            twitterId = new AdsId(0,0,0,0,"","","","","","","","","","");
            igSaverId = new AdsId(0,0,0,0,"","","","","","","","","","");
            pintrestId = new AdsId(0,0,0,0,"","","","","","","","","","");
            vimeId = new AdsId(0,0,0,0,"","","","","","","","","","");
            googleId = new AdsId(0,0,0,0,"","","","","","","","","","");
            fbVideoDownloaderId = new AdsId(0,0,0,0,"","","","","","","","","","");
            instaVideoDownloaderId = new AdsId(0,0,0,0,"","","","","","","","","","");
            hashTagId = new AdsId(0,0,0,0,"","","","","","","","","","");
            captionId = new AdsId(0,0,0,0,"","","","","","","","","","");
            bioId = new AdsId(0,0,0,0,"","","","","","","","","","");
            allVideoDownloadId = new AdsId(0,0,0,0,"","","","","","","","","","");
            languageId = new AdsId(0,0,0,0,"","","","","","","","","","");

//            introId = new AdsId(jsonObject.getInt("introInterType"), jsonObject.getInt("introTopNativeType"), jsonObject.getInt("introMiddleNativeType"), jsonObject.getInt("introBottomNativeType"), jsonObject.getString("introTopNativeGoogleId"), jsonObject.getString("introMiddleNativeGoogleId"), jsonObject.getString("introBottomNativeGoogleId"), jsonObject.getString("introInterGoogleId"), jsonObject.getString("introBottomNativeGoogleId"), jsonObject.getString("introTopNativeFBID"), jsonObject.getString("introMiddleNativeFBID"), jsonObject.getString("introBottomNativeFBID"), jsonObject.getString("introIinterFBId"), jsonObject.getString("introBottomNativeFBID"));
//            settingId = new AdsId(jsonObject.getInt("settingInterType"), jsonObject.getInt("settingTopNativeType"), jsonObject.getInt("settingMiddleNativeType"), jsonObject.getInt("settingBottomNativeType"), jsonObject.getString("settingTopNativeGoogleId"), jsonObject.getString("settingMiddleNativeGoogleId"), jsonObject.getString("settingBottomNativeGoogleId"), jsonObject.getString("settingInterGoogleId"), jsonObject.getString("settingBottomNativeGoogleId"), jsonObject.getString("settingTopNativeFBID"), jsonObject.getString("settingMiddleNativeFBID"), jsonObject.getString("settingBottomNativeFBID"), jsonObject.getString("settingIinterFBId"), jsonObject.getString("settingBottomNativeFBID"));
//            fbSaverId = new AdsId(jsonObject.getInt("fbSaverInterType"), jsonObject.getInt("fbSaverTopNativeType"), jsonObject.getInt("settingMiddleNativeType"), jsonObject.getInt("fbSaverBottomNativeType"), jsonObject.getString("fbSaverTopNativeGoogleId"), jsonObject.getString("fbSaverMiddleNativeGoogleId"), jsonObject.getString("fbSaverBottomNativeGoogleId"), jsonObject.getString("fbSaverInterGoogleId"), jsonObject.getString("fbSaverBottomNativeGoogleId"), jsonObject.getString("fbSaverTopNativeFBID"), jsonObject.getString("fbSaverMiddleNativeFBID"), jsonObject.getString("fbSaverBottomNativeFBID"), jsonObject.getString("fbSaverIinterFBId"), jsonObject.getString("fbSaverBottomNativeFBID"));
//            whatsappId = new AdsId(jsonObject.getInt("whatsappInterType"), jsonObject.getInt("whatsappTopNativeType"), jsonObject.getInt("whatsappMiddleNativeType"), jsonObject.getInt("whatsappBottomNativeType"), jsonObject.getString("whatsappTopNativeGoogleId"), jsonObject.getString("whatsappMiddleNativeGoogleId"), jsonObject.getString("whatsappBottomNativeGoogleId"), jsonObject.getString("whatsappInterGoogleId"), jsonObject.getString("whatsappBottomNativeGoogleId"), jsonObject.getString("whatsappTopNativeFBID"), jsonObject.getString("whatsappMiddleNativeFBID"), jsonObject.getString("whatsappBottomNativeFBID"), jsonObject.getString("whatsappIinterFBId"), jsonObject.getString("whatsappBottomNativeFBID"));
//            twitterId = new AdsId(jsonObject.getInt("twitterInterType"), jsonObject.getInt("twitterTopNativeType"), jsonObject.getInt("twitterMiddleNativeType"), jsonObject.getInt("twitterBottomNativeType"), jsonObject.getString("twitterTopNativeGoogleId"), jsonObject.getString("twitterMiddleNativeGoogleId"), jsonObject.getString("twitterBottomNativeGoogleId"), jsonObject.getString("twitterInterGoogleId"), jsonObject.getString("twitterBottomNativeGoogleId"), jsonObject.getString("twitterTopNativeFBID"), jsonObject.getString("twitterMiddleNativeFBID"), jsonObject.getString("twitterBottomNativeFBID"), jsonObject.getString("twitterIinterFBId"), jsonObject.getString("twitterBottomNativeFBID"));
//            igSaverId = new AdsId(jsonObject.getInt("igSaverInterType"), jsonObject.getInt("igSaverTopNativeType"), jsonObject.getInt("igSaverMiddleNativeType"), jsonObject.getInt("igSaverBottomNativeType"), jsonObject.getString("igSaverTopNativeGoogleId"), jsonObject.getString("igSaverMiddleNativeGoogleId"), jsonObject.getString("igSaverBottomNativeGoogleId"), jsonObject.getString("igSaverInterGoogleId"), jsonObject.getString("igSaverBottomNativeGoogleId"), jsonObject.getString("igSaverTopNativeFBID"), jsonObject.getString("igSaverMiddleNativeFBID"), jsonObject.getString("igSaverBottomNativeFBID"), jsonObject.getString("igSaverIinterFBId"), jsonObject.getString("igSaverBottomNativeFBID"));
//            pintrestId = new AdsId(jsonObject.getInt("pintrestInterType"), jsonObject.getInt("pintrestTopNativeType"), jsonObject.getInt("pintrestMiddleNativeType"), jsonObject.getInt("pintrestBottomNativeType"), jsonObject.getString("pintrestTopNativeGoogleId"), jsonObject.getString("pintrestMiddleNativeGoogleId"), jsonObject.getString("pintrestBottomNativeGoogleId"), jsonObject.getString("pintrestInterGoogleId"), jsonObject.getString("pintrestBottomNativeGoogleId"), jsonObject.getString("pintrestTopNativeFBID"), jsonObject.getString("pintrestMiddleNativeFBID"), jsonObject.getString("pintrestBottomNativeFBID"), jsonObject.getString("pintrestIinterFBId"), jsonObject.getString("pintrestBottomNativeFBID"));
//            vimeId = new AdsId(jsonObject.getInt("vimeInterType"), jsonObject.getInt("vimeTopNativeType"), jsonObject.getInt("vimeMiddleNativeType"), jsonObject.getInt("vimeBottomNativeType"), jsonObject.getString("vimeTopNativeGoogleId"), jsonObject.getString("vimeMiddleNativeGoogleId"), jsonObject.getString("vimeBottomNativeGoogleId"), jsonObject.getString("vimeInterGoogleId"), jsonObject.getString("vimeBottomNativeGoogleId"), jsonObject.getString("vimeTopNativeFBID"), jsonObject.getString("vimeMiddleNativeFBID"), jsonObject.getString("vimeBottomNativeFBID"), jsonObject.getString("vimeIinterFBId"), jsonObject.getString("vimeBottomNativeFBID"));
//            googleId = new AdsId(jsonObject.getInt("googleInterType"), jsonObject.getInt("googleTopNativeType"), jsonObject.getInt("googleMiddleNativeType"), jsonObject.getInt("googleBottomNativeType"), jsonObject.getString("googleTopNativeGoogleId"), jsonObject.getString("googleMiddleNativeGoogleId"), jsonObject.getString("googleBottomNativeGoogleId"), jsonObject.getString("googleInterGoogleId"), jsonObject.getString("googleBottomNativeGoogleId"), jsonObject.getString("googleTopNativeFBID"), jsonObject.getString("googleMiddleNativeFBID"), jsonObject.getString("googleBottomNativeFBID"), jsonObject.getString("googleIinterFBId"), jsonObject.getString("googleBottomNativeFBID"));
//            fbVideoDownloaderId = new AdsId(jsonObject.getInt("fbVideoDownloaderInterType"), jsonObject.getInt("fbVideoDownloaderTopNativeType"), jsonObject.getInt("fbVideoDownloaderMiddleNativeType"), jsonObject.getInt("fbVideoDownloaderBottomNativeType"), jsonObject.getString("fbVideoDownloaderTopNativeGoogleId"), jsonObject.getString("fbVideoDownloaderMiddleNativeGoogleId"), jsonObject.getString("fbVideoDownloaderBottomNativeGoogleId"), jsonObject.getString("fbVideoDownloaderInterGoogleId"), jsonObject.getString("fbVideoDownloaderBottomNativeGoogleId"), jsonObject.getString("fbVideoDownloaderTopNativeFBID"), jsonObject.getString("fbVideoDownloaderMiddleNativeFBID"), jsonObject.getString("fbVideoDownloaderBottomNativeFBID"), jsonObject.getString("fbVideoDownloaderIinterFBId"), jsonObject.getString("fbVideoDownloaderBottomNativeFBID"));
//            instaVideoDownloaderId = new AdsId(jsonObject.getInt("instaVideoDownloaderInterType"), jsonObject.getInt("instaVideoDownloaderTopNativeType"), jsonObject.getInt("instaVideoDownloaderMiddleNativeType"), jsonObject.getInt("instaVideoDownloaderBottomNativeType"), jsonObject.getString("instaVideoDownloaderTopNativeGoogleId"), jsonObject.getString("instaVideoDownloaderMiddleNativeGoogleId"), jsonObject.getString("instaVideoDownloaderBottomNativeGoogleId"), jsonObject.getString("instaVideoDownloaderInterGoogleId"), jsonObject.getString("instaVideoDownloaderBottomNativeGoogleId"), jsonObject.getString("instaVideoDownloaderTopNativeFBID"), jsonObject.getString("instaVideoDownloaderMiddleNativeFBID"), jsonObject.getString("instaVideoDownloaderBottomNativeFBID"), jsonObject.getString("instaVideoDownloaderIinterFBId"), jsonObject.getString("instaVideoDownloaderBottomNativeFBID"));
//            hashTagId = new AdsId(jsonObject.getInt("hashTagInterType"), jsonObject.getInt("hashTagTopNativeType"), jsonObject.getInt("hashTagMiddleNativeType"), jsonObject.getInt("hashTagBottomNativeType"), jsonObject.getString("hashTagTopNativeGoogleId"), jsonObject.getString("hashTagMiddleNativeGoogleId"), jsonObject.getString("hashTagBottomNativeGoogleId"), jsonObject.getString("hashTagInterGoogleId"), jsonObject.getString("hashTagBottomNativeGoogleId"), jsonObject.getString("hashTagTopNativeFBID"), jsonObject.getString("hashTagMiddleNativeFBID"), jsonObject.getString("hashTagBottomNativeFBID"), jsonObject.getString("hashTagIinterFBId"), jsonObject.getString("hashTagBottomNativeFBID"));
//            captionId = new AdsId(jsonObject.getInt("captionInterType"), jsonObject.getInt("captionTopNativeType"), jsonObject.getInt("captionMiddleNativeType"), jsonObject.getInt("captionBottomNativeType"), jsonObject.getString("captionTopNativeGoogleId"), jsonObject.getString("captionMiddleNativeGoogleId"), jsonObject.getString("captionBottomNativeGoogleId"), jsonObject.getString("captionInterGoogleId"), jsonObject.getString("captionBottomNativeGoogleId"), jsonObject.getString("captionTopNativeFBID"), jsonObject.getString("captionMiddleNativeFBID"), jsonObject.getString("captionBottomNativeFBID"), jsonObject.getString("captionIinterFBId"), jsonObject.getString("captionBottomNativeFBID"));
//            bioId = new AdsId(jsonObject.getInt("bioInterType"), jsonObject.getInt("bioTopNativeType"), jsonObject.getInt("bioMiddleNativeType"), jsonObject.getInt("bioBottomNativeType"), jsonObject.getString("bioTopNativeGoogleId"), jsonObject.getString("bioMiddleNativeGoogleId"), jsonObject.getString("bioBottomNativeGoogleId"), jsonObject.getString("bioInterGoogleId"), jsonObject.getString("bioBottomNativeGoogleId"), jsonObject.getString("bioTopNativeFBID"), jsonObject.getString("bioMiddleNativeFBID"), jsonObject.getString("bioBottomNativeFBID"), jsonObject.getString("bioIinterFBId"), jsonObject.getString("bioBottomNativeFBID"));
//            allVideoDownloadId = new AdsId(jsonObject.getInt("allVideoDownloadInterType"), jsonObject.getInt("allVideoDownloadTopNativeType"), jsonObject.getInt("allVideoDownloadMiddleNativeType"), jsonObject.getInt("allVideoDownloadBottomNativeType"), jsonObject.getString("allVideoDownloadTopNativeGoogleId"), jsonObject.getString("allVideoDownloadMiddleNativeGoogleId"), jsonObject.getString("allVideoDownloadBottomNativeGoogleId"), jsonObject.getString("allVideoDownloadInterGoogleId"), jsonObject.getString("allVideoDownloadBottomNativeGoogleId"), jsonObject.getString("allVideoDownloadTopNativeFBID"), jsonObject.getString("allVideoDownloadMiddleNativeFBID"), jsonObject.getString("allVideoDownloadBottomNativeFBID"), jsonObject.getString("allVideoDownloadIinterFBId"), jsonObject.getString("allVideoDownloadBottomNativeFBID"));
//            languageId = new AdsId(jsonObject.getInt("languageInterType"), jsonObject.getInt("languageTopNativeType"), jsonObject.getInt("languageMiddleNativeType"), jsonObject.getInt("languageBottomNativeType"), jsonObject.getString("languageTopNativeGoogleId"), jsonObject.getString("languageMiddleNativeGoogleId"), jsonObject.getString("languageBottomNativeGoogleId"), jsonObject.getString("languageInterGoogleId"), jsonObject.getString("languageBottomNativeGoogleId"), jsonObject.getString("languageTopNativeFBID"), jsonObject.getString("languageMiddleNativeFBID"), jsonObject.getString("languageBottomNativeFBID"), jsonObject.getString("languageIinterFBId"), jsonObject.getString("languageBottomNativeFBID"));

            /*special vars*/
            HOME_CALLER_VIEW = "1";//jsonObject.has("extrabutton") ? jsonObject.getString("extrabutton") : "0";

            Log.e("SNEH", "AdHelper: init ads ");
            TEMP_CHECK_VAR = "initIds";
            return null;
        } catch (JSONException e) {
            Log.e("JSONException", "AdHelper: " + e.getMessage());
            return null;
        }

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        onShowAdCompleteListener.onShowAdComplete();
    }


    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

}
