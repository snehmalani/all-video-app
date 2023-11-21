package videodownloader.privatebrowser.free.hd.statussaver.network;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

@Keep
public class Graphql implements Serializable {
    @SerializedName("user")
    public Model_User model_user;
    @SerializedName("shortcode_media")
    private Model_ShortcodeMedia shortcode_media;

    public Model_ShortcodeMedia getShortcode_media() {
        return this.shortcode_media;
    }

    public Model_User getUser_media() {
        return this.model_user;
    }

    public void setShortcode_media(Model_ShortcodeMedia model_ShortcodeMedia) {
        this.shortcode_media = model_ShortcodeMedia;
    }
}
