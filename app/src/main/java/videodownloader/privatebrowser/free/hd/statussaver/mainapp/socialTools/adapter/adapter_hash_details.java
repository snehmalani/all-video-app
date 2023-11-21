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
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.store_model_tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class adapter_hash_details extends RecyclerView.Adapter<adapter_hash_details.ViewHolder> {
    private final Context context;
    private OnItemClickListener mOnItemClickListener;
    private final List<store_model_tag> tag_list;

    public interface OnItemClickListener {
        void onItemClick(int position, String allStrind, String tagName, ArrayList<String> tagList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout linSelect;
        public final TextView tvTagCount;
        public final TextView tvTagName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvTagName = (TextView) itemView.findViewById(R.id.tvTagName);
            this.tvTagCount = (TextView) itemView.findViewById(R.id.tvTagCount);
            this.linSelect = (LinearLayout) itemView.findViewById(R.id.linSelect);
        }
    }

    public adapter_hash_details(Context context, List<store_model_tag> tagList) {
        this.context = context;
        this.tag_list = tagList;
    }

    public static String getRandomBg() {
        ArrayList arrayList = new ArrayList();
        if (arrayList.size() == 0) {
            arrayList.add("#E0EFF7");
            arrayList.add("#FFF2E6");
            arrayList.add("#F5F5F3");
            arrayList.add("#FFF6F5");
            arrayList.add("#E6E4E9");
            arrayList.add("#EEEBEA");
            arrayList.add("#E0ECF0");
        }
        return (String) arrayList.get(new Random().nextInt(7));
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
        final store_model_tag store_model_tagVar = this.tag_list.get(holder.getAdapterPosition());
        holder.tvTagName.setText(store_model_tagVar.getName().trim());
        TextView textView = holder.tvTagCount;
        textView.setText(store_model_tagVar.getTagList().size() + StringUtils.SPACE + this.context.getString(R.string.TAGS));
        holder.linSelect.getBackground().setColorFilter(Color.parseColor(getRandomBg()), PorterDuff.Mode.SRC_ATOP);
        holder.itemView.setOnClickListener(v -> {
            if (adapter_hash_details.this.mOnItemClickListener != null) {
                adapter_hash_details.this.mOnItemClickListener.onItemClick(position, store_model_tagVar.getCopyShare(), store_model_tagVar.getName(), store_model_tagVar.getTagList());
            }
        });
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag_list, parent, false));
    }
}
