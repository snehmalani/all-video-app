package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model;

import java.util.ArrayList;

public class store_model_tag {
    public final String copyShare;
    public final String name;
    public final ArrayList<String> tagList;

    public store_model_tag(String name, String copyShare, ArrayList<String> tagList) {
        this.name = name;
        this.tagList = tagList;
        this.copyShare = copyShare;
    }

    public String getCopyShare() {
        return this.copyShare;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getTagList() {
        return this.tagList;
    }
}
