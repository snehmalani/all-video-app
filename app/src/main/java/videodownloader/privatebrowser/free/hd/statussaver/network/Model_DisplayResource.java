package videodownloader.privatebrowser.free.hd.statussaver.network;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

@Keep
public class Model_DisplayResource implements Serializable {
    @SerializedName("config_height")
    private int config_height;
    @SerializedName("config_width")
    private int config_width;
    @SerializedName("src")
    private String src;

    public int getConfig_height() {
        return this.config_height;
    }

    public int getConfig_width() {
        return this.config_width;
    }

    public String getSrc() {
        return this.src;
    }

    public void setConfig_height(int i) {
        this.config_height = i;
    }

    public void setConfig_width(int i) {
        this.config_width = i;
    }

    public void setSrc(String str) {
        this.src = str;
    }
}
