package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model;

import androidx.annotation.Keep;

@Keep
public class store_model_hashtag {
    public String color;
    public String image;
    public String title;

    public store_model_hashtag(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public String getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public store_model_hashtag(String title, String color, String image) {
        this.title = title;
        this.color = color;
        this.image = image;
    }
}
