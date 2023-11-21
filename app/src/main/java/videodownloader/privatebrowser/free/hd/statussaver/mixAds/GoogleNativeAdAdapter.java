package videodownloader.privatebrowser.free.hd.statussaver.mixAds;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.NativeAdLayout;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;

import videodownloader.privatebrowser.free.hd.statussaver.R;


//GoogleNativeAdAdapter.Builder.with(getActivity(),"",downloadListAdapter).adItemInterval(6).enableSpanRow(gridLayoutManager).build()
// GoogleNativeAdAdapter googleAdapter = GoogleNativeAdAdapter.Builder.with(getActivity(),getString(R.string.nativeid),mVoiceAdapter).adItemInterval(2).build();
public class GoogleNativeAdAdapter extends RecyclerViewAdapterWrapper {

    private static final int TYPE_FB_NATIVE_ADS = 900;
    private static final int DEFAULT_AD_ITEM_INTERVAL = 3;
    static Activity mContext;
    private final Param mParam;
    LinearLayout frameLayout;
    private NativeAd nativeAdmain;

    private GoogleNativeAdAdapter(Param param) {
        super(param.adapter);
        this.mParam = param;

        assertConfig();
        setSpanAds();
    }

    private void assertConfig() {
        if (mParam.gridLayoutManager != null) {
            //if user set span ads
            int nCol = mParam.gridLayoutManager.getSpanCount();
            if (mParam.adItemInterval % nCol != 0) {
                throw new IllegalArgumentException(String.format("The adItemInterval (%d) is not divisible by number of columns in GridLayoutManager (%d)", mParam.adItemInterval, nCol));
            }
        }
    }

    private int convertAdPosition2OrgPosition(int position) {
        return position - (position + 1) / (mParam.adItemInterval + 1);
    }

    @Override
    public int getItemCount() {
        int realCount = super.getItemCount();
        return realCount + realCount / mParam.adItemInterval;
    }

    @Override
    public int getItemViewType(int position) {
        if (isAdPosition(position)) {
            return TYPE_FB_NATIVE_ADS;
        }
        return super.getItemViewType(convertAdPosition2OrgPosition(position));
    }

    private boolean isAdPosition(int position) {
        return (position + 1) % (mParam.adItemInterval + 1) == 0;
    }

    private void onBindAdViewHolder(final RecyclerView.ViewHolder holder) {
        final AdViewHolder adHolder = (AdViewHolder) holder;
        if (mParam.forceReloadAdOnBind || !adHolder.loaded) {

            refreshAd(mParam.facebookPlacementId, mParam.itemContainerId, adHolder.frameLayout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FB_NATIVE_ADS) {
            onBindAdViewHolder(holder);
        } else {
            super.onBindViewHolder(holder, convertAdPosition2OrgPosition(position));
        }
    }

    private RecyclerView.ViewHolder onCreateAdViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View adLayoutOutline = inflater
                .inflate(mParam.itemContainerLayoutRes, parent, false);


        return new AdViewHolder(adLayoutOutline);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FB_NATIVE_ADS) {
            return onCreateAdViewHolder(parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    private void setSpanAds() {
        if (mParam.gridLayoutManager == null) {
            return;
        }

        mParam.gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
//                if (AdHelper.IS_GALLERY_NATIVE.matches("1")) {
//                    return 1;
//                }
                Log.e("SNEH", "getSpanSize: position" + position);
                if (isAdPosition(position)) {
                    Log.e("SNEH", "getSpanSize: if " + mParam.gridLayoutManager.getSpanCount());
                    return mParam.gridLayoutManager.getSpanCount();
                }
                Log.e("SNEH", "getSpanSize: return 1");
                return 1;
            }
        });
    }

    private void refreshAd(AdsId PlacementId, final int itemContainerId, NativeAdLayout frameLayout00) {
//        AllInOneAds.getInstance().showGalleryAd(mContext, frameLayout00);

        AllInOneAds.getInstance().showMiddleNativeWithId(mContext,PlacementId,frameLayout00);
    }

    private static class Param {
        AdsId facebookPlacementId;
        RecyclerView.Adapter adapter;
        int adItemInterval;
        boolean forceReloadAdOnBind;

        @LayoutRes
        int itemContainerLayoutRes;

        @IdRes
        int itemContainerId;

        GridLayoutManager gridLayoutManager;
    }

    public static class Builder {
        private final Param mParam;

        private Builder(Param param) {
            mParam = param;
        }

        public static Builder with(Activity context, AdsId placementId, RecyclerView.Adapter wrapped) {

            mContext = context;


            MobileAds.initialize(mContext, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            Param param = new Param();
            param.facebookPlacementId = placementId;
            param.adapter = wrapped;

            //default value
            param.adItemInterval = DEFAULT_AD_ITEM_INTERVAL;
            param.itemContainerLayoutRes = R.layout.item_google_native_ad_outline;
            param.itemContainerId = R.id.native_container;
            param.forceReloadAdOnBind = true;
            return new Builder(param);
        }

        public Builder adItemInterval(int interval) {
            mParam.adItemInterval = interval;
            return this;
        }

        public Builder adLayout(@LayoutRes int layoutContainerRes, @IdRes int itemContainerId) {
            mParam.itemContainerLayoutRes = layoutContainerRes;
            mParam.itemContainerId = itemContainerId;
            return this;
        }

        public GoogleNativeAdAdapter build() {
            return new GoogleNativeAdAdapter(mParam);
        }

        public Builder enableSpanRow(GridLayoutManager layoutManager) {
            mParam.gridLayoutManager = layoutManager;
            return this;
        }

        public Builder forceReloadAdOnBind(boolean forced) {
            mParam.forceReloadAdOnBind = forced;
            return this;
        }
    }

    private static class AdViewHolder extends RecyclerView.ViewHolder {

        boolean loaded;
        NativeAdLayout frameLayout;

        AdViewHolder(View view) {
            super(view);
            loaded = false;
            frameLayout = view.findViewById(R.id.native_container);
        }

    }


}
