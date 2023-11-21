package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebStorage;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import com.github.angads25.toggle.widget.LabeledSwitch;

import java.io.File;
import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;
import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.ConstantForApp;
import videodownloader.privatebrowser.free.hd.statussaver.tool.Delegate;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

public class ScreenAppSettings extends AppCompatActivity implements View.OnClickListener {
    public LabeledSwitch SwitchAdBlocker;
    private SharedPreferences prefs;
    public RelativeLayout realtiveAppFeedBack;
    public RelativeLayout realtiveFeedBack;
    public RelativeLayout realtiveHowUse;
    public RelativeLayout realtivePrivacyPolicy;
    public RelativeLayout realtiveShareApp;
    public RelativeLayout realtiveclearData;
    public RelativeLayout relativeChangeLanguage;
    public boolean isABChanged = false;
    public final boolean isUaChanged = false;
    public final boolean isSeChanged = false;
    public final int isNotification = 0;

    private void FetchDataId() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.settings));
        this.SwitchAdBlocker = (LabeledSwitch) findViewById(R.id.SwitchAdBlocker);
        this.realtiveclearData = (RelativeLayout) findViewById(R.id.realtiveclearData);
        this.realtiveHowUse = (RelativeLayout) findViewById(R.id.realtiveHowUse);
        this.realtiveAppFeedBack = (RelativeLayout) findViewById(R.id.realtiveAppFeedBack);
        this.realtiveFeedBack = (RelativeLayout) findViewById(R.id.realtiveFeedBack);
        this.realtiveShareApp = (RelativeLayout) findViewById(R.id.realtiveShareApp);
        this.realtivePrivacyPolicy = (RelativeLayout) findViewById(R.id.realtivePrivacyPolicy);
        this.relativeChangeLanguage = (RelativeLayout) findViewById(R.id.relativeChangeLanguage);
        this.SwitchAdBlocker.setOn(this.isNotification == 1);
    }

    private void ShareAppMsg() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name));
        intent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.msgforappsharing) + getString(R.string.app_download_link));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.share_via)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearCache() {
        try {
            CookieManager.getInstance().removeAllCookies(null);
            WebStorage.getInstance().deleteAllData();
            clearApplicationData(getCacheDir());
            clearApplicationData(getExternalCacheDir());
            UtilsForApp.showToastMsg(getString(R.string.cache_cleared_successfully), this, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir2(File dir) {
        String[] list;
        if (dir != null && dir.isDirectory() && (list = dir.list()) != null) {
            for (String str : list) {
                if (!deleteDir2(new File(dir, str))) {
                    return false;
                }
            }
        }
        if (dir != null) {
            return dir.delete();
        }
        return false;
    }


    private void opePrivayWebView() {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(getString(R.string.privay_url)));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rateAppMsg() {
        try {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                startActivity(intent);
            } catch (Exception unused) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearApplicationData(File x) {
        String[] list;
        if (x == null || x.getParent() == null) {
            return;
        }
        File file = new File(x.getParent());
        if (!file.exists() || (list = file.list()) == null) {
            return;
        }
        for (String str : list) {
            if (!str.equals("lib") && !str.equals("files")) {
                deleteDir2(new File(file, str));
            }
        }
    }

    @Override
    public void onBackPressed() {


        AllInOneAds.getInstance().showBackInter(this, () -> {
            Intent intent = new Intent();
            intent.putExtra("is_ab_changed", this.isABChanged);
            intent.putExtra("is_ua_changed", this.isUaChanged);
            intent.putExtra("is_se_changed", this.isSeChanged);
            setResult(-1, intent);

            finish();
        });


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.realtiveclearData) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.clear_cache_data));
            builder.setMessage(getString(R.string.would_you_like_to_clear_application_cache));
            builder.setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
                dialogInterface.dismiss();
                clearCache();
            });
            builder.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            final AlertDialog create = builder.create();
            create.setOnShowListener(dialogInterface -> {
                create.getButton(-1).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                create.getButton(-2).setTextColor(ViewCompat.MEASURED_STATE_MASK);
            });
            create.show();
        } else if (view.getId() == R.id.realtiveHowUse) {
            startActivity(new Intent(this, ScreenHelpForDownload.class));
        } else if (view.getId() == R.id.realtiveAppFeedBack) {
            rateAppMsg();
        } else if (view.getId() == R.id.realtiveFeedBack) {
        } else if (view.getId() == R.id.realtiveShareApp) {
            ShareAppMsg();
        } else if (view.getId() == R.id.realtivePrivacyPolicy) {
            opePrivayWebView();
        } else if (view.getId() == R.id.relativeChangeLanguage) {
            AllInOneAds.getInstance().showInterWithId(this, AdHelper.languageId, () -> {
                Intent intent = new Intent(this, ChooseLanguageActivity.class);
                intent.putExtra("IS_FROM_SETTING", true);
                startActivity(intent);
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_settings);


        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.settingId, findViewById(R.id.banner_container));

        Delegate.appSettings = this;
        FetchDataId();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance());
        this.prefs = defaultSharedPreferences;
        this.SwitchAdBlocker.setOn(defaultSharedPreferences.getBoolean(ConstantForApp.ADVERT_BLOCK_APP, true));
        this.realtiveclearData.setOnClickListener(this);
        this.realtiveHowUse.setOnClickListener(this);
        this.realtiveAppFeedBack.setOnClickListener(this);
        this.realtiveFeedBack.setOnClickListener(this);
        this.realtiveShareApp.setOnClickListener(this);
        this.realtivePrivacyPolicy.setOnClickListener(this);
        this.relativeChangeLanguage.setOnClickListener(this);
        this.SwitchAdBlocker.setOnToggledListener((toggleableView, isOn) -> {
            try {
                ScreenAppSettings screenAppSettings = ScreenAppSettings.this;
                screenAppSettings.isABChanged = true;
                screenAppSettings.prefs.edit().putBoolean(ConstantForApp.ADVERT_BLOCK_APP, isOn).apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroy() {
        Delegate.appSettings = null;
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
