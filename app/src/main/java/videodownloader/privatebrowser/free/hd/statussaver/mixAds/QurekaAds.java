package videodownloader.privatebrowser.free.hd.statussaver.mixAds;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import pl.droidsonroids.gif.GifImageView;

public class QurekaAds {

    public static QurekaAds getInstance() {
        return new QurekaAds();
    }

    public void showTopNativeAds(Activity activity, AdsId adsId, ViewGroup viewGroup) {
        if (AdHelper.NATIVE_SIZE.matches("1")) {
            loadQurekaNativeSmall(activity,viewGroup);
        }else if (AdHelper.NATIVE_SIZE.matches("2")) {
            loadQurekaNative(activity,viewGroup);
        }
    }

    public void showMiddleNativeAds(Activity activity, AdsId adsId, ViewGroup viewGroup) {
        if (AdHelper.MID_NATIVE_SIZE.matches("1")) {
            loadQurekaNativeSmall(activity,viewGroup);
        }else if (AdHelper.MID_NATIVE_SIZE.matches("2")) {
            loadQurekaNative(activity,viewGroup);
        }
    }


    public void showBottomNativeAds(Activity activity, AdsId adsId, ViewGroup viewGroup) {
        if (AdHelper.BANNER_SIZE.matches("1")) {
            loadQurekaBanner(activity,viewGroup);
        }else if (AdHelper.BANNER_SIZE.matches("2")) {
            loadQurekaNativeSmall(activity,viewGroup);
        }else if (AdHelper.BANNER_SIZE.matches("3")) {
            loadQurekaNative(activity,viewGroup);
        }
    }

