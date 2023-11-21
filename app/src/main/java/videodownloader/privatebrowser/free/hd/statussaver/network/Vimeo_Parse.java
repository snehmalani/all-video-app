package videodownloader.privatebrowser.free.hd.statussaver.network;


import android.util.Log;

import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_video_link;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class Vimeo_Parse {
    private final Def_Parse defParse;
    private final String linkTitle;

    public Vimeo_Parse(Def_Parse defParse, String str) {
        this.linkTitle = str;
        this.defParse = defParse;
    }

    public final void findLinks(String str) {
        JSONObject optJSONObject;
        JSONArray optJSONArray;
        ArrayList<store_model_video_link.listVideos> arrayList = new ArrayList<>();
        try {
            JSONObject optJSONObject2 = new JSONObject(str).optJSONObject("request");
            Log.e("opoopooo", "findLinks: " + optJSONObject2 );
            if (optJSONObject2 != null && (optJSONObject = optJSONObject2.optJSONObject("files")) != null && (optJSONArray = optJSONObject.optJSONArray("progressive")) != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject3 = optJSONArray.optJSONObject(i);
                    store_model_video_link.listVideos listvideos = new store_model_video_link.listVideos();
                    listvideos.setN_link_url(optJSONObject3.optString("url"));
                    listvideos.setN_link_extension("mp4");
                    listvideos.setN_link_title(this.linkTitle);
                    listvideos.setN_libk_width(optJSONObject3.optInt("width"));
                    listvideos.setN_link_height(optJSONObject3.optInt("height"));
       
                    arrayList.add(listvideos);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Def_Parse def_Parse = this.defParse;
        if (def_Parse != null) {
            def_Parse.parseData(arrayList);
        }
    }
}
