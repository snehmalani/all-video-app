package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

import java.util.List;

@Keep
public class store_model_video_link {
    @SerializedName("list_videos")
    @Expose
    private final List<listVideos> ListVideos = null;

    @Keep
    public static class listVideos {
        @SerializedName("n_headers")
        @Expose
        private String nHeaders;
        @SerializedName("n_cookie")
        @Expose
        private String n_cookie;
        @SerializedName("n_link_extension")
        @Expose
        private String n_link_extension;
        @SerializedName("n_link_format")
        @Expose
        private String n_link_format;
        @SerializedName("n_link_height")
        @Expose
        private Integer n_link_height;
        @SerializedName("n_link_size")
        @Expose
        private long n_link_size;
        @SerializedName("n_link_title")
        @Expose
        private String n_link_title;
        @SerializedName("n_link_width")
        @Expose
        private Integer n_link_width;
        @SerializedName("n_link_url")
        @Expose
        private String n_link_url = "";
        @SerializedName("n_link_image")
        @Expose
        private String n_link_image = "";
        @SerializedName("is_local_video")
        @Expose
        private boolean is_local_video = false;
        @SerializedName("is_selected")
        @Expose
        private boolean is_selected = false;
        @SerializedName("local_size")
        @Expose
        private String local_size = "";
        @SerializedName("local_quality")
        @Expose
        private String local_quality = "";

        public String getLocal_quality() {
            return this.local_quality;
        }

        public String getLocal_size() {
            return this.local_size;
        }

        public String getN_cookie() {
            return this.n_cookie;
        }

        public Integer getN_libk_width() {
            if (this.n_link_width == null) {
                this.n_link_width = 0;
            }
            return this.n_link_width;
        }

        public String getN_link_extension() {
            return this.n_link_extension;
        }

        public String getN_link_format() {
            return this.n_link_format;
        }

        public Integer getN_link_height() {
            if (this.n_link_height == null) {
                this.n_link_height = 0;
            }
            return this.n_link_height;
        }

        public String getN_link_image() {
            return UtilsForApp.fixSomeURL(this.n_link_image);
        }

        public long getN_link_size() {
            return this.n_link_size;
        }

        public String getN_link_title() {
            return UtilsForApp.VerifyTitle(this.n_link_title);
        }

        public String getN_link_url() {
            return UtilsForApp.fixSomeURL(this.n_link_url);
        }

        public String getnHeaders() {
            if (this.nHeaders == null) {
                this.nHeaders = "";
            }
            return this.nHeaders;
        }

        public boolean isIs_local_video() {
            return this.is_local_video;
        }

        public boolean isIs_selected() {
            return this.is_selected;
        }

        public void setIs_local_video(boolean is_local_video) {
            this.is_local_video = is_local_video;
        }

        public void setIs_selected(boolean is_selected) {
            this.is_selected = is_selected;
        }

        public void setLocal_quality(String local_quality) {
            this.local_quality = local_quality;
        }

        public void setLocal_size(String local_size) {
            this.local_size = local_size;
        }

        public void setN_cookie(String n_cookie) {
            this.n_cookie = n_cookie;
        }

        public void setN_libk_width(Integer n_libk_width) {
            this.n_link_width = n_libk_width;
        }

        public void setN_link_extension(String n_link_extension) {
            this.n_link_extension = n_link_extension;
        }

        public void setN_link_format(String n_link_format) {
            this.n_link_format = n_link_format;
        }

        public void setN_link_height(Integer n_link_height) {
            this.n_link_height = n_link_height;
        }

        public void setN_link_image(String n_link_image) {
            this.n_link_image = UtilsForApp.easeUrl(n_link_image);
        }

        public void setN_link_size(long n_link_size) {
            this.n_link_size = n_link_size;
        }

        public void setN_link_title(String n_link_title) {
            this.n_link_title = UtilsForApp.VerifyTitle(n_link_title);
        }

        public void setN_link_url(String n_link_url) {
            this.n_link_url = n_link_url;
        }

        public void setnHeaders(String nHeaders) {
            this.nHeaders = nHeaders;
        }
    }

    public List<listVideos> getLinksLists() {
        return this.ListVideos;
    }
}
