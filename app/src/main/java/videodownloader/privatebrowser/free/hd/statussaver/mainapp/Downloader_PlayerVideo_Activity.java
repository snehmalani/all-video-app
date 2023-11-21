package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import static videodownloader.privatebrowser.free.hd.statussaver.mainapp.Downloader_Creation_Activity.getShareType;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.ConstantForApp;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

public class Downloader_PlayerVideo_Activity extends AppCompatActivity {
    ImageView download,share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);
        FindIdsAndClicks();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    void FindIdsAndClicks() {
        download = findViewById(R.id.download);

        share = findViewById(R.id.share);

        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        try {
            String path = getIntent().getStringExtra("path");
            String name = getIntent().getStringExtra("file_name");
            ((TextView) findViewById(R.id.titletext)).setText(name);

            if (getIntent().getStringExtra("location").equals("downloads")){
                download.setVisibility(View.GONE);
            }else {
                download.setVisibility(View.VISIBLE);
            }
            if (getIntent().getStringExtra("location" ).equals("whatsapp")){
                share.setVisibility(View.GONE);
            }

            share.setOnClickListener(v -> {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType(getShareType(path));
                File fileToShare = new File(path);
                Uri imageUri = FileProvider.getUriForFile(Downloader_PlayerVideo_Activity.this, getPackageName() + ".provider", fileToShare);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Get more status with this app \nDownload Now \nhttps://play.google.com/store/apps/details?id=" + getPackageName());
                sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            });

            download.setOnClickListener(v -> {
                UtilsForApp.sendFileInDownloadFolder(this, name, Uri.parse(path), true, -1, false, (status, position, newPath) -> {
                    if (Downloader_PlayerVideo_Activity.this.isFinishing()) {
                        return;
                    }
                    if (status == 1) {
                        Downloader_PlayerVideo_Activity.this.download.setVisibility(View.GONE);
                        UtilsForApp.showToastMsg(getResources().getString(R.string.status_saved), Downloader_PlayerVideo_Activity.this, false);
                        return;
                    }
                    UtilsForApp.showToastMsg(getString(R.string.failed_to_save_status), Downloader_PlayerVideo_Activity.this, false);
                });

            });

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
            sb.append(name);
            File file = new File(sb.toString());
            if (file.exists() && file.isFile()) {
                download.setVisibility(View.GONE);
            } else if (getIntent().getStringExtra("location").equals("whatsapp")) {
                download.setVisibility(View.VISIBLE);
            }


            if (!TextUtils.isEmpty(path)) {
                VideoView videoplayer = findViewById(R.id.videoplayer);
                videoplayer.setVideoURI(Uri.parse(path));
                videoplayer.start();

            } else {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}