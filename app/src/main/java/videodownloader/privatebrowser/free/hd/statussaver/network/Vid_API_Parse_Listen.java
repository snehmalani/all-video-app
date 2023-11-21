package videodownloader.privatebrowser.free.hd.statussaver.network;

import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_video_link;
import java.util.ArrayList;

public interface Vid_API_Parse_Listen {
    void onParseResponse(int status, store_model_video_link modelVideoLink, ArrayList<store_model_video_link.listVideos> arrayList, String type);
}
