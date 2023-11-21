package videodownloader.privatebrowser.free.hd.statussaver.network;

import android.text.TextUtils;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_video_link;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Snak_Parse {
    private final String linkTitle;
    private final Def_Parse resultHandler;

    public Snak_Parse(Def_Parse paserResultHandler, String str) {
        this.linkTitle = str;
        this.resultHandler = paserResultHandler;
    }

    public final void findLinks(String str) {
        String str2;
        ArrayList<store_model_video_link.listVideos> arrayList = new ArrayList<>();
        try {
            if (!TextUtils.isEmpty(str)) {
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
                String group = matcher.find() ? matcher.group(1) : "";
                Matcher matcher3 = Pattern.compile("\"contentUrl\": \"([^\"]*)\"").matcher(str);
                if (matcher3.find()) {
                    String group2 = matcher3.group(1);
                    store_model_video_link.listVideos listvideos = new store_model_video_link.listVideos();
                    if (group2 != null && !TextUtils.isEmpty(group2)) {
                        listvideos.setN_link_url(URLDecoder.decode(group2.replace("\\", ""), "UTF-8"));
                        listvideos.setN_link_extension("mp4");
                        if (str2 != null && !str2.isEmpty()) {
                            listvideos.setN_link_image(URLDecoder.decode(str2, "UTF-8"));
                        }
                        if (group != null && !group.isEmpty()) {
                            listvideos.setN_link_title(group);
                        } else {
                            listvideos.setN_link_title(this.linkTitle);
                        }
                        arrayList.add(listvideos);
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
