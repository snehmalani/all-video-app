package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Database.DBHashTagsHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.store_model_tag;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter.adapter_hash_details;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;

public class HashTagDetailsActivity extends AdLoaderBase {

    public adapter_hash_details adapterHashDetails;
    public DBHashTagsHelper dbHashTagsHelper;
    public ImageView ivBack;
    public ImageView ivCatLogo;
    public RecyclerView rvHashTagDetails;
    public final ArrayList<store_model_tag> storeModelTags = new ArrayList<>();
    public TextView txtHeader;

    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_tag_details);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.hashTagId, findViewById(R.id.banner_container));

        this.rvHashTagDetails = (RecyclerView) findViewById(R.id.rvHashTagDetails);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.ivCatLogo = (ImageView) findViewById(R.id.ivCatLogo);
        this.txtHeader = (TextView) findViewById(R.id.txtHeader);
        try {
            InputStream open = getAssets().open(Objects.requireNonNull(getIntent().getStringExtra("CAT_LOGO")));
            this.ivCatLogo.setImageDrawable(Drawable.createFromStream(open, null));
            open.close();

            this.dbHashTagsHelper = new DBHashTagsHelper(this);
            this.rvHashTagDetails.setHasFixedSize(true);
            this.rvHashTagDetails.setLayoutManager(new GridLayoutManager(this, 1));
            this.rvHashTagDetails.setNestedScrollingEnabled(false);
            String stringExtra = getIntent().getStringExtra("CAT_NAME");
            this.txtHeader.setText(stringExtra);
            Cursor rawQuery = this.dbHashTagsHelper.openDatabase().rawQuery("select * from Collections where  category_name like '" + stringExtra + "'", null);
            if (rawQuery.moveToFirst()) {
                do {
                    int columnIndex = rawQuery.getColumnIndex("name_en");
                    int columnIndex2 = rawQuery.getColumnIndex("hashtags_en");
                    String string = rawQuery.getString(columnIndex);
                    String string2 = rawQuery.getString(columnIndex2);
                    if (string2 != null && string2.trim().length() > 0) {
                        String[] split = string2.split("#");
                        ArrayList arrayList = new ArrayList();
                        for (String str : split) {
                            if (!str.equals("")) {
                                arrayList.add("#" + str);
                            }
                        }
                        this.storeModelTags.add(new store_model_tag(string, string2, arrayList));
                    }
                } while (rawQuery.moveToNext());
            } else {
                rawQuery.close();
            }
            adapter_hash_details adapter_hash_detailsVar = new adapter_hash_details(this, this.storeModelTags);
            this.adapterHashDetails = adapter_hash_detailsVar;
            this.rvHashTagDetails.setAdapter(adapter_hash_detailsVar);
            this.adapterHashDetails.setOnItemClickListener(new adapter_hash_details.OnItemClickListener() {
                @Override
                public void onItemClick(int position, String allStrind, String tagName, ArrayList<String> tagList) {
                    AllInOneAds.getInstance().showInterWithId(HashTagDetailsActivity.this, AdHelper.hashTagId, () -> {
                        Intent intent = new Intent(HashTagDetailsActivity.this, HashTagShareCopyActivity.class);
                        intent.putStringArrayListExtra("TAG_LIST", tagList);
                        intent.putExtra("TAG_NAME", tagName);
                        intent.putExtra("SHARE_ALL", allStrind);
                        HashTagDetailsActivity.this.startActivity(intent);
                    });
                }
            });
            this.ivBack.setOnClickListener(v -> HashTagDetailsActivity.this.finish());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
