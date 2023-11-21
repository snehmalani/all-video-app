package videodownloader.privatebrowser.free.hd.statussaver.whatstool;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;
import videodownloader.privatebrowser.free.hd.statussaver.tool.ConstantForApp;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

public class MyStatuesActivity extends AdLoaderBase {
    private ImageView imgWaWab;
    private WaFragment mImagesFragment;
    private WaFragment mVideosFragment;
    private RelativeLayout realtiveTab1;
    private RelativeLayout realtiveTab2;
    private RelativeLayout relativeProgress;
    private TextView txtTba1Text;
    private TextView txtTba2Text;
    private ViewPager viewPager;
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
            File[] listFiles;
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().toString());
            String str = File.separator;
            sb.append(str);
            sb.append("Download");
            sb.append(str);
            sb.append(ConstantForApp.VID_DOWN_PATH);
            sb.append("/");
            sb.append(ConstantForApp.MY_STATUS_PATH);
            File file = new File(sb.toString());
            if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
                Arrays.sort(listFiles, (Comparator) (obj, obj2) -> {

                    return (int) (((File) obj2).lastModified() - ((File) obj).lastModified());

                });
                for (File file2 : listFiles) {
                    if (UtilsForApp.getMimeType(FileProvider.getUriForFile(MyStatuesActivity.this, "videodownloader.privatebrowser.free.hd.statussaver.provider", new File(file2.getPath()))).contains("video")) {
                        MyStatuesActivity.this.videoList.add(new WaModel(file2.getName(), file2.getPath()));
                    } else {
                        MyStatuesActivity.this.imgList.add(new WaModel(file2.getName(), file2.getPath()));
                    }
                }
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }

        @Override
        public void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            MyStatuesActivity.this.setupPager();
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
        getSupportActionBar().setTitle(getString(R.string.saved_status));
        this.viewPager = (ViewPager) findViewById(R.id.viewPagerSub);
        this.imgWaWab = (ImageView) findViewById(R.id.imgWaWab);
        this.txtTba1Text = (TextView) findViewById(R.id.txtTba1Text);
        this.txtTba2Text = (TextView) findViewById(R.id.txtTba2Text);
        this.realtiveTab1 = (RelativeLayout) findViewById(R.id.realtiveTab1);
        this.realtiveTab2 = (RelativeLayout) findViewById(R.id.realtiveTab2);
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
            bundle.putString("from_", "my");
            this.mImagesFragment.setArguments(bundle);
        }
        if (this.mVideosFragment == null) {
            this.mVideosFragment = new WaFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("files_list", this.videoList);
            bundle2.putString("type_", "video");
            bundle2.putString("from_", "my");
            this.mVideosFragment.setArguments(bundle2);
        }
        viewPagerAdapter.addFragment(this.mImagesFragment, "Images");
        viewPagerAdapter.addFragment(this.mVideosFragment, "Videos");
        this.viewPager.setAdapter(viewPagerAdapter);
        this.realtiveTab1.callOnClick();
        this.relativeProgress.setVisibility(View.GONE);
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

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_wa_wb);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.whatsappId, findViewById(R.id.banner_container));

        FetchXMLId();
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
                viewPager.setCurrentItem(position, true);
            }
        });
        this.imgWaWab.setOnClickListener(v -> {
            try {
                MyStatuesActivity.this.startActivity(MyStatuesActivity.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp"));
            } catch (Exception unused) {
                try {
                    MyStatuesActivity.this.startActivity(MyStatuesActivity.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp.w4b"));
                } catch (Exception unused2) {
                    UtilsForApp.showToastMsg("Whatapp is not installed", MyStatuesActivity.this, false);
                }
            }
        });
        new FileLoader().execute(new Void[0]);

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
