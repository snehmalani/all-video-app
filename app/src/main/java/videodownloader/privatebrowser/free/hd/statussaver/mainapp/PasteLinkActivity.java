package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.network.Vid_API_Parse_Listen;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;
import videodownloader.privatebrowser.free.hd.statussaver.tool.Delegate;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

public class PasteLinkActivity extends AdLoaderBase {
    private Button btnDownload;
    private Button btnPaste;
    private ClipboardManager clipboard;
    private TextInputEditText edtLink;
    private ImageView icon_social;
    private ImageView imgBackPress;
    private ImageView imgFinished;
    private RelativeLayout relativeCheck;
    private RelativeLayout relativeError;
    private TextView txtTitle;
    private View viewTop;
    private String StrEdtLink = "";
    private String type = "";

    private void FetchXMLId() {
        this.edtLink = (TextInputEditText) findViewById(R.id.edtLink);
        this.btnPaste = (Button) findViewById(R.id.btnPaste);
        this.relativeCheck = (RelativeLayout) findViewById(R.id.relativeCheck);
        this.relativeError = (RelativeLayout) findViewById(R.id.relativeError);
        this.btnDownload = (Button) findViewById(R.id.btnDownload);
        this.imgFinished = (ImageView) findViewById(R.id.imgFinished);
        this.imgBackPress = (ImageView) findViewById(R.id.imgBackPress);
        this.viewTop = findViewById(R.id.viewTop);
        this.icon_social = (ImageView) findViewById(R.id.icon_social);
        this.txtTitle = (TextView) findViewById(R.id.txtTitle);
        this.relativeCheck.setVisibility(View.GONE);
        this.relativeError.setVisibility(View.GONE);
    }

    private void changeView() {
        if (this.type.equalsIgnoreCase("fb")) {
            this.imgFinished.setImageResource(R.drawable.fbtop);
            this.viewTop.setBackgroundResource(R.drawable.drawavle_view_bg_fb);
            this.txtTitle.setText(getString(R.string.facebook_downloader));
            this.icon_social.setImageResource(R.drawable.img_as_d_fb);
            this.btnDownload.setBackgroundResource(R.drawable.drawable_button_download_fb);
        } else if (this.type.equalsIgnoreCase("insta")) {
            this.imgFinished.setImageResource(R.drawable.instatop);
            this.viewTop.setBackgroundResource(R.drawable.drawavle_view_bg_ints);
            this.txtTitle.setText(getString(R.string.instagram_downloader));
            this.icon_social.setImageResource(R.drawable.img_as_d_insta);
            this.btnDownload.setBackgroundResource(R.drawable.drawable_button_download_insta);
        } else {
            this.imgFinished.setImageResource(R.drawable.downloadtop);
            this.viewTop.setBackgroundColor(ContextCompat.getColor(this, R.color.color_normla));
            this.txtTitle.setText(getString(R.string.video_downloader));
            this.icon_social.setImageResource(R.drawable.img_as_www);
            this.btnDownload.setBackgroundResource(R.drawable.drawable_button_download_normal);
        }
    }

