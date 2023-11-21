package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;
import videodownloader.privatebrowser.free.hd.statussaver.tool.ConstantForApp;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

public class ClassImageViewer extends AdLoaderBase implements View.OnClickListener {
    private String file_name;
    private String file_uri;
    public ImageView imgBack;
    public ImageView imgMainImage;
    public ImageView imgSave;
    public ImageView imgShare;
    public RelativeLayout realtiveShare;
    public RelativeLayout realtiveShave;
    private String type_;

    private void init() {
        this.imgBack = (ImageView) findViewById(R.id.imgBack);
        this.imgMainImage = (ImageView) findViewById(R.id.imgMainImage);
        this.realtiveShare = (RelativeLayout) findViewById(R.id.realtiveShare);
        this.realtiveShave = (RelativeLayout) findViewById(R.id.realtiveShave);
        this.imgShare = (ImageView) findViewById(R.id.imgShare);
        this.imgSave = (ImageView) findViewById(R.id.imgSave);
        this.imgBack.setOnClickListener(this);
        this.imgShare.setVisibility(View.GONE);
        this.realtiveShare.setOnClickListener(this);
        this.imgShare.setOnClickListener(this);
        this.realtiveShave.setOnClickListener(this);
        this.imgSave.setOnClickListener(this);
    }




    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.realtiveShare || v.getId() == R.id.imgShare) {
            try {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");
                intent.putExtra("android.intent.extra.STREAM", Uri.parse(this.file_uri));
                intent.putExtra("android.intent.extra.TEXT", this.file_name);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setClipData(ClipData.newUri(getContentResolver(), getString(R.string.app_name), Uri.parse(this.file_uri)));
                startActivity(Intent.createChooser(intent, getString(R.string.share)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (v.getId() != R.id.realtiveShave && v.getId() != R.id.imgSave) {
                if (v.getId() == R.id.imgBack) {
                    onBackPressed();
                    return;
                }
                return;
            }
            AllInOneAds.getInstance().showInterWithId(this, AdHelper.whatsappId, () -> {

                UtilsForApp.sendFileInDownloadFolder(this, this.file_name, Uri.parse(this.file_uri), true, -1, false, (status, position, newPath) -> {
                    if (ClassImageViewer.this.isFinishing()) {
                        return;
                    }
                    if (status == 1) {
                        ClassImageViewer.this.realtiveShave.setVisibility(View.GONE);
                        UtilsForApp.showToastMsg(ClassImageViewer.this.getResources().getString(R.string.status_saved), ClassImageViewer.this, false);
                        return;
                    }
                    UtilsForApp.showToastMsg(ClassImageViewer.this.getString(R.string.failed_to_save_status), ClassImageViewer.this, false);
                });
            });
            return;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vid_viewer_activity);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.whatsappId, findViewById(R.id.banner_container));

        init();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.file_uri = extras.getString("file_uri");
            this.file_name = extras.getString("file_name");
            this.type_ = extras.getString("type_");
        }
        if (Objects.requireNonNull(this.type_).equalsIgnoreCase("status")) {
            this.realtiveShave.setVisibility(View.VISIBLE);
        } else {
            this.realtiveShave.setVisibility(View.GONE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().toString());
        String str = File.separator;
        sb.append(str);
        sb.append("Download");
        sb.append(str);
        sb.append(ConstantForApp.VID_DOWN_PATH);
        sb.append("/");
        sb.append(ConstantForApp.MY_STATUS_PATH);
        sb.append("/");
        sb.append(this.file_name);
        File file = new File(sb.toString());
        if (file.exists() && file.isFile()) {
            realtiveShave.setVisibility(View.GONE);
            realtiveShare.setVisibility(View.VISIBLE);
        } else if (this.type_.equalsIgnoreCase("status")) {
            this.realtiveShave.setVisibility(View.VISIBLE);
        }
        Glide.with((FragmentActivity) this).load(this.file_uri).into(this.imgMainImage);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
