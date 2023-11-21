package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.ModelCaption;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter.AdapterCaption;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;

public class CaptionScreenActivity extends AdLoaderBase {
    public AdapterCaption adapterCaption;
    public ImageView ivBack;
    public ArrayList<ModelCaption> modelCaptions = new ArrayList<>();
    public RecyclerView rvCaption;


    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption_screen);


        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.captionId, findViewById(R.id.banner_container));

        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        rvCaption = (RecyclerView) findViewById(R.id.rvCaption);
        rvCaption.setHasFixedSize(true);
        this.rvCaption.setLayoutManager(new LinearLayoutManager(this));
        this.rvCaption.setNestedScrollingEnabled(false);
        try {
            InputStream open = getAssets().open("main-cat.json");
            int available = open.available();
            byte[] bArr = new byte[available];
            open.read(bArr);
            open.close();
            if (available > 0) {
                this.modelCaptions = (ArrayList) new Gson().fromJson(new String(bArr), new TypeToken<ArrayList<ModelCaption>>() {
                }.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < this.modelCaptions.size(); i++) {
            String lowerCase = this.modelCaptions.get(i).getName().trim().toLowerCase(Locale.ROOT);
            if (lowerCase.contains("&")) {
                lowerCase = lowerCase.replace("&", "");
            }
            if (lowerCase.contains(StringUtils.SPACE)) {
                lowerCase = lowerCase.replace(StringUtils.SPACE, "");
            }
            this.modelCaptions.get(i).setIamge(lowerCase + ".webp");
        }
        AdapterCaption adapterCaption = new AdapterCaption(this, this.modelCaptions);
        this.adapterCaption = adapterCaption;

        this.rvCaption.setAdapter(adapterCaption);
        this.adapterCaption.setOnItemClickListener((position, tagName) -> {

            AllInOneAds.getInstance().showInterWithId(this, AdHelper.captionId, () -> {
                Intent intent = new Intent(CaptionScreenActivity.this, CaptionSubCategoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DATA_MODEL", CaptionScreenActivity.this.modelCaptions.get(position));
                bundle.putString("TITLE", tagName);
                intent.putExtras(bundle);
                CaptionScreenActivity.this.startActivity(intent);
            });
        });
        this.ivBack.setOnClickListener(v -> CaptionScreenActivity.this.finish());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
