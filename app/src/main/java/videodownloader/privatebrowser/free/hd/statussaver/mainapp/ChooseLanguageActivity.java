package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;
import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.ConstantForApp;

public class ChooseLanguageActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private String selectedCode = "en";
    private boolean isFromSetting = false;
    private final ArrayList<LanguageModel> modelsList = new ArrayList<>();


    public void changeLng() {
        Locale locale;
        this.preferences.edit().putString(ConstantForApp.PREF_LNG_CODE, this.selectedCode).apply();
        if (!this.selectedCode.isEmpty()) {
            if (this.selectedCode.equals("zh")) {
                locale = Locale.SIMPLIFIED_CHINESE;
            } else if (this.selectedCode.equals("zh-rTW")) {
                locale = Locale.TRADITIONAL_CHINESE;
            } else {
                locale = new Locale(this.selectedCode);
            }
            Resources resources = getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.setLayoutDirection(locale);
            configuration.locale = locale;
            resources.updateConfiguration(configuration, displayMetrics);
        }

        AllInOneAds.getInstance().showInterWithId(this, AdHelper.languageId, () -> {
            if (this.isFromSetting) {
                startActivity(new Intent(this, MainActivityVideos.class));
                finish();
                return;
            }
            startActivity(new Intent(this, MainActivityVideos.class));
            finish();
        });
    }

    private void inItIds() {
        RecyclerView rvChooseLanguage = (RecyclerView) findViewById(R.id.rvChooseLanguage);

        TextView tvNext = (TextView) findViewById(R.id.tvNext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rvChooseLanguage.setHasFixedSize(true);
        rvChooseLanguage.setLayoutManager(gridLayoutManager);
        SelectAppLanguageAdapter selectAppLanguageAdapter = new SelectAppLanguageAdapter(this, getLanguageList());
        rvChooseLanguage.setAdapter(selectAppLanguageAdapter);
        if (!this.preferences.getString(ConstantForApp.PREF_LNG_CODE, "").equals("")) {
            selectAppLanguageAdapter.update(this.preferences.getString(ConstantForApp.PREF_LNG_CODE, ""));
            this.selectedCode = this.preferences.getString(ConstantForApp.PREF_LNG_CODE, "");
        }
        selectAppLanguageAdapter.setOnItemClickListener((LanguageModelList, position, selectionCode) -> ChooseLanguageActivity.this.selectedCode = selectionCode);
        tvNext.setOnClickListener(v -> ChooseLanguageActivity.this.changeLng());
    }

    public ArrayList<LanguageModel> getLanguageList() {
        this.modelsList.add(new LanguageModel("English", "English", "en", true, R.drawable.english));
        this.modelsList.add(new LanguageModel("Chinese Simplified", "简体中文", "zh", false, R.drawable.chinese_simplified));
        this.modelsList.add(new LanguageModel("Chinese Traditional", "中國傳統的", "zh-rTW", false, R.drawable.chinese_terd));
        this.modelsList.add(new LanguageModel("Portuguese", "Português", "pt", false, R.drawable.portuguese));
        this.modelsList.add(new LanguageModel("French", "Français", "fr", false, R.drawable.french));
        this.modelsList.add(new LanguageModel("Spanish", "Española", "es", false, R.drawable.spanish));
        this.modelsList.add(new LanguageModel("Russian", "Русский", "ru", false, R.drawable.russian));
        this.modelsList.add(new LanguageModel("Indonesian", "bahasa Indonesia", "in", false, R.drawable.indonesia));
        this.modelsList.add(new LanguageModel("German", "Deutsch", "de", false, R.drawable.germany));
        this.modelsList.add(new LanguageModel("Hindi", "हिन्दी", "hi", false, R.drawable.hindi));
        this.modelsList.add(new LanguageModel("Korean", "한국인", "ko", false, R.drawable.korian));
        this.modelsList.add(new LanguageModel("Japanese", "日本", "ja", false, R.drawable.japanese));
        this.modelsList.add(new LanguageModel("Bengali", "বাংলা", "bn", false, R.drawable.bengali));
        this.modelsList.add(new LanguageModel("Urdu", "اردو", "ur", false, R.drawable.urdu));
        return this.modelsList;
    }

    @Override
    public void onBackPressed() {
        changeLng();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.languageId,findViewById(R.id.banner_container));

        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance());
        this.preferences = defaultSharedPreferences;
        defaultSharedPreferences.edit().putBoolean(ConstantForApp.PREF_USER_FIRST, false).apply();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.isFromSetting = extras.getBoolean("IS_FROM_SETTING", false);
        }
        inItIds();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
