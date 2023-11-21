package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.ModelBio;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdapterMainBio extends RecyclerView.Adapter<AdapterMainBio.ViewHolder> {
    private final Context context;
    private OnItemClickListener mOnItemClickListener;
    private final List<ModelBio> tag_list;

    public interface OnItemClickListener {
        void onItemClick(int position, String tagName, String image, String color);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView cardChangeColor;
        public final ImageView ivBio;
        public final TextView tvBioText;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivBio = (ImageView) itemView.findViewById(R.id.ivBio);
            this.tvBioText = (TextView) itemView.findViewById(R.id.tvBioText);
            this.cardChangeColor = (CardView) itemView.findViewById(R.id.cardChangeColor);
        }
    }

    public AdapterMainBio(Context context, List<ModelBio> tagList) {
        this.context = context;
        this.tag_list = tagList;
    }

    public static String getRandomBg() {
        ArrayList arrayList = new ArrayList();
        if (arrayList.size() == 0) {
            arrayList.add("#e5f2fb");
            arrayList.add("#f9e9e2");
            arrayList.add("#f4e8c1");
            arrayList.add("#ffe6c8");
            arrayList.add("#e7edff");
            arrayList.add("#e1e9e5");
            arrayList.add("#d7f8f1");
            arrayList.add("#fbdac8");
            arrayList.add("#f4e7d7");
            arrayList.add("#bbded6");
            arrayList.add("#edfafd");
            arrayList.add("#e5f2fb");
            arrayList.add("#e1e9e5");
        }
        return (String) arrayList.get(new Random().nextInt(13));
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
        final ModelBio modelBio = this.tag_list.get(holder.getAdapterPosition());
        holder.tvBioText.setText(modelBio.getCatagryName().trim());
        final String randomBg = getRandomBg();
        holder.cardChangeColor.setCardBackgroundColor(Color.parseColor(randomBg));
        try {
            InputStream open = this.context.getAssets().open(modelBio.getCatagryImage());
            holder.ivBio.setImageDrawable(Drawable.createFromStream(open, null));
            open.close();
            holder.itemView.setOnClickListener(v -> {
                if (AdapterMainBio.this.mOnItemClickListener != null) {
                    AdapterMainBio.this.mOnItemClickListener.onItemClick(position, modelBio.getCatagryName(), modelBio.getCatagryImage(), randomBg);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_bio_main_category, parent, false));
    }
}
