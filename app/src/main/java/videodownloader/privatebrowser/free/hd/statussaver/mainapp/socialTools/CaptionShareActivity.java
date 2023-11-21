package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;

public class CaptionShareActivity extends AdLoaderBase {
    public String captionData;
    public ImageView ivBack;
    public LinearLayout linCopy;
    public LinearLayout linShareFace;
    public LinearLayout linShareInsta;
    public LinearLayout linShareTwitter;
    public TextView tvText;

    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption_share);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.captionId, findViewById(R.id.banner_container));

        this.tvText = (TextView) findViewById(R.id.tvText);
        this.linShareInsta = (LinearLayout) findViewById(R.id.linShareInsta);
        this.linShareTwitter = (LinearLayout) findViewById(R.id.linShareTwitter);
        this.linShareFace = (LinearLayout) findViewById(R.id.linShareFace);
        this.linCopy = (LinearLayout) findViewById(R.id.linCopy);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String string = extras.getString("SHARE_DATA");
            this.captionData = string;
            this.tvText.setText(string);
        }

        this.ivBack.setOnClickListener(v -> CaptionShareActivity.this.onBackPressed());
        this.linShareInsta.setOnClickListener(v -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("android.intent.extra.TEXT", CaptionShareActivity.this.captionData);
            intent.setPackage("com.instagram.android");
            try {
                CaptionShareActivity.this.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.linShareTwitter.setOnClickListener(v -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", CaptionShareActivity.this.captionData);
            for (ResolveInfo resolveInfo : v.getContext().getPackageManager().queryIntentActivities(intent, 0)) {
                if (resolveInfo.activityInfo.name.contains("com.twitter")) {
                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                    ComponentName componentName = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                    intent.addCategory("android.intent.category.LAUNCHER");
                    intent.setFlags(270532608);
                    intent.setComponent(componentName);
                    try {
                        v.getContext().startActivity(intent);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        });
        this.linShareFace.setOnClickListener(v -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", CaptionShareActivity.this.captionData);
            for (ResolveInfo resolveInfo : v.getContext().getPackageManager().queryIntentActivities(intent, 0)) {
                if (resolveInfo.activityInfo.name.contains("facebook")) {
                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                    ComponentName componentName = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                    intent.addCategory("android.intent.category.LAUNCHER");
                    intent.setFlags(270532608);
                    intent.setComponent(componentName);
                    try {
                        v.getContext().startActivity(intent);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        });
        this.linCopy.setOnClickListener(v -> {
            ((ClipboardManager) CaptionShareActivity.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Copy", CaptionShareActivity.this.captionData));
            Toast.makeText(CaptionShareActivity.this, (int) R.string.caption_copy, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
