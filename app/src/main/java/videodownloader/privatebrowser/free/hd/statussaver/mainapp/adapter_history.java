package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import videodownloader.privatebrowser.free.hd.statussaver.R;

public class adapter_history extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private final List<store_model_history> noticeList;

    public interface OnItemClickListener {
        void onItemClick(int position, int which);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imgSettings;
        public final TextView txtTitle;
        public final TextView txtUrl;

        public ViewHolder(View v) {
            super(v);
            this.txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            this.txtUrl = (TextView) v.findViewById(R.id.txtUrl);
            this.imgSettings = (ImageView) v.findViewById(R.id.imgSettings);
        }
    }

    public adapter_history(Activity discussion_list, List<store_model_history> items) {
        this.ctx = discussion_list.getBaseContext();
        this.noticeList = items;
    }

    @Override
    public int getItemCount() {
        return this.noticeList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            store_model_history store_model_historyVar = this.noticeList.get(position);
            final ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.txtTitle.setText(store_model_historyVar.getTitle());
            viewHolder.txtUrl.setText(store_model_historyVar.getUrl());
            holder.itemView.setOnClickListener(view -> {
                if (adapter_history.this.mOnItemClickListener != null) {
                    adapter_history.this.mOnItemClickListener.onItemClick(holder.getAdapterPosition(), 0);
                }
            });
            viewHolder.imgSettings.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(adapter_history.this.ctx, viewHolder.imgSettings);
                popupMenu.getMenu().add(adapter_history.this.ctx.getResources().getString(R.string.open_new_tab));
                popupMenu.getMenu().add(adapter_history.this.ctx.getResources().getString(R.string.share));
                popupMenu.getMenu().add(adapter_history.this.ctx.getResources().getString(R.string.copy_link));
                popupMenu.getMenu().add(adapter_history.this.ctx.getResources().getString(R.string.delete));
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (adapter_history.this.mOnItemClickListener != null) {
                        if (item.getTitle().equals(adapter_history.this.ctx.getResources().getString(R.string.open_new_tab))) {
                            adapter_history.this.mOnItemClickListener.onItemClick(holder.getAdapterPosition(), 21);
                            return true;
                        } else if (item.getTitle().equals(adapter_history.this.ctx.getResources().getString(R.string.share))) {
                            adapter_history.this.mOnItemClickListener.onItemClick(holder.getAdapterPosition(), 22);
                            return true;
                        } else if (item.getTitle().equals(adapter_history.this.ctx.getResources().getString(R.string.copy_link))) {
                            adapter_history.this.mOnItemClickListener.onItemClick(holder.getAdapterPosition(), 23);
                            return true;
                        } else if (item.getTitle().equals(adapter_history.this.ctx.getResources().getString(R.string.delete))) {
                            adapter_history.this.mOnItemClickListener.onItemClick(holder.getAdapterPosition(), 24);
                            return true;
                        } else {
                            return true;
                        }
                    }
                    return true;
                });
                popupMenu.show();
            });
        }
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history, parent, false));
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }
}
