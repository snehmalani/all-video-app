package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.Locale;

import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;
import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdsPrefrences;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.FacebookAds;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.GoogleAds;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.MyApplication;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.NetworkStateUtility;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi.AdsApiClient;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi.AdsApiInterface;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi.IPAdsApiClient;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi.IPAdsApiInterface;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi.StackAppIPDownloadApiClient;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi.StackAppIPDownloadApiInterface;
import videodownloader.privatebrowser.free.hd.statussaver.tool.ConstantForApp;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private CardView cardNoInternet;

    private RelativeLayout mainrelative;
    private SharedPreferences prefs;

    private TextView txtOk;
    private String urlRec = "";
    private String extraData = "";
    private boolean IsUserFirst = true;


    public static Dialog customDialog;
    String TAG = "SplashScreenActivity";
    boolean vpnInUse = false;
    WifiManager wifi;

    private void CehckNet() {
        if (UtilsForApp.isNetworkAvailable(this)) {
            return;
        }
        this.cardNoInternet.setVisibility(View.VISIBLE);
    }

    private void doLoad() {
        handleIntentData();
        CehckNet();
    }

    private void fetchIds() {
        this.mainrelative = (RelativeLayout) findViewById(R.id.mainrelative);


        this.cardNoInternet = (CardView) findViewById(R.id.cardNoInternet);
        this.txtOk = (TextView) findViewById(R.id.txtOk);
    }

    private void handleIntentData() {
        String string;
        try {
            String str = "";
            Bundle extras = getIntent().getExtras();
            if (extras != null && (string = extras.getString("android.intent.extra.TEXT")) != null && !string.isEmpty()) {
                if (UtilsForApp.IsValidUrl(string.trim())) {
                    str = string.trim();
                } else {
                    this.extraData = string.trim();
                }
            }
            if (str.isEmpty()) {
                Uri data = getIntent().getData();
                if (data != null) {
                    String trim = data.toString().trim();
                    if (trim.isEmpty() || !UtilsForApp.IsValidUrl(trim)) {
                        return;
                    }
                    this.urlRec = trim;
                    return;
                }
                return;
            }
            this.urlRec = str;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void loadDataFromSplash(store_model_splash modelSplash) {
//
//        int i = this.prefs.getInt(ConstantForApp.SCRP_VERS, 1);
//        int intValue = modelSplash.getScrpVer();
//        if (intValue > i) {
//            this.prefs.edit().putInt(ConstantForApp.SCRP_FORCE, 1).apply();
//        } else {
//            this.prefs.edit().putInt(ConstantForApp.SCRP_FORCE, 0).apply();
//        }
//        this.prefs.edit().putInt(ConstantForApp.SCRP_VERS, intValue).apply();
//
//        this.prefs.edit().putString(ConstantForApp.SCRP_URLS, modelSplash.getScrpUrl()).apply();
//
//        Log.e("aaaaaaaaa", "loadDataFromSplash: "+ modelSplash.getScrpUrl());
//
//
//        openFinalClass();
//    }

    @SuppressLint("WrongConstant")
    public void openFinalClass() {
        boolean z = this.prefs.getBoolean(ConstantForApp.PREF_USER_FIRST, true);

        if (z) {
            Intent intent = new Intent(this, IntroActivity.class);
            intent.putExtra("received_url", this.urlRec);
            intent.putExtra("received_data", this.extraData);
            intent.putExtra("Is_user_first", this.IsUserFirst);
            startActivity(intent);
        } else if (this.IsUserFirst) {
            Intent intent3 = new Intent(this, ChooseLanguageActivity.class);
            intent3.putExtra("IS_FROM_SETTING", false);
            startActivity(intent3);
        } else {
            Intent intent4 = new Intent(this, MainActivityVideos.class);
            intent4.putExtra("received_url", this.urlRec);
            intent4.putExtra("received_data", this.extraData);
            if (!this.urlRec.isEmpty() || !this.extraData.isEmpty()) {
                intent4.setFlags(335544320);
            }
            startActivity(intent4);
        }

        finish();
    }



    @Override
    public void onBackPressed() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Locale locale;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_activity);

        OnesignalANdFirebaseInitialize();

        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance());

        this.prefs = defaultSharedPreferences;
        String string = defaultSharedPreferences.getString(ConstantForApp.PREF_LNG_CODE, "");
        if (!string.isEmpty()) {
            if (string.equals("zh")) {
                locale = Locale.SIMPLIFIED_CHINESE;
            } else if (string.equals("zh-rTW")) {
                locale = Locale.TRADITIONAL_CHINESE;
            } else {
                locale = new Locale(string);
            }
            Resources resources = getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.setLayoutDirection(locale);
            configuration.locale = locale;
            resources.updateConfiguration(configuration, displayMetrics);
        }
        this.IsUserFirst = this.prefs.getBoolean(ConstantForApp.PREF_USER_FIRST, true);
        boolean isFirstTimeApp = this.prefs.getBoolean(ConstantForApp.FIRST_INIT_APP, true);
        fetchIds();
        this.txtOk.setOnClickListener(view -> {
            cardNoInternet.setVisibility(View.GONE);
            CehckNet();
        });
//        if (isFirstTimeApp) {
//            TextView textView = (TextView) findViewById(R.id.txt_policy);
//            SpannableString spannableString = new SpannableString("By tapping Continue, agree to our Terms of use and \nconfirm You have read our Privacy policy.");
//            spannableString.setSpan(new ClickableSpan() {
//
//                @Override
//                public void onClick(View textView2) {
//                    try {
//                        Intent intent = new Intent("android.intent.action.VIEW");
//                        intent.setData(Uri.parse(SplashScreenActivity.this.getString(R.string.privay_url)));
//                        SplashScreenActivity.this.startActivity(intent);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void updateDrawState(TextPaint ds) {
//                    super.updateDrawState(ds);
//                    ds.setUnderlineText(true);
//                    ds.setColor(Color.parseColor("#ababab"));
//                }
//            }, 77, 93, 33);
//            textView.setText(spannableString);
//            textView.setMovementMethod(LinkMovementMethod.getInstance());
//            textView.setHighlightColor(-1);
//            this.mainrelative.setVisibility(View.GONE);
//            this.firstrelative.setVisibility(View.VISIBLE);
//            this.privacyOK.setOnClickListener(view -> {
//                prefs.edit().putBoolean(ConstantForApp.FIRST_INIT_APP, false).apply();
//                mainrelative.setVisibility(View.VISIBLE);
//                firstrelative.setVisibility(View.GONE);
//                doLoad();
//            });
//            return;
//        }
        this.mainrelative.setVisibility(View.VISIBLE);

        doLoad();


        wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (NetworkStateUtility.isOnline(this)) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            Network activeNetwork;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activeNetwork = cm.getActiveNetwork();
                NetworkCapabilities caps = cm.getNetworkCapabilities(activeNetwork);
                vpnInUse = caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
            }

            try {
                String INSTALL_PREF = "install_pref_vd";
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if (!sharedPreferences.getBoolean(INSTALL_PREF, false)) {
//                    updatecounter();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(INSTALL_PREF, true);
                    editor.apply();
                }
            } catch (Exception e) {
            }
