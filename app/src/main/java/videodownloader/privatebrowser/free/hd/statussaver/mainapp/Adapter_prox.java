package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import videodownloader.privatebrowser.free.hd.statussaver.R;

import java.util.List;

public class Adapter_prox extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private final List<Store_model_prox> store_model_proxes;

    public interface OnItemClickListener {
        void onItemClick(int position, int type);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Button btnConnect;
        public final CircleImageView imgThumb;
        public final TextView txtTitle;
        public final TextView txtUrl;

        public ViewHolder(View v) {
            super(v);
            this.txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            this.txtUrl = (TextView) v.findViewById(R.id.txtUrl);
            this.imgThumb = (CircleImageView) v.findViewById(R.id.imgThumb);
            this.btnConnect = (Button) v.findViewById(R.id.btnConnect);
        }
    }

    public Adapter_prox(Activity discussion_list, List<Store_model_prox> items) {
        this.ctx = discussion_list.getBaseContext();
        this.store_model_proxes = items;
    }

    @Override
    public int getItemCount() {
        return this.store_model_proxes.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final Store_model_prox store_model_prox = this.store_model_proxes.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            Glide.with(this.ctx).load(store_model_prox.getpFl()).into(viewHolder.imgThumb);
            viewHolder.txtTitle.setText(store_model_prox.getpCnt());
            viewHolder.txtUrl.setText(store_model_prox.getpDs());
            if (store_model_prox.getIsConnected() == 1) {
                viewHolder.btnConnect.setBackgroundResource(R.drawable.btn_disconnect_prox);
                viewHolder.btnConnect.setText(R.string.disconnect);
            } else {
                viewHolder.btnConnect.setBackgroundResource(R.drawable.btn_connect_prox);
                viewHolder.btnConnect.setText(R.string.connect);
            }
            viewHolder.btnConnect.setOnClickListener(view -> {
                if (Adapter_prox.this.mOnItemClickListener != null) {
                    Adapter_prox.this.mOnItemClickListener.onItemClick(holder.getAdapterPosition(), store_model_prox.getIsConnected());
                }
            });
        }
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_proxy, parent, false));
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }
}
