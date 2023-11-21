package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.webkit.ProxyController;
import androidx.webkit.WebViewFeature;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.google.android.gms.actions.SearchIntents;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import info.guardianproject.netcipher.webkit.WebkitProxy;
import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;
import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.BioMainActivity;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.CaptionScreenActivity;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.HashTagScreenActivity;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.network.FacebookViewInterface;
import videodownloader.privatebrowser.free.hd.statussaver.network.InstaWebViewInterface;
import videodownloader.privatebrowser.free.hd.statussaver.network.ListnerForTabChange;
import videodownloader.privatebrowser.free.hd.statussaver.network.ListnerForUrlChange;
import videodownloader.privatebrowser.free.hd.statussaver.network.UtilsForBlockAds;
import videodownloader.privatebrowser.free.hd.statussaver.network.WebviewLink_Listen;
import videodownloader.privatebrowser.free.hd.statussaver.tool.ConstantForApp;
import videodownloader.privatebrowser.free.hd.statussaver.tool.Delegate;
import videodownloader.privatebrowser.free.hd.statussaver.tool.PersistentDataManager;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;
import videodownloader.privatebrowser.free.hd.statussaver.whatstool.WhatsMainActivity;

public class MainActivityVideos extends AppCompatActivity implements WebviewLink_Listen, ListnerForUrlChange, ListnerForTabChange, View.OnClickListener {

    public static int currentTabIndex = 0;
    public static boolean isDownwasVisible = false;
    public static final Map<String, CopyOnWriteArrayList<store_model_video_link.listVideos>> localDownloadLinks = new HashMap<>();
    private static final String searchCompleteUrl = "https://www.google.com/complete/search?client=firefox&q=%s";
    private AutoCompleteTextView autoCompleteTextView;
    private String currentSearchEngine;
    private String currentUserAgent;
    private FloatingActionButton downloadButton;
    private String downloadFilename;
    private String downloadURL;
    private DrawerLayout drawer;
    private ValueCallback<Uri[]> fileUploadCallback;
    private boolean fileUploadCallbackShouldReset;
    private ImageView icon_overflow;
    private ImageView imgReloadCancel;

    private ImageView imgTab3;
    private PersistentDataManager mLocalAllTransactionsDB;
    private adp_tab mTabAdapter;
    private String mUrl;
    private ClipboardManager myClipBoard;
    private ScanOptions options;
    private SharedPreferences prefs;
    private RelativeLayout realtiveTab3;

