package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRendomQuots {
    @SerializedName("quotes")
    @Expose
    private String quotes;

    public String getQuotes() {
        return this.quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }
}
