package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import androidx.annotation.Keep;

@Keep
public class store_model_history {
    private final Integer _id;
    private String title;
    private String url;

    public store_model_history(Integer _id, String title, String url, Long visitedtime) {
        this._id = _id;
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public Integer get_id() {
        return this._id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
