package videodownloader.privatebrowser.free.hd.statussaver.whatstool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;

import videodownloader.privatebrowser.free.hd.statussaver.R;

public class WaStatusAdapter extends RecyclerView.Adapter<WaStatusAdapter.ViewHolder> {
    private final Context ctx;
    private final ArrayList<WaModel> filesModels;
    private OnItemClickListener mOnItemClickListener;
    private final String type_;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imgMainImage;
        public final ImageView imgVideo;

        public ViewHolder(View v) {
            super(v);
            this.imgMainImage = (ImageView) v.findViewById(R.id.imgMainImage);
            this.imgVideo = (ImageView) v.findViewById(R.id.imgVideo);
        }
    }

    public WaStatusAdapter(Context ctx, ArrayList<WaModel> items, String type_) {
        this.ctx = ctx;
        this.filesModels = items;
        this.type_ = type_;
    }


    @Override
    public int getItemCount() {
        return this.filesModels.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        WaModel waModel = this.filesModels.get(position);
        if (this.type_.equals("image")) {
            holder.imgVideo.setVisibility(View.GONE);
        } else {
            holder.imgVideo.setVisibility(View.VISIBLE);
        }
        Glide.with(this.ctx).load(waModel.getFileUri()).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().fitCenter().override(500, 500).format(DecodeFormat.PREFER_RGB_565)).into(holder.imgMainImage);
        holder.itemView.setOnClickListener(view -> {
            OnItemClickListener onItemClickListener = this.mOnItemClickListener;
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, holder.getAdapterPosition());
            }
        });
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wa_wab_item, parent, false));
    }
}
