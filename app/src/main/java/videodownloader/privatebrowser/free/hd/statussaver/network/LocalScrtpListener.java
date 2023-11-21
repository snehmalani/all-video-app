package videodownloader.privatebrowser.free.hd.statussaver.network;

import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_video_link;
import java.util.ArrayList;

public interface LocalScrtpListener {
    void onReceivedData(int status, ArrayList<store_model_video_link.listVideos> arrayList, String type);
}
