package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;
import videodownloader.privatebrowser.free.hd.statussaver.tool.Delegate;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

public class ActivityVideoLink extends AdLoaderBase {
    private adapter_video_url adapter;
    private RecyclerView appRecyclerview;
    private LinearLayout btnForDownload;
    private RelativeLayout cardLoaindView;
    private Dialog dialog;
    private ImageView imgMainVideoThumb;
    private LinearLayout linCopy;
    private LinearLayout linShare;
    private String localfNAME;
    private String mfNAME;
    private PermissionListener permissionListener;
    private RelativeLayout relative_error;
    private TextView txtMainTitle;
    private TextView txtVidLength;
    private List<store_model_video_link.listVideos> vidVideoLinks = new ArrayList<>();
    private String mTitle = "";
    private String mUrl = "";
    private int mPos = -1;
    private boolean alreadyLoaded = false;
    private String type_ = "";

    private boolean fbcdnFound = false;
    private boolean isFirstSelected = false;

    private boolean onlyImgLoad = false;
    private boolean thumbLoaded = false;

    public static String m(String str, int i) {
        return str + i;
    }


    public static class NRecyclerLayoutManager extends GridLayoutManager {

        @Override

        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public NRecyclerLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

    }

    private void doDownloadClick() {
        if (Build.VERSION.SDK_INT <= 29) {
            TedPermission.create().setPermissionListener(this.permissionListener).setRationaleTitle(getString(R.string.storage_permission)).setRationaleMessage(R.string.please_allow_to_downloaded_video).setDeniedTitle(R.string.permission_denied).setDeniedMessage(R.string.if_you_reject_permission).setGotoSettingButtonText(getResources().getString(R.string.settings)).setPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").check();
        } else {
            AllInOneAds.getInstance().showInterWithId(this, AdHelper.allVideoDownloadId, () -> {
                startDownload();
            });
        }
    }

