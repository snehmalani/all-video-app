package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import androidx.annotation.Keep;

@Keep
public class store_model_history_ForFunc {
    public final int isFounded;
    public final String urlFounded;

    public store_model_history_ForFunc(int isFounded, String urlFounded) {
        this.urlFounded = urlFounded;
        this.isFounded = isFounded;
    }

    public int getIsFounded() {
        return this.isFounded;
    }

    public String getUrlFounded() {
        return this.urlFounded;
    }
}
