package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.ModelCaption;

public class AdapterCaption extends RecyclerView.Adapter<AdapterCaption.ViewHolder> {

    private final Context context;
    private OnItemClickListener mOnItemClickListener;
    private final List<ModelCaption> tag_list;

    public interface OnItemClickListener {
        void onItemClick(int position, String tagName);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView ivCaption;
        public final TextView tvCaption;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivCaption = (ImageView) itemView.findViewById(R.id.ivCaption);
            this.tvCaption = (TextView) itemView.findViewById(R.id.tvCaption);
        }
    }

    public AdapterCaption(Context context, List<ModelCaption> tagList) {
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
        final ModelCaption modelCaption = this.tag_list.get(holder.getAdapterPosition());
        holder.tvCaption.setText(modelCaption.getName().trim());
        try {
            AssetManager assets = this.context.getAssets();
            InputStream ims = assets.open("caption/" + modelCaption.getIamge());
            Drawable d = Drawable.createFromStream(ims, null);
            holder.ivCaption.setImageDrawable(d);

            holder.itemView.setOnClickListener(v -> {
                if (AdapterCaption.this.mOnItemClickListener != null) {
                    AdapterCaption.this.mOnItemClickListener.onItemClick(position, modelCaption.getName());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_caption, parent, false));
    }
}