    private void extarctLinks(ArrayList<store_model_video_link.listVideos> arrayList, String type) {
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (this.isFirstSelected) {
                arrayList.get(i).setIs_selected(false);
            } else if (i == 0) {
                this.mPos = this.vidVideoLinks.size();
                arrayList.get(i).setIs_selected(true);
            } else {
                arrayList.get(i).setIs_selected(false);
            }
            if (arrayList.get(i).getN_link_image() != null && !arrayList.get(i).getN_link_image().isEmpty()) {
                loadOnlyThumb(arrayList.get(i).getN_link_image());
            }
            this.vidVideoLinks.add(arrayList.get(i));
        }
        this.adapter.notifyDataSetChanged();
        this.alreadyLoaded = false;
        showHideViewAtLast();
    }

    private void initUIs() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.download_vid));
        this.appRecyclerview = (RecyclerView) findViewById(R.id.appRecyclerview);
        this.cardLoaindView = (RelativeLayout) findViewById(R.id.cardLoaindView);
        this.imgMainVideoThumb = (ImageView) findViewById(R.id.imgMainVideoThumb);
        this.txtVidLength = (TextView) findViewById(R.id.txtVidLength);
        this.txtMainTitle = (TextView) findViewById(R.id.txtMainTitle);
        this.cardLoaindView.setVisibility(View.VISIBLE);
        this.txtVidLength.setVisibility(View.GONE);
        this.relative_error = (RelativeLayout) findViewById(R.id.relative_error);
        this.btnForDownload = (LinearLayout) findViewById(R.id.btnForDownload);
        this.linShare = (LinearLayout) findViewById(R.id.linShare);
        this.linCopy = (LinearLayout) findViewById(R.id.linCopy);
        TextView textView = (TextView) findViewById(R.id.txtHeadetText);
        textView.setText(R.string.due_to_technical_fault_this_video_content);
    }

    public static StringBuilder mnew(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb;
    }


    private void setData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mTitle = extras.getString("d_title");
            this.mUrl = extras.getString("d_url");
            this.type_ = extras.getString("type_");
            this.txtMainTitle.setText(this.mTitle);
            if (this.vidVideoLinks == null) {
                this.vidVideoLinks = new ArrayList<>();
            }
            CopyOnWriteArrayList<store_model_video_link.listVideos> copyOnWriteArrayList = MainActivityVideos.localDownloadLinks.get(this.mUrl);
            if (copyOnWriteArrayList != null) {
                this.isFirstSelected = false;
                for (int i = 0; i < copyOnWriteArrayList.size(); i++) {
                    if (copyOnWriteArrayList.get(i).getN_link_url().contains("fbcdn.net")) {
                        this.fbcdnFound = true;
                    }
                    if (i == 0) {
                        this.isFirstSelected = true;
                        this.mPos = 0;
                        copyOnWriteArrayList.get(i).setIs_selected(true);
                    } else {
                        copyOnWriteArrayList.get(i).setIs_selected(false);
                    }
                    if (copyOnWriteArrayList.get(i).getN_link_image() != null && !copyOnWriteArrayList.get(i).getN_link_image().isEmpty()) {
                        loadOnlyThumb(copyOnWriteArrayList.get(i).getN_link_image());
                    }
                    this.vidVideoLinks.add(copyOnWriteArrayList.get(i));
                }
                this.adapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        onBackPressed();
    }

    private void showHideViewAtLast() {
        RelativeLayout relativeLayout;
        if (isFinishing() || (relativeLayout = this.cardLoaindView) == null) {
            return;
        }
        relativeLayout.setVisibility(View.GONE);
        List<store_model_video_link.listVideos> list = this.vidVideoLinks;
        if (list != null) {
            if (list.size() == 0) {
                this.relative_error.setVisibility(View.VISIBLE);
            } else {
                this.relative_error.setVisibility(View.GONE);
            }
        }
    }

    public void startDownload() {
        if (this.mPos >= this.vidVideoLinks.size()) {
            UtilsForApp.showToastMsg(getString(R.string.please_try_later) + "aaaaaa", this, true);
        } else if (this.vidVideoLinks.get(this.mPos).getN_link_url() == null || this.vidVideoLinks.get(this.mPos).getN_link_url().isEmpty()) {
            UtilsForApp.showToastMsg(getString(R.string.link_is_invalid_please_try_other_file), this, true);
        } else {
            boolean isStart = false;
            String str = this.mfNAME;
            if (str == null || str.isEmpty()) {
                StringBuilder m = new StringBuilder("Video-");
                m.append(System.currentTimeMillis());
                m.append(".");
                m.append(this.vidVideoLinks.get(this.mPos).getN_link_extension());
                this.mfNAME = m.toString();
            }

            PRDownloaderConfig config = PRDownloaderConfig.newBuilder().setDatabaseEnabled(true).build();
            PRDownloader.initialize(getApplicationContext(), config);

            config = PRDownloaderConfig.newBuilder().setReadTimeout(30_000).setConnectTimeout(30_000).build();
            PRDownloader.initialize(getApplicationContext(), config);
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Video Downloader";
            int downloadId = PRDownloader.download(vidVideoLinks.get(this.mPos).getN_link_url(), path, mfNAME).build().setOnStartOrResumeListener(() -> Toast.makeText(ActivityVideoLink.this, "Download Start", Toast.LENGTH_SHORT).show()).setOnPauseListener(() -> {

            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {


                    Toast.makeText(ActivityVideoLink.this, "Download Complete", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(Error error) {

                }


            });


        }
    }

    public void LoadImageAndSize(final String str, final String str3, final String str2, final Bitmap bitmap) {
        if (isFinishing()) {
            return;
        }
        runOnUiThread(() -> {

            if (isFinishing() || this.alreadyLoaded) {
                return;
            }
            try {
                if (!str.isEmpty()) {
                    this.txtVidLength.setText(str);
                    this.txtVidLength.setVisibility(View.VISIBLE);
                    this.alreadyLoaded = true;
                }
                if (this.txtMainTitle.getText().toString().isEmpty()) {
                    this.txtMainTitle.setText(str2);
                }
                if (isFinishing() || this.thumbLoaded) {
                    return;
                }
                if (bitmap != null) {
                    Glide.with((FragmentActivity) this).asBitmap().load(bitmap).placeholder((int) R.drawable.net_plceholder).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().fitCenter().override(400, 400).format(DecodeFormat.PREFER_RGB_565)).into(this.imgMainVideoThumb);
                } else {
                    Glide.with((FragmentActivity) this).asBitmap().load(str3).placeholder((int) R.drawable.net_plceholder).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().fitCenter().override(400, 400).format(DecodeFormat.PREFER_RGB_565)).into(this.imgMainVideoThumb);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
    }

    public void loadOnlyThumb(String imgURL) {
        if (isFinishing() || this.onlyImgLoad) {
            return;
        }
        this.onlyImgLoad = true;
        Glide.with((FragmentActivity) this).load(imgURL).placeholder((int) R.drawable.net_plceholder).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().fitCenter().override(400, 400).format(DecodeFormat.PREFER_RGB_565)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                ActivityVideoLink.this.thumbLoaded = false;
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                ActivityVideoLink.this.thumbLoaded = true;
                return false;
            }
        }).into(this.imgMainVideoThumb);
    }


    @Override
    public void onBackPressed() {

        AllInOneAds.getInstance().showBackInter(this, () -> {

            setResult(-1, new Intent());
            finish();
        });

    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vid_links_activity);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.allVideoDownloadId, findViewById(R.id.banner_container));

        Delegate.activityVideoLink = this;
        initUIs();
        this.appRecyclerview.setHasFixedSize(true);
        this.adapter = new adapter_video_url(this, this.vidVideoLinks);
        this.appRecyclerview.setLayoutManager(new NRecyclerLayoutManager(this, 3));
        this.appRecyclerview.setAdapter(this.adapter);
        setData();
        this.adapter.setOnItemClickListener((view, i, str) -> {
            if (view == null || i == -1) {
                return;
            }
            this.mPos = i;
            this.localfNAME = str;
            int i2 = 0;
            while (true) {
                if (i2 >= this.vidVideoLinks.size()) {
                    i2 = -1;
                    break;
                } else if (this.vidVideoLinks.get(i2).isIs_selected()) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 != -1) {
                this.vidVideoLinks.get(i2).setIs_selected(false);
                this.adapter.notifyItemChanged(i2);
            }
            this.vidVideoLinks.get(this.mPos).setIs_selected(true);
            this.adapter.notifyItemChanged(this.mPos);
        });
        this.permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                ActivityVideoLink.this.startDownload();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };
        if (this.type_.equalsIgnoreCase("natural")) {
            if (!this.mUrl.equalsIgnoreCase("https://9gag.com/") && !this.mUrl.equalsIgnoreCase("https://9gag.com") && !this.mUrl.equalsIgnoreCase("http://9gag.com/") && !this.mUrl.equalsIgnoreCase("http://9gag.com")) {
                UtilsForApp.checkVideoLink(this, this.mUrl, this.mTitle, "web", (i, store_model_video_linkVar, arrayList, str) -> {
                    if (i == 0) {
                        showHideViewAtLast();
                    } else if (i == 1) {
                        extarctLinks(store_model_video_linkVar);
                    } else if (i == 2) {
                        extarctLinks(arrayList, str);
                    }
                });
            } else {
                showHideViewAtLast();
            }
        } else {
            showHideViewAtLast();
        }
        this.btnForDownload.setOnClickListener(view -> {
            if (this.mPos == -1) {
                UtilsForApp.showToastMsg(getString(R.string.please_select_video_to_download), this, true);
                return;
            }
            if (this.txtMainTitle.getText().toString().isEmpty()) {
                this.mfNAME = this.localfNAME;
            } else {
                String VerifyTitle = UtilsForApp.VerifyTitle(this.txtMainTitle.getText().toString());
                this.mfNAME = VerifyTitle;
                if (VerifyTitle.isEmpty()) {
                    this.mfNAME = this.localfNAME;
                } else {
                    String str = this.mfNAME;
                    StringBuilder m = mnew(str.substring(0, Math.min(str.length(), 20)), "_");
                    m.append(System.currentTimeMillis());
                    m.append(".");
                    m.append(this.vidVideoLinks.get(this.mPos).getN_link_extension());
                    this.mfNAME = m.toString();
                }
            }
            doDownloadClick();
        });
        this.linShare.setOnClickListener(view -> {
            int i = this.mPos;
            if (i == -1) {
                UtilsForApp.showToastMsg("Please select Video to share", this, true);
                return;
            }
            String n_link_url = this.vidVideoLinks.get(i).getN_link_url();
            if (n_link_url == null) {
                n_link_url = "";
            }
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", "Video Link");
            intent.putExtra("android.intent.extra.TEXT", n_link_url);
            startActivity(Intent.createChooser(intent, "Share via"));
        });
        this.linCopy.setOnClickListener(view -> {
            int i = this.mPos;
            if (i == -1) {
                UtilsForApp.showToastMsg("Please select Video to copy", this, true);
                return;
            }
            String n_link_url = this.vidVideoLinks.get(i).getN_link_url();
            if (n_link_url == null) {
                n_link_url = "";
            }
            ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Video Link", n_link_url));
            Toast.makeText(this, "copy", Toast.LENGTH_SHORT).show();
        });

    }

    @Override

    public void onDestroy() {

        UtilsForApp.cancelRetroCall();
        Delegate.activityVideoLink = null;
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

    public void showLoading() {
        if (isFinishing()) {
            return;
        }
        if (this.dialog == null) {
            this.dialog = new Dialog(this);
        }
        if (this.dialog.getContext() != null) {
            this.dialog.setContentView(R.layout.add_to_download_loading);
            Objects.requireNonNull(this.dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setCancelable(false);
            if (this.dialog.isShowing()) {
                return;
            }
            this.dialog.show();
        }
    }

    public void stopLoading() {
        try {
            Dialog dialog = this.dialog;
            if (dialog == null || !dialog.isShowing()) {
                return;
            }
            this.dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void extarctLinks(store_model_video_link modelVideoLink) {
        if (this.fbcdnFound && this.alreadyLoaded) {
            this.alreadyLoaded = false;
            showHideViewAtLast();
            return;
        }
        List<store_model_video_link.listVideos> linksLists = modelVideoLink.getLinksLists();
        ArrayList arrayList = new ArrayList();
        if (linksLists != null && linksLists.size() > 0) {
            int i = 0;
            for (int i2 = 0; i2 < linksLists.size(); i2++) {
                if (linksLists.get(i2).getN_link_height() == 0) {
                    if (this.isFirstSelected) {
                        linksLists.get(i2).setIs_selected(false);
                    } else if (i == 0) {
                        this.mPos = this.vidVideoLinks.size();
                        linksLists.get(i2).setIs_selected(true);
                    } else {
                        linksLists.get(i2).setIs_selected(false);
                    }
                    if (linksLists.get(i2).getN_link_image() != null && !linksLists.get(i2).getN_link_image().isEmpty()) {
                        loadOnlyThumb(linksLists.get(i2).getN_link_image());
                    }
                    this.vidVideoLinks.add(linksLists.get(i2));
                } else if (!arrayList.contains(linksLists.get(i2).getN_link_height())) {
                    arrayList.add(linksLists.get(i2).getN_link_height());
                    if (this.isFirstSelected) {
                        linksLists.get(i2).setIs_selected(false);
                    } else if (i == 0) {
                        this.mPos = this.vidVideoLinks.size();
                        linksLists.get(i2).setIs_selected(true);
                    } else {
                        linksLists.get(i2).setIs_selected(false);
                    }
                    if (linksLists.get(i2).getN_link_image() != null && !linksLists.get(i2).getN_link_image().isEmpty()) {
                        loadOnlyThumb(linksLists.get(i2).getN_link_image());
                    }
                    this.vidVideoLinks.add(linksLists.get(i2));
                }
                i++;
            }
            this.adapter.notifyDataSetChanged();
            this.alreadyLoaded = false;
        }
        showHideViewAtLast();
    }
}
