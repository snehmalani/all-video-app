package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.ModelDataBio;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter.AdapterSubBio;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;

public class BioSubCategoryActivity extends AdLoaderBase {
    public AdapterSubBio adapterSubBio;
    public int catId;
    public ImageView ivBack;
    public ImageView ivSubBio;
    public RelativeLayout relTop;
    public RecyclerView rvSubCategoryBio;
    public TextView txtHeader;
    public ArrayList<ModelDataBio> modelDataBios = new ArrayList<>();
    public final ArrayList<String> stringArrayList = new ArrayList<>();

    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }

    public class ClickListner implements AdapterSubBio.OnItemClickListener {


        public ClickListner() {
        }


        @Override
        public void onItemClick(int position, final String tagName, int type_) {
            if (type_ == 0) {
                ((ClipboardManager) BioSubCategoryActivity.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Copy", tagName));
                Toast.makeText(BioSubCategoryActivity.this, (int) R.string.bio_copy, Toast.LENGTH_SHORT).show();
            } else if (type_ != 1) {
                AllInOneAds.getInstance().showInterWithId(BioSubCategoryActivity.this, AdHelper.bioId, () -> {
                    if (type_ == 2) {
                        Intent intent = new Intent(BioSubCategoryActivity.this, BioEditShareActivity.class);
                        intent.putExtra("IS_FROM_EDIT", true);
                        intent.putExtra("SHARE_TEXT", tagName);
                        BioSubCategoryActivity.this.startActivity(intent);
                    } else if (type_ == 3) {
                        Intent intent = new Intent(BioSubCategoryActivity.this, BioEditShareActivity.class);
                        intent.putExtra("SHARE_TEXT", tagName);
                        BioSubCategoryActivity.this.startActivity(intent);
                    }
                });
            } else {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", tagName);
                try {
                    BioSubCategoryActivity.this.startActivity(Intent.createChooser(intent, "Share Using"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setData() {
        try {
            InputStream open = getAssets().open("bios_data.json");
            int available = open.available();
            byte[] bArr = new byte[available];
            open.read(bArr);
            open.close();
            if (available > 0) {
                this.modelDataBios = (ArrayList) new Gson().fromJson(new String(bArr), new TypeToken<ArrayList<ModelDataBio>>() {

                }.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < this.modelDataBios.size(); i++) {
            if (this.modelDataBios.get(i).getCatagryId() == this.catId) {
                this.stringArrayList.add(this.modelDataBios.get(i).getData());
            }
        }
        this.rvSubCategoryBio.setHasFixedSize(true);
        this.rvSubCategoryBio.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        this.rvSubCategoryBio.setNestedScrollingEnabled(false);
        AdapterSubBio adapterSubBio = new AdapterSubBio(this, this.stringArrayList);
        this.adapterSubBio = adapterSubBio;
        this.rvSubCategoryBio.setAdapter(adapterSubBio);
        this.adapterSubBio.setOnItemClickListener(new ClickListner());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_sub_category);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.captionId, findViewById(R.id.banner_container));

        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.txtHeader = (TextView) findViewById(R.id.txtHeader);
        this.rvSubCategoryBio = (RecyclerView) findViewById(R.id.rvSubCategoryBio);
        this.ivSubBio = (ImageView) findViewById(R.id.ivSubBio);
        this.relTop = (RelativeLayout) findViewById(R.id.relTop);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.catId = extras.getInt("DATA_MODEL");
            this.txtHeader.setText(extras.getString("TITLE"));
            getWindow().setStatusBarColor(Color.parseColor(extras.getString("COLOR")));
            this.relTop.setBackgroundColor(Color.parseColor(extras.getString("COLOR")));
            try {
                InputStream open = getAssets().open(Objects.requireNonNull(extras.getString("IMAGE")));
                this.ivSubBio.setImageDrawable(Drawable.createFromStream(open, null));
                open.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            finish();
        }

        setData();
        this.ivBack.setOnClickListener(v -> BioSubCategoryActivity.this.finish());
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