    public void loadQurekaBanner(Activity activity, ViewGroup frameLayout) {
        if (AdHelper.IS_QUREKA_BANNER_ON.matches("0")) {
            return;
        }

        FrameLayout adView = (FrameLayout) activity.getLayoutInflater().inflate(R.layout.qureka_banner, null);
        frameLayout.removeAllViews();
        frameLayout.addView(adView);
        ImageView QurekaAds_banner = frameLayout.findViewById(R.id.QurekaAds_banner);
        Random randm = new Random();
        String[] allIdsArray = AdHelper.QurekaBanner.split(",");
        ArrayList<String> idsList = new ArrayList<>(Arrays.asList(allIdsArray));
        Glide.with(activity).load(idsList.get(randm.nextInt(idsList.size()))).into(QurekaAds_banner);
        QurekaAds_banner.setOnClickListener(view -> {
            try {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor("#FFFFFF")).setShowTitle(true);
                CustomTabsIntent build = builder.build();
                build.intent.setPackage("com.android.chrome");
                build.launchUrl(activity, Uri.parse(AdHelper.redirectLink_banner));

            } catch (Exception e) {
            }
        });
    }

    public void loadQurekaNativeSmall(Activity activity, ViewGroup frameLayout) {
        if (AdHelper.IS_QUREKA_NATIVE_ON.matches("0")) {
            return;
        }

        ViewGroup adView;
        if (AdHelper.NATIVE_COLOR.matches("1")) {
            adView = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.qureka_native_small_trn, null);
        } else {
            adView = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.qureka_native_small, null);
        }

        frameLayout.removeAllViews();
        frameLayout.addView(adView);
        GifImageView imageView2;
        ImageView imageView = frameLayout.findViewById(R.id.imagview);
        imageView2 = frameLayout.findViewById(R.id.gifimagview);
        TextView des = frameLayout.findViewById(R.id.description);
        TextView shortdisc = frameLayout.findViewById(R.id.shortdiscrip);
        TextView actionbutton = frameLayout.findViewById(R.id.actionbutton);

        String button_titel = AdHelper.button_titel;
        String disc = AdHelper.disc;
        String image = AdHelper.image;
        String image2 = AdHelper.image2;
        String short_disc = AdHelper.short_disc;
        String redirectLink_native = AdHelper.redirectLink_native;


        actionbutton.setText("" + button_titel);
        shortdisc.setText("" + short_disc);
        des.setText("" + disc);
        Glide.with(activity).load(image).into(imageView);
        Glide.with(activity).load(image2).into(imageView2);


        if (AdHelper.IS_NATIVE_BLINK.matches("1")) {
            YoYo.with(Techniques.FadeIn).duration(AdHelper.BLINK_TIME).repeat(10000).playOn(actionbutton);
        }

        actionbutton.setTextColor(activity.getColor(R.color.white));
        if (AdHelper.NATIVE_BTN_COLOR.matches("1")) {
            actionbutton.setBackgroundResource(R.drawable.first_button_chage);
        } else {
            actionbutton.setBackgroundResource(R.drawable.first_button_color);
        }


        LinearLayout QurekaAds_native = frameLayout.findViewById(R.id.QurekaAds_native);
        QurekaAds_native.setOnClickListener(view -> {
            try {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor("#FFFFFF")).setShowTitle(true);
                CustomTabsIntent build = builder.build();
                build.intent.setPackage("com.android.chrome");
                build.launchUrl(activity, Uri.parse(redirectLink_native));
            } catch (Exception e) {
            }
        });

    }


    public void loadQurekaNative(Activity activity, ViewGroup frameLayout) {
        if (AdHelper.IS_QUREKA_NATIVE_ON.matches("0")) {
            return;
        }

        ViewGroup adView;
        if (AdHelper.NATIVE_COLOR.matches("1")) {
            adView = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.qureka_native_big_trn, null);
        } else {
            adView = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.qureka_native_big, null);
        }

        frameLayout.removeAllViews();
        frameLayout.addView(adView);
        GifImageView imageView2;
        ImageView imageView = frameLayout.findViewById(R.id.imagview);
        imageView2 = frameLayout.findViewById(R.id.gifimagview);
        TextView des = frameLayout.findViewById(R.id.description);
        TextView shortdisc = frameLayout.findViewById(R.id.shortdiscrip);
        TextView actionbutton = frameLayout.findViewById(R.id.actionbutton);

        String button_titel = AdHelper.button_titel;
        String disc = AdHelper.disc;
        String image = AdHelper.image;
        String image2 = AdHelper.image2;
        String short_disc = AdHelper.short_disc;
        String redirectLink_native = AdHelper.redirectLink_native;

        actionbutton.setText("" + button_titel);
        shortdisc.setText("" + short_disc);
        des.setText("" + disc);
        Glide.with(activity).load(image).into(imageView);
        Glide.with(activity).load(image2).into(imageView2);

        if (AdHelper.IS_NATIVE_BLINK.matches("1")) {
            YoYo.with(Techniques.FadeIn).duration(AdHelper.BLINK_TIME).repeat(10000).playOn(actionbutton);
        }

        if (AdHelper.NATIVE_BTN_COLOR.matches("1")) {
            actionbutton.setBackgroundResource(R.drawable.first_button_chage);
        } else {
            actionbutton.setBackgroundResource(R.drawable.first_button_color);
        }


        LinearLayout QurekaAds_native = frameLayout.findViewById(R.id.QurekaAds_native);
        QurekaAds_native.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                try {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    builder.setToolbarColor(Color.parseColor(activity.getString(R.color.white))).setShowTitle(true);
                    CustomTabsIntent build = builder.build();
                    build.intent.setPackage("com.android.chrome");
                    build.launchUrl(activity, Uri.parse(redirectLink_native));

                } catch (Exception e) {
                }
            }
        });
    }

    public void loadQurekaInter(Activity context) {
        if (AdHelper.IS_QUREKA_INTER_ON.matches("0")) {
            return;
        }

        String admob_qureka_ads2 = AdHelper.inter_redirectLink;
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(Color.parseColor("#FFFFFF")).setShowTitle(true);
            CustomTabsIntent build = builder.build();
            build.intent.setPackage("com.android.chrome");
            build.launchUrl(context, Uri.parse(admob_qureka_ads2));
        } catch (Exception e) {
        }
    }

}
