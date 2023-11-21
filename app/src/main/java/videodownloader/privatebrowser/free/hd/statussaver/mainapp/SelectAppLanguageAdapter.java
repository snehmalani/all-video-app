package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;
import videodownloader.privatebrowser.free.hd.statussaver.R;

import java.util.List;

public class SelectAppLanguageAdapter extends RecyclerView.Adapter<SelectAppLanguageAdapter.ViewHolder> {
    private final List<LanguageModel> ArrVidList;
    private final Context context;
    private OnItemClickListener mOnItemClickListener;
    private final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance());

    public interface OnItemClickListener {
        void onItemClick(List<LanguageModel> LanguageModelList, int position, String dayName);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView ivCheck;
        public final ImageView ivFlag;
        public final LinearLayout linLanguage;
        public final TextView tvLanguage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvLanguage = (TextView) itemView.findViewById(R.id.tvLanguage);
            this.linLanguage = (LinearLayout) itemView.findViewById(R.id.linLanguage);
            this.ivCheck = (ImageView) itemView.findViewById(R.id.ivCheck);
            this.ivFlag = (ImageView) itemView.findViewById(R.id.ivFlag);
        }
    }

    public SelectAppLanguageAdapter(Context context, List<LanguageModel> weekDays) {
        this.context = context;
        this.ArrVidList = weekDays;
    }

    @Override
    public int getItemCount() {
        return this.ArrVidList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setArrayListSelected(String selectionCode) {
        for (int i = 0; i < this.ArrVidList.size(); i++) {
            if (this.ArrVidList.get(i).getSelectionCode().equals(selectionCode)) {
                this.ArrVidList.get(i).setSelected(true);
            } else {
                this.ArrVidList.get(i).setSelected(false);
            }
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void update(String selectionCode) {
        for (int i = 0; i < this.ArrVidList.size(); i++) {
            if (this.ArrVidList.get(i).getSelectionCode().equals(selectionCode)) {
                this.ArrVidList.get(i).setSelected(true);
            } else {
                this.ArrVidList.get(i).setSelected(false);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint({"RecyclerView"}) final int position) {
        final LanguageModel languageModel = this.ArrVidList.get(holder.getAdapterPosition());
        holder.tvLanguage.setText(languageModel.getOriginalLanguageName());
        if (languageModel.isSelected()) {
            holder.ivCheck.setVisibility(View.VISIBLE);
        } else {
            holder.ivCheck.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (SelectAppLanguageAdapter.this.mOnItemClickListener != null) {
                SelectAppLanguageAdapter.this.setArrayListSelected(languageModel.getSelectionCode());
                SelectAppLanguageAdapter.this.mOnItemClickListener.onItemClick(SelectAppLanguageAdapter.this.ArrVidList, position, languageModel.getSelectionCode());
            }
        });
        Glide.with(this.context).load(languageModel.getImage()).into(holder.ivFlag);
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_choose_language, parent, false));
    }
}
