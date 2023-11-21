package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.store_model_hashtag;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class adapter_hash_tag extends RecyclerView.Adapter<adapter_hash_tag.ViewHolder> {
    private final Context context;
    private OnItemClickListener mOnItemClickListener;
    private final List<store_model_hashtag> tag_list;

    public interface OnItemClickListener {
        void onItemClick(int position, String tagName);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout cardMain;
        public final ImageView ivHashTag;
        public final TextView tvHashTag;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cardMain = (LinearLayout) itemView.findViewById(R.id.cardMain);
            this.ivHashTag = (ImageView) itemView.findViewById(R.id.ivHashTag);
            this.tvHashTag = (TextView) itemView.findViewById(R.id.tvHashTag);
        }
    }

    public adapter_hash_tag(Context context, List<store_model_hashtag> tagList) {
        this.context = context;
        this.tag_list = tagList;
    }

    @Override
    public int getItemCount() {
        return this.tag_list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint({"RecyclerView"}) final int position) {
        final store_model_hashtag store_model_hashtagVar = this.tag_list.get(holder.getAdapterPosition());
        holder.tvHashTag.setText(store_model_hashtagVar.getTitle().trim());
        try {
            InputStream open = this.context.getAssets().open(store_model_hashtagVar.getImage());
            holder.ivHashTag.setImageDrawable(Drawable.createFromStream(open, null));
            open.close();
            holder.itemView.setOnClickListener(v -> {
                if (adapter_hash_tag.this.mOnItemClickListener != null) {
                    adapter_hash_tag.this.mOnItemClickListener.onItemClick(position, store_model_hashtagVar.getTitle());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_hash_tag, parent, false));
    }
}
