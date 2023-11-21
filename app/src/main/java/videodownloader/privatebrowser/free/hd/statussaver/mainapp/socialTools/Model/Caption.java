package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Caption implements Serializable {
    @SerializedName("list_captions")
    @Expose
    private List<ListCaption> listCaption = null;

    public static class ListCaption implements Serializable {
        @SerializedName("caption")
        @Expose
        private String caption;

        public String getCaption() {
            return this.caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }
    }

    public List<ListCaption> getNewsubcategories() {
        return this.listCaption;
    }

    public void setNewsubcategories(List<ListCaption> newsubcategories) {
        this.listCaption = newsubcategories;
    }
}
