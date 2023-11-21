package videodownloader.privatebrowser.free.hd.statussaver.mainapp;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;


public class MK_WhtsappShowActivity extends AppCompatActivity {
    public static MK_WhtsappShowActivity mk_whtsappShowActivity;
    LinearLayout mkLayoutDownload, mkDownloadLL, mkLayoutDelete, mkProgressLayout, mkVideoLayout;
    ProgressBar mkpbar;
    TextView mkTxtName, mkTxtCurrent;
    int mkPosition, mkPos, mkCurrentPos, mkTextTotalDuration;
    String mkDelete;
    boolean mkaBoolean;
    ImageView mkImgPause;
    VideoView mkview;
    SeekBar mkSeekBar;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mk_whatsappshow);

        //nativeGDD.getInstance().ShowNativeADSGoogleFb(this, view.findViewById(R.id.banner_container));

        mkFindIdsClicks();

    }


    private void mkFindIdsClicks() {
        mk_whtsappShowActivity = this;

        mkTxtName = findViewById(R.id.txtname);

        mkview = findViewById(R.id.videoview);
        mkVideoLayout = findViewById(R.id.llvideo);
        mkProgressLayout = findViewById(R.id.llseek);
        mkSeekBar = findViewById(R.id.seekbar);
        mkTxtCurrent = findViewById(R.id.current);
        mkImgPause = findViewById(R.id.pause);


        findViewById(R.id.back).setOnClickListener(v -> {

            onBackPressed();
        });

        MKVideoPlay(getIntent().getStringExtra("path"));

        mkImgPause.setOnClickListener(v -> {
            if (mkview.isPlaying()) {
                mkview.pause();
                mkImgPause.setImageResource(R.drawable.ic_play_stick);
            } else {
                mkview.start();
                mkImgPause.setImageResource(R.drawable.baseline_pause_24);
            }
        });

    }

    public void MKCallSetVideoProgress() {
        mkCurrentPos = mkview.getCurrentPosition();
        mkTextTotalDuration = mkview.getDuration();
        ((TextView) findViewById(R.id.total)).setText(timeConversion(mkTextTotalDuration));
        mkTxtCurrent.setText(timeConversion(mkCurrentPos));
        mkSeekBar.setMax(mkTextTotalDuration);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mkCurrentPos = mkview.getCurrentPosition();
                    mkTxtCurrent.setText(timeConversion(mkCurrentPos));
                    mkSeekBar.setProgress(mkCurrentPos);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
        mkSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mkCurrentPos = seekBar.getProgress();
                mkview.seekTo(mkCurrentPos);
            }
        });
    }

    private void MKVideoPlay(String video_index) {
        try {
            mkview.setVideoURI(Uri.parse(video_index));
            mkview.start();
            mkImgPause.setImageResource(R.drawable.baseline_pause_24);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String timeConversion(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;
        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }

    @Override
    public void onBackPressed() {
        if (mkVideoLayout.getVisibility() == View.VISIBLE) {
            mkVideoLayout.setVisibility(View.GONE);
            mkview.setVisibility(View.GONE);
            mkLayoutDownload.setVisibility(View.VISIBLE);
            mkProgressLayout.setVisibility(View.GONE);
            if (mkview.isPlaying()) {
                mkview.stopPlayback();
            }
        } else {
            AllInOneAds.getInstance().showBackInter(this, () -> finish());
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mkview.getVisibility() == View.VISIBLE) {
            mkview.stopPlayback();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mkview.getVisibility() == View.VISIBLE) {
            mkImgPause.setImageResource(R.drawable.baseline_pause_24);
            mkview.start();
        }
    }
}