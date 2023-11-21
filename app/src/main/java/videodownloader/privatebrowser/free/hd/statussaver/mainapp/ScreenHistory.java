package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.Delegate;
import videodownloader.privatebrowser.free.hd.statussaver.tool.PersistentDataManager;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

public class ScreenHistory extends AppCompatActivity implements View.OnClickListener {
    private final List<store_model_history> arrModelHistory = new ArrayList<>();
    public ImageView imgDelete;
    private adapter_history mHistoryAdapter;
    private PersistentDataManager mLocalAllTransactionsDB;
    private RecyclerView recyclerView;
    public RelativeLayout relative_error;
    public TextView txtHeader;
    public TextView txtHeadetText;

    public static class NRecyclerLayoutManager extends LinearLayoutManager {
        public NRecyclerLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (Exception unused) {
                throw new RuntimeException(unused);
            }
        }
    }

    private void FetchDataId() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.imgDelete = (ImageView) findViewById(R.id.imgDelete);
        this.txtHeader = (TextView) findViewById(R.id.txtHeader);
        this.relative_error = (RelativeLayout) findViewById(R.id.relative_error);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        this.txtHeader.setText(getResources().getString(R.string.history));
        TextView textView = (TextView) findViewById(R.id.txtHeadetText);
        this.txtHeadetText = textView;
        textView.setText(getString(R.string.no_browsing_history_found));
    }

    @SuppressLint("Range")
    private void FetchdataToList() {
        Cursor GetAllHistory = this.mLocalAllTransactionsDB.GetAllHistory();
        this.arrModelHistory.clear();
        if (GetAllHistory.getCount() > 0) {
            GetAllHistory.moveToFirst();
            while (!GetAllHistory.isAfterLast()) {
                this.arrModelHistory.add(new store_model_history(GetAllHistory.getInt(GetAllHistory.getColumnIndex("_id")), GetAllHistory.getString(GetAllHistory.getColumnIndex("title")), GetAllHistory.getString(GetAllHistory.getColumnIndex("url")), GetAllHistory.getLong(GetAllHistory.getColumnIndex("visitedtime"))));
                GetAllHistory.moveToNext();
            }
        }
        if (this.arrModelHistory.size() > 0) {
            this.relative_error.setVisibility(View.GONE);
            adapter_history adapter_historyVar = this.mHistoryAdapter;
            if (adapter_historyVar != null) {
                adapter_historyVar.notifyDataSetChanged();
                return;
            }
            return;
        }
        this.relative_error.setVisibility(View.VISIBLE);
    }

    private void callDleteCinsirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.clear_history));
        builder.setMessage(getString(R.string.all_your_browsing_history_will_be_deleted));
        builder.setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
            dialogInterface.dismiss();
            this.mLocalAllTransactionsDB.RemoveHistoryTable();
            FetchdataToList();
        });
        builder.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        final AlertDialog create = builder.create();
        create.setOnShowListener(dialogInterface -> {
            create.getButton(-1).setTextColor(ViewCompat.MEASURED_STATE_MASK);
            create.getButton(-2).setTextColor(ViewCompat.MEASURED_STATE_MASK);        });
        create.show();
    }



    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgDelete) {
            callDleteCinsirm();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        Delegate.screenHistory = this;
        FetchDataId();
        this.mLocalAllTransactionsDB = PersistentDataManager.getSQInstance(this);
        this.recyclerView.setLayoutManager(new NRecyclerLayoutManager(this));
        this.recyclerView.setHasFixedSize(true);
        adapter_history adapter_historyVar = new adapter_history(this, this.arrModelHistory);
        this.mHistoryAdapter = adapter_historyVar;
        this.recyclerView.setAdapter(adapter_historyVar);
        this.mHistoryAdapter.setOnItemClickListener((pos, type) -> {
            if (pos != -1) {
                store_model_history store_model_historyVar = (store_model_history) ScreenHistory.this.arrModelHistory.get(pos);
                if (type == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("open_url", store_model_historyVar.getUrl());
                    ScreenHistory.this.setResult(-1, intent);
                    ScreenHistory.this.finish();
                } else if (type == 21) {
                    Intent intent2 = new Intent();
                    intent2.putExtra("new_open_url", store_model_historyVar.getUrl());
                    ScreenHistory.this.setResult(-1, intent2);
                    ScreenHistory.this.finish();
                } else if (type == 22) {
                    Intent intent3 = new Intent("android.intent.action.SEND");
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    intent3.putExtra("android.intent.extra.TEXT", store_model_historyVar.getUrl());
                    intent3.setType("text/plain");
                    try {
                        ScreenHistory screenHistory = ScreenHistory.this;
                        screenHistory.startActivity(Intent.createChooser(intent3, screenHistory.getResources().getString(R.string.share_url)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (type == 23) {
                    try {
                        ((ClipboardManager) ScreenHistory.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("link", store_model_historyVar.getUrl()));
                        UtilsForApp.showToastMsg(ScreenHistory.this.getResources().getString(R.string.url_copied), ScreenHistory.this, false);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else if (type == 24) {
                    ScreenHistory.this.mLocalAllTransactionsDB.RemoveHistory(store_model_historyVar.get_id());
                    if (pos < ScreenHistory.this.arrModelHistory.size()) {
                        ScreenHistory.this.arrModelHistory.remove(pos);
                        ScreenHistory.this.mHistoryAdapter.notifyItemRemoved(pos);
                        if (ScreenHistory.this.arrModelHistory.size() > 0) {
                            ScreenHistory.this.relative_error.setVisibility(View.GONE);
                        } else {
                            ScreenHistory.this.relative_error.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        FetchdataToList();
        this.imgDelete.setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        Delegate.screenHistory = null;
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
