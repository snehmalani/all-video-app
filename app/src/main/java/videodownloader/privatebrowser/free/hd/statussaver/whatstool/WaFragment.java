package videodownloader.privatebrowser.free.hd.statussaver.whatstool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.ClassImageViewer;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.Downloader_PlayerVideo_Activity;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;

public class WaFragment extends Fragment {
    private RecyclerView recyclerView;
    private RelativeLayout relative_error;
    private TextView txtHeadetText;
    private ArrayList<WaModel> localList = new ArrayList<>();
    private String type_ = "";
    private String from_ = "";

    public static class WrapContentGridLayoutManager extends GridLayoutManager {

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        public WrapContentGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

    }

    private void initView(View view) {
        this.recyclerView = (RecyclerView) view.findViewById(R.id.appRecyclerview);
        this.relative_error = (RelativeLayout) view.findViewById(R.id.relative_error);
        this.txtHeadetText = (TextView) view.findViewById(R.id.txtHeadetText);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.localList = (ArrayList) getArguments().get("files_list");
            this.type_ = getArguments().getString("type_");
            this.from_ = getArguments().getString("from_");
        }
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wa_wab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        WaStatusAdapter adapter = new WaStatusAdapter(getActivity(), this.localList, this.type_);
        this.recyclerView.setLayoutManager(new WrapContentGridLayoutManager(getActivity(), 2));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (this.localList.size() == 0) {
            this.relative_error.setVisibility(View.VISIBLE);
            if (this.type_.equals("image")) {
                if (this.from_.equalsIgnoreCase("my")) {
                    this.txtHeadetText.setText(getString(R.string.no_saved_image_status_found));
                } else {
                    this.txtHeadetText.setText(getString(R.string.please_view_status_on_whatsapp_app_first));
                }
            } else if (this.from_.equalsIgnoreCase("my")) {
                this.txtHeadetText.setText(getString(R.string.no_saved_video_status_found));
            } else {
                this.txtHeadetText.setText(getString(R.string.please_view_status_on_whatsapp_app_first));
            }
        } else {
            this.relative_error.setVisibility(View.GONE);
        }
        adapter.setOnItemClickListener((view1, position) -> {
            if (position != -1) {
                try {
                    AllInOneAds.getInstance().showInterWithId(getActivity(), AdHelper.whatsappId, () -> {
                        if (WaFragment.this.type_.equals("image")) {
                            Intent intent = new Intent(WaFragment.this.getActivity(), ClassImageViewer.class);
                            intent.putExtra("file_uri", ((WaModel) WaFragment.this.localList.get(position)).getFileUri());
                            intent.putExtra("file_name", ((WaModel) WaFragment.this.localList.get(position)).getFileName());
                            intent.putExtra("type_", "status");
                            WaFragment.this.startActivity(intent);
                            return;
                        }
                        Intent intent = new Intent(getActivity(), Downloader_PlayerVideo_Activity.class);
                        intent.putExtra("path", ((WaModel) WaFragment.this.localList.get(position)).getFileUri());
                        intent.putExtra("file_name", ((WaModel) WaFragment.this.localList.get(position)).getFileName());

                        if (from_.equals("wa")){
                            intent.putExtra("location", "whatsapp");
                        }else {
                            intent.putExtra("location", "downloads");
                        }

                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        WaFragment.this.startActivity(intent);
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
