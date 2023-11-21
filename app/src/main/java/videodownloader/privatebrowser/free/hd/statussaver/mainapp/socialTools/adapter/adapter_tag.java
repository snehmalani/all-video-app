package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.model_tag;

import java.util.List;

public class adapter_tag extends RecyclerView.Adapter<adapter_tag.ViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    public boolean selectAll = false;
    private final List<model_tag> tag_list;

    public interface OnItemClickListener {
        void onItemClick(int position, List<model_tag> tag_list);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Chip chip;

        public ViewHolder(View itemView) {
            super(itemView);
            this.chip = (Chip) itemView.findViewById(R.id.chip);
        }
    }

    public adapter_tag(Context context, List<model_tag> tagList) {
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

    public boolean updateView() {
        if (this.selectAll) {
            this.selectAll = false;
        } else {
            this.selectAll = true;
        }
        notifyDataSetChanged();
        return this.selectAll;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint({"RecyclerView"}) final int position) {
        holder.chip.setText(this.tag_list.get(position).getName());
        this.tag_list.get(position).setSetSelected(this.selectAll);
        if (this.tag_list.get(position).isSetSelected()) {
            holder.chip.setChecked(true);
        } else {
            holder.chip.setChecked(false);
        }
        holder.chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ((model_tag) adapter_tag.this.tag_list.get(position)).setSetSelected(isChecked);
            if (adapter_tag.this.mOnItemClickListener != null) {
                adapter_tag.this.mOnItemClickListener.onItemClick(position, adapter_tag.this.tag_list);
            }
        });
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_tag, parent, false));
    }
}
