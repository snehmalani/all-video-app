package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import static videodownloader.privatebrowser.free.hd.statussaver.mainapp.Downloader_Creation_Activity.getShareType;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;


public class Downloader_ViewMultipleImagePager_Activity extends AppCompatActivity {
    ViewPager pager;
    ArrayList<String> strings = new ArrayList<>();
    int mPos = 0;
    TextView mPhotoName, mArraySize;


    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_slider_layout);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.allVideoDownloadId, findViewById(R.id.banner_container));

        FindIds();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void FindIds() {
        pager = findViewById(R.id.vp);
        mPhotoName = findViewById(R.id.text_name);
        mArraySize = findViewById(R.id.total);
        strings = getIntent().getStringArrayListExtra("arraylist");
        mPos = getIntent().getIntExtra("pos", 0);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.share).setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType(getShareType(strings.get(mPos)));
            File fileToShare = new File(strings.get(mPos));
            Uri imageUri = FileProvider.getUriForFile(Downloader_ViewMultipleImagePager_Activity.this, getPackageName() + ".provider", fileToShare);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Get more status with this app \nDownload Now \nhttps://play.google.com/store/apps/details?id=" + getPackageName());
            sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });
        mPhotoName.setText(new File(strings.get(mPos)).getName());
        AdapterPager pagerAdapetr = new AdapterPager(strings, this, getSupportFragmentManager());
        pager.setAdapter(pagerAdapetr);
        pager.setCurrentItem(mPos);
        mArraySize.setText(mPos + "/" + strings.size());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPhotoName.setText(new File(strings.get(position)).getName());
                mArraySize.setText((position + 1) + " / " + strings.size());
                mPos = position;
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public static class AdapterPager extends PagerAdapter {
        public final ArrayList<String> allImages;
        public final Context animeContx;
        final FragmentManager manager;

        public AdapterPager(ArrayList<String> allImages, Context full_activity, FragmentManager manager) {
            this.allImages = allImages;
            this.animeContx = full_activity;
            this.manager = manager;
        }

        @Override
        public int getCount() {
            return allImages.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup containerCollection, final int position) {
            ImageView image, playbtn;
            RelativeLayout rrmain;
            LayoutInflater layoutinflater = (LayoutInflater) containerCollection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutinflater.inflate(R.layout.item_pager, containerCollection, false);
            image = view.findViewById(R.id.img);
            rrmain = view.findViewById(R.id.rrmain);
            playbtn = view.findViewById(R.id.playbtn);
            containerCollection.addView(view);
            if (allImages.get(position).contains("mp4")) {
                playbtn.setVisibility(View.VISIBLE);
            } else if (allImages.get(position).contains("mp3")) {
                playbtn.setVisibility(View.VISIBLE);
            } else {
                playbtn.setVisibility(View.GONE);
            }
            if (allImages.get(position).contains(".mp3")) {
                Glide.with(animeContx).load(animeContx.getResources().getDrawable(R.drawable.files_place_holder)).apply(new RequestOptions().fitCenter()).into(image);
            } else {
                Glide.with(animeContx).load(allImages.get(position)).apply(new RequestOptions().fitCenter()).into(image);
            }
            rrmain.setOnClickListener(v -> {
                if (allImages.get(position).contains("mp4")) {
                    if (allImages.size() != 0) {
                        Intent intent = new Intent(animeContx, Downloader_PlayerVideo_Activity.class);
                        intent.putExtra("path", allImages.get(position));
                        intent.putExtra("file_name", new File(allImages.get(position)).getName());
                        intent.putExtra("location", "downloads");

                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        animeContx.startActivity(intent);
                    }
                } else if (allImages.get(position).contains("mp3")) {
                    if (allImages.size() != 0) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        File file = new File(allImages.get(position));
                        Uri uri = FileProvider.getUriForFile(animeContx, animeContx.getPackageName() + ".provider", file);
                        intent.setDataAndType(uri, "audio/*");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        animeContx.startActivity(intent);
                    }
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup containerCollection, int position, @NonNull Object view) {
            (containerCollection).removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == (object);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            int index = allImages.indexOf(object);
            if (index == -1) {
                return PagerAdapter.POSITION_NONE;
            } else {
                return index;
            }
        }
    }

}