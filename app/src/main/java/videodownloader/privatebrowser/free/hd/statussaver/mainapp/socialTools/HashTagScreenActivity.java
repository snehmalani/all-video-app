package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Database.DBHashTagsHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.store_model_hashtag;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter.adapter_hash_tag;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;

public class HashTagScreenActivity extends AdLoaderBase {
    public adapter_hash_tag adapterHashTag;
    public DBHashTagsHelper dbHashTagsHelper;
    public ImageView ivBack;
    public RecyclerView rvHashTag;
    public final ArrayList<store_model_hashtag> storeModelHashtags = new ArrayList<>();
    public TextView txtHeader;

    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int i = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_tag_screen);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.hashTagId, findViewById(R.id.banner_container));

        this.rvHashTag = (RecyclerView) findViewById(R.id.rvHashTag);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.txtHeader = (TextView) findViewById(R.id.txtHeader);
        this.dbHashTagsHelper = new DBHashTagsHelper(this);
        this.txtHeader.setText(getString(R.string.trending_hash_tag));
        try {
            this.dbHashTagsHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.rvHashTag.setLayoutManager(new GridLayoutManager(this, 3));
        this.rvHashTag.setHasFixedSize(true);
        @SuppressLint("Recycle") Cursor rawQuery = this.dbHashTagsHelper.openDatabase().rawQuery("SELECT * FROM Categories", null);
        if (rawQuery.moveToFirst()) {
            do {
                int columnIndex = rawQuery.getColumnIndex("name_en");
                int columnIndex2 = rawQuery.getColumnIndex("color");
                String string = rawQuery.getString(columnIndex);
                String string2 = rawQuery.getString(columnIndex2);
                if (!string.equals("Love")) {
                    this.storeModelHashtags.add(new store_model_hashtag(string, string2));
                }
            } while (rawQuery.moveToNext());
            for (i = 0; i < this.storeModelHashtags.size(); i++) {
                String lowerCase = this.storeModelHashtags.get(i).getTitle().trim().toLowerCase(Locale.ROOT);
                if (lowerCase.contains("&")) {
                    lowerCase = lowerCase.replace("&", "");
                }
                if (lowerCase.contains(StringUtils.SPACE)) {
                    lowerCase = lowerCase.replace(StringUtils.SPACE, "");
                }
                this.storeModelHashtags.get(i).setImage(lowerCase + ".webp");
            }
            adapter_hash_tag adapter_hash_tagVar = new adapter_hash_tag(this, this.storeModelHashtags);
            this.adapterHashTag = adapter_hash_tagVar;
            this.rvHashTag.setAdapter(adapter_hash_tagVar);
            this.adapterHashTag.setOnItemClickListener((position, tagName) -> {
//                AllInOneAds.getInstance().showInterWithId(this, AdHelper.hashTagId, () -> {
                    Intent intent = new Intent(HashTagScreenActivity.this, HashTagDetailsActivity.class);
                    intent.putExtra("CAT_NAME", tagName);
                    intent.putExtra("CAT_LOGO", HashTagScreenActivity.this.storeModelHashtags.get(position).getImage());
                    HashTagScreenActivity.this.startActivity(intent);
//                });
            });
            this.ivBack.setOnClickListener(v -> HashTagScreenActivity.this.finish());
        }
        while (i < this.storeModelHashtags.size()) {
        }
        adapter_hash_tag adapter_hash_tagVar2 = new adapter_hash_tag(this, this.storeModelHashtags);
        this.adapterHashTag = adapter_hash_tagVar2;
        this.rvHashTag.setAdapter(adapter_hash_tagVar2);
        this.adapterHashTag.setOnItemClickListener((position, tagName) -> {
//            AllInOneAds.getInstance().showInterWithId(this, AdHelper.hashTagId, () -> {
                Intent intent = new Intent(HashTagScreenActivity.this, HashTagDetailsActivity.class);
                intent.putExtra("CAT_NAME", tagName);
                intent.putExtra("CAT_LOGO", HashTagScreenActivity.this.storeModelHashtags.get(position).getImage());
                HashTagScreenActivity.this.startActivity(intent);
//            });
        });
        this.ivBack.setOnClickListener(v -> HashTagScreenActivity.this.finish());
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
