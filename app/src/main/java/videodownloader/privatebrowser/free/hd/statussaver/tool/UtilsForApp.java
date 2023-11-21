package videodownloader.privatebrowser.free.hd.statussaver.tool;

import static videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Database.DBHashTagsHelper.m;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.loader.content.CursorLoader;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;
import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.FrgForHelp;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.ScreenHelpForDownload;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_video_link;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.network.ApiConnectionClass;
import videodownloader.privatebrowser.free.hd.statussaver.network.FB_Parse;
import videodownloader.privatebrowser.free.hd.statussaver.network.FirstEncode;
import videodownloader.privatebrowser.free.hd.statussaver.network.HttpApis;
import videodownloader.privatebrowser.free.hd.statussaver.network.Insta_Parse;
import videodownloader.privatebrowser.free.hd.statussaver.network.ListnerForVideoStore;
import videodownloader.privatebrowser.free.hd.statussaver.network.LocalScrtpListener;
import videodownloader.privatebrowser.free.hd.statussaver.network.OK_Parse;
import videodownloader.privatebrowser.free.hd.statussaver.network.SampleLis;
import videodownloader.privatebrowser.free.hd.statussaver.network.Sharec_Parse;
import videodownloader.privatebrowser.free.hd.statussaver.network.Snak_Parse;
import videodownloader.privatebrowser.free.hd.statussaver.network.Vid_API_Parse_Listen;
import videodownloader.privatebrowser.free.hd.statussaver.network.Vimeo_Parse;
import videodownloader.privatebrowser.free.hd.statussaver.network.XV_Parse;
import videodownloader.privatebrowser.free.hd.statussaver.whatstool.WaModel;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilsForApp {
    private static String EtValueUrl = null;
    private static final OkHttpClient OK_HTTP_CLIENT = null;
    public static final String SE_BING = "se_four";
    public static final String SE_DUCKDUCKGO = "se_two";
    public static final String SE_FACEBOOK = "se_three";
    public static final String SE_GOOGLE = "se_one";
    public static final String SE_YAHOO = "se_five";
    public static final String UA_ANDROID = "ua2";
    public static final String UA_BRAVE = "ua9";
    public static final String UA_CHROME = "ua4";
    public static final String UA_DEF = "ua1";
    public static final String UA_EDGE = "ua8";
    public static final String UA_FIREFOX = "ua5";
    public static final String UA_IE = "ua3";
    public static final String UA_OPERA = "ua6";
    public static final String UA_SAFARI = "ua7";
    private static String av1Code;
    private static Call<String> callLocal;
    private static Call<store_model_video_link> callMain;
    private static String mPlace;
    private static String mTitle;
    private static Vid_API_Parse_Listen vidAPIParseListen;
    private static String webURL;
    private static final String[] SEHalf = {"https://www.google.com", "https://duckduckgo.com", "https://www.bing.com", "https://www.facebook.com"};
    private static final String[] SPHalf = {"/search?q=%s", "https://search.yahoo.com/search?p=%s", "/search/top/?q=%s", "/?q=%s"};
    private static final String[] userAgents = {"Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) %1$s/%2$s Mobile Safari/537.36", "Mozilla/5.0 (Mobile; Windows Phone 8.1; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; NOKIA; Lumia 635) like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537", "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.162 Mobile Safari/537.36", "Mozilla/5.0 (Android 8.0.0; Mobile; rv:61.0) Gecko/61.0 Firefox/68.0", "Mozilla/5.0 (Linux; Android 10; SM-N975F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.162 Mobile Safari/537.36 OPR/55.2.2719", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/80.0.3987.95 Mobile/15E148 Safari/604.1", "Mozilla/5.0 (Windows Mobile 10; Android 8.0.0; Microsoft; Lumia 950XL) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.162 Mobile Safari/537.36 Edge/80.0.361.109", "Mozilla/5.0 (Linux; Android 8.0.0;) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.162 Mobile Safari/537.36", "Mozilla/5.0 (Linux; Android 8.0.0; SM-G935F Build/R16NW) AppleWebKit/537.36 (KHTML, like Gecko) Brave Chrome/69.0.3497.100 Mobile Safari/537.36"};


    public static class AnonymousClass4 implements SampleLis {
        public final ArrayList val$arrayList;
        public final LocalScrtpListener val$localScrtpListener;
        public final String val$url;

        public AnonymousClass4(final String val$url, final ArrayList val$arrayList, final LocalScrtpListener val$localScrtpListener) {
            this.val$url = val$url;
            this.val$arrayList = val$arrayList;
            this.val$localScrtpListener = val$localScrtpListener;
        }

        @Override
        public void onResGet(int status, String res1) {
            if (status == 1) {
                try {
                    JSONObject jSONObject = new JSONObject(UtilsForApp.decypher(res1));
                    JSONObject jSONObject2 = jSONObject.getJSONObject("domaindata");
                    final JSONObject jSONObject3 = jSONObject.getJSONObject("maindata");
                    final String host2 = UtilsForApp.getHost2(this.val$url);
                    if (jSONObject2.has(host2)) {
                        final String string = jSONObject2.getString(host2);
                        if (jSONObject3.has(string)) {
                            HashMap hashMap = new HashMap();
                            try {
                                String string2 = jSONObject3.getJSONObject(string).getString("t_header");
                                if (string2 != null && !string2.isEmpty()) {
                                    JSONObject jSONObject4 = new JSONObject(string2);
                                    Iterator<String> keys = jSONObject4.keys();
                                    while (keys.hasNext()) {
                                        String next = keys.next();
                                        try {
                                            hashMap.put(next, jSONObject4.get(next).toString());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            UtilsForApp.runSampleAPI(false, false, this.val$url, hashMap, (status2, res) -> {
                                Exception e;
                                LocalScrtpListener localScrtpListener;
                                JSONObject jSONObject4 = null;
                                String string2 = null;
                                String string3 = null;
                                String string4 = null;
                                String string5 = null;
                                int i = 0;
                                int i2 = 0;
                                int i3 = 0;
                                int i4;
                                String anonymousClass1 = "b_header";
                                if (status2 == 1) {
                                    try {
                                        jSONObject4 = jSONObject3.getJSONObject(string);
                                        string2 = jSONObject4.getString("t_url");
                                        string3 = jSONObject4.getString("t_img");
                                        string4 = jSONObject4.getString("t_title");
                                        string5 = jSONObject4.getString("t_url2");
                                        i = jSONObject4.getInt("t_url_k");
                                        i2 = jSONObject4.getInt("t_img_k");
                                        i3 = jSONObject4.getInt("t_title_k");
                                    } catch (Exception e2) {
                                        e = e2;
                                    }
                                    try {
                                        i4 = jSONObject4.getInt("t_url2_k");
                                    } catch (Exception e3) {
                                        e = e3;
                                        e.printStackTrace();
                                        AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                                        localScrtpListener = anonymousClass4.val$localScrtpListener;
                                        if (localScrtpListener != null) {
                                            return;
                                        }
                                        localScrtpListener.onReceivedData(0, anonymousClass4.val$arrayList, host2);
                                        return;
                                    }
                                    try {
                                        if (!TextUtils.isEmpty(string2)) {
                                            String findLinksX = UtilsForApp.findLinksX(string2, res, i);
                                            if (!jSONObject4.isNull("url_source")) {
                                                findLinksX = UtilsForApp.findLinksX(string2, UtilsForApp.EtValueUrl, i);
                                            }
                                            String str = findLinksX;
                                            if (!jSONObject4.isNull("bottom_append_url_source") && !str.isEmpty()) {
                                                str = jSONObject4.getString("bottom_append_url_source").replace("{data}", str);
                                            }
                                            if (!jSONObject4.isNull("t_replace_data") && str != null) {
                                                try {
                                                    JSONObject jSONObject5 = new JSONObject(jSONObject4.getString("t_replace_data"));
                                                    Iterator<String> keys = jSONObject5.keys();
                                                    while (keys.hasNext()) {
                                                        String next = keys.next();
                                                        try {
                                                            str = str.replace(next, jSONObject5.get(next).toString());
                                                        } catch (JSONException e4) {
                                                            e4.printStackTrace();
                                                        }
                                                    }
                                                } catch (Exception e5) {
                                                    e5.printStackTrace();
                                                }
                                            }
                                            final String findLinksX2 = UtilsForApp.findLinksX(string4, res, i3);
                                            final String findLinksX3 = UtilsForApp.findLinksX(string3, res, i2);
                                            String findLinksX4 = UtilsForApp.findLinksX(string5, res, i4);
                                            StringBuilder sb = new StringBuilder();
                                            if (!jSONObject4.isNull("video_append_url")) {
                                                sb.append(jSONObject4.getString("video_append_url"));
                                                sb.append(str);
                                                str = sb.toString();
                                            }
                                            if (!jSONObject4.isNull("bottom")) {
                                                if (str != null) {
                                                    HashMap hashMap1 = new HashMap();
                                                    try {
                                                        JSONObject optJSONObject = jSONObject4.optJSONObject("bottom");
                                                        if (optJSONObject != null && !optJSONObject.isNull(anonymousClass1)) {
                                                            JSONObject jSONObject6 = new JSONObject(optJSONObject.getString(anonymousClass1));
                                                            Iterator<String> keys2 = jSONObject6.keys();
                                                            while (keys2.hasNext()) {
                                                                String next2 = keys2.next();
                                                                try {
                                                                    hashMap1.put(next2, jSONObject6.get(next2).toString());
                                                                } catch (JSONException e6) {
                                                                    e6.printStackTrace();
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e7) {
                                                        e7.printStackTrace();
                                                    }
                                                    if (!str.isEmpty()) {
                                                        str = str.replace("\\", "").replace("&amp;", "&").replace("u0026", "&");
                                                    }
                                                    JSONObject finalJSONObject = jSONObject4;
                                                    UtilsForApp.runSampleAPI(false, false, str, hashMap1, (status3, res2) -> {
                                                        if (status3 == 1) {
                                                            try {
                                                                JSONObject optJSONObject2 = finalJSONObject.optJSONObject("bottom");
                                                                if (optJSONObject2 != null && optJSONObject2.length() > 0) {
                                                                    String string6 = optJSONObject2.getString("b_url");
                                                                    String string7 = optJSONObject2.getString("b_img");
                                                                    String string8 = optJSONObject2.getString("b_title");
                                                                    String string9 = optJSONObject2.getString("b_url2");
                                                                    int i5 = optJSONObject2.getInt("b_url_k");
                                                                    int i6 = optJSONObject2.getInt("b_img_k");
                                                                    int i7 = optJSONObject2.getInt("b_title_k");
                                                                    int i8 = optJSONObject2.getInt("b_url2_k");
                                                                    String string10 = optJSONObject2.getString("b_type");
                                                                    if (!TextUtils.isEmpty(string6)) {
                                                                        String findLinksX5 = UtilsForApp.findLinksX(string6, res2, i5);
                                                                        StringBuilder sb2 = new StringBuilder();
                                                                        if (!optJSONObject2.isNull("b_video_append_url") && findLinksX5 != null) {
                                                                            sb2.append(optJSONObject2.getString("b_video_append_url"));
                                                                            sb2.append(findLinksX5);
                                                                            findLinksX5 = sb2.toString();
                                                                        }
                                                                        String findLinksX6 = UtilsForApp.findLinksX(string8, res2, i7);
                                                                        String findLinksX7 = UtilsForApp.findLinksX(string7, res2, i6);
                                                                        String findLinksX8 = UtilsForApp.findLinksX(string9, res2, i8);
                                                                        if (string10.equalsIgnoreCase("single")) {
                                                                            if (findLinksX5 != null) {
                                                                                store_model_video_link.listVideos listvideos = new store_model_video_link.listVideos();
                                                                                listvideos.setN_link_url(findLinksX5.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                listvideos.setN_link_extension("mp4");
                                                                                if (findLinksX6 != null) {
                                                                                    listvideos.setN_link_title(findLinksX6);
                                                                                } else {
                                                                                    if (findLinksX2 != null) {
                                                                                        listvideos.setN_link_title(findLinksX2);
                                                                                    }
                                                                                }
                                                                                if (findLinksX7 != null) {
                                                                                    listvideos.setN_link_image(findLinksX7.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                } else {
                                                                                    if (findLinksX3 != null) {
                                                                                        listvideos.setN_link_image(findLinksX3.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                    }
                                                                                }
                                                                                AnonymousClass4.this.val$arrayList.add(listvideos);
                                                                            }
                                                                            if (findLinksX8 != null) {
                                                                                store_model_video_link.listVideos listvideos2 = new store_model_video_link.listVideos();
                                                                                listvideos2.setN_link_url(findLinksX8.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                listvideos2.setN_link_extension("mp4");
                                                                                if (findLinksX6 != null) {
                                                                                    listvideos2.setN_link_title(findLinksX6);
                                                                                } else {
                                                                                    if (findLinksX2 != null) {
                                                                                        listvideos2.setN_link_title(findLinksX2);
                                                                                    }
                                                                                }
                                                                                if (findLinksX7 != null) {
                                                                                    listvideos2.setN_link_image(findLinksX7.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                } else {
                                                                                    if (findLinksX3 != null) {
                                                                                        listvideos2.setN_link_image(findLinksX3.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                    }
                                                                                }
                                                                                AnonymousClass4.this.val$arrayList.add(listvideos2);
                                                                            }
                                                                        } else if (findLinksX5 != null) {
                                                                            String str6 = "b_type_img";
                                                                            if (optJSONObject2.getString("b_type_sub").equalsIgnoreCase("array")) {
                                                                                JSONArray jSONArray = new JSONArray(findLinksX5);
                                                                                if (jSONArray.length() > 0) {
                                                                                    int i9 = 0;
                                                                                    while (i9 < jSONArray.length()) {
                                                                                        store_model_video_link.listVideos listvideos3 = new store_model_video_link.listVideos();
                                                                                        String str7 = findLinksX7;
                                                                                        String string11 = optJSONObject2.getString("b_type_url");
                                                                                        String string12 = !optJSONObject2.isNull("b_type_title") ? optJSONObject2.getString("b_type_title") : "";
                                                                                        String string13 = !optJSONObject2.isNull(str6) ? optJSONObject2.getString(str6) : "";
                                                                                        String str8 = str6;
                                                                                        listvideos3.setN_link_url(jSONArray.getJSONObject(i9).getString(string11).replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                        listvideos3.setN_link_extension("mp4");
                                                                                        if (!string12.isEmpty()) {
                                                                                            listvideos3.setN_link_title(string12);
                                                                                        } else if (findLinksX6 != null) {
                                                                                            listvideos3.setN_link_title(findLinksX6);
                                                                                        } else {
                                                                                            if (findLinksX2 != null) {
                                                                                                listvideos3.setN_link_title(findLinksX2);
                                                                                            }
                                                                                        }
                                                                                        if (!string13.isEmpty()) {
                                                                                            listvideos3.setN_link_image(string13.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                            findLinksX7 = str7;
                                                                                        } else if (str7 != null) {
                                                                                            findLinksX7 = str7;
                                                                                            listvideos3.setN_link_image(findLinksX7.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                        } else {
                                                                                            findLinksX7 = str7;
                                                                                            if (findLinksX3 != null) {
                                                                                                listvideos3.setN_link_image(findLinksX3.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                            }
                                                                                        }
                                                                                        AnonymousClass4.this.val$arrayList.add(listvideos3);
                                                                                        i9++;
                                                                                        str6 = str8;
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                JSONObject jSONObject7 = new JSONObject(findLinksX5);
                                                                                store_model_video_link.listVideos listvideos4 = new store_model_video_link.listVideos();
                                                                                String string14 = optJSONObject2.getString("b_type_url");
                                                                                String string15 = !optJSONObject2.isNull("b_type_title") ? jSONObject7.getString("b_type_title") : "";
                                                                                String string16 = !optJSONObject2.isNull(str6) ? jSONObject7.getString(str6) : "";
                                                                                listvideos4.setN_link_url(jSONObject7.getString(string14).replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                listvideos4.setN_link_extension("mp4");
                                                                                if (!string15.isEmpty()) {
                                                                                    listvideos4.setN_link_title(string15);
                                                                                } else if (findLinksX6 != null) {
                                                                                    listvideos4.setN_link_title(findLinksX6);
                                                                                } else {
                                                                                    if (findLinksX2 != null) {
                                                                                        listvideos4.setN_link_title(findLinksX2);
                                                                                    }
                                                                                }
                                                                                if (!string16.isEmpty()) {
                                                                                    listvideos4.setN_link_image(string16.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                } else if (findLinksX7 != null) {
                                                                                    listvideos4.setN_link_image(findLinksX7.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                } else {
                                                                                    if (findLinksX3 != null) {
                                                                                        listvideos4.setN_link_image(findLinksX3.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                                                    }
                                                                                }
                                                                                AnonymousClass4.this.val$arrayList.add(listvideos4);
                                                                            }
                                                                        }
                                                                    }
                                                                }

                                                                AnonymousClass4 anonymousClass42 = AnonymousClass4.this;
                                                                LocalScrtpListener localScrtpListener2 = anonymousClass42.val$localScrtpListener;
                                                                if (localScrtpListener2 == null) {
                                                                    return;
                                                                }
                                                                localScrtpListener2.onReceivedData(2, anonymousClass42.val$arrayList, host2);
                                                                return;
                                                            } catch (Exception e8) {
                                                                e8.printStackTrace();
                                                                AnonymousClass4 anonymousClass43 = AnonymousClass4.this;
                                                                LocalScrtpListener localScrtpListener3 = anonymousClass43.val$localScrtpListener;
                                                                if (localScrtpListener3 == null) {
                                                                    return;
                                                                }
                                                                localScrtpListener3.onReceivedData(0, anonymousClass43.val$arrayList, host2);
                                                                return;
                                                            }
                                                        }
                                                        AnonymousClass4 anonymousClass44 = AnonymousClass4.this;
                                                        LocalScrtpListener localScrtpListener4 = anonymousClass44.val$localScrtpListener;
                                                        if (localScrtpListener4 == null) {
                                                            return;
                                                        }
                                                        localScrtpListener4.onReceivedData(-1, anonymousClass44.val$arrayList, host2);
                                                    });
                                                    return;
                                                }
                                                AnonymousClass4 anonymousClass42 = AnonymousClass4.this;
                                                LocalScrtpListener localScrtpListener2 = anonymousClass42.val$localScrtpListener;
                                                if (localScrtpListener2 == null) {
                                                    return;
                                                }
                                                localScrtpListener2.onReceivedData(-2, anonymousClass42.val$arrayList, host2);
                                                return;
                                            }
                                            if (str != null) {
                                                store_model_video_link.listVideos listvideos = new store_model_video_link.listVideos();
                                                listvideos.setN_link_url(str.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                listvideos.setN_link_extension("mp4");
                                                if (findLinksX2 != null) {
                                                    listvideos.setN_link_title(findLinksX2);
                                                }
                                                if (findLinksX3 != null) {
                                                    listvideos.setN_link_image(findLinksX3.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                }
                                                AnonymousClass4.this.val$arrayList.add(listvideos);
                                            }
                                            if (findLinksX4 != null) {
                                                store_model_video_link.listVideos listvideos2 = new store_model_video_link.listVideos();
                                                listvideos2.setN_link_url(findLinksX4.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                listvideos2.setN_link_extension("mp4");
                                                if (findLinksX2 != null) {
                                                    listvideos2.setN_link_title(findLinksX2);
                                                }
                                                if (findLinksX3 != null) {
                                                    listvideos2.setN_link_image(findLinksX3.replace("(", "").replace(")", "").replace("\\/", "/"));
                                                }
                                                AnonymousClass4.this.val$arrayList.add(listvideos2);
                                            }
                                            AnonymousClass4 anonymousClass43 = AnonymousClass4.this;
                                            LocalScrtpListener localScrtpListener3 = anonymousClass43.val$localScrtpListener;
                                            if (localScrtpListener3 == null) {
                                                return;
                                            }
                                            localScrtpListener3.onReceivedData(1, anonymousClass43.val$arrayList, host2);
                                            return;
                                        }
                                        AnonymousClass4 anonymousClass44 = AnonymousClass4.this;
                                        LocalScrtpListener localScrtpListener4 = anonymousClass44.val$localScrtpListener;
                                        if (localScrtpListener4 == null) {
                                            return;
                                        }
                                        localScrtpListener4.onReceivedData(-2, anonymousClass44.val$arrayList, host2);
                                    } catch (Exception e8) {
                                        e = e8;
                                        e.printStackTrace();
                                        AnonymousClass4 anonymousClass45 = AnonymousClass4.this;
                                        localScrtpListener = anonymousClass45.val$localScrtpListener;
                                        if (localScrtpListener != null) {
                                        }
                                    }
                                } else {
                                    AnonymousClass4 anonymousClass46 = AnonymousClass4.this;
                                    LocalScrtpListener localScrtpListener5 = anonymousClass46.val$localScrtpListener;
                                    if (localScrtpListener5 == null) {
                                        return;
                                    }
                                    localScrtpListener5.onReceivedData(-1, anonymousClass46.val$arrayList, host2);
                                }
                            });
                            return;
                        }
                        LocalScrtpListener localScrtpListener = this.val$localScrtpListener;
                        if (localScrtpListener != null) {
                            localScrtpListener.onReceivedData(-3, this.val$arrayList, host2);
                            return;
                        }
                        return;
                    }
                    LocalScrtpListener localScrtpListener2 = this.val$localScrtpListener;
                    if (localScrtpListener2 != null) {
                        localScrtpListener2.onReceivedData(-4, this.val$arrayList, host2);
                        return;
                    }
                    return;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    LocalScrtpListener localScrtpListener3 = this.val$localScrtpListener;
                    if (localScrtpListener3 != null) {
                        localScrtpListener3.onReceivedData(0, this.val$arrayList, "other");
                        return;
                    }
                    return;
                }
            }
            LocalScrtpListener localScrtpListener4 = this.val$localScrtpListener;
            if (localScrtpListener4 != null) {
                localScrtpListener4.onReceivedData(-1, this.val$arrayList, "api_fail");
            }
        }
    }

    public static class DgFragment extends DialogFragment {
        private ImageView[] dots;
        private int dotscount;
        private FrgForHelp mFragmentHelp1 = null;
        private FrgForHelp mFragmentHelp2 = null;
        private final int[] vpPos = {0};

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Dialogss);
        }

        @Override
        @NotNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog onCreateDialog = super.onCreateDialog(savedInstanceState);
            Objects.requireNonNull(onCreateDialog.getWindow()).requestFeature(1);
            return onCreateDialog;
        }

        @Override
        @Nullable
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View inflate = inflater.inflate(R.layout.asddasdasdasd, container, false);
            final ViewPager viewPager = (ViewPager) inflate.findViewById(R.id.helpPager);
            final TextView textView = (TextView) inflate.findViewById(R.id.txtOK);
            final TextView textView2 = (TextView) inflate.findViewById(R.id.txtSkip);
            LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.SliderDots);
            ScreenHelpForDownload.ViewPagerAdapter viewPagerAdapter = new ScreenHelpForDownload.ViewPagerAdapter(getChildFragmentManager());
            if (this.mFragmentHelp1 == null) {
                this.mFragmentHelp1 = new FrgForHelp();
                Bundle bundle = new Bundle();
                bundle.putInt("int_data", 4);
                this.mFragmentHelp1.setArguments(bundle);
            }
            if (this.mFragmentHelp2 == null) {
                this.mFragmentHelp2 = new FrgForHelp();
                Bundle bundle = new Bundle();
                bundle.putInt("int_data", 5);
                this.mFragmentHelp2.setArguments(bundle);
            }
            viewPagerAdapter.addFragment(this.mFragmentHelp1, "0");
            viewPagerAdapter.addFragment(this.mFragmentHelp2, "1");
            int count = viewPagerAdapter.getCount();
            this.dotscount = count;
            this.dots = new ImageView[count];
            for (int i = 0; i < this.dotscount; i++) {
                if (getActivity() != null) {
                    linearLayout.setWeightSum(this.dotscount);
                    this.dots[i] = new ImageView(getActivity());
                    this.dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_main_img));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                    layoutParams.setMargins(0, 0, 0, 0);
                    layoutParams.weight = 1.0f;
                    linearLayout.addView(this.dots[i], layoutParams);
                }
            }
            if (getActivity() != null) {
                this.dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_small));
            }
            textView.setOnClickListener(v -> {
                if (DgFragment.this.vpPos[0] == 1) {
                    DgFragment.this.dismiss();
                    return;
                }
                DgFragment.this.vpPos[0] = DgFragment.this.vpPos[0] + 1;
                viewPager.setCurrentItem(DgFragment.this.vpPos[0]);
            });
            textView2.setOnClickListener(v -> DgFragment.this.dismiss());
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrollStateChanged(int state) {
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (DgFragment.this.getActivity() != null) {
                        for (int i2 = 0; i2 < DgFragment.this.dotscount; i2++) {
                            DgFragment.this.dots[i2].setImageDrawable(ContextCompat.getDrawable(DgFragment.this.getActivity(), R.drawable.non_active_main_img));
                        }
                        DgFragment.this.dots[position].setImageDrawable(ContextCompat.getDrawable(DgFragment.this.getActivity(), R.drawable.active_small));
                        DgFragment.this.vpPos[0] = position;
                        if (position == 1) {
                            textView.setText(R.string.Continue);
                            textView.setTextSize(23.0f);
                            textView2.setVisibility(View.GONE);
                            return;
                        }
                        textView.setTextSize(20.0f);
                        textView.setText(R.string.NEXT);
                        textView2.setVisibility(View.VISIBLE);
                    }
                }
            });
            viewPager.setCurrentItem(0);
            this.vpPos[0] = 0;
            viewPager.setOffscreenPageLimit(1);
            viewPager.setAdapter(viewPagerAdapter);
            viewPagerAdapter.notifyDataSetChanged();
            return inflate;
        }

        @Override

        public void onDismiss(@NonNull DialogInterface dialog) {
            super.onDismiss(dialog);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog dialog = getDialog();
            if (dialog != null) {
                Window window = dialog.getWindow();
                Objects.requireNonNull(window);
                window.setLayout(-1, -1);
                Dialog dialog2 = getDialog();
                Objects.requireNonNull(dialog2);
                Window window2 = dialog2.getWindow();
                Objects.requireNonNull(window2);
                window2.setGravity(17);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
        }
    }

    private static void FetchLocalLinks(String siteType, String newUrl) {
        HttpApis httpApis = (HttpApis) ApiConnectionClass.getRetrofitInstance().create(HttpApis.class);
        if (newUrl.isEmpty()) {
            newUrl = webURL;
        }
        if (siteType.equalsIgnoreCase("in")) {
            String idFromPref = "?__a=1&__d=dis";
            StringBuilder sb = new StringBuilder();
            sb.append(urlWithoutParam(newUrl));
            sb.append(idFromPref);
            String sb2 = sb.toString();
            String cookie = CookieManager.getInstance().getCookie(newUrl);
            if (cookie != null && !cookie.isEmpty()) {
                callLocal = httpApis.findLocalVideo(cookie, sb2);
            } else {
                callLocal = httpApis.findLocalVideo(sb2);
            }
        } else {
            String cookie2 = CookieManager.getInstance().getCookie(newUrl);
            if (cookie2 != null && !cookie2.isEmpty()) {
                callLocal = httpApis.findLocalVideo(cookie2, newUrl);
            } else {
                callLocal = httpApis.findLocalVideo(newUrl);
            }
        }
        callLocal.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.isSuccessful()) {
                        if (siteType.equalsIgnoreCase("fb")) {
                            final String str = siteType;
                            new FB_Parse(arrayList -> {
                                if (arrayList != null && arrayList.size() > 0) {
                                    if (UtilsForApp.vidAPIParseListen != null) {
                                        UtilsForApp.vidAPIParseListen.onParseResponse(2, null, arrayList, str);
                                        return;
                                    }
                                    return;
                                }
                                UtilsForApp.fetchNewLinks();

                            }, UtilsForApp.mTitle).findLinks(response.body());
                        } else if (siteType.equalsIgnoreCase("sc")) {
                            final String str2 = siteType;
                            new Sharec_Parse(arrayList -> {
                                if (arrayList != null && arrayList.size() > 0) {
                                    if (UtilsForApp.vidAPIParseListen != null) {
                                        UtilsForApp.vidAPIParseListen.onParseResponse(2, null, arrayList, str2);
                                        return;
                                    }
                                    return;
                                }
                                UtilsForApp.fetchNewLinks();

                            }, UtilsForApp.mTitle).findLinks(response.body());
                        } else if (siteType.equalsIgnoreCase("sn")) {
                            final String str3 = siteType;
                            new Snak_Parse(arrayList -> {
                                if (arrayList != null && arrayList.size() > 0) {
                                    if (UtilsForApp.vidAPIParseListen != null) {
                                        UtilsForApp.vidAPIParseListen.onParseResponse(2, null, arrayList, str3);
                                        return;
                                    }
                                    return;
                                }
                                UtilsForApp.fetchNewLinks();
                            }, UtilsForApp.mTitle).findLinks(response.body());
                        } else if (siteType.equalsIgnoreCase("xv")) {
                            final String str4 = siteType;
                            new XV_Parse(arrayList -> {
                                if (arrayList != null && arrayList.size() > 0) {
                                    if (UtilsForApp.vidAPIParseListen != null) {
                                        UtilsForApp.vidAPIParseListen.onParseResponse(2, null, arrayList, str4);
                                        return;
                                    }
                                    return;
                                }
                                UtilsForApp.fetchNewLinks();
                            }, UtilsForApp.mTitle).findLinks(response.body());
                        } else if (siteType.equalsIgnoreCase("ok")) {
                            final String str5 = siteType;
                            new OK_Parse(arrayList -> {
                                if (arrayList != null && arrayList.size() > 0) {
                                    if (UtilsForApp.vidAPIParseListen != null) {
                                        UtilsForApp.vidAPIParseListen.onParseResponse(2, null, arrayList, str5);
                                        return;
                                    }
                                    return;
                                }
                                newFetchLinkMathod();
                            }, UtilsForApp.mTitle, UtilsForApp.webURL).findLinks(response.body());
                        } else if (siteType.equalsIgnoreCase("in")) {
                            final String str6 = siteType;
                            new Insta_Parse(arrayList -> {
                                if (arrayList != null && arrayList.size() > 0) {
                                    if (UtilsForApp.vidAPIParseListen != null) {
                                        UtilsForApp.vidAPIParseListen.onParseResponse(2, null, arrayList, str6);
                                        return;
                                    }
                                    return;
                                }
                                UtilsForApp.fetchNewLinks();

                            }, UtilsForApp.mTitle).findLinks(response.body());
                        } else if (siteType.equalsIgnoreCase("vm")) {
                            final String str7 = siteType;
                            new Vimeo_Parse(arrayList -> {
                                if (arrayList != null && arrayList.size() > 0) {
                                    if (UtilsForApp.vidAPIParseListen != null) {
                                        UtilsForApp.vidAPIParseListen.onParseResponse(2, null, arrayList, str7);
                                        return;
                                    }
                                    return;
                                }
                                UtilsForApp.fetchNewLinks();
                            }, UtilsForApp.mTitle).findLinks(response.body());
                        } else {
                            UtilsForApp.fetchNewLinks();
                        }
                    } else {
                        UtilsForApp.fetchNewLinks();
                    }
                } catch (Exception unused) {
                    UtilsForApp.fetchNewLinks();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isCanceled()) {
                    return;
                }
                t.printStackTrace();
                UtilsForApp.fetchNewLinks();
            }
        });
    }

    public static void FillLocalPardata() {
        String readFileToString = readFileToString("psr");
        if (readFileToString.isEmpty()) {
            return;
        }
        DclassApp.localparsdata = readFileToString;
    }

    public static void FillRegexers() {
        try {
            String readFileToString = readFileToString("used_pattern5");
            if (!readFileToString.isEmpty()) {
                String[] split = readFileToString.split(StringUtils.LF);
                if (split.length > 0) {
                    DclassApp.allUrlRegx = split;
                    return;
                }
                return;
            }
            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(DclassApp.getInstance().getAssets().open("used_pattern_data.txt"));
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
                sb.append('\n');
            }
            String sb2 = sb.toString();
            String[] split2 = sb2.split(StringUtils.LF);
            if (!TextUtils.isEmpty(sb2) && split2.length > 0) {
                DclassApp.allUrlRegx = split2;
                writeStringToFile(sb2, "used_pattern5");
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void FillUnwanted() {
        try {
            String readFileToString = readFileToString("exclude_data4");
            if (!readFileToString.isEmpty()) {
                List<String> list = DclassApp.UnwantedDomains;
                if (list != null) {
                    list.clear();
                }
                DclassApp.UnwantedDomains = (List) new Gson().fromJson(readFileToString, List.class);
                return;
            }
            InputStream open = DclassApp.getInstance().getAssets().open("main_exclude_data.txt");
            int available = open.available();
            byte[] bArr = new byte[available];
            open.read(bArr);
            open.close();
            if (available > 0) {
                List<String> list2 = DclassApp.UnwantedDomains;
                if (list2 != null) {
                    list2.clear();
                }
                DclassApp.UnwantedDomains = (List) new Gson().fromJson(new String(bArr), List.class);
                writeStringToFile(new String(bArr), "exclude_data4");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean IsNonVideoURL(String str) {
        String host;
        return (TextUtils.isEmpty(str) || (host = getHost(str)) == null || (!host.toLowerCase().contains("youtube.com") && !host.toLowerCase().contains("youtu.be") && !host.toLowerCase().contains("soundcloud") && !host.toLowerCase().contains("accounts.google") && !str.equalsIgnoreCase("https://m.facebook.com") && !str.equalsIgnoreCase("https://facebook.com") && !str.equalsIgnoreCase("https://m.facebook.com/?_rdr") && !str.equalsIgnoreCase("https://facebook.com/?_rdr") && !str.equalsIgnoreCase("https://m.facebook.com/home.php?tbua=1") && !str.equalsIgnoreCase("https://m.facebook.com/?tbua=1") && !str.toLowerCase().contains("xnxx.com/search") && !str.toLowerCase().contains("doubleclick.net") && !str.toLowerCase().contains("googleadservices.com") && !str.toLowerCase().contains("adssettings.google.com") && !str.toLowerCase().contains("syndication.exdynsrv.com") && !str.toLowerCase().contains("drive.google.com") && !str.toLowerCase().contains("google.com/search") && !str.toLowerCase().contains("google.com/googleplay/") && !str.toLowerCase().contains("google.com/view/") && !str.toLowerCase().contains("google.com/url?") && !str.toLowerCase().contains("google.com/store/") && !str.toLowerCase().contains("google.com/youtube") && !str.toLowerCase().contains("support.google.com") && !str.toLowerCase().contains("facebook.com/profile.php") && !str.toLowerCase().contains("facebook.com/home.php") && !str.toLowerCase().contains("facebook.com/photo.php") && !str.toLowerCase().contains("facebook.com/reg/") && !str.toLowerCase().contains("app.goo.gl") && !str.toLowerCase().contains("facebook.com/upgrade") && !str.toLowerCase().contains("facebook.com/messages") && !str.toLowerCase().contains("facebook.com/marketplace") && !str.toLowerCase().contains("facebook.com/?_rdr") && !str.toLowerCase().contains("api.twitter.com"))) ? false : true;
    }

    public static boolean IsValidUrl(String urlString) {
        try {
            if (URLUtil.isValidUrl(urlString)) {
                return Patterns.WEB_URL.matcher(urlString).matches();
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public static ArrayList<WaModel> NewListFiles(String wPath) {
        ArrayList<WaModel> arrayList = new ArrayList<>();
        try {
            Uri parse = Uri.parse(wPath);
            Uri buildChildDocumentsUriUsingTree = DocumentsContract.buildChildDocumentsUriUsingTree(parse, DocumentsContract.getTreeDocumentId(parse));
            Cursor query = DclassApp.getInstance().getContentResolver().query(buildChildDocumentsUriUsingTree, new String[]{"_display_name", "document_id"}, null, null, null);
            while (Objects.requireNonNull(query).moveToNext()) {
                Uri buildDocumentUriUsingTree = DocumentsContract.buildDocumentUriUsingTree(buildChildDocumentsUriUsingTree, query.getString(1));
                if (!query.getString(0).equalsIgnoreCase(".nomedia")) {
                    arrayList.add(new WaModel(query.getString(0), buildDocumentUriUsingTree.toString()));
                }
            }
            if (!query.isClosed()) {
                query.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static String VerifyTitle(String str) {
        try {
            return Pattern.compile("[`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。，、？]").matcher(str).replaceAll("").replace("  ", StringUtils.SPACE).replace(StringUtils.SPACE, "_").replace("-", "").replaceAll("\\W+", "").replace("__", "_").trim();
        } catch (Exception unused) {
            return str;
        }
    }

    public static void cancelRetroCall() {
        Call<String> call = callLocal;
        if (call != null && !call.isCanceled()) {
            callLocal.cancel();
        }
        Call<store_model_video_link> call2 = callMain;
        if (call2 == null || call2.isCanceled()) {
            return;
        }
        callMain.cancel();
    }

    public static void checkVideoLink(Context context, String videoURL, String VideoTitle, String place, Vid_API_Parse_Listen resListener) {
        webURL = videoURL;
        EtValueUrl = videoURL;
        mTitle = VideoTitle;
        mPlace = place;
        vidAPIParseListen = resListener;
        av1Code = FirstEncode.firstOccur(videoURL, "cd1799ccb7b102d16790727de5f12a9b");
        if (isNetworkAvailable(context)) {
            if (!webURL.contains("facebook.com/") && !webURL.contains("fb.com/") && !webURL.contains("fb.watch/")) {
                if (webURL.contains(new String(Base64.decode("b2sueHh4DQo=", 0)).trim())) {
                    FetchLocalLinks("ok", "");
                    return;
                } else if (webURL.contains("instagram.com/")) {
                    FetchLocalLinks("in", "");
                    return;
                } else if (webURL.contains("sharechat.com/")) {
                    FetchLocalLinks("sc", "");
                    return;
                } else if (!webURL.contains("snackvideo.com/") && !webURL.contains("sck.io/")) {
                    if (!Pattern.compile("xnxx(.*).com/video").matcher(webURL).find() && !Pattern.compile("xnxx(.*).health/video").matcher(webURL).find() && !webURL.contains(new String(Base64.decode("eHZpZGVvcy5jb20vdmlkZW8=", 0)).trim()) && !webURL.contains(new String(Base64.decode("eG54eC5jb20vdmlkZW8=", 0)).trim()) && !webURL.contains(new String(Base64.decode("eHZpZGVvczMuY29tL3ZpZGVv", 0)).trim()) && !webURL.contains(new String(Base64.decode("eHZpZGVvczIuY29tL3ZpZGVv", 0)).trim()) && !webURL.contains(new String(Base64.decode("eG54eC50di92aWRlbw==", 0)).trim()) && !webURL.contains(new String(Base64.decode("eHYtdmlkZW9zMS5jb20=", 0)).trim()) && !webURL.contains(new String(Base64.decode("eHZpZGVvcy53cHRyaS5jb20=", 0)).trim()) && !webURL.contains(new String(Base64.decode("eHZpZGVvc3BsdXMubmV0", 0)).trim()) && !webURL.contains(new String(Base64.decode("eG54eC54eHgvdmlkZW8=", 0)).trim()) && !webURL.contains(new String(Base64.decode("eG54eDMuY29tL3ZpZGVv", 0)).trim()) && !webURL.contains(new String(Base64.decode("eG54eDExNS5jb20vdmlkZW8=", 0)).trim()) && !webURL.contains(new String(Base64.decode("eHYtdmlkZW9zMS5jb20vdmlkZW8=", 0)).trim()) && !webURL.contains(new String(Base64.decode("eG54eDIuY29tL3ZpZGVv", 0)).trim())) {
                        if (!webURL.contains("vimeo.com/") && !Pattern.compile("/vimeo.com/([0-9]+)").matcher(webURL).find()) {
                            newFetchLinkMathod();
                            return;
                        }
                        Matcher matcher = Pattern.compile("/vimeo.com/([a-zA-Z0-9]+)").matcher(webURL);
                        if (matcher.find()) {
                            String group = matcher.group(1);
                            if (!TextUtils.isEmpty(group)) {
                                FetchLocalLinks("vm", MediaMetadataCompat$Builder$$ExternalSyntheticOutline0("https://player.vimeo.com/video/", group, "/config"));
                                return;
                            }
                        }
                        fetchNewLinks();
                        return;
                    }
                    FetchLocalLinks("xv", "");
                    return;
                } else {
                    FetchLocalLinks("sn", "");
                    return;
                }
            }
            FetchLocalLinks("fb", "");
            return;
        }
        Vid_API_Parse_Listen vid_API_Parse_Listen = vidAPIParseListen;
        if (vid_API_Parse_Listen != null) {
            vid_API_Parse_Listen.onParseResponse(0, null, null, "none");
        }
    }

    public static String MediaMetadataCompat$Builder$$ExternalSyntheticOutline0(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static String convertMillieToHMmSs(long millie) {
        long j = millie / 1000;
        long j2 = j % 60;
        long j3 = (j / 60) % 60;
        long j4 = (j / 3600) % 24;
        return j4 > 0 ? String.format(Locale.getDefault(), "%02d:%02d:%02d", j4, j3, j2) : String.format(Locale.getDefault(), "%02d:%02d", j3, j2);
    }

    public static String decypher(String res1) {
        try {
            String string = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance()).getString("parseiv", "");
            byte[] decode = Base64.decode(DclassApp.getInstance().getString(R.string.loc_p_key), 2);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, new SecretKeySpec(decode, "AES"), new IvParameterSpec(string.getBytes(StandardCharsets.UTF_8)));
            return new String(cipher.doFinal(Base64.decode(res1, 2)));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    private static void doCheck(boolean forceScrp, String scrpURL, String url, LocalScrtpListener localScrtpListener) {
        ArrayList<store_model_video_link.listVideos> arrayList = new ArrayList<>();
        try {
            runSampleAPI(forceScrp, true, scrpURL, new HashMap<>(), new AnonymousClass4(url, arrayList, localScrtpListener));
        } catch (Exception e) {
            e.printStackTrace();
            if (localScrtpListener != null) {
                localScrtpListener.onReceivedData(0, arrayList, null);
            }
        }
    }

    public static String easeUrl(String s) {
        if (s.isEmpty()) {
            return s;
        }
        if (s.startsWith("//")) {
            return SupportMenuInflater$$ExternalSyntheticOutline0("https:", s);
        }
        return !s.startsWith("http") ? SupportMenuInflater$$ExternalSyntheticOutline0("https://", s) : s;
    }

    public static void fetchNewLinks() {
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder m = new StringBuilder("");
        m.append(webURL);
        m.append("");
        m.append(currentTimeMillis);
        m.append(mPlace);
        m.append("");


        Call<store_model_video_link> ListVideoLinks = ((HttpApis) ApiConnectionClass.getRetrofitInstance().create(HttpApis.class)).ListVideoLinks(loadalgo(m.toString()), currentTimeMillis, webURL, av1Code, mPlace, 36);


        callMain = ListVideoLinks;
        ListVideoLinks.enqueue(new Callback<store_model_video_link>() {
            @Override
            public void onFailure(@NonNull Call<store_model_video_link> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    return;
                }
                t.printStackTrace();
                if (UtilsForApp.vidAPIParseListen != null) {
                    UtilsForApp.vidAPIParseListen.onParseResponse(0, null, null, "net");
                }
            }

            @Override
            public void onResponse(@NonNull Call<store_model_video_link> call, @NonNull Response<store_model_video_link> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (UtilsForApp.vidAPIParseListen != null) {
                                UtilsForApp.vidAPIParseListen.onParseResponse(1, response.body(), null, "net");
                            }
                        } else if (UtilsForApp.vidAPIParseListen != null) {
                            UtilsForApp.vidAPIParseListen.onParseResponse(0, null, null, "net");
                        }
                    } else if (UtilsForApp.vidAPIParseListen != null) {
                        UtilsForApp.vidAPIParseListen.onParseResponse(0, null, null, "net");
                    }
                } catch (Exception unused) {
                    if (UtilsForApp.vidAPIParseListen != null) {
                        UtilsForApp.vidAPIParseListen.onParseResponse(0, null, null, "net");
                    }
                }
            }
        });
    }

    public static void InvalidationTracker(StringBuilder sb, String str, String str2, String str3, String str4) {
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
    }

    public static String fetchWABusinessPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        String str = File.separator;
        InvalidationTracker(sb, str, "Android/media/com.whatsapp.w4b/WhatsApp Business", str, "Media");
        return new File(m(sb, str, ".Statuses")).isDirectory() ? "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia%2F.Statuses" : "WhatsApp Business%2FMedia%2F.Statuses";
    }

    public static String fetchWAPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        String str = File.separator;
        InvalidationTracker(sb, str, "Android/media/com.whatsapp/WhatsApp", str, "Media");
        return new File(m(sb, str, ".Statuses")).isDirectory() ? "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses" : "WhatsApp%2FMedia%2F.Statuses";
    }

    public static String fetchWaOrWaBPath(String pkg) {
        if (pkg.equalsIgnoreCase("com.whatsapp")) {
            return fetchWAPath();
        }
        return pkg.equalsIgnoreCase("com.whatsapp.w4b") ? fetchWABusinessPath() : "";
    }

    public static String findLinksX(String regex, String resource, int num) {
        try {
            if (regex.isEmpty()) {
                return null;
            }
            Matcher matcher = Pattern.compile(regex).matcher(resource);
            if (!matcher.find() || matcher.groupCount() <= num - 1) {
                return null;
            }
            return matcher.group(num);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String fixSomeURL(String s) {
        String replace = null;
        String str;
        if (s != null) {
            try {
                if (s.isEmpty()) {
                    return s;
                }
                try {
                    replace = s.replace("\\", "").replace("&amp;", "&");
                    String replace2 = replace.replace("u0026", "&");
                    if (replace2.startsWith("//")) {
                        str = "https:" + replace2;
                    } else {
                        if (!s.startsWith("http")) {
                        }
                        str = replace2;
                    }
                    return str;
                } catch (Exception unused) {
                    return replace;
                }
            } catch (Exception unused2) {
                return s;
            }
        }
        return s;
    }

    public static String getFileResolution(Context context, int width, int height) {
        String string = context.getString(R.string.working);
        if (height == 1024 && width == 576) {
            return "576p";
        }
        if (width != 0 && height != 0 && height == width) {
            return height + "p";
        } else if (height > width) {
            if (width > 0) {
                if (width >= 200) {
                    if (width >= 300) {
                        if (width >= 400) {
                            if (width >= 520) {
                                if (width >= 600) {
                                    if (width >= 800) {
                                        return "1080p";
                                    }
                                    return "720p";
                                }
                                return "540p";
                            }
                            return "480p";
                        }
                        return "360p";
                    }
                    return "240p";
                }
                return "144p";
            } else if (height <= 0) {
                return string;
            } else {
                if (height >= 300) {
                    if (height >= 500) {
                        if (height >= 700) {
                            if (height >= 900) {
                                if (height >= 1100) {
                                    if (height >= 1500) {
                                        return "1080p";
                                    }
                                    return "720p";
                                }
                                return "540p";
                            }
                            return "480p";
                        }
                        return "360p";
                    }
                    return "240p";
                }
                return "144p";
            }
        } else if (height > 0) {
            if (height >= 200) {
                if (height >= 300) {
                    if (height >= 400) {
                        if (height >= 520) {
                            if (height >= 600) {
                                if (height >= 800) {
                                    return "1080p";
                                }
                                return "720p";
                            }
                            return "540p";
                        }
                        return "480p";
                    }
                    return "360p";
                }
                return "240p";
            }
            return "144p";
        } else if (width <= 0) {
            return string;
        } else {
            if (width >= 300) {
                if (width >= 500) {
                    if (width >= 700) {
                        if (width >= 900) {
                            if (width >= 1100) {
                                if (width >= 1500) {
                                    return "1080p";
                                }
                                return "720p";
                            }
                            return "540p";
                        }
                        return "480p";
                    }
                    return "360p";
                }
                return "240p";
            }
            return "144p";
        }
    }

    public static String getHost(String url) {
        try {
            return new URI(url).getHost();
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getHost2(String url) {
        String str = "xyz";
        try {
            str = new URI(url).getHost().replace("www.", "");
            return str.replace("www3.", "");
        } catch (Exception unused) {
            return str;
        }
    }

    public static String getMimeType(Uri uri) {
        String mimeTypeFromExtension;
        if (uri.getScheme() != null && FirebaseAnalytics.Param.CONTENT.equals(uri.getScheme())) {
            mimeTypeFromExtension = DclassApp.getInstance().getContentResolver().getType(uri);
        } else {
            mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uri.toString()).toLowerCase());
        }
        return mimeTypeFromExtension == null ? "" : mimeTypeFromExtension;
    }

    private static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor loadInBackground;
        if (context == null) {
            return "";
        }
        if (((context instanceof AppCompatActivity) && ((AppCompatActivity) context).isFinishing()) || (loadInBackground = new CursorLoader(context, contentUri, new String[]{"_data"}, null, null, null).loadInBackground()) == null) {
            return "";
        }
        int columnIndexOrThrow = loadInBackground.getColumnIndexOrThrow("_data");
        loadInBackground.moveToFirst();
        String string = loadInBackground.getString(columnIndexOrThrow);
        loadInBackground.close();
        return string;
    }

    public static String getSearch(String sType) {
        char c;
        switch (sType.hashCode()) {
            case -906398983:
                if (sType.equals(SE_GOOGLE)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -906393889:
                if (sType.equals(SE_DUCKDUCKGO)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 833393009:
                if (sType.equals(SE_FACEBOOK)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1966130303:
                if (sType.equals(SE_YAHOO)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1966136051:
                if (sType.equals(SE_BING)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            return SEHalf[1] + SPHalf[3];
        } else if (c == 3) {
            return SEHalf[2] + SPHalf[0];
        } else if (c != 4) {
            if (c != 5) {
                return SEHalf[0] + SPHalf[0];
            }
            return SEHalf[3] + SPHalf[2];
        } else {
            return SPHalf[1];
        }
    }

    public static String getUserAgent(Application application, String userAgentChoice) {
        String str;
        str = "";
        if (userAgentChoice.equalsIgnoreCase(DclassApp.getInstance().getString(R.string.agent_default))) {
            return Pattern.compile("Version/.+? ").matcher(Pattern.compile(" Build/.+; wv").matcher(WebSettings.getDefaultUserAgent(application)).replaceAll("")).replaceAll("");
        } else if (userAgentChoice.equalsIgnoreCase(DclassApp.getInstance().getString(R.string.agent_windows_desktop))) {
            StringBuilder m = new StringBuilder("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            m.append(webViewEngineVersionDesktop(application));
            return m.toString();
        } else if (userAgentChoice.equalsIgnoreCase(DclassApp.getInstance().getString(R.string.agent_linux_desktop))) {
            return "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:85.0) Gecko/20100101 Firefox/85.0";
        } else {
            if (userAgentChoice.equalsIgnoreCase(DclassApp.getInstance().getString(R.string.agent_macos_desktop))) {
                return "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.2 Safari/605.1.15";
            }
            if (userAgentChoice.equalsIgnoreCase(DclassApp.getInstance().getString(R.string.agent_android_mobile))) {
                StringBuilder m2 = new StringBuilder("Mozilla/5.0 (Linux; Android 11; Pixel 5 Build/RQ1A.210205.004; wv)");
                m2.append(webViewEngineVersion(application));
                return m2.toString();
            } else if (userAgentChoice.equalsIgnoreCase(DclassApp.getInstance().getString(R.string.agent_ios_mobile))) {
                return "Mozilla/5.0 (iPhone; CPU iPhone OS 14_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/604.1";
            } else {
                if (userAgentChoice.equalsIgnoreCase(DclassApp.getInstance().getString(R.string.agent_system))) {
                    String property = System.getProperty("http.agent");
                    return property == null ? StringUtils.SPACE : property;
                } else if (userAgentChoice.equalsIgnoreCase(DclassApp.getInstance().getString(R.string.agent_web_view))) {
                    return WebSettings.getDefaultUserAgent(application);
                } else {
                    if (userAgentChoice.equalsIgnoreCase(DclassApp.getInstance().getString(R.string.agent_custom))) {
                        str = "".length() <= 0 ? null : "";
                        if (str == null) {
                            return StringUtils.SPACE;
                        }
                    } else if (userAgentChoice.equalsIgnoreCase(DclassApp.getInstance().getString(R.string.agent_hide_device))) {
                        StringBuilder m3 = new StringBuilder("Mozilla/5.0 (Linux; Android ");
                        m3.append(Build.VERSION.RELEASE);
                        m3.append(')');
                        m3.append(webViewEngineVersion(application));
                        return m3.toString();
                    }
                    return str;
                }
            }
        }
    }

    public static boolean hasNonValidUrl(String str) {
        if (DclassApp.UnwantedDomains == null) {
            return false;
        }
        int i = 0;
        while (i < DclassApp.UnwantedDomains.size()) {
            if (str.toLowerCase().contains(DclassApp.UnwantedDomains.get(i).toLowerCase())) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static boolean haveTwitLink(String str) {
        return str.contains("twitter.com") || str.contains("mobile.twitter");
    }

    public static void hideKeyboard(final AppCompatActivity activity) {
        final View view = activity != null ? activity.getCurrentFocus() : null;
        new Handler().postDelayed(() -> {
            InputMethodManager inputMethodManager;
            if (view == null || (inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)) == null) {
                return;
            }
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }, 1L);
    }

    public static boolean isAppInstalled(String uri) {
        try {
            DclassApp.getInstance().getPackageManager().getPackageInfo(uri, 1);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isNetworkAvailable(Context activity) {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null && (allNetworkInfo = connectivityManager.getAllNetworkInfo()) != null) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String loadalgo(String raw) {
        String str = DclassApp.getInstance().getString(R.string.app_alg1) + "AUNSxzB7lmeJM3g1efcDPonQb%Q" + ConstantForApp.APP_ALG2;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(str.getBytes(StandardCharsets.UTF_8), str));
            return Base64.encodeToString(mac.doFinal(raw.getBytes(StandardCharsets.UTF_8)), 2);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void newFetchLinkMathod() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance());
        String string = AdHelper.scrapUrl;
        if (string != null && !string.isEmpty()) {
            doCheck(true, string, EtValueUrl, (i, arrayList, str) -> {
                if (arrayList != null && arrayList.size() > 0) {
                    Vid_API_Parse_Listen vid_API_Parse_Listen = vidAPIParseListen;
                    if (vid_API_Parse_Listen != null) {
                        vid_API_Parse_Listen.onParseResponse(2, null, arrayList, str);
                        return;
                    }
                    return;
                }
                fetchNewLinks();
            });
            return;
        }
        fetchNewLinks();
    }

    public static String readFileToString(String fileName) {
        if (fileName.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(DclassApp.getInstance().getFilesDir(), fileName);
            if (file.exists() && file.isFile() && file.canRead()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                    sb.append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString().trim();
    }

    public static String SupportMenuInflater$$ExternalSyntheticOutline0(String str, String str2) {
        return str + str2;
    }

    public static void runSampleAPI(boolean forceScrp, final boolean siteApiCalling, String url, HashMap<String, String> headers, final SampleLis resListener) {
        Call<String> initUrlfetch;
        String str;
        if (siteApiCalling && !forceScrp && (str = DclassApp.localparsdata) != null && !str.isEmpty()) {
            if (resListener != null) {
                resListener.onResGet(1, DclassApp.localparsdata);
                return;
            }
            return;
        }
        HttpApis httpApis = (HttpApis) ApiConnectionClass.getRetrofitInstance().create(HttpApis.class);
        if (!url.startsWith("http")) {
            url = SupportMenuInflater$$ExternalSyntheticOutline0("http://", url);
        }
        if (headers == null) {
            initUrlfetch = httpApis.initUrlfetch(url);
        } else {
            initUrlfetch = httpApis.initUrlfetch(url, headers);
        }
        initUrlfetch.enqueue(new Callback<String>() {
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                if (resListener != null) {
                    resListener.onResGet(0, "");
                }
            }

            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try {
                    if (response.isSuccessful()) {
                        String body = response.body();
                        if (body != null && !TextUtils.isEmpty(body)) {
                            if (siteApiCalling) {
                                if (body.length() > 16) {
                                    SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance());
                                    String substring = body.substring(body.length() - 16);
                                    String replace = body.replace(substring, "");
                                    UtilsForApp.writeStringToFile(replace, "psr");
                                    DclassApp.localparsdata = replace;
                                    defaultSharedPreferences.edit().putString("parseiv", substring).apply();
                                    if (resListener != null) {
                                        resListener.onResGet(1, replace);
                                    }
                                } else {
                                    if (resListener != null) {
                                        resListener.onResGet(1, body);
                                    }
                                }
                            } else {
                                if (resListener != null) {
                                    resListener.onResGet(1, body);
                                }
                            }
                        } else {
                            if (resListener != null) {
                                resListener.onResGet(0, "");
                            }
                        }
                    } else {
                        if (resListener != null) {
                            resListener.onResGet(0, "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (resListener != null) {
                        resListener.onResGet(0, "");
                    }
                }
            }
        });
    }

    public static void sendFileInDownloadFolder(Context context, String filename, Uri fileuri, boolean isURI, int adapterPosition, boolean deleteOriginal, ListnerForVideoStore videoStoreListen) {
        File file;
        String str;
        Uri uri;
        OutputStream openOutputStream;
        InputStream openInputStream;
        File file2;
        File file3;
        try {
            if (context == null) {
                if (videoStoreListen != null) {
                    videoStoreListen.onFileMoved(0, adapterPosition, "");
                }
            } else if ((context instanceof AppCompatActivity) && ((AppCompatActivity) context).isFinishing()) {
                if (videoStoreListen != null) {
                    videoStoreListen.onFileMoved(0, adapterPosition, "");
                }
            } else {
                if (isURI) {
                    file = null;
                } else {
                    file = new File(context.getExternalCacheDir(), filename);
                    if (!file.exists()) {
                        if (videoStoreListen != null) {
                            videoStoreListen.onFileMoved(3, adapterPosition, "");
                            return;
                        }
                        return;
                    }
                }
                ContentResolver contentResolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                if (Build.VERSION.SDK_INT < 29) {
                    File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    if (!externalStoragePublicDirectory.exists()) {
                        externalStoragePublicDirectory.mkdirs();
                    }
                    if (!isURI) {
                        file2 = new File(externalStoragePublicDirectory + "/" + ConstantForApp.VID_DOWN_PATH);
                    } else {
                        file2 = new File(externalStoragePublicDirectory + "/" + ConstantForApp.VID_DOWN_PATH + "/" + ConstantForApp.MY_STATUS_PATH);
                    }
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                    if (!isURI) {
                        file3 = new File(externalStoragePublicDirectory + "/" + ConstantForApp.VID_DOWN_PATH, filename);
                    } else {
                        file3 = new File(externalStoragePublicDirectory + "/" + ConstantForApp.VID_DOWN_PATH + "/" + ConstantForApp.MY_STATUS_PATH, filename);
                    }
                    openOutputStream = new FileOutputStream(file3);
                    str = file3.getPath();
                    uri = null;
                } else {
                    contentValues.put("_display_name", filename);
                    contentValues.put("title", filename);
                    contentValues.put("date_added", System.currentTimeMillis() / 1000);
                    contentValues.put("datetaken", System.currentTimeMillis());
                    contentValues.put("is_pending", (Integer) 1);
                    if (!isURI) {
                        contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS + "/" + ConstantForApp.VID_DOWN_PATH);
                    } else {
                        contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS + "/" + ConstantForApp.VID_DOWN_PATH + "/" + ConstantForApp.MY_STATUS_PATH);
                    }
                    Uri insert = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
                    str = "";
                    uri = insert;
                    openOutputStream = contentResolver.openOutputStream(Objects.requireNonNull(insert));
                }
                if (!isURI) {
                    openInputStream = new FileInputStream(file);
                } else {
                    openInputStream = DclassApp.getInstance().getContentResolver().openInputStream(fileuri);
                }
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = Objects.requireNonNull(openInputStream).read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    Objects.requireNonNull(openOutputStream).write(bArr, 0, read);
                }
                Objects.requireNonNull(openOutputStream).close();
                openInputStream.close();
                int i = Build.VERSION.SDK_INT;
                if (i >= 29) {
                    contentValues.clear();
                    contentValues.put("is_pending", (Integer) 0);
                    contentResolver.update(uri, contentValues, null, null);
                }
                if (deleteOriginal && Objects.requireNonNull(file).exists()) {
                    file.delete();
                }
                if (adapterPosition == -1) {
                    if (videoStoreListen != null) {
                        videoStoreListen.onFileMoved(1, adapterPosition, "");
                        return;
                    }
                    return;
                }
                if (i >= 29) {
                    str = getRealPathFromURI(context, uri);
                }
                if (videoStoreListen != null) {
                    if (!str.isEmpty()) {
                        videoStoreListen.onFileMoved(1, adapterPosition, str);
                    } else {
                        videoStoreListen.onFileMoved(0, adapterPosition, str);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAllHelpDialog(AppCompatActivity context) {
        new DgFragment().show(context.getSupportFragmentManager(), "MyFragment");
    }


    public static void showToastMsg(String message, Activity activity, boolean isCenter) {
        if (activity != null) {
            View inflate = activity.getLayoutInflater().inflate(R.layout.layout_custom_data, (ViewGroup) null);
            ((TextView) inflate.findViewById(R.id.txttoast)).setText(message);
            Toast toast = new Toast(activity);
            toast.setDuration(Toast.LENGTH_SHORT);
            if (isCenter) {
                toast.setGravity(17, 0, 0);
            }
            toast.setView(inflate);
            toast.show();
        }
    }

    public static void showToastMsgLong(String message, Activity activity, boolean isCenter) {
        if (activity != null) {
            View inflate = activity.getLayoutInflater().inflate(R.layout.layout_custom_data, (ViewGroup) null);
            ((TextView) inflate.findViewById(R.id.txttoast)).setText(message);
            Toast toast = new Toast(activity);
            toast.setDuration(Toast.LENGTH_LONG);
            if (isCenter) {
                toast.setGravity(17, 0, 0);
            }
            toast.setView(inflate);
            toast.show();
        }
    }

    private static String urlWithoutParam(String str) {
        try {
            URI uri = new URI(str);
            return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, uri.getFragment()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String webViewEngineVersion(Application application) {
        return Pattern.compile("Version/.+? ").matcher(StringUtils.substringAfter(WebSettings.getDefaultUserAgent(application), "wv)")).replaceAll("");
    }

    public static String webViewEngineVersionDesktop(Application application) {
        return webViewEngineVersion(application).replace(" Mobile ", StringUtils.SPACE);
    }

    public static void writeEmpty(String fileContents, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(new File(DclassApp.getInstance().getFilesDir(), fileName));
            fileWriter.write(fileContents);
            fileWriter.close();
        } catch (Exception unused) {
            throw new RuntimeException(unused);
        }
    }

    public static void writeStringToFile(String fileContents, String fileName) {
        try {
            if (!fileContents.isEmpty() && !fileName.isEmpty()) {
                FileWriter fileWriter = new FileWriter(new File(DclassApp.getInstance().getFilesDir(), fileName));
                fileWriter.write(fileContents);
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUserAgent(String sType) {
        char c;
        switch (sType.hashCode()) {
            case 115493:
                if (sType.equals(UA_DEF)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 115494:
                if (sType.equals(UA_ANDROID)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 115495:
                if (sType.equals(UA_IE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 115496:
                if (sType.equals(UA_CHROME)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 115497:
                if (sType.equals(UA_FIREFOX)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 115498:
                if (sType.equals(UA_OPERA)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 115499:
                if (sType.equals(UA_SAFARI)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 115500:
                if (sType.equals(UA_EDGE)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 115501:
                if (sType.equals(UA_BRAVE)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c != 0) {
            switch (c) {
                case 3:
                    return userAgents[1];
                case 4:
                    return userAgents[2];
                case 5:
                    return userAgents[3];
                case 6:
                    return userAgents[4];
                case 7:
                    return userAgents[5];
                case '\b':
                    return userAgents[6];
                case '\t':
                    return userAgents[8];
                default:
                    return "";
            }
        }
        return userAgents[0];
    }
}
