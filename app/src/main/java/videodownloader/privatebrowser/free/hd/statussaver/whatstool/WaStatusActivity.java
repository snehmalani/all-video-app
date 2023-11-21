package videodownloader.privatebrowser.free.hd.statussaver.whatstool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;
import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;
import videodownloader.privatebrowser.free.hd.statussaver.tool.ConstantForApp;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

public class WaStatusActivity extends AdLoaderBase {

    private ImageView imgWaWab;
    private WaFragment mImagesFragment;
    private WaFragment mVideosFragment;
    private String pathFinal;
    private RelativeLayout realtiveTab1;
    private RelativeLayout realtiveTab2;
    private RelativeLayout relativeProgress;
    private TextView txtTba1Text;
    private TextView txtTba2Text;
    private ViewPager viewPager;
    private int request_code = 0;
    private final ArrayList<WaModel> imgList = new ArrayList<>();
    private final ArrayList<WaModel> videoList = new ArrayList<>();

        public final class FileLoader extends AsyncTask<Void, Void, Boolean> {
        private FileLoader() {
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            try {
                ArrayList<WaModel> NewListFiles = UtilsForApp.NewListFiles(WaStatusActivity.this.pathFinal);
                if (NewListFiles.size() > 0) {
                    for (WaModel next : NewListFiles) {
                        if (next != null && next.getFileUri() != null && !next.getFileUri().isEmpty()) {
                            if (UtilsForApp.getMimeType(Uri.parse(next.getFileUri())).contains("video")) {
                                WaStatusActivity.this.videoList.add(new WaModel(next.getFileName(), next.getFileUri()));
                            } else {
                                WaStatusActivity.this.imgList.add(new WaModel(next.getFileName(), next.getFileUri()));
                            }
                        }
                    }
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            } catch (Exception e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }
        }

        @Override
        public void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            WaStatusActivity.this.setupPager();
        }
    }

        public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList;
        private final List<String> mFragmentTitleList;

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            this.mFragmentList = new ArrayList<>();
            this.mFragmentTitleList = new ArrayList<>();
        }

        public void addFragment(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {
            return this.mFragmentList.size();
        }

        @Override
        @NonNull
        public Fragment getItem(int position) {
            return this.mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return this.mFragmentTitleList.get(position);
        }
    }

    private void FetchXMLId() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        this.viewPager = (ViewPager) findViewById(R.id.viewPagerSub);
        this.txtTba1Text = (TextView) findViewById(R.id.txtTba1Text);
        this.txtTba2Text = (TextView) findViewById(R.id.txtTba2Text);
        this.realtiveTab1 = (RelativeLayout) findViewById(R.id.realtiveTab1);
        this.realtiveTab2 = (RelativeLayout) findViewById(R.id.realtiveTab2);
        this.imgWaWab = (ImageView) findViewById(R.id.imgWaWab);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeProgress);
        this.relativeProgress = relativeLayout;
        relativeLayout.setVisibility(View.VISIBLE);
    }

    public void setupPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        if (this.mImagesFragment == null) {
            this.mImagesFragment = new WaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("files_list", this.imgList);
            bundle.putString("type_", "image");
            bundle.putString("from_", "wa");
            this.mImagesFragment.setArguments(bundle);
        }
        if (this.mVideosFragment == null) {
            this.mVideosFragment = new WaFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("files_list", this.videoList);
            bundle2.putString("type_", "video");
            bundle2.putString("from_", "wa");
            this.mVideosFragment.setArguments(bundle2);
        }
        viewPagerAdapter.addFragment(this.mImagesFragment, "Images");
        viewPagerAdapter.addFragment(this.mVideosFragment, "Videos");
        this.viewPager.setAdapter(viewPagerAdapter);
        this.realtiveTab1.callOnClick();
        this.relativeProgress.setVisibility(View.GONE);
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_wa_wb);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.whatsappId, findViewById(R.id.banner_container));

        FetchXMLId();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.request_code = extras.getInt("request_code");
            this.imgWaWab.setOnClickListener(v -> {
                Intent launchIntentForPackage;
                Intent launchIntentForPackage2;
                if (WaStatusActivity.this.request_code == 260) {
                    launchIntentForPackage = WaStatusActivity.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                } else {
                    launchIntentForPackage = WaStatusActivity.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp.w4b");
                }
                try {
                    WaStatusActivity.this.startActivity(launchIntentForPackage);
                } catch (Exception unused) {
                    if (WaStatusActivity.this.request_code == 260) {
                        launchIntentForPackage2 = WaStatusActivity.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp.w4b");
                    } else {
                        launchIntentForPackage2 = WaStatusActivity.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                    }
                    try {
                        WaStatusActivity.this.startActivity(launchIntentForPackage2);
                    } catch (Exception unused2) {
                        UtilsForApp.showToastMsg(WaStatusActivity.this.getString(R.string.Whatsapp_is_not_installed), WaStatusActivity.this, false);
                    }
                }
            });
            int i = this.request_code;
            if (i == 260) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.wa_status));
                this.pathFinal = prefs.getString(ConstantForApp.PREF_WA_PATH, "");
            } else if (i == 497) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.wa_business_status));
                this.pathFinal = prefs.getString(ConstantForApp.PREF_WA_BUSINESS_PATH, "");
            }
            this.realtiveTab1.setOnClickListener(view -> {
                this.txtTba1Text.setTextColor(getResources().getColor(R.color.app_white));
                this.txtTba2Text.setTextColor(getResources().getColor(R.color.subTextColor));
                this.realtiveTab1.setBackgroundResource(R.drawable.drawable_tab_select);
                this.realtiveTab2.setBackgroundResource(R.drawable.drawable_un_select);
                this.viewPager.setCurrentItem(0, true);

            });
            this.realtiveTab2.setOnClickListener(view -> {
                this.txtTba1Text.setTextColor(getResources().getColor(R.color.subTextColor));
                this.txtTba2Text.setTextColor(getResources().getColor(R.color.app_white));
                this.realtiveTab1.setBackgroundResource(R.drawable.drawable_un_select);
                this.realtiveTab2.setBackgroundResource(R.drawable.drawable_tab_select);
                this.viewPager.setCurrentItem(1, true);

            });
            this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrollStateChanged(int arg0) {
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                       txtTba1Text.setTextColor(getResources().getColor(R.color.app_white));
                       txtTba2Text.setTextColor(getResources().getColor(R.color.subTextColor));
                       realtiveTab1.setBackgroundResource(R.drawable.drawable_tab_select);
                       realtiveTab2.setBackgroundResource(R.drawable.drawable_un_select);
                    } else if (position == 1) {
                       txtTba1Text.setTextColor(getResources().getColor(R.color.subTextColor));
                       txtTba2Text.setTextColor(getResources().getColor(R.color.app_white));
                       realtiveTab1.setBackgroundResource(R.drawable.drawable_un_select);
                       realtiveTab2.setBackgroundResource(R.drawable.drawable_tab_select);
                    }
                    viewPager.setCurrentItem(position, true);                }
            });
            new FileLoader().execute(new Void[0]);
            
            return;
        }
        finish();
    }

    @Override
    public void onDestroy() {
        
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

    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }
}
