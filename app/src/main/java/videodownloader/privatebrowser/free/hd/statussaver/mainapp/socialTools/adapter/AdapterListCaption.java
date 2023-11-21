package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.Caption;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdapterListCaption extends RecyclerView.Adapter<AdapterListCaption.ViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private final List<Caption.ListCaption> modelCaptionList;

    public interface OnItemClickListener {
        void onItemClick(int position, String captionText, int type);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout linChangeColorLay;
        public final LinearLayout linCopy;
        public final LinearLayout linShare;
        public final LinearLayout linWhatsApp;
        public final TextView tvText;

        public ViewHolder(View itemView) {
            super(itemView);
            this.linWhatsApp = (LinearLayout) itemView.findViewById(R.id.linWhatsApp);
            this.linShare = (LinearLayout) itemView.findViewById(R.id.linShare);
            this.linCopy = (LinearLayout) itemView.findViewById(R.id.linCopy);
            this.tvText = (TextView) itemView.findViewById(R.id.tvText);
            this.linChangeColorLay = (LinearLayout) itemView.findViewById(R.id.linChangeColorLay);
        }
    }

    public AdapterListCaption(Context context, List<Caption.ListCaption> modelCaptionList) {
        this.modelCaptionList = modelCaptionList;
    }

    public static String getRandomBg() {
        ArrayList arrayList = new ArrayList();
        if (arrayList.size() == 0) {
            arrayList.add("#FAF0D7");
            arrayList.add("#DAEDF0");
            arrayList.add("#FCE4D9");
            arrayList.add("#CEE5F0");
            arrayList.add("#FFE6B8");
            arrayList.add("#D1D9DE");
            arrayList.add("#EDDDD1");
        }
        return (String) arrayList.get(new Random().nextInt(7));
    }

    @Override
    public int getItemCount() {
        return this.modelCaptionList.size();
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
        final Caption.ListCaption listCaption = this.modelCaptionList.get(holder.getAdapterPosition());
        holder.tvText.setText(listCaption.getCaption().trim());
        holder.linChangeColorLay.getBackground().setColorFilter(Color.parseColor(getRandomBg()), PorterDuff.Mode.SRC_ATOP);
        holder.linCopy.setOnClickListener(v -> {
            if (AdapterListCaption.this.mOnItemClickListener != null) {
                AdapterListCaption.this.mOnItemClickListener.onItemClick(position, listCaption.getCaption(), 0);
            }
        });
        holder.linWhatsApp.setOnClickListener(v -> {
            if (AdapterListCaption.this.mOnItemClickListener != null) {
                AdapterListCaption.this.mOnItemClickListener.onItemClick(position, listCaption.getCaption(), 1);
            }
        });
        holder.linShare.setOnClickListener(v -> {
            if (AdapterListCaption.this.mOnItemClickListener != null) {
                AdapterListCaption.this.mOnItemClickListener.onItemClick(position, listCaption.getCaption(), 2);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (AdapterListCaption.this.mOnItemClickListener != null) {
                AdapterListCaption.this.mOnItemClickListener.onItemClick(position, listCaption.getCaption(), 3);
            }
        });
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_caption_list, parent, false));
    }
}
