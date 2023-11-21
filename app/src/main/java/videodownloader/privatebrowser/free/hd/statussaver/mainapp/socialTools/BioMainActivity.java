package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.ModelBio;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter.AdapterMainBio;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;

public class BioMainActivity extends AdLoaderBase {
    public AdapterMainBio adapterMainBio;
    public ImageView ivBack;
    public ArrayList<ModelBio> modelBios = new ArrayList<>();
    public RecyclerView rvMainBio;


    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }
    private void setData() {
        this.rvMainBio.setHasFixedSize(true);
        this.rvMainBio.setLayoutManager(new GridLayoutManager(this, 2));
        this.rvMainBio.setNestedScrollingEnabled(false);
        try {
            InputStream open = getAssets().open("bios_catagery.json");
            int available = open.available();
            byte[] bArr = new byte[available];
            open.read(bArr);
            open.close();
            if (available > 0) {
                this.modelBios = (ArrayList) new Gson().fromJson(new String(bArr), new TypeToken<ArrayList<ModelBio>>() {
                }.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdapterMainBio adapterMainBio = new AdapterMainBio(this, this.modelBios);
        this.adapterMainBio = adapterMainBio;
        this.rvMainBio.setAdapter(adapterMainBio);
        this.adapterMainBio.setOnItemClickListener((position, tagName, image, color) -> {
            AllInOneAds.getInstance().showInterWithId(this, AdHelper.bioId, () -> {
                Intent intent = new Intent(BioMainActivity.this, BioSubCategoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("DATA_MODEL", BioMainActivity.this.modelBios.get(position).getCatagryId().intValue());
                bundle.putString("TITLE", tagName);
                bundle.putString("COLOR", color);
                bundle.putString("IMAGE", image);
                intent.putExtras(bundle);
                BioMainActivity.this.startActivity(intent);
            });
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_min);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.captionId, findViewById(R.id.banner_container));


        this.rvMainBio = (RecyclerView) findViewById(R.id.rvMainBio);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        setData();

        this.ivBack.setOnClickListener(v -> BioMainActivity.this.finish());
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
