package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;


public class Downloader_Creation_Activity extends AppCompatActivity {
    public final ArrayList<String> strings = new ArrayList<>();
    GridView gridView;
    MyPhotosAdapter adapter;
    TextView mnoimages;


    public class MyPhotosAdapter extends BaseAdapter {
        final Activity mActivity;
        final ArrayList<String> stringArrayList1;
        final LayoutInflater inflater;

        public class Holder {
            ImageView imageView;
            ImageView imgDelete;
            ImageView imgShare, playicon;
        }

        public MyPhotosAdapter(Activity activity, ArrayList<String> list) {
            this.mActivity = activity;
            this.stringArrayList1 = list;
            this.inflater = LayoutInflater.from(activity);
        }

        public int getCount() {
            return stringArrayList1.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ResourceType")
        public View getView(final int position, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.adapter_myphoto, null, false);
            Holder holder = new Holder();
            holder.imageView = view.findViewById(R.id.imageView);
            holder.imgDelete = view.findViewById(R.id.imgDelete);
            holder.imgShare = view.findViewById(R.id.imgShare);
            holder.playicon = view.findViewById(R.id.playicon);

            if (stringArrayList1.get(position).contains(".jpg") || stringArrayList1.get(position).contains(".png") || stringArrayList1.get(position).contains(".webp")) {
                holder.playicon.setVisibility(GONE);
            } else if (stringArrayList1.get(position).contains(".php")) {
                holder.playicon.setVisibility(GONE);
            } else {
                holder.playicon.setVisibility(VISIBLE);
            }

            Glide.with(mActivity).load(stringArrayList1.get(position)).into(holder.imageView);

            holder.imageView.setOnClickListener(view1 -> {

                AllInOneAds.getInstance().showInterWithId(Downloader_Creation_Activity.this, AdHelper.allVideoDownloadId, () -> {
                    Intent intent = new Intent(mActivity, Downloader_ViewMultipleImagePager_Activity.class);
                    intent.putExtra("arraylist", stringArrayList1);
                    intent.putExtra("pos", position);
                    mActivity.startActivity(intent);
                });


            });
            holder.imgShare.setOnClickListener(v -> {
                Uri imageUri = FileProvider.getUriForFile(Downloader_Creation_Activity.this, mActivity.getPackageName() + ".provider", new File(stringArrayList1.get(position)));
                try {
                    String mimetype = getShareType(stringArrayList1.get(position));
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType(mimetype);
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            holder.imgDelete.setOnClickListener(view12 -> {
                Builder alertDialogbuilder = new Builder(mActivity);
                alertDialogbuilder.setMessage(R.string.collage_lib_delete_message);
                alertDialogbuilder.setPositiveButton(R.string.yes, (dialog, which) -> {
                    File file = new File(stringArrayList1.get(position));
                    if (file.exists()) {
                        file.delete();
                        callBroadCast(String.valueOf(file));

                    }
                    stringArrayList1.remove(position);
                    notifyDataSetChanged();
                    if (stringArrayList1.size() == 0) {
                        mnoimages.setVisibility(VISIBLE);
                    }
                    Snackbar.make(findViewById(16908290), mActivity.getString(R.string.photo_delete), -1).show();
                });
                alertDialogbuilder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
                alertDialogbuilder.setCancelable(false);
                if (!isFinishing()) {
                    alertDialogbuilder.show();
                }
            });
            return view;
        }

        public void callBroadCast(String path) {
            MediaScannerConnection.scanFile(mActivity, new String[]{path}, null, (path1, uri) -> {
            });
        }
    }

    public static String getShareType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photo);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.allVideoDownloadId, findViewById(R.id.banner_container));

        FindIds();
        ((TextView) findViewById(R.id.txtfoldername)).setText("My Downloads");

        new Handler().postDelayed((Runnable) () -> getFolderImages(), 100);
    }


    private void FindIds() {
        gridView = findViewById(R.id.gridView);
        mnoimages = findViewById(R.id.no_data);
        gridView.setEmptyView(mnoimages);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
    }

    protected void onResume() {
        super.onResume();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return false;
    }

    public void getFolderImages() {
        try {
            if (strings != null) {
                strings.clear();
            }
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Download/Video Downloader" + "/");

            if (file.isDirectory()) {
                File[] listFile = file.listFiles();
                Arrays.sort(Objects.requireNonNull(listFile), (f1, f2) -> Long.valueOf(f2.lastModified()).compareTo(f1.lastModified()));
                for (File value : Objects.requireNonNull(file.listFiles(new FileExtensionFilter()))) {

                    Objects.requireNonNull(strings).add(value.getAbsolutePath());
                }
                adapter = new MyPhotosAdapter(this, strings);
                gridView.setAdapter(adapter);
            }
        } catch (Exception ignored) {

        }
    }
    static class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".jpg") || name.endsWith(".JPG") || name.endsWith(".mp4") || name.endsWith(".MP4")|| name.endsWith(".png")|| name.endsWith(".PNG")|| name.endsWith(".webp")|| name.endsWith(".WEBP"));
        }
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }

}
