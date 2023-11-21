package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import videodownloader.privatebrowser.free.hd.statussaver.R;

public class AdapterSubBio extends RecyclerView.Adapter<AdapterSubBio.ViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private final List<String> tag_list;

    public interface OnItemClickListener {
        void onItemClick(int position, String tagName, int type_);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout linCopy;
        public final LinearLayout linEdit;
        public final LinearLayout linShare;
        public final TextView tvText;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvText = (TextView) itemView.findViewById(R.id.tvText);
            this.linCopy = (LinearLayout) itemView.findViewById(R.id.linCopy);
            this.linShare = (LinearLayout) itemView.findViewById(R.id.linShare);
            this.linEdit = (LinearLayout) itemView.findViewById(R.id.linEdit);
        }
    }

    public AdapterSubBio(Context context, List<String> tagList) {
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
        final String str = this.tag_list.get(holder.getAdapterPosition());
        holder.tvText.setText(str);
        holder.linCopy.setOnClickListener(v -> {
            if (AdapterSubBio.this.mOnItemClickListener != null) {
                AdapterSubBio.this.mOnItemClickListener.onItemClick(position, str, 0);
            }
        });
        holder.linShare.setOnClickListener(v -> {
            if (AdapterSubBio.this.mOnItemClickListener != null) {
                AdapterSubBio.this.mOnItemClickListener.onItemClick(position, str, 1);
            }
        });
        holder.linEdit.setOnClickListener(v -> {
            if (AdapterSubBio.this.mOnItemClickListener != null) {
                AdapterSubBio.this.mOnItemClickListener.onItemClick(position, str, 2);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (AdapterSubBio.this.mOnItemClickListener != null) {
                AdapterSubBio.this.mOnItemClickListener.onItemClick(position, str, 3);
            }
        });
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_bio_sub_category, parent, false));
    }
}
