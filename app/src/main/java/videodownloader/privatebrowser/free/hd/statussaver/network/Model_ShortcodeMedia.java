package videodownloader.privatebrowser.free.hd.statussaver.network;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

@Keep
public class Model_ShortcodeMedia implements Serializable {
    @SerializedName("accessibility_caption")
    private String accessibility_caption;
    @SerializedName("display_resources")
    private List<Model_DisplayResource> display_resources;
    @SerializedName("display_url")
    private String display_url;
    @SerializedName("edge_sidecar_to_children")
    private Model_EdgeSidecarToChildren edge_sidecar_to_children;
    @SerializedName("is_video")
    private boolean is_video;
    @SerializedName("thumbnail_src")
    private String thumbnail_src;
    @SerializedName("title")
    private String title;
    @SerializedName("video_url")
    private String video_url;

    public String getAccessibility_caption() {
        return this.accessibility_caption;
    }

    public List<Model_DisplayResource> getDisplay_resources() {
        return this.display_resources;
    }

    public String getDisplay_url() {
        return this.display_url;
    }

    public Model_EdgeSidecarToChildren getEdge_sidecar_to_children() {
        return this.edge_sidecar_to_children;
    }

    public String getThumbnail_src() {
        return this.thumbnail_src;
    }

    public String getTitle() {
        return this.title;
    }

    public String getVideo_url() {
        return this.video_url;
    }

    public boolean isIs_video() {
        return this.is_video;
    }

    public void setAccessibility_caption(String str) {
        this.accessibility_caption = str;
    }

    public void setDisplay_resources(List<Model_DisplayResource> list) {
        this.display_resources = list;
    }

    public void setDisplay_url(String str) {
        this.display_url = str;
    }

    public void setEdge_sidecar_to_children(Model_EdgeSidecarToChildren model_EdgeSidecarToChildren) {
        this.edge_sidecar_to_children = model_EdgeSidecarToChildren;
    }

    public void setIs_video(boolean z) {
        this.is_video = z;
    }

    public void setThumbnail_src(String thumbnail_src) {
        this.thumbnail_src = thumbnail_src;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideo_url(String str) {
        this.video_url = str;
    }
}
