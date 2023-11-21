package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import videodownloader.privatebrowser.free.hd.statussaver.R;

public class adp_tab extends RecyclerView.Adapter<adp_tab.ViewHolderPostVideos> {
    private final Context ctx;
    private final List<store_model_tab> items;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int pos, int action, String strFeedKey);
    }

    public static class ViewHolderPostVideos extends RecyclerView.ViewHolder {
        public final ImageView imgClose;
        public final ImageView imgFavicon;
        public final RelativeLayout relativeRecent;
        public final TextView tvUrl;

        public ViewHolderPostVideos(View v) {
            super(v);
            this.tvUrl = (TextView) v.findViewById(R.id.tvUrl);
            this.imgClose = (ImageView) v.findViewById(R.id.imgClose);
            this.imgFavicon = (ImageView) v.findViewById(R.id.imgFavicon);
            this.relativeRecent = (RelativeLayout) v.findViewById(R.id.relativeRecent);
        }
    }

    public adp_tab(Context ctx, List<store_model_tab> items) {
        this.ctx = ctx;
        this.items = items;
    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderPostVideos holder, final int position) {
        store_model_tab store_model_tabVar = this.items.get(position);
        if (store_model_tabVar.getTitleTab().equalsIgnoreCase("about:blank")) {
            holder.tvUrl.setText(this.ctx.getResources().getString(R.string.homepage));
        } else {
            holder.tvUrl.setText(store_model_tabVar.getTitleTab());
        }
        if (position == MainActivityVideos.currentTabIndex) {
            holder.relativeRecent.setBackgroundColor(Color.parseColor("#F0F2F7"));
        } else {
            holder.relativeRecent.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        if (store_model_tabVar.getBitmapTab() != null && !store_model_tabVar.getTitleTab().equalsIgnoreCase("about:blank")) {
            Glide.with(this.ctx).load(store_model_tabVar.getBitmapTab()).dontAnimate().into(holder.imgFavicon);
        } else {
            Glide.with(this.ctx).load((int) R.drawable.fcon_for_default_web).dontAnimate().dontTransform().into(holder.imgFavicon);
        }
        holder.itemView.setOnClickListener(view -> {
            OnItemClickListener onItemClickListener = this.mOnItemClickListener;
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(holder.getAdapterPosition(), 0, "");
            }
        });
        holder.imgClose.setOnClickListener(view -> {
            OnItemClickListener onItemClickListener = this.mOnItemClickListener;
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(holder.getAdapterPosition(), 1, "");
            }
        });
    }

    @Override
    @NonNull
    public ViewHolderPostVideos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderPostVideos(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_web_tabs, parent, false));
    }
}