//            getIPADDRESS();
//            getId("117.99.109.55");
            new AdHelper(SplashScreenActivity.this, "", () -> openNext()).execute();
//            new Handler().postDelayed(this::openNext,2500);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Network Error...").setMessage("Internet is not available, reconnect network and try again.").setNegativeButton("OK", (dialog, id) -> {
                dialog.cancel();
                finishAffinity();
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void OnesignalANdFirebaseInitialize() {
        OneSignal.initWithContext(this);
        OneSignal.setAppId("b23e2b22-7234-49ad-9297-bd792ebb8d86");
        OneSignal.promptForPushNotifications();
        FirebaseApp.initializeApp(this);
    }
    public void getIPADDRESS() {
        IPAdsApiInterface apiService = IPAdsApiClient.getClient().create(IPAdsApiInterface.class);
        Call call = apiService.getip("oHGtOxqe8gqlgwj");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                int statusCode = response.code();
                if (response.isSuccessful()) {
                    try {
                        if (statusCode == 200) {
                            String data = new Gson().toJson(response.body());
                            JSONObject jso = new JSONObject(data);
                            String query = jso.getString("query");
                            getId(query);
                        }
                    } catch (Exception e) {
//                        Toast.makeText(SplashScreenActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                        getId("117.99.109.55");
                    }
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

//                Toast.makeText(SplashScreenActivity.this, "" + t, Toast.LENGTH_SHORT).show();
                getId("117.99.109.55");
            }
        });
    }

    public void getId(String s) {
        AdsApiInterface apiService = AdsApiClient.getClient().create(AdsApiInterface.class);
        Call call = apiService.getid(s);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                int statusCode = response.code();
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: 1");
                    /**decode json*/
                    String data = new Gson().toJson(response.body());
                    AdsPrefrences.getInstance(SplashScreenActivity.this).saveString(AdsPrefrences.TAG_ADS_JSON, data);
                    Log.e(TAG, "onResponse: " + data);
                    new AdHelper(SplashScreenActivity.this, data, () -> next()).execute();
                } else {
                    Log.e(TAG, "onResponse: 2");

                    new AdHelper(SplashScreenActivity.this, null, () -> next()).execute();
                }
                Log.e(TAG, "onResponse: 3");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(SplashScreenActivity.this, "" + t, Toast.LENGTH_SHORT).show();

                Log.e("apiapiapi", "" + t);
                next();
            }
        });
    }

    public void next() {
        if (AdHelper.VPN_DIALOG.matches("1")) {
            if (vpnInUse) {
                if (customDialog != null) {
                    customDialog.dismiss();
                    customDialog = null;
                }
                customDialog = new Dialog(SplashScreenActivity.this);
                customDialog.requestWindowFeature(1);
                customDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.vpn_doalog);
                customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                customDialog.setCanceledOnTouchOutside(false);
                customDialog.setCancelable(false);
                Window window = customDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                LinearLayout llOKay = customDialog.findViewById(R.id.llOKay);
                llOKay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                        finishAffinity();

                    }
                });
                if (!customDialog.isShowing() && !isFinishing()) {
                    customDialog.show();
                }
            } else {
                OpenAppNext();
            }
        } else {
            OpenAppNext();
        }
    }

    public void OpenAppNext() {
        MyApplication.initMobileAds();

//        AllInOneAds.getInstance().loadPreloadNative(this,AdHelper.splashId);

        AppOpenAd.AppOpenAdLoadCallback onShowAdCompleteListener;
        if (AdHelper.splashScreen == 0) {
            FacebookAds.getInstance().showInterWithId(this, AdHelper.splashInterFb, () -> {
                openNext();
            });
        } else if (AdHelper.splashScreen == 1) {
            onShowAdCompleteListener = new AppOpenAd.AppOpenAdLoadCallback() {
                public void onAdLoaded(AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    appOpenAd.show(SplashScreenActivity.this);
                    appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            openNext();
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            openNext();
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    openNext();
                }
            };
            try {
                AppOpenAd.load(SplashScreenActivity.this, AdHelper.splashOpenGoogle, new AdRequest.Builder().build(), 1, onShowAdCompleteListener);
            } catch (Exception e) {
            }
        } else if (AdHelper.splashScreen == 2) {
            GoogleAds.getInstance().loadAndShowInter(this, AdHelper.splashInterGoogle, new GoogleAds.GoogleAdsInterClick() {
                @Override
                public void onInterDismiss() {
                    openNext();
                }

                @Override
                public void onInterFailedToLoad() {
                    openNext();
                }
            });

        } else if (AdHelper.splashScreen == 3) {
            AppOpen_Custom();
        } else {
            new Handler().postDelayed(() -> openNext(), 2500);
        }
    }

    public void AppOpen_Custom() {
        Dialog dialog = new Dialog(this, 16974120);
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
        Glide.with(this).load(AdHelper.image_open).into(imageView);
        TextView textView = dialog.findViewById(R.id.titel);
        textView.setText("" + AdHelper.title);
        LinearLayout btnQureka = dialog.findViewById(R.id.btnQureka);
        btnQureka.setOnClickListener(view -> {
            openNext();
            try {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor("#FFFFFF")).setShowTitle(true);
                CustomTabsIntent build = builder.build();
                build.intent.setPackage("com.android.chrome");
                build.launchUrl(SplashScreenActivity.this, Uri.parse(AdHelper.redirectLink_open));

            } catch (Exception e) {

            }
            finish();
            dialog.dismiss();
        });

        RelativeLayout btnSkip = dialog.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(view -> {
            openNext();
            dialog.dismiss();
        });

    }

    public void openNext() {
        openFinalClass();
    }

    public void updatecounter() {
        StackAppIPDownloadApiInterface client = StackAppIPDownloadApiClient.getClient().create(StackAppIPDownloadApiInterface.class);
        client.countDownload(MyApplication.getInstance().getPackageName(), 1).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
            }
        });

    }
}
