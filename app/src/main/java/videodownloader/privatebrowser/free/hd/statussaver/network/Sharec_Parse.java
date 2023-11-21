package videodownloader.privatebrowser.free.hd.statussaver.network;

import android.text.TextUtils;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_video_link;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sharec_Parse {
    private final Def_Parse resultHandler;
    private final String titlestr;

    public Sharec_Parse(Def_Parse paserResultHandler, String str) {
        this.titlestr = str;
        this.resultHandler = paserResultHandler;
    }

    public final void findLinks(String str) {
        String str2;
        String group;
        ArrayList<store_model_video_link.listVideos> arrayList = new ArrayList<>();
        try {
            if (!TextUtils.isEmpty(str)) {
                boolean z = false;
                Matcher matcher = Pattern.compile("\"og:title\" content=\"(.*?)\"").matcher(str);
                Matcher matcher2 = Pattern.compile("\"og:image\" content=\"(.*?)\"").matcher(str);
                if (matcher2.find()) {
                    str2 = matcher2.group(1);
                    if (str2 != null && !str2.isEmpty()) {
                        str2 = str2.replace("\\", "").replace("&amp;", "&").replace("u0026", "&");
                    }
                } else {
                    str2 = "";
                }
                String group2 = matcher.find() ? matcher.group(1) : "";
                Matcher matcher3 = Pattern.compile("\"og:video\" content=\"(.*?)\"").matcher(str);
                if (matcher3.find() && (group = matcher3.group(1)) != null && !group.isEmpty()) {
                    store_model_video_link.listVideos listvideos = new store_model_video_link.listVideos();
                    listvideos.setN_link_url(group.replace("\\", "").replace("&amp;", "&"));
                    if (str2 != null && !str2.isEmpty()) {
                        listvideos.setN_link_image(URLDecoder.decode(str2, "UTF-8"));
                    }
                    if (group2 != null && !group2.isEmpty()) {
                        listvideos.setN_link_title(group2);
                    } else {
                        listvideos.setN_link_title(this.titlestr);
                    }
                    listvideos.setN_link_extension("mp4");
                    arrayList.add(listvideos);
                    z = true;
                }
                if (!z) {
                    Matcher matcher4 = Pattern.compile("\"contentUrl\":\"([^\"]*)\"").matcher(str);
                    if (matcher4.find()) {
                        String group3 = matcher4.group(1);
                        store_model_video_link.listVideos listvideos2 = new store_model_video_link.listVideos();
                        if (group3 != null && !TextUtils.isEmpty(group3)) {
                            listvideos2.setN_link_url(URLDecoder.decode(group3.replace("\\", ""), "UTF-8"));
                            listvideos2.setN_link_extension("mp4");
                            if (str2 != null && !str2.isEmpty()) {
                                listvideos2.setN_link_image(URLDecoder.decode(str2, "UTF-8"));
                            }
                            if (group2 != null && !group2.isEmpty()) {
                                listvideos2.setN_link_title(group2);
                            } else {
                                listvideos2.setN_link_title(this.titlestr);
                            }
                            arrayList.add(listvideos2);
                        }
                    }
                }
            }
        } catch (Throwable unused) {
            throw new RuntimeException(unused);
        }
        Def_Parse def_Parse = this.resultHandler;
        if (def_Parse != null) {
            def_Parse.parseData(arrayList);
        }
    }
}
