package videodownloader.privatebrowser.free.hd.statussaver.network;

import android.text.TextUtils;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpHeaders;

import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_video_link;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class OK_Parse {
    private final String mainURL;
    private final Def_Parse resultHandler;

    public OK_Parse(Def_Parse paserResultHandler, String str, String mainURL) {
        this.resultHandler = paserResultHandler;
        this.mainURL = mainURL;
    }

    public final void findLinks(String str) {
        ArrayList<store_model_video_link.listVideos> arrayList = new ArrayList<>();
        try {
            if (!TextUtils.isEmpty(str)) {
                String findLinks = findLinks("(download-link\" href=\")(.*?)\"", str, 2);
                String findLinks2 = findLinks("og:image\" content=\"(.*?)\"", str, 1);
                String findLinks3 = findLinks("og:title\" content=\"(.*?)\"", str, 1);
                if (findLinks != null && !TextUtils.isEmpty(findLinks)) {
                    store_model_video_link.listVideos listvideos = new store_model_video_link.listVideos();
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(HttpHeaders.REFERER, this.mainURL);
                    listvideos.setN_link_url(findLinks);
                    listvideos.setN_link_extension("mp4");
                    listvideos.setN_link_title(findLinks3);
                    listvideos.setN_link_format("");
                    listvideos.setN_link_image(findLinks2);
                    listvideos.setnHeaders(jSONObject.toString());
                    arrayList.add(listvideos);

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

    private String findLinks(String str, String str2, int num) {
        Matcher matcher = Pattern.compile(str).matcher(str2);
        if (matcher.find()) {
            return matcher.group(num);
        }
        return null;
    }
}
