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
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;
import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.model_tag;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter.adapter_tag;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;

public class HashTagShareCopyActivity extends AdLoaderBase {
    public adapter_tag adapterTag;
    public CheckBox cbSelectAll;
    public String header;
    public ImageView ivBack;
    public LinearLayout layNoData;
    public LinearLayout linCopy;
    public LinearLayout linSelectAll;
    public LinearLayout linShareFace;
    public LinearLayout linShareInsta;
    public LinearLayout linShareWhats;
    public ArrayList<model_tag> modelTagsShare;
    public RelativeLayout relData;
    public RecyclerView rvTag;
    public String shareAll;
    public ArrayList<String> tagNames;
    public TextView tvTagCount;
    public TextView txtHeader;
    public boolean isAllSelected = false;
    public final ArrayList<model_tag> modelTags = new ArrayList<>();

    @SuppressLint("WrongConstant")
    private void inItIds() {
        this.tvTagCount = (TextView) findViewById(R.id.tvTagCount);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.txtHeader = (TextView) findViewById(R.id.txtHeader);
        this.linSelectAll = (LinearLayout) findViewById(R.id.linSelectAll);
        this.cbSelectAll = (CheckBox) findViewById(R.id.cbSelectAll);
        this.rvTag = (RecyclerView) findViewById(R.id.rvTag);
        this.layNoData = (LinearLayout) findViewById(R.id.layNoData);
        this.relData = (RelativeLayout) findViewById(R.id.relData);
        this.linShareInsta = (LinearLayout) findViewById(R.id.linShareInsta);
        this.linShareWhats = (LinearLayout) findViewById(R.id.linShareWhats);
        this.linShareFace = (LinearLayout) findViewById(R.id.linShareFace);
        this.linCopy = (LinearLayout) findViewById(R.id.linCopy);
        this.ivBack.setOnClickListener(v -> HashTagShareCopyActivity.this.onBackPressed());
        this.cbSelectAll.setOnClickListener(v -> {
            HashTagShareCopyActivity hashTagShareCopyActivity = HashTagShareCopyActivity.this;
            adapter_tag adapter_tagVar = hashTagShareCopyActivity.adapterTag;
            if (adapter_tagVar != null) {
                hashTagShareCopyActivity.isAllSelected = adapter_tagVar.updateView();
            }
            ArrayList<model_tag> arrayList = HashTagShareCopyActivity.this.modelTagsShare;
            if (arrayList != null) {
                arrayList.clear();
            }
        });
        this.linSelectAll.setOnClickListener(v -> {
            HashTagShareCopyActivity hashTagShareCopyActivity = HashTagShareCopyActivity.this;
            adapter_tag adapter_tagVar = hashTagShareCopyActivity.adapterTag;
            if (adapter_tagVar != null) {
                hashTagShareCopyActivity.isAllSelected = adapter_tagVar.updateView();
            }
            CheckBox checkBox = HashTagShareCopyActivity.this.cbSelectAll;
            checkBox.setChecked(!checkBox.isChecked());
            ArrayList<model_tag> arrayList = HashTagShareCopyActivity.this.modelTagsShare;
            if (arrayList != null) {
                arrayList.clear();
            }
        });
        this.linShareInsta.setOnClickListener(v -> {
            HashTagShareCopyActivity hashTagShareCopyActivity = HashTagShareCopyActivity.this;
            if (hashTagShareCopyActivity.isAllSelected) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("android.intent.extra.TEXT", HashTagShareCopyActivity.this.shareAll);
                intent.setPackage("com.instagram.android");
                try {
                    HashTagShareCopyActivity.this.startActivity(intent);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            ArrayList<model_tag> arrayList = hashTagShareCopyActivity.modelTagsShare;
            if (arrayList != null && arrayList.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < HashTagShareCopyActivity.this.modelTagsShare.size(); i++) {
                    if (HashTagShareCopyActivity.this.modelTagsShare.get(i).isSetSelected()) {
                        sb.append(HashTagShareCopyActivity.this.modelTagsShare.get(i).getName());
                    }
                }
                Intent intent2 = new Intent("android.intent.action.SEND");
                intent2.setType("text/plain");
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra("android.intent.extra.TEXT", sb.toString());
                intent2.setPackage("com.instagram.android");
                try {
                    HashTagShareCopyActivity.this.startActivity(intent2);
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            HashTagShareCopyActivity hashTagShareCopyActivity2 = HashTagShareCopyActivity.this;
            Toast.makeText(hashTagShareCopyActivity2, hashTagShareCopyActivity2.getString(R.string.please_select_tag_to_share), Toast.LENGTH_SHORT).show();
        });
        this.linShareWhats.setOnClickListener(v -> {
            HashTagShareCopyActivity hashTagShareCopyActivity = HashTagShareCopyActivity.this;
            if (hashTagShareCopyActivity.isAllSelected) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", HashTagShareCopyActivity.this.shareAll);
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
            ArrayList<model_tag> arrayList = hashTagShareCopyActivity.modelTagsShare;
            if (arrayList != null && arrayList.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < HashTagShareCopyActivity.this.modelTagsShare.size(); i++) {
                    if (HashTagShareCopyActivity.this.modelTagsShare.get(i).isSetSelected()) {
                        sb.append(HashTagShareCopyActivity.this.modelTagsShare.get(i).getName());
                    }
                }
                Intent intent2 = new Intent("android.intent.action.SEND");
                intent2.setType("text/plain");
                intent2.putExtra("android.intent.extra.TEXT", sb.toString());
                for (ResolveInfo resolveInfo2 : v.getContext().getPackageManager().queryIntentActivities(intent2, 0)) {
                    if (resolveInfo2.activityInfo.name.contains("com.twitter")) {
                        ActivityInfo activityInfo2 = resolveInfo2.activityInfo;
                        ComponentName componentName2 = new ComponentName(activityInfo2.applicationInfo.packageName, activityInfo2.name);
                        intent2.addCategory("android.intent.category.LAUNCHER");
                        intent2.setFlags(270532608);
                        intent2.setComponent(componentName2);
                        try {
                            v.getContext().startActivity(intent2);
                            return;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                }
                return;
            }
            HashTagShareCopyActivity hashTagShareCopyActivity2 = HashTagShareCopyActivity.this;
            Toast.makeText(hashTagShareCopyActivity2, hashTagShareCopyActivity2.getString(R.string.please_select_tag_to_share), 0).show();
        });
        this.linShareFace.setOnClickListener(v -> {
            HashTagShareCopyActivity hashTagShareCopyActivity = HashTagShareCopyActivity.this;
            if (hashTagShareCopyActivity.isAllSelected) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", HashTagShareCopyActivity.this.shareAll);
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
                return;
            }
            ArrayList<model_tag> arrayList = hashTagShareCopyActivity.modelTagsShare;
            if (arrayList != null && arrayList.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < HashTagShareCopyActivity.this.modelTagsShare.size(); i++) {
                    if (HashTagShareCopyActivity.this.modelTagsShare.get(i).isSetSelected()) {
                        sb.append(HashTagShareCopyActivity.this.modelTagsShare.get(i).getName());
                    }
                }
                Intent intent2 = new Intent("android.intent.action.SEND");
                intent2.setType("text/plain");
                intent2.putExtra("android.intent.extra.TEXT", sb.toString());
                for (ResolveInfo resolveInfo2 : v.getContext().getPackageManager().queryIntentActivities(intent2, 0)) {
                    if (resolveInfo2.activityInfo.name.contains("facebook")) {
                        ActivityInfo activityInfo2 = resolveInfo2.activityInfo;
                        ComponentName componentName2 = new ComponentName(activityInfo2.applicationInfo.packageName, activityInfo2.name);
                        intent2.addCategory("android.intent.category.LAUNCHER");
                        intent2.setFlags(270532608);
                        intent2.setComponent(componentName2);
                        try {
                            v.getContext().startActivity(intent2);
                            return;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                }
                return;
            }
            HashTagShareCopyActivity hashTagShareCopyActivity2 = HashTagShareCopyActivity.this;
            Toast.makeText(hashTagShareCopyActivity2, hashTagShareCopyActivity2.getString(R.string.please_select_tag_to_share), 0).show();
        });
        this.linCopy.setOnClickListener(v -> {
            HashTagShareCopyActivity hashTagShareCopyActivity = HashTagShareCopyActivity.this;
            if (hashTagShareCopyActivity.isAllSelected) {
                HashTagShareCopyActivity hashTagShareCopyActivity2 = HashTagShareCopyActivity.this;
                ((ClipboardManager) hashTagShareCopyActivity.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(hashTagShareCopyActivity2.header, hashTagShareCopyActivity2.shareAll));
                Toast.makeText(HashTagShareCopyActivity.this, "Tag Copy", Toast.LENGTH_SHORT).show();

                return;
            }
            ArrayList<model_tag> arrayList = hashTagShareCopyActivity.modelTagsShare;
            if (arrayList != null && arrayList.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < HashTagShareCopyActivity.this.modelTagsShare.size(); i++) {
                    if (HashTagShareCopyActivity.this.modelTagsShare.get(i).isSetSelected()) {
                        sb.append(HashTagShareCopyActivity.this.modelTagsShare.get(i).getName());
                    }
                }
                ((ClipboardManager) HashTagShareCopyActivity.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(HashTagShareCopyActivity.this.header, sb));
                HashTagShareCopyActivity hashTagShareCopyActivity3 = HashTagShareCopyActivity.this;
                Toast.makeText(hashTagShareCopyActivity3, hashTagShareCopyActivity3.getString(R.string.tag_copy), Toast.LENGTH_SHORT).show();
                return;
            }
            HashTagShareCopyActivity hashTagShareCopyActivity4 = HashTagShareCopyActivity.this;
            Toast.makeText(hashTagShareCopyActivity4, hashTagShareCopyActivity4.getString(R.string.please_select_tag_to_copy), Toast.LENGTH_SHORT).show();
        });
    }

    public static String mmm(String str, String str2) {
        return str + str2;
    }

    private void setRecyclerData() {
        String[] split;
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        this.rvTag.setLayoutManager(flexboxLayoutManager);
        String str = this.shareAll;
        if (str != null && str.trim().length() > 0) {
            for (String str2 : this.shareAll.split("#")) {
                if (!str2.equals("")) {
                    this.modelTags.add(new model_tag(mmm("#", str2)));
                }
            }
        }
        adapter_tag adapter_tagVar = new adapter_tag(this, this.modelTags);
        this.adapterTag = adapter_tagVar;
        this.rvTag.setAdapter(adapter_tagVar);
        this.adapterTag.setOnItemClickListener((position, tag_list) -> {
            HashTagShareCopyActivity hashTagShareCopyActivity = HashTagShareCopyActivity.this;
            hashTagShareCopyActivity.isAllSelected = false;
            ArrayList<model_tag> arrayList = hashTagShareCopyActivity.modelTagsShare;
            if (arrayList != null) {
                arrayList.clear();
            }
            HashTagShareCopyActivity.this.modelTagsShare = new ArrayList<>();
            for (int i = 0; i < tag_list.size(); i++) {
                if (tag_list.get(i).isSetSelected()) {
                    HashTagShareCopyActivity.this.modelTagsShare.add(tag_list.get(i));
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_tag_share_copy);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.hashTagId, findViewById(R.id.banner_container));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance());
        inItIds();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.header = extras.getString("TAG_NAME");
            this.shareAll = extras.getString("SHARE_ALL");
            this.tagNames = extras.getStringArrayList("TAG_LIST");
        } else {
            finish();
        }
        if (Objects.requireNonNull(this.tagNames).size() == 0) {
            this.layNoData.setVisibility(View.VISIBLE);
            this.relData.setVisibility(View.GONE);
        }

        TextView textView = this.tvTagCount;
        textView.setText(this.tagNames.size() + " TAGS");
        this.txtHeader.setText(getIntent().getStringExtra("TAG_NAME"));
        setRecyclerData();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
