package videodownloader.privatebrowser.free.hd.statussaver.network;

import android.text.TextUtils;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_video_link;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XV_Parse {
    private final Def_Parse resultHandler;

    public XV_Parse(Def_Parse paserResultHandler, String str) {
        this.resultHandler = paserResultHandler;
    }

    public final void findLinks(String str) {
        ArrayList<store_model_video_link.listVideos> arrayList = new ArrayList<>();
        try {
            if (!TextUtils.isEmpty(str)) {
                String findLinks = findLinks("html5player.setVideoTitle\\('(.*)'\\)", str);
                String findLinks2 = findLinks("html5player.setVideoUrlLow\\('(.*)'\\)", str);
                String findLinks3 = findLinks("html5player.setVideoUrlHigh\\('(.*)\\)", str);
                String findLinks4 = findLinks("html5player.setThumbSlide\\('(.*)\\)", str);
                if (!TextUtils.isEmpty(findLinks4) && findLinks4 != null) {
                    findLinks4 = findLinks4.replaceAll("'", "");
                }
                if (!TextUtils.isEmpty(findLinks3)) {
                    String replaceAll = findLinks3 != null ? findLinks3.replaceAll("'", "") : null;
                    store_model_video_link.listVideos listvideos = new store_model_video_link.listVideos();
                    listvideos.setN_link_url(replaceAll);
                    listvideos.setN_link_extension("mp4");
                    listvideos.setN_link_title(findLinks);
                    listvideos.setN_link_format("360");
                    listvideos.setN_link_image(findLinks4);
                    arrayList.add(listvideos);
                }
                if (!TextUtils.isEmpty(findLinks2)) {
                    String replaceAll2 = findLinks2 != null ? findLinks2.replaceAll("'", "") : null;
                    store_model_video_link.listVideos listvideos2 = new store_model_video_link.listVideos();
                    listvideos2.setN_link_url(replaceAll2);
                    listvideos2.setN_link_extension("mp4");
                    listvideos2.setN_link_title(findLinks);
                    listvideos2.setN_link_format("720");
                    listvideos2.setN_link_image(findLinks4);
                    arrayList.add(listvideos2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Def_Parse def_Parse = this.resultHandler;
        if (def_Parse != null) {
            def_Parse.parseData(arrayList);
        }
    }

    private String findLinks(String str, String str2) {
        Matcher matcher = Pattern.compile(str).matcher(str2);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
