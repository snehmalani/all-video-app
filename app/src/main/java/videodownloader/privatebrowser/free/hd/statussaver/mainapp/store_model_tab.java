package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.graphics.Bitmap;

import androidx.annotation.Keep;

@Keep
public class store_model_tab {
    public final Bitmap bitmapTab;
    public final String titleTab;

    public store_model_tab(String tabUrl, Bitmap bitmapTab) {
        this.titleTab = tabUrl;
        this.bitmapTab = bitmapTab;
    }

    public Bitmap getBitmapTab() {
        return this.bitmapTab;
    }

    public String getTitleTab() {
        return this.titleTab;
    }
}
