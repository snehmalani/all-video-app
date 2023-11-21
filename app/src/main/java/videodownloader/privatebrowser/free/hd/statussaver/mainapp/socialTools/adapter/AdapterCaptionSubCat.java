package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.ModelCaption;

import java.util.List;

public class AdapterCaptionSubCat extends RecyclerView.Adapter<AdapterCaptionSubCat.MyMenuHolder> {
    public final Context ctx;
    public boolean isFirstTime = true;
    public final List<ModelCaption.Newsubcategory> newsubcategories;
    public OnClickListener onClickListener;

    public static class MyMenuHolder extends RecyclerView.ViewHolder {
        public final LinearLayout linSelect;
        public final TextView tvName;

        public MyMenuHolder(@NonNull View itemView) {
            super(itemView);
            this.linSelect = (LinearLayout) itemView.findViewById(R.id.linSelect);
            this.tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public AdapterCaptionSubCat(Context ctx, List<ModelCaption.Newsubcategory> departmentList) {
        this.ctx = ctx;
        this.newsubcategories = departmentList;
    }

    @Override
    public int getItemCount() {
        return this.newsubcategories.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setSelected(int position) {
        for (int i = 0; i < this.newsubcategories.size(); i++) {
            this.newsubcategories.get(i).setSetSelected(false);
        }
        this.isFirstTime = false;
        this.newsubcategories.get(position).setSetSelected(true);
        notifyDataSetChanged();
    }

    @Override
    @SuppressLint({"SetTextI18n"})
    public void onBindViewHolder(@NonNull final MyMenuHolder holder, @SuppressLint({"RecyclerView"}) final int position) {
        ModelCaption.Newsubcategory newsubcategory = this.newsubcategories.get(position);
        holder.tvName.setText(newsubcategory.getName());
        if (newsubcategory.isSetSelected()) {
            holder.tvName.setTextColor(-1);
            holder.linSelect.setBackgroundResource(R.drawable.cat_selected);
        } else {
            holder.tvName.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            holder.linSelect.setBackgroundResource(R.drawable.cat_unselected);
        }
        holder.itemView.setOnClickListener(v -> {
            OnClickListener onClickListener = AdapterCaptionSubCat.this.onClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(position);
            }
        });
    }

    @Override
    @NonNull
    public MyMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyMenuHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw__caption_sub_cat, parent, false));
    }
}
