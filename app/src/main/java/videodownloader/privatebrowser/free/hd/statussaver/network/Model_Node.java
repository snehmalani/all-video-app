package videodownloader.privatebrowser.free.hd.statussaver.network;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

@Keep
public class Model_Node implements Serializable {
    @SerializedName("display_resources")
    private List<Model_DisplayResource> display_resources;
    @SerializedName("display_url")
    private String display_url;
    @SerializedName("is_video")
    private boolean is_video;
    @SerializedName("video_url")
    private String video_url;

    public List<Model_DisplayResource> getDisplay_resources() {
        return this.display_resources;
    }

    public String getDisplay_url() {
        return this.display_url;
    }

    public String getVideo_url() {
        return this.video_url;
    }

    public boolean isIs_video() {
        return this.is_video;
    }

    public void setDisplay_resources(List<Model_DisplayResource> list) {
        this.display_resources = list;
    }

    public void setDisplay_url(String str) {
        this.display_url = str;
    }

    public void setIs_video(boolean z) {
        this.is_video = z;
    }

    public void setVideo_url(String str) {
        this.video_url = str;
    }
}
