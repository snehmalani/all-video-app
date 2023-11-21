package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

public class adapter_video_url extends RecyclerView.Adapter<adapter_video_url.ViewHolder> {
    private final Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private final List<store_model_video_link.listVideos> noticeList;
    long aLong;
    private final HashMap<Integer, String> titleList = new HashMap<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String fNAME);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout lins;
        public final TextView txtQuality;
        public final TextView txtSize;

        public ViewHolder(View v) {
            super(v);
            this.txtQuality = (TextView) v.findViewById(R.id.txtQuality);
            this.txtSize = (TextView) v.findViewById(R.id.txtSize);
            this.lins = (LinearLayout) v.findViewById(R.id.lins);
        }
    }

    public adapter_video_url(Context ctx, List<store_model_video_link.listVideos> items) {
        this.ctx = ctx;
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

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        String n_link_image;
        final store_model_video_link.listVideos listvideos = this.noticeList.get(position);
        if (listvideos.isIs_selected()) {
            viewHolder.lins.setBackgroundResource(R.drawable.drw_link_selected_custom);
        } else {
            viewHolder.lins.setBackgroundResource(R.drawable.drw_link_unselected_custom);
        }
        if (listvideos.isIs_local_video()) {
            n_link_image = listvideos.getN_link_url();
        } else {
            n_link_image = listvideos.getN_link_image();
            if (n_link_image == null || n_link_image.isEmpty()) {
                n_link_image = listvideos.getN_link_url();
            }
        }
        final String str = n_link_image;
        if (!listvideos.getLocal_quality().isEmpty()) {
            viewHolder.txtQuality.setText(listvideos.getLocal_quality());
        } else if (listvideos.getN_libk_width() != null && listvideos.getN_link_height() != null && listvideos.getN_libk_width() > 0 && listvideos.getN_link_height() > 0) {
            viewHolder.txtQuality.setText(UtilsForApp.getFileResolution(this.ctx, listvideos.getN_libk_width(), listvideos.getN_link_height()));
        } else {
            viewHolder.txtQuality.setText(R.string.working);
        }
        if (!listvideos.getLocal_size().isEmpty()) {
            viewHolder.txtSize.setText(listvideos.getLocal_size());
        }
        final String guessFileName = URLUtil.guessFileName(listvideos.getN_link_url(), null, null);
        if (!listvideos.getLocal_size().isEmpty() && !viewHolder.txtSize.getText().toString().isEmpty() && !viewHolder.txtSize.getText().toString().equalsIgnoreCase("---")) {
            if (!listvideos.getLocal_size().isEmpty()) {
                viewHolder.txtSize.setText(listvideos.getLocal_size());
            }
        } else {
            new Thread(() -> {

                final String str3;
                try {
                    Context context = this.ctx;
                    if (context != null && !((AppCompatActivity) context).isFinishing()) {
                        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                        HashMap hashMap = new HashMap();
                        String str4 = listvideos.getnHeaders();
                        if (!str4.isEmpty()) {
                            try {
                                JSONObject jSONObject = new JSONObject(str4);
                                Iterator<String> keys = jSONObject.keys();
                                while (keys.hasNext()) {
                                    String next = keys.next();
                                    String optString = jSONObject.optString(next);
                                    if (!optString.isEmpty()) {
                                        hashMap.put(next, optString);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        mediaMetadataRetriever.setDataSource(listvideos.getN_link_url(), hashMap);
                        String extractMetadata = mediaMetadataRetriever.extractMetadata(9);
                        String extractMetadata2 = mediaMetadataRetriever.extractMetadata(20);
                        final String extractMetadata3 = mediaMetadataRetriever.extractMetadata(18);
                        final String extractMetadata4 = mediaMetadataRetriever.extractMetadata(19);
                        final Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime();
                        if (extractMetadata == null || extractMetadata.isEmpty()) {
                            str3 = "";
                        } else {
                            long parseLong = Long.parseLong(extractMetadata);
                            aLong = extractMetadata2 != null ? ((Long.parseLong(extractMetadata2) / 8) * parseLong) / 1000 : 0L;
                            str3 = UtilsForApp.convertMillieToHMmSs(parseLong);
                        }
                        mediaMetadataRetriever.release();
                        ((ActivityVideoLink) this.ctx).runOnUiThread(() -> {

                            String formatFileSize = Formatter.formatFileSize(this.ctx, aLong);
                            if (!formatFileSize.equalsIgnoreCase("0bytes") && !formatFileSize.equalsIgnoreCase("0 B")) {
                                viewHolder.txtSize.setText(formatFileSize);
                                listvideos.setLocal_size(formatFileSize);
                            } else {
                                viewHolder.txtSize.setText(this.ctx.getString(R.string.size_unknown));
                            }
                            if (listvideos.getLocal_quality().isEmpty()) {
                                if (str != null && extractMetadata4 != null && !extractMetadata4.isEmpty() && !str.isEmpty() && !extractMetadata4.equalsIgnoreCase("0") && !extractMetadata4.equalsIgnoreCase("0") && !str.equalsIgnoreCase("null") && !extractMetadata4.equalsIgnoreCase("null")) {

                                    String fileResolution = UtilsForApp.getFileResolution(this.ctx, Integer.parseInt(extractMetadata4), Integer.parseInt(extractMetadata4));
                                    viewHolder.txtQuality.setText(fileResolution);
                                    listvideos.setLocal_quality(fileResolution);
                                } else {
                                    viewHolder.txtQuality.setText(UtilsForApp.getFileResolution(this.ctx, listvideos.getN_libk_width(), listvideos.getN_link_height()));
                                }
                            }
                            ((ActivityVideoLink) this.ctx).LoadImageAndSize(str3, str4, guessFileName, frameAtTime);

                        });
                        this.titleList.put(position, guessFileName);
                    }
                } catch (Exception unused) {
                    ((ActivityVideoLink) this.ctx).runOnUiThread(() -> {
                        viewHolder.txtSize.setText(this.ctx.getString(R.string.size_unknown));
                        ((ActivityVideoLink) this.ctx).LoadImageAndSize("", str, guessFileName, null);
                    });
                }

            }).start();
        }
        viewHolder.itemView.setOnClickListener(view -> {
            OnItemClickListener onItemClickListener = this.mOnItemClickListener;
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, viewHolder.getAdapterPosition(), this.titleList.get(viewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video_links, parent, false));
    }
}