    private void doVideoLinkChecking() {
        UtilsForApp.checkVideoLink(this, this.StrEdtLink, "", "paste", new Vid_API_Parse_Listen() {
            @Override
            public void onParseResponse(int status2, store_model_video_link modelVideoLink, ArrayList<store_model_video_link.listVideos> arrayList, String type) {
                if (status2 == 0) {
                    PasteLinkActivity.this.relativeError.setVisibility(View.VISIBLE);
                    UtilsForApp.showToastMsg(PasteLinkActivity.this.getString(R.string.failed_to_download_please_use_try_again), PasteLinkActivity.this, true);
                } else if (status2 == 1) {
                    if (modelVideoLink != null) {
                        List<store_model_video_link.listVideos> linksLists = modelVideoLink.getLinksLists();
                        ArrayList arrayList2 = new ArrayList();
                        final CopyOnWriteArrayList<store_model_video_link.listVideos> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
                        if (linksLists != null && linksLists.size() > 0) {
                            for (int i = 0; i < linksLists.size(); i++) {
                                if (linksLists.get(i).getN_link_height().intValue() == 0) {
                                    copyOnWriteArrayList.add(0, linksLists.get(i));
                                } else if (!arrayList2.contains(linksLists.get(i).getN_link_height())) {
                                    arrayList2.add(linksLists.get(i).getN_link_height());
                                    copyOnWriteArrayList.add(0, linksLists.get(i));
                                }
                            }
                            if (copyOnWriteArrayList.size() > 0) {
                                MainActivityVideos.localDownloadLinks.put(PasteLinkActivity.this.StrEdtLink, copyOnWriteArrayList);
                                if (UtilsForApp.isNetworkAvailable(PasteLinkActivity.this)) {
                                    Intent intent = new Intent(PasteLinkActivity.this, ActivityVideoLink.class);
                                    intent.putExtra("d_url", PasteLinkActivity.this.StrEdtLink);
                                    intent.putExtra("d_title", ((store_model_video_link.listVideos) copyOnWriteArrayList.get(0)).getN_link_title());
                                    intent.putExtra("type_", "paste_link");
                                    PasteLinkActivity.this.startActivity(intent);

                                } else {
                                    UtilsForApp.showToastMsg(PasteLinkActivity.this.getString(R.string.no_internet), PasteLinkActivity.this, true);
                                }
                            } else {
                                PasteLinkActivity.this.relativeError.setVisibility(View.VISIBLE);
                                UtilsForApp.showToastMsg(PasteLinkActivity.this.getString(R.string.no_download_link_found_please_use_different_URL), PasteLinkActivity.this, true);
                            }
                        } else {
                            PasteLinkActivity.this.relativeError.setVisibility(View.VISIBLE);
                            UtilsForApp.showToastMsg(PasteLinkActivity.this.getString(R.string.no_download_link_found_please_use_different_URL), PasteLinkActivity.this, true);
                        }
                    } else {
                        PasteLinkActivity.this.relativeError.setVisibility(View.VISIBLE);
                        UtilsForApp.showToastMsg(PasteLinkActivity.this.getString(R.string.no_download_link_found_please_use_different_URL), PasteLinkActivity.this, true);
                    }
                } else if (status2 == 2) {
                    if (arrayList != null && arrayList.size() > 0) {
                        MainActivityVideos.localDownloadLinks.put(PasteLinkActivity.this.StrEdtLink, new CopyOnWriteArrayList<>(arrayList));
                        if (UtilsForApp.isNetworkAvailable(PasteLinkActivity.this)) {
                            Intent intent = new Intent(PasteLinkActivity.this, ActivityVideoLink.class);
                            intent.putExtra("d_url", PasteLinkActivity.this.StrEdtLink);
                            intent.putExtra("d_title", ((store_model_video_link.listVideos) arrayList.get(0)).getN_link_title());
                            intent.putExtra("type_", "paste_link");
                            PasteLinkActivity.this.startActivity(intent);
                        } else {
                            UtilsForApp.showToastMsg(PasteLinkActivity.this.getString(R.string.no_internet), PasteLinkActivity.this, true);
                        }
                    } else {
                        PasteLinkActivity.this.relativeError.setVisibility(View.VISIBLE);
                        UtilsForApp.showToastMsg(PasteLinkActivity.this.getString(R.string.no_download_link_found_please_use_different_URL), PasteLinkActivity.this, true);
                    }
                }
                if (PasteLinkActivity.this.isFinishing()) {
                    return;
                }
                PasteLinkActivity.this.relativeCheck.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paste_link);
        FetchXMLId();
        Delegate.pasteLinkActivity = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String other_link = extras.getString("other_link");
            String string = extras.getString("type");
            this.type = string;
            if (string == null) {
                this.type = "normal";
            }
            if (other_link == null) {
                other_link = "";
            }
            if (!other_link.isEmpty()) {
                this.edtLink.setText(other_link);
                this.edtLink.setSelection(0);
                this.btnDownload.startAnimation(AnimationUtils.loadAnimation(this, R.anim.cool_paste_anim));
            }
        }
        this.clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        this.btnPaste.setOnClickListener(view -> {
            ClipboardManager clipboardManager = this.clipboard;
            if (clipboardManager == null || !clipboardManager.hasPrimaryClip() || this.clipboard.getPrimaryClip() == null) {
                return;
            }
            this.edtLink.setText(this.clipboard.getPrimaryClip().getItemAt(0).coerceToText(this).toString().toString());
            this.edtLink.requestFocus();
        });
        this.imgBackPress.setOnClickListener(view -> {
            finish();
        });
        this.imgFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PasteLinkActivity.this.type.equalsIgnoreCase("fb")) {
                    try {
                        try {
                            PasteLinkActivity.this.startActivity(PasteLinkActivity.this.getPackageManager().getLaunchIntentForPackage("com.facebook.katana"));
                        } catch (Exception unused) {
                            PasteLinkActivity.this.startActivity(PasteLinkActivity.this.getPackageManager().getLaunchIntentForPackage("com.facebook.lite"));
                        }
                    } catch (Exception unused2) {
                        UtilsForApp.showToastMsg(PasteLinkActivity.this.getString(R.string.facebook_app_is_not_installed), PasteLinkActivity.this, false);
                    }
                } else if (PasteLinkActivity.this.type.equalsIgnoreCase("insta")) {
                    try {
                        try {
                            PasteLinkActivity.this.startActivity(PasteLinkActivity.this.getPackageManager().getLaunchIntentForPackage("com.instagram.android"));
                        } catch (Exception unused3) {
                            PasteLinkActivity.this.startActivity(PasteLinkActivity.this.getPackageManager().getLaunchIntentForPackage("com.instagram.lite"));
                        }
                    } catch (Exception unused4) {
                        UtilsForApp.showToastMsg(PasteLinkActivity.this.getString(R.string.Instagram_app_is_not_installed), PasteLinkActivity.this, false);
                    }
                } else {

                    PasteLinkActivity.this.startActivity(new Intent(PasteLinkActivity.this, Downloader_Creation_Activity.class));

                }
            }
        });
        this.btnDownload.setOnClickListener(view -> {
            if (this.edtLink.getText() != null) {
                this.StrEdtLink = this.edtLink.getText().toString().trim();
                UtilsForApp.hideKeyboard(this);
                if (this.StrEdtLink.isEmpty()) {
                    UtilsForApp.showToastMsg(getString(R.string.please_paste_URL_to_download), this, true);
                } else if (!UtilsForApp.IsValidUrl(this.StrEdtLink)) {
                    UtilsForApp.showToastMsg(getString(R.string.please_paste_valid_URL), this, true);
                } else if (!UtilsForApp.IsNonVideoURL(this.StrEdtLink) && !UtilsForApp.hasNonValidUrl(this.StrEdtLink)) {
                    if (UtilsForApp.isNetworkAvailable(this)) {
                        this.relativeCheck.setVisibility(View.VISIBLE);
                        this.relativeError.setVisibility(View.GONE);
                        doVideoLinkChecking();
                        return;
                    }
                    UtilsForApp.showToastMsg(getString(R.string.no_internet), this, true);
                } else {
                    UtilsForApp.showToastMsgLong(getString(R.string.video_downloader_app_does_not_support), this, true);
                }
            }
        });

        changeView();
    }

    @Override
    public void onDestroy() {

        UtilsForApp.cancelRetroCall();
        Delegate.pasteLinkActivity = null;
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

}
