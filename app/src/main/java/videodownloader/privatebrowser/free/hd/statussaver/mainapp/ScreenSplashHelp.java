package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import static videodownloader.privatebrowser.free.hd.statussaver.mainapp.IntroActivity.m;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import videodownloader.privatebrowser.free.hd.statussaver.R;

public class ScreenSplashHelp extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout SliderDots;
    private ImageView[] dots;
    private int dotscount;
    private ViewPagerAdapter mViewPagerAdapter;
    private Button txtOK;
    private TextView txtSkip;
    private ViewPager viewPager;
    private FrgForHelp mFragmentHelp1 = null;
    private FrgForHelp mFragmentHelp2 = null;
    private FrgForHelp mFragmentHelp3 = null;
    private FrgForHelp mFragmentHelp4 = null;
    private int vpPos = 0;
    private String received_url = "";
    private String received_data = "";
    private boolean Is_user_first = false;

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList;
        private final List<String> mFragmentTitleList;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
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

    private void AssignPageVericalDots() {
        int count = this.mViewPagerAdapter.getCount();
        this.dotscount = count;
        this.dots = new ImageView[count];
        int i = 0;
        while (true) {
            int i2 = this.dotscount;
            if (i < i2) {
                this.SliderDots.setWeightSum(i2);
                this.dots[i] = new ImageView(this);
                this.dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_main_img));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                layoutParams.setMargins(0, 0, 0, 0);
                layoutParams.weight = 1.0f;
                this.SliderDots.addView(this.dots[i], layoutParams);
                i++;
            } else {
                this.dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_small));
                return;
            }
        }
    }

    private void init() {
        this.SliderDots = (LinearLayout) findViewById(R.id.SliderDots);
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.txtOK = (Button) findViewById(R.id.txtOK);
        TextView textView = (TextView) findViewById(R.id.txtSkip);
        this.txtSkip = textView;
        textView.setVisibility(View.GONE);
    }

    private void setupViewPager() {
        this.mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (this.mFragmentHelp1 == null) {
            this.mFragmentHelp1 = new FrgForHelp();
            this.mFragmentHelp1.setArguments(m("int_data", 0));
        }
        if (this.mFragmentHelp2 == null) {
            this.mFragmentHelp2 = new FrgForHelp();
            this.mFragmentHelp2.setArguments(m("int_data", 1));
        }
        if (this.mFragmentHelp3 == null) {
            this.mFragmentHelp3 = new FrgForHelp();
            this.mFragmentHelp3.setArguments(m("int_data", 2));
        }
        if (this.mFragmentHelp4 == null) {
            this.mFragmentHelp4 = new FrgForHelp();
            this.mFragmentHelp4.setArguments(m("int_data", 3));
        }
        this.mViewPagerAdapter.addFragment(this.mFragmentHelp1, "0");
        this.mViewPagerAdapter.addFragment(this.mFragmentHelp2, "1");
        this.mViewPagerAdapter.addFragment(this.mFragmentHelp3, ExifInterface.GPS_MEASUREMENT_2D);
        this.mViewPagerAdapter.addFragment(this.mFragmentHelp4, ExifInterface.GPS_MEASUREMENT_3D);
        AssignPageVericalDots();
        this.viewPager.setCurrentItem(0);
        this.vpPos = 0;
        this.viewPager.setOffscreenPageLimit(1);
        this.viewPager.setAdapter(this.mViewPagerAdapter);
        this.mViewPagerAdapter.notifyDataSetChanged();
        this.viewPager.setAdapter(this.mViewPagerAdapter);
    }


    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        if (this.Is_user_first) {
            Intent intent = new Intent(this, ChooseLanguageActivity.class);
            intent.putExtra("IS_FROM_SETTING", false);
            startActivity(intent);
        } else {
            Intent intent2 = new Intent(this, MainActivityVideos.class);
            intent2.putExtra("received_url", this.received_url);
            intent2.putExtra("received_data", this.received_data);
            if (!this.received_url.isEmpty() || !this.received_data.isEmpty()) {
                intent2.setFlags(335544320);
            }
            startActivity(intent2);
        }
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txtSkip) {
            finish();
        } else if(v.getId() == R.id.txtOK) {
            int i = this.vpPos;
            if (i == 3) {
                onBackPressed();
            } else {
                this.viewPager.setCurrentItem(i + 1);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_how_to);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.received_url = extras.getString("received_url");
            this.received_data = extras.getString("received_data");
            this.Is_user_first = extras.getBoolean("Is_user_first", false);
        }
        init();
        setupViewPager();
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < ScreenSplashHelp.this.dotscount; i++) {
                    ScreenSplashHelp.this.dots[i].setImageDrawable(ContextCompat.getDrawable(ScreenSplashHelp.this.getApplicationContext(), R.drawable.non_active_main_img));
                }
                ScreenSplashHelp.this.dots[position].setImageDrawable(ContextCompat.getDrawable(ScreenSplashHelp.this.getApplicationContext(), R.drawable.active_small));
                ScreenSplashHelp.this.vpPos = position;
                if (position == 3) {
                    ScreenSplashHelp.this.txtOK.setText(ScreenSplashHelp.this.getResources().getString(R.string.got_it));
                } else {
                    ScreenSplashHelp.this.txtOK.setText(ScreenSplashHelp.this.getResources().getString(R.string.next));
                }
            }
        });
        this.txtSkip.setOnClickListener(this);
        this.txtOK.setOnClickListener(this);
    }
}
