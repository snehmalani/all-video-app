package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;
import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;

public class BioEditShareActivity extends AdLoaderBase {
    public EditText etText;
    public boolean isFromEdit = false;
    public ImageView ivBack;
    public LinearLayout linCopy;
    public LinearLayout linEdit;
    public LinearLayout linShareInsta;
    public LinearLayout linShareTwitter;

    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_edit_share);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.captionId, findViewById(R.id.banner_container));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance());
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.etText = (EditText) findViewById(R.id.etText);
        this.linShareInsta = (LinearLayout) findViewById(R.id.linShareInsta);
        this.linShareTwitter = (LinearLayout) findViewById(R.id.linShareTwitter);
        this.linCopy = (LinearLayout) findViewById(R.id.linCopy);
        this.linEdit = (LinearLayout) findViewById(R.id.linEdit);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.etText.setText(extras.getString("SHARE_TEXT"));
            this.isFromEdit = extras.getBoolean("IS_FROM_EDIT");
        } else {
            finish();
        }

        this.linShareInsta.setOnClickListener(v -> {
            if (BioEditShareActivity.this.etText.getText().toString().trim().length() > 0) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("android.intent.extra.TEXT", BioEditShareActivity.this.etText.getText().toString());
                intent.setPackage("com.instagram.android");
                try {
                    BioEditShareActivity.this.startActivity(intent);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            Toast.makeText(BioEditShareActivity.this, "No text found", Toast.LENGTH_SHORT).show();
        });
        this.linShareTwitter.setOnClickListener(v -> {
            if (BioEditShareActivity.this.etText.getText().toString().trim().length() > 0) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", BioEditShareActivity.this.etText.getText().toString());
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
                return;
            }
            Toast.makeText(BioEditShareActivity.this, "No text found", 0).show();
        });
        this.linCopy.setOnClickListener(v -> {
            if (BioEditShareActivity.this.etText.getText().toString().trim().length() > 0) {
                ((ClipboardManager) BioEditShareActivity.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Copy", BioEditShareActivity.this.etText.getText().toString()));
                Toast.makeText(BioEditShareActivity.this, (int) R.string.bio_copy, Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(BioEditShareActivity.this, "No text found", Toast.LENGTH_SHORT).show();
        });
        this.linEdit.setOnClickListener(new View.OnClickListener() {
            public static final boolean $assertionsDisabled = false;


            @Override
            public void onClick(View v) {
                BioEditShareActivity.this.etText.requestFocus();
                ((InputMethodManager) BioEditShareActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(1, 0);
            }
        });
        this.ivBack.setOnClickListener(v -> BioEditShareActivity.this.onBackPressed());
        new Handler(Objects.requireNonNull(Looper.myLooper())).postDelayed(() -> {
            if (this.isFromEdit) {
                this.linEdit.performClick();
                this.etText.performClick();
            }
        }, 500L);
    }

}