    private RelativeLayout relad;
    private RelativeLayout relativeHomepage;
    private RelativeLayout relativeSarch;
    public String rendomtextg;
    private RelativeLayout scrollHomepage;
    public ScrollView scrollvieww;
    private TextView searchCount;
    private EditText searchEdit;
    private TextView tvPrivacy;
    private TextView txtData;
    private FrameLayout webviews;
    private int tabI = 0;
    private String desktopUA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36";
    public ArrayList<ModelRendomQuots> quotslist = new ArrayList<>();
    private final ArrayList<Tab> tabs = new ArrayList<>();
    private final List<ListnerForUrlChange> mObserverList = new ArrayList<>();
    private final List<WebviewLink_Listen> mLoadUrlStatusObserverList = new ArrayList<>();
    private String downurl = "";
    private String lastUrlDetected = "";
    private final ArrayList<store_model_tab> ArrTabList = new ArrayList<>();
    private boolean isReload = false;
    private boolean bHasClipChangedListener = false;
    private long lastCopiedTime = 0;
    private WebView mBottomForWebView = null;
    private String received_url = "";
    private String received_data = "";
    private String copy_data = "";
    private long shakeTime = 0;
    private final boolean isWbPxAllow = false;
    private final String wbPxUSR = "";
    private final String wbPxPASS = "";
    private boolean agentsetFB = false;
    private boolean privacyFlag = true;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    newTab(result.getContents());
                    switchToTab(this.tabs.size() - 1);
                }
            });



    public final ClipboardManager.OnPrimaryClipChangedListener mPrimaryClipChangedListener = () -> {
        ClipData.Item itemAt;
        try {
            if (MainActivityVideos.this.myClipBoard == null || !MainActivityVideos.this.myClipBoard.hasPrimaryClip() || MainActivityVideos.this.myClipBoard.getPrimaryClip() == null || MainActivityVideos.this.myClipBoard.getPrimaryClip().getItemCount() <= 0 || (itemAt = MainActivityVideos.this.myClipBoard.getPrimaryClip().getItemAt(0)) == null) {
                return;
            }
            String trim = itemAt.coerceToText(MainActivityVideos.this).toString().trim();
            if (TextUtils.isEmpty(trim)) {
                return;
            }
            if (System.currentTimeMillis() - MainActivityVideos.this.lastCopiedTime > TimeUnit.SECONDS.toMillis(1L) && MainActivityVideos.this.isValidTwitterVideoUrlRegex(trim)) {
                if (UtilsForApp.isNetworkAvailable(MainActivityVideos.this)) {
                    if (MainActivityVideos.this.getCurrentWebView() != null) {
                        MainActivityVideos.this.getCurrentWebView().onPause();
                    }
                    Intent intent = new Intent(MainActivityVideos.this, ActivityVideoLink.class);
                    intent.putExtra("d_url", trim);
                    intent.putExtra("d_title", MainActivityVideos.this.getCurrentTab().title);
                    intent.putExtra("type_", "natural");
                    MainActivityVideos.this.startActivityForResult(intent, 1591);
                } else {
                    UtilsForApp.showToastMsg(MainActivityVideos.this.getString(R.string.internet_connection_error), MainActivityVideos.this, false);
                }
            }
            MainActivityVideos.this.lastCopiedTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };


    public class WebviewProgress extends WebViewClient {
        public final String[] sslErrors;
        private String title;
        public final ProgressBar val$progressBar;

        public WebviewProgress(final ProgressBar val$progressBar) {
            this.val$progressBar = val$progressBar;
            this.sslErrors = new String[]{getResources().getString(R.string.not_valid), getResources().getString(R.string.expired), getResources().getString(R.string.hostname), getResources().getString(R.string.untrusted), getResources().getString(R.string.invalide_date), getResources().getString(R.string.unknown_error)};
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            MainActivityVideos.this.mUrl = url;
            MainActivityVideos mainActivityVideos = MainActivityVideos.this;
            mainActivityVideos.notifyUrlChanged(this.title, mainActivityVideos.getCurrentTab().tabID, true, url);
            MainActivityVideos.this.autoCompleteTextView.setText(url);
            MainActivityVideos.this.checkAndAddIconInET(url);
            MainActivityVideos.this.autoCompleteTextView.setSelection(0);
            view.requestFocus();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            if (MainActivityVideos.this.isThisInstaPage(url)) {
                MainActivityVideos.this.setInstaJS(view, url);
            } else if (!MainActivityVideos.this.isThisFBPage(url) && !url.contains("fbcdn.net")) {
                for (WebviewLink_Listen webviewLink_Listen : MainActivityVideos.this.mLoadUrlStatusObserverList) {
                    webviewLink_Listen.resourceUrlChecking(view, url);
                }
            } else {
                MainActivityVideos.this.setFbJS(view, url);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            String title = view.getTitle();
            this.title = title;
            if (TextUtils.isEmpty(title)) {
                this.title = url;
            }
            MainActivityVideos.this.mBottomForWebView = view;
            for (ListnerForUrlChange listnerForUrlChange : MainActivityVideos.this.mObserverList) {
                listnerForUrlChange.notifyUrlChanged(this.title, MainActivityVideos.this.getCurrentTab().tabID, false, url);
            }
            if (MainActivityVideos.this.relativeHomepage.getVisibility() != View.VISIBLE) {
                MainActivityVideos.this.imgTab3.setImageResource(R.drawable.fcon_ad_fg_home);
                MainActivityVideos.this.realtiveTab3.setClickable(true);
            } else {
                MainActivityVideos.this.imgTab3.setImageResource(R.drawable.icon_ad_fg_home_unslected);
                MainActivityVideos.this.realtiveTab3.setClickable(false);
            }
            if (view == MainActivityVideos.this.getCurrentWebView() && MainActivityVideos.this.autoCompleteTextView.getSelectionStart() == 0 && MainActivityVideos.this.autoCompleteTextView.getSelectionEnd() == 0 && MainActivityVideos.this.autoCompleteTextView.getText().toString().equals(view.getUrl())) {
                view.requestFocus();
            }
            MainActivityVideos.this.injectCSS(view);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            MainActivityVideos.this.mUrl = url;
            String title = view.getTitle();
            this.title = title;
            if (TextUtils.isEmpty(title)) {
                this.title = url;
            } else if (url.equals(MainActivityVideos.this.mUrl)) {
                for (ListnerForUrlChange listnerForUrlChange : MainActivityVideos.this.mObserverList) {
                    listnerForUrlChange.notifyUrlChanged(this.title, MainActivityVideos.this.getCurrentTab().tabID, false, url);
                }
            }
            MainActivityVideos.this.isReload = false;
            MainActivityVideos.this.imgReloadCancel.setImageResource(R.drawable.fcon_close_black_24dp);
            this.val$progressBar.setProgress(0);
            this.val$progressBar.setVisibility(View.VISIBLE);
            if (view == MainActivityVideos.this.getCurrentWebView()) {
                MainActivityVideos.this.autoCompleteTextView.setText(url);
                MainActivityVideos.this.checkAndAddIconInET(url);
                MainActivityVideos.this.autoCompleteTextView.setSelection(0);
                view.requestFocus();
            }
            MainActivityVideos.this.injectCSS(view);
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, final HttpAuthHandler handler, String host, String realm) {
            if (MainActivityVideos.this.isWbPxAllow) {
                handler.proceed(MainActivityVideos.this.wbPxUSR, MainActivityVideos.this.wbPxPASS);
            } else {
                new AlertDialog.Builder(MainActivityVideos.this).setTitle(host).setView(R.layout.dia_leading).setCancelable(false).setPositiveButton(MainActivityVideos.this.getResources().getString(R.string.ok), (dialogInterface, i) -> {
                    Dialog dialog = (Dialog) dialogInterface;
                    handler.proceed(((EditText) dialog.findViewById(R.id.username)).getText().toString(), ((EditText) dialog.findViewById(R.id.password)).getText().toString());
                }).setNegativeButton(MainActivityVideos.this.getString(R.string.cancel), (dialogInterface, i) -> handler.cancel()).show();
            }
        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            String str;
            try {
                if (MainActivityVideos.this.isFinishing()) {
                    return;
                }
                int primaryError = error.getPrimaryError();
                if (primaryError >= 0) {
                    String[] strArr = this.sslErrors;
                    if (primaryError < strArr.length) {
                        str = strArr[primaryError];
                        new AlertDialog.Builder(MainActivityVideos.this).setTitle(MainActivityVideos.this.getString(R.string.insecure_connection)).setMessage(String.format("Error: %s\nURL: %s\n\nCertificate:\n%s", str, error.getUrl(), MainActivityVideos.this.certificateToStr(error.getCertificate()))).setPositiveButton("Proceed", (dialogInterface, i) -> handler.proceed()).setNegativeButton("Cancel", (dialogInterface, i) -> handler.cancel()).show();
                    }
                }
                str = "Unknown error " + primaryError;
                new AlertDialog.Builder(MainActivityVideos.this).setTitle(MainActivityVideos.this.getString(R.string.insecure_connection)).setMessage(String.format("Error: %s\nURL: %s\n\nCertificate:\n%s", str, error.getUrl(), MainActivityVideos.this.certificateToStr(error.getCertificate()))).setPositiveButton("Proceed", (dialogInterface, i) -> handler.proceed()).setNegativeButton("Cancel", (dialogInterface, i) -> handler.cancel()).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(final WebView view, WebResourceRequest request) {
            MainActivityVideos.this.runOnUiThread(() -> {
                if (MainActivityVideos.this.agentsetFB || !MainActivityVideos.this.isThisFBPage(view.getUrl())) {
                    return;
                }
                MainActivityVideos.this.agentsetFB = true;
                if (MainActivityVideos.this.isFinishing()) {
                    return;
                }
                view.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 6.0.1; SM-J500M Build/MMB29M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36 Instagram 37.0.0.21.97 Android (23/6.0.1; 640dpi; 1440x2560; samsung; en-US; SM-N910F; trlte; qcom; pt_PT; 98288242)");
            });

            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            int indexOf;
            int i;
            int indexOf2;
            if (!url.startsWith("intent://") || (indexOf = url.indexOf(";S.browser_fallback_url=")) == -1 || (indexOf2 = url.indexOf(59, (i = indexOf + 24))) == -1 || indexOf2 == i) {
                return false;
            }
            view.loadUrl(Uri.decode(url.substring(i, indexOf2)));
            return true;
        }
    }

    public static class SearchAutocompleteAdapter extends BaseAdapter implements Filterable {
        private final OnSearchCommitListener commitListener;
        private List<String> completions = new ArrayList<>();
        private final Context mContext;

        public interface OnSearchCommitListener {
            void onSearchCommit(String text);
        }

        public SearchAutocompleteAdapter(Context context, OnSearchCommitListener commitListener) {
            this.mContext = context;
            this.commitListener = commitListener;
        }

        public List<String> getCompletions(String text) {
            byte[] bArr = new byte[16384];
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(URLUtil.composeSearchUrl(text, MainActivityVideos.searchCompleteUrl, "%s")).openConnection();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                int i = 0;
                while (i <= 16384) {
                    int read = bufferedInputStream.read(bArr, i, 16384 - i);
                    if (read == -1) {
                        break;
                    }
                    i += read;
                }
                if (i == 16384) {
                    ArrayList arrayList = new ArrayList();
                    httpURLConnection.disconnect();
                    return arrayList;
                }
                httpURLConnection.disconnect();
                try {
                    JSONArray optJSONArray = new JSONArray(new String(bArr, StandardCharsets.UTF_8)).optJSONArray(1);
                    if (optJSONArray == null) {
                        return new ArrayList<>();
                    }
                    ArrayList arrayList2 = new ArrayList(Math.min(optJSONArray.length(), 10));
                    for (int i2 = 0; i2 < optJSONArray.length() && arrayList2.size() < 10; i2++) {
                        String optString = optJSONArray.optString(i2);
                        if (optString != null && !optString.isEmpty()) {
                            arrayList2.add(optString);
                        }
                    }
                    return arrayList2;
                } catch (Exception unused) {
                    return new ArrayList<>();
                }
            } catch (Exception unused2) {
                return new ArrayList<>();
            }
        }

        @Override
        public int getCount() {
            return this.completions.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                public FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        List completions = SearchAutocompleteAdapter.this.getCompletions(constraint.toString());
                        filterResults.values = completions;
                        filterResults.count = completions.size();
                    }
                    return filterResults;
                }

                @Override
                public void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        SearchAutocompleteAdapter.this.completions = (List) results.values;
                        SearchAutocompleteAdapter.this.notifyDataSetChanged();
                        return;
                    }
                    SearchAutocompleteAdapter.this.notifyDataSetInvalidated();
                }
            };
        }

        @Override
        public Object getItem(int position) {
            return this.completions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        @SuppressLint({"ClickableViewAccessibility"})
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_search_hint, parent, false);
            }
            @SuppressLint("ResourceType") TextView textView = (TextView) convertView.findViewById(16908308);
            textView.setText(this.completions.get(position));
            textView.setOnTouchListener((view, motionEvent) -> {
                if (motionEvent.getAction() != 0) {
                    return false;
                }
                if (motionEvent.getX() > textView.getWidth() - textView.getCompoundPaddingRight()) {
                    this.commitListener.onSearchCommit(getItem(position).toString());
                    return true;
                }
                return false;
            });
            return convertView;
        }
    }

    public static class Tab {
        public boolean isDesktopUA;
        private boolean isOn;
        private final List<ListnerForTabChange> mTabChangedObserverList = new ArrayList<>();
        public final int tabID;
        private String title;
        public final WebView webview;

        public Tab(WebView w, int is, String title) {
            this.webview = w;
            this.tabID = is;
            this.title = title;
        }

        public void notifyTab(String url) {
            for (ListnerForTabChange listnerForTabChange : this.mTabChangedObserverList) {
                listnerForTabChange.notifyTabChanged(url, null);
            }
        }

        public void registerObserver(ListnerForTabChange observer) {
            this.mTabChangedObserverList.add(observer);
        }

        public void setDownStatus(boolean isOn) {
            this.isOn = isOn;
        }

        public void setTitle(String tabTitle) {
            this.title = tabTitle;
        }

        public void unregisterObserver(ListnerForTabChange observer) {
            this.mTabChangedObserverList.remove(observer);
        }
    }

    private void CheckCurrentSite(String str, boolean shouldDownloadLink) {
        checkUrlForDownload(str, shouldDownloadLink);
    }

    public void CommonCallForBottonSheet(int actionType) {
        if (actionType == 43) {
            toggleDesktopUA();
        } else if (actionType == 40) {
            addBookmark();
        } else if (actionType == 44) {
            shareUrl();
        } else if (actionType != 45) {
            if (actionType == 42) {
                startActivityForResult(new Intent(this, ScreenHistory.class), 6941);
            }
        } else if (Objects.requireNonNull(getCurrentWebView().getUrl()).isEmpty() || getCurrentWebView().getUrl().equalsIgnoreCase("about:blank")) {
        } else {
            ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("URL", getCurrentWebView().getUrl()));
            UtilsForApp.showToastMsg(getResources().getString(R.string.url_copied), this, false);
        }
    }


    public void DialogNoVideoDound() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_video_not_found);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(true);
        TextView textView = (TextView) dialog.findViewById(R.id.txtSubMit);
        TextView textView2 = (TextView) dialog.findViewById(R.id.reportTxt);
        RelativeLayout relativeLayout = (RelativeLayout) dialog.findViewById(R.id.mainNoView);
        RelativeLayout relativeLayout2 = (RelativeLayout) dialog.findViewById(R.id.realtiveTwitter);
        TextView textView3 = (TextView) dialog.findViewById(R.id.txtSubMitTwitter);
        String host = UtilsForApp.getHost(this.downurl);
        if (!host.equalsIgnoreCase("twitter.com") && !host.equalsIgnoreCase("mobile.twitter.com")) {
            relativeLayout2.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            relativeLayout2.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        textView.setOnClickListener(v -> dialog.dismiss());
        textView3.setOnClickListener(v -> dialog.dismiss());
        textView2.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void DialogNotSupportedSite() {
        if (this.tabs != null && getCurrentWebView().getUrl() != null && !getCurrentWebView().getUrl().equals("about:blank") && getCurrentWebView() != null) {
            getCurrentWebView().clearHistory();
            getCurrentWebView().clearCache(true);
            this.autoCompleteTextView.setText("about:blank");
            checkAndAddIconInET("about:blank");
            getCurrentWebView().requestFocus();
        }
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_not_valid_site);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        TextView textView = (TextView) dialog.findViewById(R.id.tvNotice);
        ((TextView) dialog.findViewById(R.id.txtSubMit)).setOnClickListener(v -> {
            if (MainActivityVideos.this.getCurrentWebView() != null) {
                MainActivityVideos.this.getCurrentWebView().clearHistory();
                MainActivityVideos.this.getCurrentWebView().clearCache(true);
                MainActivityVideos.this.autoCompleteTextView.setText("about:blank");
                MainActivityVideos.this.checkAndAddIconInET("about:blank");
                MainActivityVideos.this.getCurrentWebView().requestFocus();
                MainActivityVideos mainActivityVideos = MainActivityVideos.this;
                mainActivityVideos.loadUrl("about:blank", mainActivityVideos.getCurrentWebView());
                MainActivityVideos.this.downloadButton.setVisibility(View.GONE);
                if (!MainActivityVideos.this.getCurrentWebView().getSettings().getUserAgentString().equals(MainActivityVideos.this.currentUserAgent)) {
                    MainActivityVideos.this.agentsetFB = false;
                    MainActivityVideos.this.getCurrentWebView().getSettings().setUserAgentString(MainActivityVideos.this.currentUserAgent);
                }
            }
            dialog.dismiss();
        });
        dialog.setOnDismissListener(dialog2 -> {
            if (MainActivityVideos.this.getCurrentWebView() != null) {
                MainActivityVideos.this.getCurrentWebView().clearHistory();
                MainActivityVideos.this.getCurrentWebView().clearCache(true);
                MainActivityVideos.this.autoCompleteTextView.setText("about:blank");
                MainActivityVideos.this.checkAndAddIconInET("about:blank");
                MainActivityVideos.this.getCurrentWebView().requestFocus();
                MainActivityVideos mainActivityVideos = MainActivityVideos.this;
                mainActivityVideos.loadUrl("about:blank", mainActivityVideos.getCurrentWebView());
                MainActivityVideos.this.downloadButton.setVisibility(View.GONE);
                if (MainActivityVideos.this.getCurrentWebView().getSettings().getUserAgentString().equals(MainActivityVideos.this.currentUserAgent)) {
                    return;
                }
                MainActivityVideos.this.agentsetFB = false;
                MainActivityVideos.this.getCurrentWebView().getSettings().setUserAgentString(MainActivityVideos.this.currentUserAgent);
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(-1, -2);
    }

    private void LoadCustomUrlInCurrentTab(String url) {
        getCurrentWebView().requestFocus();
        loadUrl(url, getCurrentWebView());
        this.autoCompleteTextView.clearFocus();
    }

    private void ShareText() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name));
        intent.putExtra("android.intent.extra.TEXT", this.rendomtextg);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.share_via)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBookmark() {
        int VerifyAndAddFavorite = this.mLocalAllTransactionsDB.VerifyAndAddFavorite(getCurrentWebView().getTitle(), Objects.requireNonNull(getCurrentWebView().getUrl()), getCurrentWebView().getFavicon(), false);
        if (VerifyAndAddFavorite == 0) {
            return;
        }
        if (VerifyAndAddFavorite == 1) {
            UtilsForApp.showToastMsg(getString(R.string.add_bookmark), this, false);
        } else if (VerifyAndAddFavorite == 2) {
            UtilsForApp.showToastMsg(getString(R.string.bookmark_updated), this, false);
        }
    }

    private void callApidata() {
        int nextInt = new Random().nextInt(1292);
        if (this.quotslist.size() > 0) {
            this.txtData.setText(this.quotslist.get(nextInt).getQuotes());
            this.rendomtextg = this.quotslist.get(nextInt).getQuotes() + "\n\nDownloader App:\n" + getString(R.string.app_download_link);
        }
    }

    public static StringBuilder mnewnew(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb;
    }

    @SuppressLint({"DefaultLocale"})
    public String certificateToStr(SslCertificate certificate) {
        if (certificate == null) {
            return null;
        }
        String str = "";
        SslCertificate.DName issuedTo = certificate.getIssuedTo();
        if (issuedTo != null) {
            StringBuilder m = mnewnew("", "Issued to: ");
            m.append(issuedTo.getDName());
            m.append(StringUtils.LF);
            str = m.toString();
        }
        SslCertificate.DName issuedBy = certificate.getIssuedBy();
        if (issuedBy != null) {
            StringBuilder m2 = mnewnew(str, "Issued by: ");
            m2.append(issuedBy.getDName());
            m2.append(StringUtils.LF);
            str = m2.toString();
        }
        Date validNotBeforeDate = certificate.getValidNotBeforeDate();
        if (validNotBeforeDate != null) {
            StringBuilder m3 = new StringBuilder(str);
            m3.append(String.format("Issued on: %tF %tT %tz\n", validNotBeforeDate, validNotBeforeDate, validNotBeforeDate));
            str = m3.toString();
        }
        Date validNotAfterDate = certificate.getValidNotAfterDate();
        if (validNotAfterDate != null) {
            StringBuilder m4 = new StringBuilder(str);
            m4.append(String.format("Expires on: %tF %tT %tz\n", validNotAfterDate, validNotAfterDate, validNotAfterDate));
            return m4.toString();
        }
        return str;
    }

    public void checkAndAddIconInET(String s) {
        try {
            if (s.equalsIgnoreCase("about:blank")) {
                this.imgReloadCancel.setVisibility(View.GONE);
                return;
            }
            this.imgReloadCancel.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void clearHistoryCache() {
        WebView currentWebView = getCurrentWebView();
        currentWebView.clearCache(true);
        currentWebView.clearFormData();
        currentWebView.clearHistory();
    }

    private void closeAllTabs() {
        ArrayList<Tab> arrayList = this.tabs;
        if (arrayList != null && arrayList.size() > 0) {
            for (int i = 0; i < this.tabs.size(); i++) {
                ((FrameLayout) findViewById(R.id.webviews)).removeView(this.tabs.get(i).webview);
                this.tabs.get(i).webview.destroy();
            }
            this.tabs.clear();
            currentTabIndex = 0;
            newTab("");
        }
        getCurrentWebView().setVisibility(View.VISIBLE);
        this.autoCompleteTextView.setText(getCurrentWebView().getUrl());
        checkAndAddIconInET(Objects.requireNonNull(getCurrentWebView().getUrl()));
        getCurrentWebView().requestFocus();
        getCurrentTab().notifyTab(getCurrentWebView().getUrl());
    }

    public void closeCurrentTab() {
        if (getCurrentWebView().getUrl() != null) {
            getCurrentWebView().getUrl().equals("about:blank");
        }
        ((FrameLayout) findViewById(R.id.webviews)).removeView(getCurrentWebView());
        getCurrentWebView().destroy();
        this.tabs.remove(currentTabIndex);
        if (currentTabIndex >= this.tabs.size()) {
            currentTabIndex = this.tabs.size() - 1;
        }
        if (currentTabIndex == -1) {
            currentTabIndex = 0;
            newTab("");
        }
        getCurrentWebView().setVisibility(View.VISIBLE);
        this.autoCompleteTextView.setText(getCurrentWebView().getUrl());
        checkAndAddIconInET(Objects.requireNonNull(getCurrentWebView().getUrl()));
        getCurrentWebView().requestFocus();
        getCurrentTab().notifyTab(getCurrentWebView().getUrl());
    }

    private WebView createWebView() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        WebView webView = new WebView(this);
        webView.setBackgroundColor(-1);
        WebSettings settings = webView.getSettings();
        settings.setLoadsImagesAutomatically(true);
        settings.setSupportZoom(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        webView.addJavascriptInterface(this, "mJava");
        webView.addJavascriptInterface(this, "INTERFACE");
        webView.addJavascriptInterface(new FacebookViewInterface(webView), "facebookDataGet");
        webView.addJavascriptInterface(new InstaWebViewInterface(this, webView), "dataGet");
        webView.setWebViewClient(new WebviewProgress(progressBar));
        webView.setWebChromeClient(new WebChromeClient() {


            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return true;
            }

            @Override
            public void onHideCustomView() {
                MainActivityVideos.this.findViewById(R.id.main_layout).setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                MainActivityVideos.this.injectCSS(view);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                    MainActivityVideos.this.isReload = true;
                    MainActivityVideos.this.imgReloadCancel.setImageResource(R.drawable.verysmall_iconreload);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                if (view == null || view.getUrl() == null) {
                    return;
                }
                if (MainActivityVideos.this.privacyFlag) {
                    MainActivityVideos.this.updateHistory("", view.getUrl(), icon);
                }
                MainActivityVideos.this.mLocalAllTransactionsDB.VerifyAndAddFavorite("", view.getUrl(), icon, true);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (MainActivityVideos.this.getCurrentTab() == null) {
                    return;
                }
                if (title != null && !title.isEmpty()) {
                    MainActivityVideos.this.getCurrentTab().setTitle(title);
                }
                if (view == null || view.getUrl() == null || !MainActivityVideos.this.privacyFlag) {
                    return;
                }
                MainActivityVideos.this.updateHistory(title, view.getUrl(), view.getFavicon());
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                MainActivityVideos.this.findViewById(R.id.main_layout).setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean onShowFileChooser(WebView webView2, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (MainActivityVideos.this.fileUploadCallback != null) {
                    MainActivityVideos.this.fileUploadCallback.onReceiveValue(null);
                }
                MainActivityVideos.this.fileUploadCallback = filePathCallback;
                Intent createIntent = fileChooserParams.createIntent();
                try {
                    MainActivityVideos.this.fileUploadCallbackShouldReset = true;
                    MainActivityVideos.this.startActivityForResult(createIntent, 1);
                    return true;
                } catch (Exception unused) {
                    createIntent.setType("*/*");
                    try {
                        MainActivityVideos.this.fileUploadCallbackShouldReset = false;
                        MainActivityVideos.this.startActivityForResult(createIntent, 1);
                        return true;
                    } catch (Exception unused2) {
                        UtilsForApp.showToastMsg(MainActivityVideos.this.getString(R.string.can_not_open_file_chooser), MainActivityVideos.this, false);
                        MainActivityVideos.this.fileUploadCallback = null;
                        return false;
                    }
                }
            }
        });
        webView.setOnLongClickListener(view -> {
            String str;
            String string;
            WebView.HitTestResult hitTestResult = webView.getHitTestResult();
            int type = hitTestResult.getType();
            String str2 = null;
            if (type != 0 && type != 4) {
                if (type == 5) {
                    str = hitTestResult.getExtra();
                    showLongPressMenu(str2, str);
                    return true;
                } else if (type == 7) {
                    string = hitTestResult.getExtra();
                    String str3 = str2;
                    str2 = string;
                    str = str3;
                    showLongPressMenu(str2, str);
                    return true;
                } else if (type != 8) {
                    return false;
                }
            }
            Message obtainMessage = new Handler().obtainMessage();
            webView.requestFocusNodeHref(obtainMessage);
            string = obtainMessage.getData().getString("url");
            if ("".equals(string)) {
                string = null;
            }
            String string2 = obtainMessage.getData().getString("src");
            if (!"".equals(string2)) {
                str2 = string2;
            }
            if (string == null && str2 == null) {
                return false;
            }
            String str32 = str2;
            str2 = string;
            str = str32;
            showLongPressMenu(str2, str);
            return true;
        });
        webView.setDownloadListener((str, str2, str3, str4, j) -> {
            final String guessFileName = URLUtil.guessFileName(str, str3, str4);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityVideos.this);
            builder.setTitle(getResources().getString(R.string.download));
            builder.setMessage(String.format(Locale.getDefault(), "Filename: %s\nSize: %.2f MB", guessFileName.trim(), (j / 1024.0d) / 1024.0d));
            builder.setPositiveButton(getResources().getString(R.string.download), (dialog, which) -> {
                dialog.dismiss();
                MainActivityVideos.this.startDownload(str, guessFileName);
            });
            builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
            final AlertDialog create = builder.create();
            create.setOnShowListener(dialogInterface -> {
                create.getButton(-1).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                create.getButton(-2).setTextColor(-7829368);
            });
            create.show();
        });
        webView.setFindListener((i, i2, z) -> {
            this.searchCount.setText(i2 == 0 ? getString(R.string.not_found) : String.format(Locale.getDefault(), "%d / %d", i + 1, i2));
        });
        return webView;
    }

    private void displayExtraDataDialog() {
        boolean z;
        final String str;
        if (isFinishing()) {
            return;
        }
        if (!this.received_url.isEmpty()) {
            str = this.received_url;
        } else if (!this.received_data.isEmpty()) {
            str = this.received_data;
            if (!UtilsForApp.IsValidUrl(str)) {
                z = false;
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                View inflate = getLayoutInflater().inflate(R.layout.bottom_screen_copy_dialog, (ViewGroup) null);
                bottomSheetDialog.setContentView(inflate);
                bottomSheetDialog.getBehavior().setDraggable(false);
                TextView textView = (TextView) inflate.findViewById(R.id.txtHead);
                TextView textView2 = (TextView) inflate.findViewById(R.id.txtData);
                TextView textView3 = (TextView) inflate.findViewById(R.id.txtOpenTab);
                TextView textView4 = (TextView) inflate.findViewById(R.id.txtPasteTab);
                ImageView imageView = (ImageView) inflate.findViewById(R.id.imgCancel);
                if (!z) {
                    textView.setText(getString(R.string.choose_action_with_your_link));
                    textView2.setText(str);
                    textView4.setVisibility(View.VISIBLE);
                    textView3.setText(getString(R.string.open));
                } else {
                    textView.setText(getString(R.string.select_for_shared_text));
                    textView2.setText(str);
                    textView4.setVisibility(View.GONE);
                    textView3.setText(getString(R.string.search));
                }
                textView3.setOnClickListener(view -> {
                    bottomSheetDialog.dismiss();
                    LoadCustomUrlInCurrentTab(str);
                });
                textView4.setOnClickListener(view -> {
                    bottomSheetDialog.dismiss();
                    AllInOneAds.getInstance().showInterWithId(this, AdHelper.allVideoDownloadId, () -> {
                        Intent intent = new Intent(this, PasteLinkActivity.class);
                        intent.putExtra("other_link", str);
                        intent.putExtra("type", "dialog");
                        startActivity(intent);
                    });
                });
                imageView.setOnClickListener(view -> bottomSheetDialog.dismiss());
                bottomSheetDialog.setOnDismissListener(dialog -> {

                });
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();
            }
        } else {
            str = this.copy_data;
        }
        z = true;
        final BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(this);
        View inflate2 = getLayoutInflater().inflate(R.layout.bottom_screen_copy_dialog, (ViewGroup) null);
        bottomSheetDialog2.setContentView(inflate2);
        bottomSheetDialog2.getBehavior().setDraggable(false);
        TextView textView5 = (TextView) inflate2.findViewById(R.id.txtHead);
        TextView textView22 = (TextView) inflate2.findViewById(R.id.txtData);
        TextView textView32 = (TextView) inflate2.findViewById(R.id.txtOpenTab);
        TextView textView42 = (TextView) inflate2.findViewById(R.id.txtPasteTab);
        ImageView imageView2 = (ImageView) inflate2.findViewById(R.id.imgCancel);

        if (!z) {
            textView5.setText(getString(R.string.choose_action_with_your_link));
            textView22.setText(str);
            textView42.setVisibility(View.VISIBLE);
            textView32.setText(getString(R.string.open));
        } else {
            textView5.setText(getString(R.string.select_for_shared_text));
            textView22.setText(str);
            textView42.setVisibility(View.GONE);
            textView32.setText(getString(R.string.search));
        }

        textView32.setOnClickListener(view -> {
            bottomSheetDialog2.dismiss();
            LoadCustomUrlInCurrentTab(str);
        });
        textView42.setOnClickListener(view -> {
            bottomSheetDialog2.dismiss();
            Intent intent = new Intent(this, PasteLinkActivity.class);
            intent.putExtra("other_link", str);
            intent.putExtra("type", "dialog");
            startActivity(intent);
        });
        imageView2.setOnClickListener(view -> bottomSheetDialog2.dismiss());
        bottomSheetDialog2.setOnDismissListener(dialog -> {

        });
        bottomSheetDialog2.setCancelable(true);
        bottomSheetDialog2.setCanceledOnTouchOutside(true);
        bottomSheetDialog2.show();
    }

    private void drawerToggle() {
        DrawerLayout drawerLayout = this.drawer;
        if (drawerLayout != null) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                this.drawer.closeDrawer(GravityCompat.START);
            } else {
                this.drawer.openDrawer(GravityCompat.START);
            }
        }
    }

    private void exitAppLogic() {

        final boolean[] zArr = {false};
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        final View inflate = getLayoutInflater().inflate(R.layout.dialog_exit_ad, (ViewGroup) null);
        bottomSheetDialog.setContentView(inflate);
        bottomSheetDialog.getBehavior().setDraggable(false);

        AllInOneAds.getInstance().showTopNativeWithId(this, AdHelper.allVideoDownloadId, inflate.findViewById(R.id.banner_container_exit));

        ((Button) inflate.findViewById(R.id.btnExit)).setOnClickListener(view -> {
            zArr[0] = true;
            bottomSheetDialog.cancel();
            exitFinalApp();
        });
        bottomSheetDialog.setOnDismissListener(dialog -> {

        });
        bottomSheetDialog.show();
    }

    @SuppressLint("WrongConstant")
    private void exitFinalApp() {
        Intent intent = new Intent(this, ClassExit.class);
        intent.setFlags(268468224);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public Tab getCurrentTab() {
        int size = this.tabs.size();
        int i = currentTabIndex;
        if (size > i) {
            return this.tabs.get(i);
        }
        return this.tabs.get(0);
    }

    public WebView getCurrentWebView() {
        return getCurrentTab().webview;
    }

    private String getUrlFromIntent(Intent intent) {
        if ("android.intent.action.VIEW".equals(intent.getAction()) && intent.getData() != null) {
            return intent.getDataString();
        }
        if ("android.intent.action.SEND".equals(intent.getAction()) && "text/plain".equals(intent.getType())) {
            return intent.getStringExtra("android.intent.extra.TEXT");
        }
        if ("android.intent.action.WEB_SEARCH".equals(intent.getAction()) && intent.getStringExtra(SearchIntents.EXTRA_QUERY) != null) {
            return intent.getStringExtra(SearchIntents.EXTRA_QUERY);
        }
        return "";
    }


    private void hideKeyboard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.autoCompleteTextView.getWindowToken(), 0);
    }


    private void initColorOfItem() {
        int rgb = Color.rgb(224, 224, 224);
        this.autoCompleteTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.searchEdit.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.searchEdit.setBackgroundColor(rgb);
        this.searchCount.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        findViewById(R.id.main_layout).setBackgroundColor(-1);
    }

    public void initStaticVariables() {
        if (DclassApp.UnwantedDomains == null) {
            UtilsForApp.FillUnwanted();
        }
        if (DclassApp.allUrlRegx == null) {
            UtilsForApp.FillRegexers();
        }
        String str = DclassApp.localparsdata;
        if (str == null || str.isEmpty()) {
            UtilsForApp.FillLocalPardata();
        }
    }

    private void initWorkAdBlkFetch() {
        PeriodicWorkRequest build = new PeriodicWorkRequest.Builder(UtilsForBlockAds.class, 6L, TimeUnit.DAYS).setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()).setBackoffCriteria(BackoffPolicy.LINEAR, WorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS).setInitialDelay(2L, TimeUnit.SECONDS).build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("ad_blok_jb", ExistingPeriodicWorkPolicy.KEEP, build);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(build.getId()).observe(this, (Observer) obj -> {});
    }

    public void injectCSS(WebView webview) {
        try {
            if (getCurrentTab().isDesktopUA) {
                return;
            }
            webview.evaluateJavascript("javascript:document.querySelector('meta[name=viewport]').content='width=device-width, initial-scale=1.0, maximum-scale=3.0, user-scalable=1';", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isSpecialUrl(String url) {
        return url.startsWith("file://") || url.endsWith("about:blank") || url.endsWith("/#");
    }

    public boolean isThisFBPage(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        return (url.contains("facebook.com/") || url.contains("fb.com/") || url.contains("fb.watch/")) && !url.contains("instagram.com");
    }

    public boolean isThisInstaPage(String url) {
        return url != null && url.contains("instagram.com/");
    }


    public void loadUrl(String url, WebView webview) {
        String str;
        String trim = url.trim();
        if (trim.isEmpty()) {
            trim = "about:blank";
        }
        if (trim.equalsIgnoreCase("about:blank")) {
            this.relativeHomepage.setVisibility(View.VISIBLE);
            this.scrollHomepage.setVisibility(View.VISIBLE);
            this.relativeSarch.setVisibility(View.VISIBLE);
            visiblweTopView(true);
            this.autoCompleteTextView.setVisibility(View.GONE);
        } else {

            this.relativeHomepage.setVisibility(View.GONE);
            this.scrollHomepage.setVisibility(View.GONE);
            this.relativeSarch.setVisibility(View.GONE);
            visiblweTopView(false);
            this.autoCompleteTextView.setVisibility(View.VISIBLE);
        }
        if (!trim.startsWith("about:") && !trim.startsWith("javascript:") && !trim.startsWith("file:") && !trim.startsWith("data:") && (trim.indexOf(32) != -1 || !Patterns.WEB_URL.matcher(trim).matches())) {
            str = URLUtil.composeSearchUrl(trim, this.currentSearchEngine, "%s");
        } else {
            int indexOf = trim.indexOf(35);
            String guessUrl = URLUtil.guessUrl(trim);
            if (indexOf == -1 || guessUrl.indexOf(35) != -1) {
                str = guessUrl;
            } else {
                StringBuilder m = new StringBuilder(guessUrl);
                m.append(trim.substring(indexOf));
                str = m.toString();
            }
        }
        if (isThisFBPage(str)) {
            webview.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 6.0.1; SM-J500M Build/MMB29M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36 Instagram 37.0.0.21.97 Android (23/6.0.1; 640dpi; 1440x2560; samsung; en-US; SM-N910F; trlte; qcom; pt_PT; 98288242)");
        } else if (!webview.getSettings().getUserAgentString().equals(this.currentUserAgent)) {
            this.agentsetFB = false;
            webview.getSettings().setUserAgentString(this.currentUserAgent);
        }
        webview.loadUrl(str);
        hideKeyboard();
    }

    private void newTab(String url) {
        try {
            WebView createWebView = createWebView();
            newTabCommon(createWebView);
            loadUrl(url, createWebView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void newTabCommon(WebView webview) {
        boolean z = !this.tabs.isEmpty() && getCurrentTab().isDesktopUA;
        if (z) {
            webview.getSettings().setUserAgentString(this.desktopUA);
        } else {
            webview.getSettings().setUserAgentString(this.currentUserAgent);
        }
        webview.getSettings().setUseWideViewPort(z);
        webview.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        webview.setVisibility(View.GONE);
        int i = this.tabI + 1;
        this.tabI = i;
        Tab tab = new Tab(webview, i, "");
        tab.registerObserver(this);
        tab.isDesktopUA = z;
        this.tabs.add(tab);
        getCurrentTab().notifyTab("about:blank");
        this.webviews.addView(webview);
    }

    private void openFiles() {
        if (Build.VERSION.SDK_INT <= 29) {
            TedPermission.create().setPermissionListener(new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    MainActivityVideos.this.startActivity(new Intent(MainActivityVideos.this, Downloader_Creation_Activity.class));
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {

                }
            }).setRationaleTitle(getString(R.string.storage_permission)).setRationaleMessage(getString(R.string.please_allow_to_downloaded_video)).setDeniedTitle(R.string.permission_denied).setDeniedMessage(getString(R.string.if_you_reject_permission)).setGotoSettingButtonText(getResources().getString(R.string.settings)).setPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").check();
        } else {
            startActivity(new Intent(this, Downloader_Creation_Activity.class));

        }
    }

    public void setFbJS(WebView mWebView, String url) {
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { var items=document.getElementsByClassName('_52jh _7om2 _15kk _15ks _15km _4b47 _4b46'); for (var i = 0; i < items.length; i++) { items[i].style.marginRight = '10px'; }})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_26pa _55x2 _56bf _55x2 jjseejtz');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\nfacebookDataGet.onMultiplePostLoading(ary)}})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_9brz    ');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\nfacebookDataGet.onWatchPostLoading(ary)}})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_2rea _24e1 _412_ _bpa _vyy _5t8z');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\nfacebookDataGet.onLoading(ary)}})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_5rgr _5gh8 _3-hy async_like');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\nfacebookDataGet.onSinglePostLoading(ary)}})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_7om2');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\nfacebookDataGet.onLinkPostLoading(ary)}})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('html-renderer Chrome Android');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\nfacebookDataGet.onSinglePostLoading(ary)}})()");
        }
    }

    public void setInstaJS(WebView mWebView, String url) {
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { var items=document.getElementsByClassName('_aamz'); for (var i = 0; i < items.length; i++) { items[i].style.marginRight = '10px'; }})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_aa08 _aatb _aatc _aatd _aatf _aath');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\ndataGet.onAccountPostLoading(ary)}})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_abj9 _ab6k _aatb _aatc _aatd _aatf');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\ndataGet.onLoading(ary)}})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_aatb _aatc _aatd _aatf');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\ndataGet.onLoadingPublic(ary)}})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_aa08 _aatb _aatc _aatd _aatf');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\ndataGet.onSearchPostLoading(ary)}})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_abj9 _ab6k _aatb _aatc _aatd _aatf _aath');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\ndataGet.onFeedBatchPostLoading(ary)}})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_aa6a _aatb _aatd _aatf _aath');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\ndataGet.onAccountBatchPostLoading(ary)}})()");
        }
        if (mWebView != null) {
            mWebView.loadUrl("javascript:(function() { const ary=[];\nvar parents=document.getElementsByClassName('_aa6a _aatb _aatd _aatf');\nfor (var j = 0; j < parents.length; j++) {\n\tary[j]=parents[j].innerHTML\n}\nif (parents.length > 0 ) {\ndataGet.onSinglePostLoading(ary)}})()");
        }
    }


    private void setUpHomePage() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerPost);
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmain);
        this.relativeHomepage = (RelativeLayout) findViewById(R.id.relativeHomepage);
        this.scrollHomepage = (RelativeLayout) findViewById(R.id.scrollHomepage);
        this.relativeSarch = (RelativeLayout) findViewById(R.id.relativeSarch);
        setSupportActionBar(toolbar);
        this.imgReloadCancel = (ImageView) findViewById(R.id.imgReloadCancel);
        //   this.imgQR2 = (ImageView) findViewById(R.id.imgQR2);
        ImageView imgQR = (ImageView) findViewById(R.id.imgQR);
        this.scrollvieww = (ScrollView) findViewById(R.id.scrollvieww);
        this.txtData = (TextView) findViewById(R.id.txtData);
        this.icon_overflow = (ImageView) findViewById(R.id.icon_overflow);
        ImageView icon_overflowTop = (ImageView) findViewById(R.id.icon_overflowTop);
        ImageView icon_rateapp = (ImageView) findViewById(R.id.icon_rateapp);
        this.relad = (RelativeLayout) findViewById(R.id.relad);
        RelativeLayout realative1 = (RelativeLayout) findViewById(R.id.realative1);
        RelativeLayout realative2 = (RelativeLayout) findViewById(R.id.realative2);
        RelativeLayout realative3 = (RelativeLayout) findViewById(R.id.realative3);
        RelativeLayout realative4 = (RelativeLayout) findViewById(R.id.realative4);
        RelativeLayout realative5 = (RelativeLayout) findViewById(R.id.realative5);
        RelativeLayout realative6 = (RelativeLayout) findViewById(R.id.realative6);
        RelativeLayout realative7 = (RelativeLayout) findViewById(R.id.realative7);
        RelativeLayout realative8 = (RelativeLayout) findViewById(R.id.realative8);
        RelativeLayout realative9 = (RelativeLayout) findViewById(R.id.realative9);
        RelativeLayout realative10 = (RelativeLayout) findViewById(R.id.realative10);
        RelativeLayout realative11 = (RelativeLayout) findViewById(R.id.realative11);

        ImageView icon_share = (ImageView) findViewById(R.id.icon_share);
        ImageView icon_refresh = (ImageView) findViewById(R.id.icon_refresh);

        this.imgTab3 = (ImageView) findViewById(R.id.imgTab3);
        ImageView imgAddNewtab = (ImageView) findViewById(R.id.imgAddNewtab);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.realtiveTab1);
        RelativeLayout realtiveTab2 = (RelativeLayout) findViewById(R.id.realtiveTab2);
        this.realtiveTab3 = (RelativeLayout) findViewById(R.id.realtiveTab3);
        RelativeLayout realtiveTab4 = (RelativeLayout) findViewById(R.id.realtiveTab4);
        RelativeLayout realtiveTab5 = (RelativeLayout) findViewById(R.id.realtiveTab5);
        RelativeLayout realtivrFbLink = (RelativeLayout) findViewById(R.id.realtivrFbLink);
        RelativeLayout realtivrInstaLink = (RelativeLayout) findViewById(R.id.realtivrInstaLink);
        RelativeLayout relHelp = (RelativeLayout) findViewById(R.id.relHelp);
        if (this.prefs.getBoolean(ConstantForApp.IS_USER_RATED, false)) {
            icon_rateapp.setVisibility(View.GONE);
        } else {
            icon_rateapp.setVisibility(View.VISIBLE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateTabList();
        adp_tab adp_tabVar = new adp_tab(this, this.ArrTabList);
        this.mTabAdapter = adp_tabVar;
        recyclerView.setAdapter(adp_tabVar);
        this.mTabAdapter.setOnItemClickListener((i, i2, str) -> {
            if (i != -1) {
                if (i2 != 1) {
                    if (i2 == 0) {
                        drawerToggle();
                        switchToTab(i);
                        return;
                    }
                    return;
                }
                ArrayList<Tab> arrayList = this.tabs;
                if (arrayList == null || arrayList.size() <= i) {
                    return;
                }
                ((FrameLayout) findViewById(R.id.webviews)).removeView(this.tabs.get(i).webview);
                this.tabs.get(i).webview.destroy();
                this.tabs.remove(i);
                if (currentTabIndex >= this.tabs.size()) {
                    currentTabIndex = this.tabs.size() - 1;
                }
                if (currentTabIndex == -1) {
                    currentTabIndex = 0;
                    newTab("");
                }
                getCurrentWebView().setVisibility(View.VISIBLE);
                this.autoCompleteTextView.setText(getCurrentWebView().getUrl());
                checkAndAddIconInET(Objects.requireNonNull(getCurrentWebView().getUrl()));
                getCurrentWebView().requestFocus();
                getCurrentTab().notifyTab(getCurrentWebView().getUrl());
                drawerToggle();
            }
        });
        this.drawer.addDrawerListener(new DrawerLayout.DrawerListener() {


            @Override
            public void onDrawerClosed(@NonNull View view) {
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
            }

            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
            }

            @Override
            public void onDrawerStateChanged(int i) {
                MainActivityVideos.this.updateTabList();
            }
        });
        relativeLayout.setOnClickListener(this);
        realtiveTab2.setOnClickListener(this);
        this.realtiveTab3.setOnClickListener(this);
        realtiveTab4.setOnClickListener(this);
        realtiveTab5.setOnClickListener(this);
        realtivrFbLink.setOnClickListener(this);
        realtivrInstaLink.setOnClickListener(this);
        relHelp.setOnClickListener(this);
        icon_share.setOnClickListener(this);
        icon_refresh.setOnClickListener(this);
        realative1.setOnClickListener(this);
        realative2.setOnClickListener(this);
        realative3.setOnClickListener(this);
        realative4.setOnClickListener(this);
        realative5.setOnClickListener(this);
        realative6.setOnClickListener(this);
        realative7.setOnClickListener(this);
        realative8.setOnClickListener(this);
        realative9.setOnClickListener(this);
        realative10.setOnClickListener(this);
        realative11.setOnClickListener(this);
        this.relativeSarch.setOnClickListener(this);
        this.icon_overflow.setOnClickListener(this);
        icon_overflowTop.setOnClickListener(this);
        icon_rateapp.setOnClickListener(this);
        imgAddNewtab.setOnClickListener(this);
        this.imgReloadCancel.setOnClickListener(this);
        //      this.imgQR2.setOnClickListener(this);
        imgQR.setOnClickListener(this);
        try {
            InputStream open = getAssets().open("quots_rendom.json");
            int available = open.available();
            byte[] bArr = new byte[available];
            open.read(bArr);
            open.close();
            if (available > 0) {
                this.quotslist = (ArrayList) new Gson().fromJson(new String(bArr), new TypeToken<ArrayList<ModelRendomQuots>>() {
                }.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        callApidata();
    }

    private void shareUrl() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.putExtra("android.intent.extra.TEXT", getCurrentWebView().getUrl());
        intent.setType("text/plain");
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.share_url)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String menu(String str, String str2) {
        return str + str2;
    }

    private void showLongPressMenu(String linkUrl, String imageUrl) {
        String[] strArr = {getResources().getString(R.string.open_new_tab), getResources().getString(R.string.copy_url)};
        if (imageUrl == null) {
            if (linkUrl == null) {
                throw new IllegalArgumentException("Bad null arguments in showLongPressMenu");
            }
        } else if (linkUrl == null) {
            linkUrl = menu("Image: ", imageUrl);
            String finalImageUrl1 = imageUrl;
            new AlertDialog.Builder(this).setTitle(linkUrl).setItems(strArr, (dialogInterface, i) -> {
                if (i == 0) {
                    newTab(finalImageUrl1);
                } else if (i != 1) {
                } else {
                    ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("URL", finalImageUrl1));
                }
            }).show();
        }
        imageUrl = linkUrl;
        String finalImageUrl = imageUrl;
        new AlertDialog.Builder(this).setTitle(linkUrl).setItems(strArr, (dialogInterface, i) -> {
            if (i == 0) {
                newTab(finalImageUrl);
            } else if (i != 1) {
            } else {
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("URL", finalImageUrl));
            }
        }).show();
    }

    private void showRateDialog() {
        if (this.prefs.getBoolean(ConstantForApp.IS_USER_RATED, false)) {
            return;
        }
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_rating);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        final TextView textView = (TextView) dialog.findViewById(R.id.txtTitle3);
        final TextView textView2 = (TextView) dialog.findViewById(R.id.txtRate);
        ((TextView) dialog.findViewById(R.id.txtTitle)).setText(getString(R.string.rate_video_downloader_app));
        ((TextView) dialog.findViewById(R.id.txtTitle2)).setText(getString(R.string.thanks_for_your_support));
        final int[] iArr = {4};
        ((RatingBar) dialog.findViewById(R.id.ratingBar)).setOnRatingBarChangeListener((ratingBar, f, z) -> {
            iArr[0] = (int) f;
            if (iArr[0] > 4) {
                textView.setText("RATE ON GOOGLE PLAY");
                textView2.setVisibility(View.GONE);
                return;
            }
            textView.setText("RATE APP");
            textView2.setVisibility(View.GONE);
        });
        ((TextView) dialog.findViewById(R.id.txtRemondLater)).setOnClickListener(view -> dialog.dismiss());
        textView2.setOnClickListener(view -> {
            dialog.dismiss();
            try {
                try {
                    this.prefs.edit().putBoolean(ConstantForApp.IS_USER_RATED, true).apply();
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(intent);
                } catch (Exception unused) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(-1, -2);
    }

    public void startDownload(String url, String filename) {
        this.downloadURL = url;
        this.downloadFilename = filename;
        if (Build.VERSION.SDK_INT <= 29) {
            TedPermission.create().setPermissionListener(new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    MainActivityVideos.this.doDownloadStart();

                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {

                }
            }).setRationaleTitle(getString(R.string.storage_permission)).setRationaleMessage(getString(R.string.please_allow_storage_permission)).setDeniedTitle(getString(R.string.permission_denied)).setDeniedMessage(getString(R.string.if_you_reject_permission)).setGotoSettingButtonText(getResources().getString(R.string.settings)).setPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").check();
        } else {
            doDownloadStart();
        }
    }

    private void switchToTab(int tab) {
        boolean z = currentTabIndex != tab;
        getCurrentWebView().setVisibility(View.GONE);
        currentTabIndex = tab;
        getCurrentWebView().setVisibility(View.VISIBLE);
        this.autoCompleteTextView.setText(getCurrentWebView().getUrl());
        checkAndAddIconInET(getCurrentWebView().getUrl());
        getCurrentWebView().requestFocus();
        if (z) {
            getCurrentTab().notifyTab(getCurrentWebView().getUrl());
        }
    }

    private void toggleDesktopUA() {
        Tab currentTab = getCurrentTab();
        boolean z = !currentTab.isDesktopUA;
        currentTab.isDesktopUA = z;
        if (z) {
            getCurrentWebView().getSettings().setUserAgentString(this.desktopUA);
        } else {
            getCurrentWebView().getSettings().setUserAgentString(this.currentUserAgent);
        }
        getCurrentWebView().getSettings().setUseWideViewPort(currentTab.isDesktopUA);
        getCurrentWebView().reload();
    }

    public void updateHistory(final String title, final String url, final Bitmap favicon) {
        if (isSpecialUrl(url)) {
            return;
        }
        new Thread(() -> {
            this.mLocalAllTransactionsDB.VerifyAndAddHistory(title, url, favicon);
        }).start();
    }

    public void updateTabList() {
        ArrayList<Tab> arrayList = this.tabs;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        this.ArrTabList.clear();
        for (int i = 0; i < this.tabs.size(); i++) {
            this.ArrTabList.add(new store_model_tab(Objects.requireNonNull(this.tabs.get(i).webview.getTitle()).isEmpty() ? "about:blank" : this.tabs.get(i).webview.getTitle(), this.tabs.get(i).webview.getFavicon()));
        }
        adp_tab adp_tabVar = this.mTabAdapter;
        if (adp_tabVar != null) {
            adp_tabVar.notifyDataSetChanged();
        }
    }

    private void visiblweTopView(boolean isShow) {
        if (isShow) {
            this.relad.setVisibility(View.VISIBLE);

            return;
        }
        this.relad.setVisibility(View.GONE);
    }

    public void DialogForBottomAddPost() {
        try {
            if (isFinishing()) {
                return;
            }
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            View inflate = getLayoutInflater().inflate(R.layout.bottom_screen_for_menus_new, (ViewGroup) null);
            bottomSheetDialog.setContentView(inflate);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.imgClose);
            RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.realtive1);
            RelativeLayout relativeLayout2 = (RelativeLayout) inflate.findViewById(R.id.realtive2);
            RelativeLayout relativeLayout3 = (RelativeLayout) inflate.findViewById(R.id.realtive3);
            RelativeLayout relativeLayout4 = (RelativeLayout) inflate.findViewById(R.id.realtive33);
            RelativeLayout relativeLayout5 = (RelativeLayout) inflate.findViewById(R.id.realtive4);
            RelativeLayout relativeLayout6 = (RelativeLayout) inflate.findViewById(R.id.realtive5);
            RelativeLayout relativeLayout7 = (RelativeLayout) inflate.findViewById(R.id.realtive6);
            RelativeLayout relativeLayout8 = (RelativeLayout) inflate.findViewById(R.id.realtive66);
            ImageView imageView2 = (ImageView) inflate.findViewById(R.id.icon_33);
            TextView textView = (TextView) inflate.findViewById(R.id.txt_33);
            ImageView imageView3 = (ImageView) inflate.findViewById(R.id.icon_5);
            TextView textView2 = (TextView) inflate.findViewById(R.id.txt_5);
            ImageView imageView4 = (ImageView) inflate.findViewById(R.id.icon_2);
            TextView textView3 = (TextView) inflate.findViewById(R.id.txt_2);
            ImageView imageView5 = (ImageView) inflate.findViewById(R.id.icon_66);
            TextView textView4 = (TextView) inflate.findViewById(R.id.txt_66);
            if (this.relativeHomepage.getVisibility() != View.VISIBLE) {
                imageView2.setImageResource(R.drawable.fcon_for_shareurl);
                textView.setTextColor(Color.parseColor("#4C4C4C"));
                relativeLayout4.setClickable(true);
                imageView3.setImageResource(R.drawable.image_copylink);
                textView2.setTextColor(Color.parseColor("#4C4C4C"));
                relativeLayout6.setClickable(true);
            } else {
                imageView2.setImageResource(R.drawable.icon_for_shareurl_unslected);
                textView.setTextColor(Color.parseColor("#b4b4b4"));
                relativeLayout4.setClickable(false);
                imageView3.setImageResource(R.drawable.img_copylink_unslected);
                textView2.setTextColor(Color.parseColor("#b4b4b4"));
                relativeLayout6.setClickable(false);
            }
            WebView webView = this.mBottomForWebView;
            if (webView != null) {
                if (webView.canGoBack()) {
                    imageView4.setImageResource(R.drawable.fcon_ad_fg_back);
                    textView3.setTextColor(Color.parseColor("#4C4C4C"));
                    relativeLayout2.setClickable(true);
                } else {
                    imageView4.setImageResource(R.drawable.icon_ad_fg_back_unselected);
                    textView3.setTextColor(Color.parseColor("#b4b4b4"));
                    relativeLayout2.setClickable(false);
                }
                if (this.mBottomForWebView.canGoForward()) {
                    imageView5.setImageResource(R.drawable.fcon_ad_fg_next);
                    textView4.setTextColor(Color.parseColor("#4C4C4C"));
                    relativeLayout8.setClickable(true);
                } else {
                    imageView5.setImageResource(R.drawable.icon_ad_fg_next_unselected);
                    textView4.setTextColor(Color.parseColor("#b4b4b4"));
                    relativeLayout8.setClickable(false);
                }
            }
            relativeLayout.setOnClickListener(v -> {
                MainActivityVideos.this.CommonCallForBottonSheet(42);
                bottomSheetDialog.dismiss();
            });
            relativeLayout2.setOnClickListener(v -> {
                if (MainActivityVideos.this.getCurrentWebView() != null && MainActivityVideos.this.getCurrentWebView().canGoBack()) {
                    MainActivityVideos.this.getCurrentWebView().goBack();
                    if (!MainActivityVideos.this.getCurrentWebView().canGoBack()) {
                        MainActivityVideos.this.closeCurrentTab();
                    }
                }
                bottomSheetDialog.dismiss();
            });
            relativeLayout3.setOnClickListener(v -> {
                MainActivityVideos.this.startActivity(new Intent(MainActivityVideos.this, ScreenHelpForDownload.class));
                bottomSheetDialog.dismiss();
            });
            relativeLayout4.setOnClickListener(v -> {
                MainActivityVideos.this.CommonCallForBottonSheet(44);
                bottomSheetDialog.dismiss();
            });
            relativeLayout5.setOnClickListener(v -> showRateDialog());
            relativeLayout6.setOnClickListener(v -> {
                MainActivityVideos.this.CommonCallForBottonSheet(45);
                bottomSheetDialog.dismiss();
            });
            relativeLayout7.setOnClickListener(v -> {
                bottomSheetDialog.dismiss();
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.settingId, () -> {
                    MainActivityVideos.this.startActivity(new Intent(MainActivityVideos.this, ScreenAppSettings.class));
                });
            });
            relativeLayout8.setOnClickListener(v -> {
                if (MainActivityVideos.this.getCurrentWebView() != null && MainActivityVideos.this.getCurrentWebView().canGoForward()) {
                    MainActivityVideos.this.getCurrentWebView().goForward();
                }
                bottomSheetDialog.dismiss();
            });
            if (imageView != null) {
                imageView.setOnClickListener(v -> bottomSheetDialog.dismiss());
            }
            bottomSheetDialog.setCancelable(true);
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.show();
        } catch (Exception unused) {
            throw new RuntimeException(unused);
        }
    }

    public void checkForDownloadStatus(String str, CopyOnWriteArrayList<store_model_video_link.listVideos> copyOnWriteArrayList) {
        if (!str.contains("facebook") && !str.contains("instagram")) {
            CopyOnWriteArrayList<store_model_video_link.listVideos> copyOnWriteArrayList2 = localDownloadLinks.get(str);
            CopyOnWriteArrayList copyOnWriteArrayList3 = new CopyOnWriteArrayList();
            if (copyOnWriteArrayList2 != null) {
                for (store_model_video_link.listVideos next : copyOnWriteArrayList) {
                    boolean z = false;
                    Iterator<store_model_video_link.listVideos> it2 = copyOnWriteArrayList2.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        } else if (next.getN_link_url().equals(it2.next().getN_link_url())) {
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        copyOnWriteArrayList3.add(next);
                    }
                }
                copyOnWriteArrayList2.addAll(copyOnWriteArrayList3);
                copyOnWriteArrayList = copyOnWriteArrayList2;
            }
            localDownloadLinks.put(str, copyOnWriteArrayList);
            CheckCurrentSite(str, true);
            return;
        }
        localDownloadLinks.put(str, copyOnWriteArrayList);
        CheckCurrentSite(str, true);
    }

    public void checkUrlForDownload(String str, boolean shouldDownloadLink) {
        if (shouldDownloadLink || this.lastUrlDetected.isEmpty() || !this.lastUrlDetected.equals(str)) {
            this.downloadButton.setVisibility(View.VISIBLE);
            isDownwasVisible = true;
            if (this.shakeTime != 0 && TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - this.shakeTime) < 2) {
                return;
            }
            this.lastUrlDetected = str;
            this.downurl = str;
            if (UtilsForApp.hasNonValidUrl(str)) {
                this.downloadButton.setTag(ExifInterface.GPS_MEASUREMENT_2D);
                DialogNotSupportedSite();
                this.downloadButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.round_dowenload));
                isDownwasVisible = false;
                getCurrentTab().setDownStatus(false);
                this.downloadButton.setVisibility(View.GONE);
            } else if (!UtilsForApp.IsNonVideoURL(str) && !UtilsForApp.hasNonValidUrl(str)) {
                if (UtilsForApp.haveTwitLink(str)) {
                    this.downloadButton.setVisibility(View.VISIBLE);
                    this.downloadButton.setTag(ExifInterface.GPS_MEASUREMENT_2D);
                    this.downloadButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.round_dowenload));
                    getCurrentTab().setDownStatus(false);
                } else if (!isValidUrlRegex(str) && !shouldDownloadLink) {
                    this.downloadButton.setVisibility(View.VISIBLE);
                    this.downloadButton.setTag(ExifInterface.GPS_MEASUREMENT_2D);
                    this.downloadButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.round_dowenload));
                    getCurrentTab().setDownStatus(false);
                } else {
                    this.downloadButton.setVisibility(View.VISIBLE);
                    this.downloadButton.setTag("1");
                    this.downloadButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_colored_download));
                    getCurrentTab().setDownStatus(true);
                    this.downloadButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_anim));
                    this.shakeTime = System.currentTimeMillis();
                }
            } else {
                this.downloadButton.setTag(ExifInterface.GPS_MEASUREMENT_2D);
                this.downloadButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.round_dowenload));
                isDownwasVisible = false;
                getCurrentTab().setDownStatus(false);
                this.downloadButton.setVisibility(View.GONE);
            }
        }
    }

    public void doDownloadStart() {
        String str = this.downloadURL;
        if (str == null || str.isEmpty()) {
            return;
        }
        String str2 = this.downloadFilename;
        if (str2 == null || str2.isEmpty()) {
            StringBuilder m = new StringBuilder("File-");
            m.append(System.currentTimeMillis());
            this.downloadFilename = m.toString();
        }

        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);

        config = PRDownloaderConfig.newBuilder()
                .setReadTimeout(30_000)
                .setConnectTimeout(30_000)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Video Downloader";
        int downloadId = PRDownloader.download(downloadURL, path, downloadFilename)
                .build()
                .setOnStartOrResumeListener(() -> Toast.makeText(MainActivityVideos.this, "Download Start", Toast.LENGTH_SHORT).show())
                .setOnPauseListener(() -> {

                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {


                        Toast.makeText(MainActivityVideos.this, "Download Complete", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Error error) {

                    }


                });


    }

    public void foundInstaURL(String instaApiCallUrl, final String instaPageUrl) {
        if (instaPageUrl == null || instaPageUrl.isEmpty()) {
            return;
        }
        runOnUiThread(() -> {
            if (MainActivityVideos.this.getCurrentWebView() != null) {
                MainActivityVideos.this.getCurrentWebView().onPause();
            }
        });
        Intent intent = new Intent(this, ActivityVideoLink.class);
        intent.putExtra("d_url", instaPageUrl);
        intent.putExtra("d_title", getCurrentTab().title);
        intent.putExtra("type_", "natural");
        startActivityForResult(intent, 1591);
    }

    @JavascriptInterface
    public final void getFBData(String downLink, String xml, final String vidUrl, String parentXml) {
        final CopyOnWriteArrayList<store_model_video_link.listVideos> copyOnWriteArrayList;
        CopyOnWriteArrayList<store_model_video_link.listVideos> copyOnWriteArrayList1;
        Element first;
        int i = 0;
        try {
            String findLinksX = UtilsForApp.findLinksX("class=\"_52je _52jh\">(.*?)<", parentXml, 1);
            String replace = StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(stringPlus("https", StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(parentXml, "style=\"background: url"), "https"), "')")), "\\26 ", "&"), "\\3a ", ":"), "\\3d ", "="), "\\", ""), "\"", "");
            Element first2 = Jsoup.parse(Jsoup.parse(Jsoup.parse(xml).getElementsByTag("MPD").html()).getElementsByTag("Period").html()).select("body").first();
            if (first2 != null) {
                copyOnWriteArrayList1 = new CopyOnWriteArrayList<>();
                try {
                    for (Element element : first2.children()) {
                        Element first3 = element.select("adaptationset").first();
                        if (first3 != null) {
                            for (Element next : first3.children()) {
                                if (next != null && (first = next.select("representation").first()) != null) {
                                    Elements elementsByTag = Jsoup.parse(first.html()).getElementsByTag("BaseURL");
                                    if (!elementsByTag.isEmpty() && first.hasAttr("FBQualityLabel")) {
                                        store_model_video_link.listVideos listvideos = new store_model_video_link.listVideos();
                                        listvideos.setN_link_url(elementsByTag.get(i).text());
                                        listvideos.setN_link_extension("mp4");
                                        if (first.hasAttr("width")) {
                                            listvideos.setN_libk_width(Integer.parseInt(first.attr("width")));
                                        }
                                        if (first.hasAttr("height")) {
                                            listvideos.setN_link_height(Integer.parseInt(first.attr("height")));
                                        }
                                        listvideos.setLocal_quality(first.attr("FBQualityLabel"));
                                        if (findLinksX != null && !findLinksX.isEmpty()) {
                                            listvideos.setN_link_title(findLinksX);
                                        } else {
                                            listvideos.setN_link_title("Facebook_" + System.currentTimeMillis());
                                        }
                                        if (!replace.isEmpty()) {
                                            listvideos.setN_link_image(replace);
                                        }
                                        copyOnWriteArrayList1.add(listvideos);
                                    }
                                }
                                i = 0;
                            }
                        }
                        i = 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Something went wrong. Please try later", Toast.LENGTH_SHORT).show();
                }
            } else {
                copyOnWriteArrayList1 = null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            copyOnWriteArrayList1 = null;
        }
        copyOnWriteArrayList = copyOnWriteArrayList1;
        if (copyOnWriteArrayList == null && copyOnWriteArrayList.size() > 0) {
            runOnUiThread(() -> {
                if (MainActivityVideos.this.getCurrentWebView() != null) {
                    MainActivityVideos.this.getCurrentWebView().onPause();
                }
            });
            localDownloadLinks.clear();
            localDownloadLinks.put(vidUrl, copyOnWriteArrayList);

            Intent intent = new Intent(this, ActivityVideoLink.class);
            intent.putExtra("d_url", vidUrl);
            intent.putExtra("d_title", ((store_model_video_link.listVideos) copyOnWriteArrayList.get(0)).getN_link_title());
            intent.putExtra("type_", "paste_link");
            startActivityForResult(intent, 1591);
        } else if (vidUrl != null && !vidUrl.isEmpty()) {
            runOnUiThread(() -> {
                if (MainActivityVideos.this.getCurrentWebView() != null) {
                    MainActivityVideos.this.getCurrentWebView().onPause();
                }
            });
            Intent intent = new Intent(this, ActivityVideoLink.class);
            intent.putExtra("d_url", vidUrl);
            intent.putExtra("d_title", getCurrentTab().title);
            intent.putExtra("type_", "natural");
            startActivityForResult(intent, 1591);
        } else {
            Toast.makeText(this, "Something went wrong.    ", Toast.LENGTH_SHORT).show();
        }
    }

    @JavascriptInterface
    public final void getFBMultiplePost(String str) {
    }

    public boolean isValidTwitterVideoUrlRegex(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.startsWith("http") || str.startsWith("https")) {
            try {
                return Pattern.compile("https?://(?:www\\.|m\\.|mobile\\.)?twitter\\.com/(?:i/web|(?<userid>[^/]+))/status/(?<id>\\d+)", 2).matcher(str).find();
            } catch (Throwable th) {
                th.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean isValidUrlRegex(String str) {
        if (!TextUtils.isEmpty(str) && ((str.startsWith("http") || str.startsWith("https")) && DclassApp.allUrlRegx != null)) {
            int i = 0;
            while (true) {
                String[] strArr = DclassApp.allUrlRegx;
                if (i >= strArr.length) {
                    break;
                }
                if (Pattern.compile(strArr[i], 2).matcher(str).find()) {
                    return true;
                }
                i++;
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public void notifyTabChanged(final String str, String isYt) {
        runOnUiThread(() -> {
            if (str.equals("about:blank")) {
                this.relativeHomepage.setVisibility(View.VISIBLE);
                this.scrollHomepage.setVisibility(View.VISIBLE);
                this.relativeSarch.setVisibility(View.VISIBLE);
                visiblweTopView(true);
                this.autoCompleteTextView.setVisibility(View.GONE);
                this.downloadButton.setVisibility(View.GONE);
            } else if (str.startsWith("http") || str.startsWith("https")) {

                this.relativeHomepage.setVisibility(View.GONE);
                this.scrollHomepage.setVisibility(View.GONE);
                this.relativeSarch.setVisibility(View.GONE);
                visiblweTopView(false);
                this.autoCompleteTextView.setVisibility(View.VISIBLE);
                this.downloadButton.setVisibility(View.VISIBLE);
                CheckCurrentSite(str, getCurrentTab().isOn);
            }
        });
    }

    @Override
    public void notifyUrlChanged(String url, int tabId, boolean isOnlyUpdateToolbar, String rawUrl) {
        if (tabId != getCurrentTab().tabID || rawUrl == null || rawUrl.equals("")) {
            return;
        }
        if (rawUrl.startsWith("http") || rawUrl.startsWith("https")) {
            CheckCurrentSite(rawUrl, false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String stringExtra;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8002) {
            if (resultCode != -1 || data == null) {
                return;
            }
            final String stringExtra2 = data.getStringExtra("status_");
            runOnUiThread(() -> {
                if (stringExtra2.equalsIgnoreCase(BooleanUtils.YES)) {
                    if (this.tabs.size() > 0) {
                        for (int i2 = 0; i2 < this.tabs.size(); i2++) {
                            this.tabs.get(i2).webview.getSettings().setUserAgentString(this.currentUserAgent);
                            if (!Objects.requireNonNull(this.tabs.get(i2).webview.getUrl()).equals("about:blank")) {
                                this.tabs.get(i2).webview.reload();
                            }
                        }
                    }
                }
            });
        } else if (requestCode == 4957) {
            if (resultCode != -1 || data == null || (stringExtra = data.getStringExtra("app_language")) == null) {
                return;
            }
            Locale locale = new Locale(stringExtra);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            Configuration configuration = getResources().getConfiguration();
            configuration.setLayoutDirection(locale);
            configuration.locale = locale;
            getResources().updateConfiguration(configuration, displayMetrics);
            recreate();
        } else if (requestCode == 1) {
            ValueCallback<Uri[]> valueCallback = this.fileUploadCallback;
            if (valueCallback != null) {
                if (this.fileUploadCallbackShouldReset) {
                    valueCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                    this.fileUploadCallback = null;
                    return;
                }
                this.fileUploadCallbackShouldReset = true;
            }
        } else if (requestCode != 7941) {
            if (requestCode != 6941 && requestCode != 5941) {
                if (requestCode != 1591 || getCurrentWebView() == null) {
                    return;
                }
                getCurrentWebView().onResume();
            } else if (resultCode == -1) {
                String stringExtra3 = data.getStringExtra("open_url");
                String stringExtra4 = data.getStringExtra("new_open_url");
                if (stringExtra3 != null && !stringExtra3.isEmpty()) {
                    LoadCustomUrlInCurrentTab(stringExtra3);
                    checkUrlForDownload(stringExtra3, true);
                } else if (stringExtra4 != null && !stringExtra4.isEmpty()) {
                    newTab(stringExtra4);
                    checkUrlForDownload(stringExtra3, true);
                    switchToTab(this.tabs.size() - 1);
                }
            }
        } else if (resultCode == -1) {
            boolean booleanExtra2 = data.getBooleanExtra("is_ua_changed", false);
            boolean booleanExtra3 = data.getBooleanExtra("is_se_changed", false);
            if (booleanExtra2) {
                String userAgent = UtilsForApp.getUserAgent(this.prefs.getString(ConstantForApp.USER_AG_SET, UtilsForApp.UA_DEF));
                if (!this.currentUserAgent.equals(userAgent)) {
                    this.currentUserAgent = userAgent;
                    for (int i = 0; i < this.tabs.size(); i++) {
                        this.tabs.get(i).webview.getSettings().setUserAgentString(this.currentUserAgent);
                        if (!Objects.requireNonNull(this.tabs.get(i).webview.getUrl()).equals("about:blank")) {
                            this.tabs.get(i).webview.reload();
                        }
                    }
                }
            }
            if (booleanExtra3) {
                this.currentSearchEngine = UtilsForApp.getSearch(this.prefs.getString(ConstantForApp.DEF_SEARCH_ENG, UtilsForApp.SE_GOOGLE));
            }
            String stringExtra5 = data.getStringExtra("app_language");
            if (stringExtra5 != null) {
                Locale locale2 = new Locale(stringExtra5);
                DisplayMetrics displayMetrics2 = getResources().getDisplayMetrics();
                Configuration configuration2 = getResources().getConfiguration();
                configuration2.setLayoutDirection(locale2);
                configuration2.locale = locale2;
                getResources().updateConfiguration(configuration2, displayMetrics2);
                recreate();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getCurrentWebView().canGoBack()) {
            getCurrentWebView().goBack();
            if (getCurrentWebView().canGoBack()) {
                return;
            }
            if (this.tabs.size() == 1 && this.relativeHomepage.getVisibility() == View.VISIBLE) {
                exitAppLogic();
            } else {
                closeCurrentTab();
            }
        } else if (this.tabs.size() > 1) {
            closeCurrentTab();
        } else if (this.relativeHomepage.getVisibility() == View.VISIBLE) {
            exitAppLogic();
        } else if (this.relativeHomepage.getVisibility() == View.GONE) {
            this.relativeHomepage.setVisibility(View.VISIBLE);
            this.scrollHomepage.setVisibility(View.VISIBLE);
            this.relativeSarch.setVisibility(View.VISIBLE);
            visiblweTopView(true);
            this.autoCompleteTextView.setVisibility(View.GONE);
        } else {
            exitAppLogic();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.icon_overflow || id == R.id.icon_overflowTop) {
            startActivity(new Intent(this, ScreenAppSettings.class));
            return;
        } else if (id == R.id.icon_rateapp) {
            showRateDialog();
            return;
        } else if (id == R.id.icon_refresh) {
            callApidata();
            return;
        } else if (id == R.id.icon_share) {
            ShareText();
            return;
        }
        if (id == R.id.imgAddNewtab) {
            newTab("");
            switchToTab(this.tabs.size() - 1);
            drawerToggle();
            return;
        } else if (id == R.id.relHelp) {
            startActivity(new Intent(this, ScreenHelpForDownload.class));
            return;
        } else if (id == R.id.relativeSarch) {
            this.autoCompleteTextView.setVisibility(View.VISIBLE);
            this.relativeHomepage.setVisibility(View.GONE);
            this.scrollHomepage.setVisibility(View.GONE);
            this.relativeSarch.setVisibility(View.GONE);
            visiblweTopView(false);

            this.autoCompleteTextView.requestFocus();
            if (this.relativeHomepage.getVisibility() != View.VISIBLE) {
                this.imgTab3.setImageResource(R.drawable.fcon_ad_fg_home);
                this.realtiveTab3.setClickable(true);
            } else {
                this.imgTab3.setImageResource(R.drawable.icon_ad_fg_home_unslected);
                this.realtiveTab3.setClickable(false);
            }
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.showSoftInput(this.autoCompleteTextView, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.autoCompleteTextView.setText("");
            return;
        }
        if (id == R.id.imgQR) {
            TedPermission.create().setPermissionListener(new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    runOnUiThread(() -> {
                        options = new ScanOptions();
                        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
                        options.setPrompt(MainActivityVideos.this.getString(R.string.scan_QR_code));
                        options.setOrientationLocked(false);
                        options.setBeepEnabled(false);
                        barcodeLauncher.launch(options);
                    });
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {

                }
            }).setRationaleTitle(getString(R.string.camera_permission)).setRationaleMessage(getString(R.string.please_allow_camera_permission_to_scan_QR_code)).setDeniedTitle(getString(R.string.permission_denied)).setDeniedMessage(getString(R.string.if_you_reject_permission)).setGotoSettingButtonText(getResources().getString(R.string.settings)).setPermissions("android.permission.CAMERA").check();
            return;
        } else if (id == R.id.imgReloadCancel) {
            if (this.isReload) {
                getCurrentWebView().reload();
                return;
            } else {
                getCurrentWebView().stopLoading();
                return;
            }
        }
        if (id == R.id.realative1) {
            AllInOneAds.getInstance().showInterWithId(this, AdHelper.googleId, () -> {
                LoadCustomUrlInCurrentTab("https://www.google.com/");
            });
            return;
        } else if (id == R.id.realative10) {
            if (UtilsForApp.isNetworkAvailable(this)) {
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.captionId, () -> {
                    startActivity(new Intent(this, CaptionScreenActivity.class));
                });
                return;
            } else {
                UtilsForApp.showToastMsg(getString(R.string.internet_connection_error), this, false);
                return;
            }
        } else if (id == R.id.realative11) {
            if (UtilsForApp.isNetworkAvailable(this)) {
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.bioId, () -> {
                    startActivity(new Intent(this, BioMainActivity.class));
                });
                return;
            } else {
                UtilsForApp.showToastMsg(getString(R.string.internet_connection_error), this, false);
                return;
            }
        } else if (id == R.id.realative2) {
            if (UtilsForApp.isNetworkAvailable(this)) {
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.fbSaverId, () -> {
                    LoadCustomUrlInCurrentTab("https://m.facebook.com/watch");
                });
                return;
            }
            UtilsForApp.showToastMsg(getString(R.string.internet_connection_error), this, false);
            return;
        } else if (id == R.id.realative3) {
            if (UtilsForApp.isNetworkAvailable(this)) {
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.igSaverId, () -> {
                    LoadCustomUrlInCurrentTab("https://www.instagram.com/");
                });
                return;
            }
            UtilsForApp.showToastMsg(getString(R.string.internet_connection_error), this, false);
            return;
        } else if (id == R.id.realative4) {
            if (UtilsForApp.isNetworkAvailable(this)) {
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.whatsappId, () -> {
                    startActivity(new Intent(this, WhatsMainActivity.class));
                });
                return;
            } else {
                UtilsForApp.showToastMsg(getString(R.string.internet_connection_error), this, false);
                return;
            }
        } else if (id == R.id.realative5) {
            if (UtilsForApp.isNetworkAvailable(this)) {
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.twitterId, () -> {
                    LoadCustomUrlInCurrentTab("https://mobile.twitter.com/");
                    String string = this.prefs.getString(ConstantForApp.FIRST_TWITTER_OPEN, "");
                    if (string == null || !string.equals("")) {
                        return;
                    }
                    UtilsForApp.showAllHelpDialog(this);
                    this.prefs.edit().putString(ConstantForApp.FIRST_TWITTER_OPEN, "Open").apply();
                });
                return;
            }
            UtilsForApp.showToastMsg(getString(R.string.internet_connection_error), this, false);
            return;
        } else if (id == R.id.realative6) {
            if (UtilsForApp.isNetworkAvailable(this)) {
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.pintrestId, () -> {
                    LoadCustomUrlInCurrentTab("https://www.pinterest.com/search/pins/?rs=typed&q=video");
                });
                return;
            }
            UtilsForApp.showToastMsg(getString(R.string.internet_connection_error), this, false);
            return;
        } else if (id == R.id.realative7) {
            if (UtilsForApp.isNetworkAvailable(this)) {
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.vimeId, () -> {
                    LoadCustomUrlInCurrentTab("https://vimeo.com/watch/");
                });
                return;
            }
            UtilsForApp.showToastMsg(getString(R.string.internet_connection_error), this, false);
            return;
        } else if (id == R.id.realative8) {
            AllInOneAds.getInstance().showInterWithId(this, AdHelper.allVideoDownloadId, () -> {
                Intent intent = new Intent(this, PasteLinkActivity.class);
                intent.putExtra("type", "normal");
                startActivity(intent);
            });
            return;
        } else if (id == R.id.realative9) {
            if (UtilsForApp.isNetworkAvailable(this)) {
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.hashTagId, () -> {
                    startActivity(new Intent(this, HashTagScreenActivity.class));
                });
                return;
            } else {
                UtilsForApp.showToastMsg(getString(R.string.internet_connection_error), this, false);
                return;
            }
        }
        if (id == R.id.realtiveTab1) {
            drawerToggle();
            return;
        } else if (id == R.id.realtiveTab2) {
            openFiles();
            return;
        } else if (id == R.id.realtiveTab3) {
            if (getCurrentWebView() != null) {
                getCurrentWebView().clearHistory();
                getCurrentWebView().clearCache(true);
                this.autoCompleteTextView.setText("about:blank");
                checkAndAddIconInET("about:blank");
                getCurrentWebView().requestFocus();
                loadUrl("about:blank", getCurrentWebView());
                this.downloadButton.setVisibility(View.GONE);
                if (getCurrentWebView().getSettings().getUserAgentString().equals(this.currentUserAgent)) {
                    return;
                }
                this.agentsetFB = false;
                getCurrentWebView().getSettings().setUserAgentString(this.currentUserAgent);
                return;
            }
            return;
        } else if (id == R.id.realtiveTab4) {
            DialogForBottomAddPost();
            return;
        } else if (id == R.id.realtiveTab5) {
            Intent intent = new Intent(this, PasteLinkActivity.class);
            intent.putExtra("type", "normal");
            startActivity(intent);
            return;
        }
        if (id == R.id.realtivrFbLink) {
            AllInOneAds.getInstance().showInterWithId(this, AdHelper.fbVideoDownloaderId, () -> {
                Intent intent = new Intent(this, PasteLinkActivity.class);
                intent.putExtra("type", "fb");
                startActivity(intent);
            });
            return;
        } else if (id == R.id.realtivrInstaLink) {
            AllInOneAds.getInstance().showInterWithId(this, AdHelper.instaVideoDownloaderId, () -> {
                Intent intent = new Intent(this, PasteLinkActivity.class);
                intent.putExtra("type", "insta");
                startActivity(intent);
            });
            return;
        }
        return;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        AllInOneAds.getInstance().showTopNativeWithId(this, AdHelper.allVideoDownloadId, findViewById(R.id.native_container));
        AllInOneAds.getInstance().showMiddleNativeWithId(this, AdHelper.allVideoDownloadId, findViewById(R.id.native_container_mid));
        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.allVideoDownloadId, findViewById(R.id.banner_container));

        try {
            currentTabIndex = 0;
            this.mLocalAllTransactionsDB = PersistentDataManager.getSQInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Delegate.mainActivityVideos = this;

        this.prefs = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance());
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.btn_download_video2);
        this.downloadButton = floatingActionButton;
        floatingActionButton.setTag(ExifInterface.GPS_MEASUREMENT_2D);
        this.currentUserAgent = UtilsForApp.getUserAgent(DclassApp.getInstance(), "WebView");
        this.desktopUA = UtilsForApp.getUserAgent(DclassApp.getInstance(), "Windows (Desktop)");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.received_url = extras.getString("received_url");
            this.received_data = extras.getString("received_data");
        }
        this.currentSearchEngine = UtilsForApp.getSearch(this.prefs.getString(ConstantForApp.DEF_SEARCH_ENG, UtilsForApp.SE_GOOGLE));
        this.myClipBoard = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        this.tvPrivacy = (TextView) findViewById(R.id.tvPrivacy);
        LinearLayout linPrivacy = (LinearLayout) findViewById(R.id.linPrivacy);

        setUpHomePage();
        linPrivacy.setOnClickListener(v -> {
            if (MainActivityVideos.this.privacyFlag) {
                MainActivityVideos.this.privacyFlag = false;
                MainActivityVideos.this.tvPrivacy.setText("Protected");
                MainActivityVideos.this.tvPrivacy.setTextColor(ContextCompat.getColor(MainActivityVideos.this, R.color.white));
                return;
            }
            MainActivityVideos.this.privacyFlag = true;
            MainActivityVideos.this.tvPrivacy.setTextColor(ContextCompat.getColor(MainActivityVideos.this, R.color.white));
            MainActivityVideos.this.tvPrivacy.setText("No Privacy");
        });
        this.webviews = (FrameLayout) findViewById(R.id.webviews);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        this.autoCompleteTextView = autoCompleteTextView;
        autoCompleteTextView.setSelected(false);
        String urlFromIntent = getUrlFromIntent(getIntent());
        this.autoCompleteTextView.setText(urlFromIntent.isEmpty() ? "about:blank" : urlFromIntent);
        if (urlFromIntent.isEmpty()) {
            urlFromIntent = "about:blank";
        }
        checkAndAddIconInET(urlFromIntent);
        this.autoCompleteTextView.setAdapter(new SearchAutocompleteAdapter(this, str -> {
            this.autoCompleteTextView.setText(str);
            checkAndAddIconInET(str);
            this.autoCompleteTextView.setSelection(str.length());
        }));
        this.autoCompleteTextView.setOnItemClickListener((adapterView, view, i, j) -> {
            getCurrentWebView().requestFocus();
            loadUrl(this.autoCompleteTextView.getText().toString(), getCurrentWebView());
            this.autoCompleteTextView.clearFocus();
        });
        this.autoCompleteTextView.setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == 0 && i == 66) {
                loadUrl(this.autoCompleteTextView.getText().toString(), getCurrentWebView());
                getCurrentWebView().requestFocus();
                this.autoCompleteTextView.clearFocus();
                return true;
            }
            return false;
        });
        EditText editText = (EditText) findViewById(R.id.searchEdit);
        this.searchEdit = editText;
        editText.addTextChangedListener(new TextWatcher() {


            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivityVideos.this.getCurrentWebView().findAllAsync(s.toString());
            }
        });
        this.searchCount = (TextView) findViewById(R.id.searchCount);
        findViewById(R.id.searchFindNext).setOnClickListener(view -> {
            hideKeyboard();
            getCurrentWebView().findNext(true);
        });
        findViewById(R.id.searchFindPrev).setOnClickListener(view -> {
            hideKeyboard();
            getCurrentWebView().findNext(false);
        });
        findViewById(R.id.searchClose).setOnClickListener(view -> {
            getCurrentWebView().clearMatches();
            this.searchEdit.setText("");
            getCurrentWebView().requestFocus();
            findViewById(R.id.searchPane).setVisibility(View.GONE);
            hideKeyboard();
        });
        newTab(this.autoCompleteTextView.getText().toString());
        getCurrentWebView().setVisibility(View.VISIBLE);
        getCurrentWebView().requestFocus();
        initColorOfItem();
        this.mLoadUrlStatusObserverList.add(this);
        this.mObserverList.add(this);
        this.downloadButton.setOnClickListener(v -> {
            if (MainActivityVideos.this.downloadButton.getTag().toString().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
                MainActivityVideos.this.DialogNoVideoDound();
                return;
            }
            final String str = MainActivityVideos.this.getCurrentTab().title;
            if (UtilsForApp.isNetworkAvailable(MainActivityVideos.this)) {
                DclassApp dclassApp = DclassApp.getInstance();
                MainActivityVideos mainActivityVideos = MainActivityVideos.this;
                if (MainActivityVideos.this.getCurrentWebView() != null) {
                    MainActivityVideos.this.getCurrentWebView().onPause();
                }
                Intent intent = new Intent(MainActivityVideos.this, ActivityVideoLink.class);
                intent.putExtra("d_url", MainActivityVideos.this.downurl);
                intent.putExtra("d_title", str);
                intent.putExtra("type_", "natural");
                MainActivityVideos.this.startActivityForResult(intent, 1591);
                return;
            }
            UtilsForApp.showToastMsg(MainActivityVideos.this.getString(R.string.internet_connection_error), MainActivityVideos.this, false);
        });

        initWorkAdBlkFetch();

        new Thread(() -> MainActivityVideos.this.initStaticVariables()).start();
        final String str = this.prefs.getString(ConstantForApp.PREF_PREVIOUS_URL, "");
        this.icon_overflow.post(() -> {
            ClipData.Item itemAt;
            ClipboardManager clipboardManager = this.myClipBoard;
            if (clipboardManager != null && clipboardManager.hasPrimaryClip() && this.myClipBoard.getPrimaryClip() != null && this.myClipBoard.getPrimaryClip().getItemCount() > 0 && (itemAt = this.myClipBoard.getPrimaryClip().getItemAt(0)) != null) {
                String trim = itemAt.coerceToText(this).toString().trim();
                if (!trim.isEmpty() && UtilsForApp.IsValidUrl(trim) && (str == null || str.isEmpty() || !str.equalsIgnoreCase(trim))) {
                    this.copy_data = trim;
                    this.prefs.edit().putString(ConstantForApp.PREF_PREVIOUS_URL, this.copy_data).apply();
                }
            }
            if (this.received_url.isEmpty() && this.received_data.isEmpty() && this.copy_data.isEmpty()) {
                return;
            }
            displayExtraDataDialog();
        });
        try {
            if (WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) {
                ProxyController.getInstance().clearProxyOverride(command -> command.run(), () -> {
                });
            } else {
                WebkitProxy.resetProxy(DclassApp.class.getName(), getApplicationContext());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        List<WebviewLink_Listen> list = this.mLoadUrlStatusObserverList;
        if (list != null) {
            list.remove(this);
        }
        List<ListnerForUrlChange> list2 = this.mObserverList;
        if (list2 != null) {
            list2.remove(this);
        }
        super.onDestroy();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String urlFromIntent = getUrlFromIntent(intent);
        if (urlFromIntent.isEmpty()) {
            return;
        }
        newTab(urlFromIntent);
        switchToTab(this.tabs.size() - 1);
    }

    @Override
    public void onPause() {
        ClipboardManager clipboardManager;
        ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener;

        super.onPause();
        if (!this.bHasClipChangedListener || (clipboardManager = this.myClipBoard) == null || (onPrimaryClipChangedListener = this.mPrimaryClipChangedListener) == null) {
            return;
        }
        clipboardManager.removePrimaryClipChangedListener(onPrimaryClipChangedListener);
        this.bHasClipChangedListener = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!this.bHasClipChangedListener) {
            if (this.myClipBoard == null) {
                this.myClipBoard = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
            }
            this.myClipBoard.addPrimaryClipChangedListener(this.mPrimaryClipChangedListener);
            this.bHasClipChangedListener = true;
        }

    }

    @Override
    public void resourceUrlChecking(WebView webView, String str) {
        String url = webView.getUrl();
        if (url == null) {
            return;
        }
        boolean z = false;
        if (url.contains("tumblr.com")) {
            String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl);
            if ((fileExtensionFromUrl.equals("ts") || mimeTypeFromExtension == null || !mimeTypeFromExtension.startsWith("video")) ? false : true) {
                CopyOnWriteArrayList<store_model_video_link.listVideos> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
                store_model_video_link.listVideos listvideos = new store_model_video_link.listVideos();
                listvideos.setN_link_url(str);
                listvideos.setN_link_extension(fileExtensionFromUrl);
                listvideos.setN_link_title(webView.getTitle());
                listvideos.setN_link_format("...");
                listvideos.setIs_local_video(true);
                copyOnWriteArrayList.add(listvideos);
                if (copyOnWriteArrayList.size() > 0) {
                    localDownloadLinks.clear();
                    checkForDownloadStatus(webView.getUrl(), copyOnWriteArrayList);
                }
            }
        }
        if (!url.contains(new String(Base64.decode("eHZpZGVvcy5jb20=", 0))) && !url.contains(new String(Base64.decode("eG54eC5jb20=", 0))) && !url.contains(new String(Base64.decode("cG9ybmh1Yi5jb20=", 0))) && !url.contains(new String(Base64.decode("eW91cG9ybi5jb20=", 0))) && !url.contains("facebook.com") && !url.contains("vimeo.com")) {
            String fileExtensionFromUrl2 = MimeTypeMap.getFileExtensionFromUrl(str);
            String mimeTypeFromExtension2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl2);
            if ((fileExtensionFromUrl2.equals("ts") || mimeTypeFromExtension2 == null || !mimeTypeFromExtension2.startsWith("video")) ? false : true) {
                CopyOnWriteArrayList<store_model_video_link.listVideos> copyOnWriteArrayList2 = new CopyOnWriteArrayList<>();
                store_model_video_link.listVideos listvideos2 = new store_model_video_link.listVideos();
                listvideos2.setN_link_url(str);
                listvideos2.setN_link_extension(fileExtensionFromUrl2);
                listvideos2.setN_link_title(webView.getTitle());
                listvideos2.setN_link_format("...");
                listvideos2.setIs_local_video(true);
                copyOnWriteArrayList2.add(listvideos2);
                if (copyOnWriteArrayList2.size() > 0) {
                    checkForDownloadStatus(webView.getUrl(), copyOnWriteArrayList2);
                }
            }
        }
        if (!str.contains("fbcdn.net") || str.contains(new String(Base64.decode("eHZpZGVvcy5jb20=", 0))) || str.contains(new String(Base64.decode("eG54eC5jb20=", 0))) || str.contains(new String(Base64.decode("cG9ybmh1Yi5jb20=", 0))) || str.contains(new String(Base64.decode("eW91cG9ybi5jb20=", 0))) || str.contains("facebook.com")) {
            return;
        }
        String fileExtensionFromUrl3 = MimeTypeMap.getFileExtensionFromUrl(str);
        String mimeTypeFromExtension3 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl3);
        if (!fileExtensionFromUrl3.equals("ts") && mimeTypeFromExtension3 != null && mimeTypeFromExtension3.startsWith("video")) {
            z = true;
        }
        if (z) {
            String guessFileName = URLUtil.guessFileName(str, null, null);
            CopyOnWriteArrayList<store_model_video_link.listVideos> copyOnWriteArrayList3 = new CopyOnWriteArrayList<>();
            store_model_video_link.listVideos listvideos3 = new store_model_video_link.listVideos();
            listvideos3.setN_link_url(str);
            listvideos3.setN_link_extension(fileExtensionFromUrl3);
            listvideos3.setN_link_title(guessFileName);
            listvideos3.setN_link_format("...");
            listvideos3.setIs_local_video(true);
            copyOnWriteArrayList3.add(listvideos3);
            if (copyOnWriteArrayList3.size() > 0) {
                checkForDownloadStatus(webView.getUrl(), copyOnWriteArrayList3);
            }
        }
    }

    public String stringPlus(String self, Object other) {
        return self + other;
    }
}
