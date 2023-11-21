package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;

public class IntroActivity extends AppCompatActivity {

    public LinearLayout SliderDots;

    public ImageView[] dots;
    public int dotscount;
    public ViewPager helpPager;
    public LinearLayout lenearlayout_next;
    public Button txtOK;
    public FrgForIntro mFragmentHelp1 = null;
    public FrgForIntro mFragmentHelp2 = null;
    public FrgForIntro mFragmentHelp3 = null;
    public final int[] vpPos = {0};

    private String received_url = "";
    private String received_data = "";
    private boolean Is_user_first = false;

    public void startMainClass() {
        onBackPressed();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showInterWithId(this, AdHelper.introId, () -> {
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
        });
    }

    public static Bundle m(String str, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(str, i);
        return bundle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.introId ,findViewById(R.id.banner_container));


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.received_url = extras.getString("received_url");
            this.received_data = extras.getString("received_data");
            this.Is_user_first = extras.getBoolean("Is_user_first", false);
        }
        this.helpPager = (ViewPager) findViewById(R.id.helpPager);
        this.SliderDots = (LinearLayout) findViewById(R.id.SliderDots);
        this.lenearlayout_next = (LinearLayout) findViewById(R.id.lenearlayout_next);
        this.txtOK = (Button) findViewById(R.id.txtOK);
        ScreenHelpForDownload.ViewPagerAdapter viewPagerAdapter = new ScreenHelpForDownload.ViewPagerAdapter(getSupportFragmentManager());
        if (this.mFragmentHelp1 == null) {
            this.mFragmentHelp1 = new FrgForIntro();
            this.mFragmentHelp1.setArguments(m("int_data", 0));
        }
        if (this.mFragmentHelp2 == null) {
            this.mFragmentHelp2 = new FrgForIntro();
            this.mFragmentHelp2.setArguments(m("int_data", 1));
        }
        if (this.mFragmentHelp3 == null) {
            this.mFragmentHelp3 = new FrgForIntro();
            this.mFragmentHelp3.setArguments(m("int_data", 2));
        }
        viewPagerAdapter.addFragment(this.mFragmentHelp1, "0");
        viewPagerAdapter.addFragment(this.mFragmentHelp2, "1");
        viewPagerAdapter.addFragment(this.mFragmentHelp3, "2");
        int count = viewPagerAdapter.getCount();
        this.dotscount = count;
        this.dots = new ImageView[count];
        int i = 0;
        while (true) {
            int i2 = this.dotscount;
            if (i < i2) {
                this.SliderDots.setWeightSum(i2);
                this.dots[i] = new ImageView(this);
                this.dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.non_active_main_img));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                layoutParams.setMargins(0, 0, 0, 0);
                layoutParams.weight = 1.0f;
                this.SliderDots.addView(this.dots[i], layoutParams);
                i++;
            } else {
                this.dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_small));
                this.txtOK.setOnClickListener(v -> {
                    IntroActivity introActivity = IntroActivity.this;
                    int[] iArr = introActivity.vpPos;
                    if (iArr[0] == 2) {
                        introActivity.startMainClass();
                        return;
                    }
                    iArr[0] = iArr[0] + 1;
                    introActivity.helpPager.setCurrentItem(iArr[0]);
                });
                this.helpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        IntroActivity introActivity;
                        int i3 = 0;
                        while (true) {
                            introActivity = IntroActivity.this;
                            if (i3 >= introActivity.dotscount) {
                                break;
                            }
                            introActivity.dots[i3].setImageDrawable(ContextCompat.getDrawable(introActivity, R.drawable.non_active_main_img));
                            i3++;
                        }
                        introActivity.dots[position].setImageDrawable(ContextCompat.getDrawable(introActivity, R.drawable.active_small));
                        IntroActivity introActivity2 = IntroActivity.this;
                        introActivity2.vpPos[0] = position;
                        if (position == 2) {
                            introActivity2.txtOK.setText(R.string.finish);
                        } else {
                            introActivity2.txtOK.setText(R.string.NEXT);
                        }
                    }
                });
                this.helpPager.setCurrentItem(0);
                this.vpPos[0] = 0;
                this.helpPager.setOffscreenPageLimit(1);
                this.helpPager.setAdapter(viewPagerAdapter);
                viewPagerAdapter.notifyDataSetChanged();
                return;
            }
        }
    }
}
