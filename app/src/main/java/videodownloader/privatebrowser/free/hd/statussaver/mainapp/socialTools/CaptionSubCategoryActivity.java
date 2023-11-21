package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.Caption;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.ModelCaption;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter.AdapterCaptionSubCat;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter.AdapterListCaption;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.network.ApiConnectionClass;
import videodownloader.privatebrowser.free.hd.statussaver.network.HttpApis;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaptionSubCategoryActivity extends AdLoaderBase {

    public String Title;
    public AdapterListCaption adapterListCaption;
    public ImageView ivBack;
    public LinearLayout linNoData;
    public LinearLayout linShowProgress;
    public ModelCaption modelCaption;
    public RecyclerView rvCaption;
    public RecyclerView rvCaptionSubCategory;
    public TextView txtHeader;

    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());
    }

    public void noData() {
        this.linShowProgress.setVisibility(View.GONE);
        this.linNoData.setVisibility(View.VISIBLE);
        this.rvCaption.setVisibility(View.GONE);
    }

    public void useAPI(int pos) {
        this.linShowProgress.setVisibility(View.VISIBLE);
        this.linNoData.setVisibility(View.GONE);
        this.rvCaption.setVisibility(View.GONE);
        Call<Caption> listRepos = ((HttpApis) ApiConnectionClass.getRetrofitInstance().create(HttpApis.class)).listRepos(pos);
        listRepos.enqueue(new Callback<Caption>() {

            @Override
            public void onFailure(@NonNull Call<Caption> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    return;
                }
                t.printStackTrace();
                CaptionSubCategoryActivity.this.noData();
            }

            @Override
            public void onResponse(@NonNull Call<Caption> call, @NonNull Response<Caption> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getNewsubcategories() != null && response.body().getNewsubcategories().size() > 0) {
                                CaptionSubCategoryActivity.this.linShowProgress.setVisibility(View.GONE);
                                CaptionSubCategoryActivity.this.linNoData.setVisibility(View.GONE);
                                CaptionSubCategoryActivity.this.rvCaption.setVisibility(View.VISIBLE);
                                CaptionSubCategoryActivity.this.rvCaption.setHasFixedSize(true);
                                CaptionSubCategoryActivity captionSubCategoryActivity = CaptionSubCategoryActivity.this;
                                captionSubCategoryActivity.rvCaption.setLayoutManager(new LinearLayoutManager(captionSubCategoryActivity, RecyclerView.VERTICAL, false));
                                CaptionSubCategoryActivity.this.rvCaption.setNestedScrollingEnabled(false);
                                CaptionSubCategoryActivity captionSubCategoryActivity2 = CaptionSubCategoryActivity.this;
                                captionSubCategoryActivity2.adapterListCaption = new AdapterListCaption(captionSubCategoryActivity2, response.body().getNewsubcategories());
                                CaptionSubCategoryActivity captionSubCategoryActivity3 = CaptionSubCategoryActivity.this;

//                                GoogleNativeAdAdapter googleAdapter = GoogleNativeAdAdapter.Builder.with(CaptionSubCategoryActivity.this,AdHelper.captionId,captionSubCategoryActivity3.adapterListCaption).adItemInterval(8).build();

                                captionSubCategoryActivity3.rvCaption.setAdapter(captionSubCategoryActivity3.adapterListCaption);

                                CaptionSubCategoryActivity.this.adapterListCaption.setOnItemClickListener(new AdapterListCaption.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position, String captionText, int type) {
                                        if (type == 0) {
                                            ((ClipboardManager) CaptionSubCategoryActivity.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Caption", captionText));
                                            Toast.makeText(CaptionSubCategoryActivity.this, (int) R.string.caption_copy, Toast.LENGTH_SHORT).show();
                                        } else if (type == 1) {
                                            Intent intent = new Intent();
                                            intent.setAction("android.intent.action.SEND");
                                            intent.setPackage("com.whatsapp");
                                            intent.putExtra("android.intent.extra.TEXT", captionText);
                                            intent.setType("text/plain");
                                            try {
                                                CaptionSubCategoryActivity.this.startActivity(Intent.createChooser(intent, "Select an app to share"));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else if (type != 2) {
                                            if (type == 3) {
                                                AllInOneAds.getInstance().showInterWithId(CaptionSubCategoryActivity.this, AdHelper.captionId, () -> {
                                                    Intent intent = new Intent(CaptionSubCategoryActivity.this, CaptionShareActivity.class);
                                                    intent.putExtra("SHARE_DATA", captionText);
                                                    CaptionSubCategoryActivity.this.startActivity(intent);
                                                });
                                            }
                                        } else {
                                            Intent intent2 = new Intent("android.intent.action.SEND");
                                            intent2.setType("text/plain");
                                            intent2.putExtra("android.intent.extra.TEXT", captionText);
                                            try {
                                                CaptionSubCategoryActivity.this.startActivity(Intent.createChooser(intent2, "Share Using"));
                                            } catch (Exception e2) {
                                                e2.printStackTrace();
                                            }
                                        }
                                    }
                                });
                            } else {
                                CaptionSubCategoryActivity.this.noData();
                            }
                        } else {
                            CaptionSubCategoryActivity.this.noData();
                        }
                    } else {
                        CaptionSubCategoryActivity.this.noData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption_sub_category);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.captionId, findViewById(R.id.banner_container));

        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.txtHeader = (TextView) findViewById(R.id.txtHeader);
        this.rvCaptionSubCategory = (RecyclerView) findViewById(R.id.rvCaptionSubCategory);
        this.rvCaption = (RecyclerView) findViewById(R.id.rvCaption);
        this.linShowProgress = (LinearLayout) findViewById(R.id.linShowProgress);
        this.linNoData = (LinearLayout) findViewById(R.id.linNoData);
        this.linShowProgress.setVisibility(View.VISIBLE);
        this.linNoData.setVisibility(View.GONE);
        this.rvCaption.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.modelCaption = (ModelCaption) extras.getSerializable("DATA_MODEL");
            String string = extras.getString("TITLE");
            this.Title = string;
            this.txtHeader.setText(string);
        }

        final AdapterCaptionSubCat adapterCaptionSubCat = new AdapterCaptionSubCat(this, this.modelCaption.getNewsubcategories());
        this.rvCaptionSubCategory.setHasFixedSize(true);
        this.rvCaptionSubCategory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.rvCaptionSubCategory.setAdapter(adapterCaptionSubCat);
        if (!this.modelCaption.getNewsubcategories().isEmpty()) {
            adapterCaptionSubCat.setSelected(0);
            useAPI(this.modelCaption.getNewsubcategories().get(0).getId());
        }
        adapterCaptionSubCat.setOnClickListener(position -> {
            adapterCaptionSubCat.setSelected(position);
            CaptionSubCategoryActivity captionSubCategoryActivity = CaptionSubCategoryActivity.this;
            captionSubCategoryActivity.useAPI(captionSubCategoryActivity.modelCaption.getNewsubcategories().get(position).getId());
        });
        this.ivBack.setOnClickListener(v -> CaptionSubCategoryActivity.this.finish());
    }

}
