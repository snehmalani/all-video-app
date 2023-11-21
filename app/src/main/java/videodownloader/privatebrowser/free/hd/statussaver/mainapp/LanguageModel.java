package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class LanguageModel {
    @SerializedName("image")
    @Expose
    private int image;
    @SerializedName("languageName")
    @Expose
    private String languageName;
    @SerializedName("original_language_name")
    @Expose
    private String originalLanguageName;
    @SerializedName("selected")
    @Expose
    private boolean selected;
    @SerializedName("selection_code")
    @Expose
    private String selectionCode;

    public LanguageModel(String languageName, String originalLanguageName, String selectionCode, boolean isDefaultSelect, int image) {
        this.languageName = languageName;
        this.selected = isDefaultSelect;
        this.selectionCode = selectionCode;
        this.originalLanguageName = originalLanguageName;
        this.image = image;
    }

    public int getImage() {
        return this.image;
    }

    public String getLanguageName() {
        return this.languageName;
    }

    public String getOriginalLanguageName() {
        return this.originalLanguageName;
    }

    public String getSelectionCode() {
        return this.selectionCode;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public void setOriginalLanguageName(String originalLanguageName) {
        this.originalLanguageName = originalLanguageName;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setSelectionCode(String selectionCode) {
        this.selectionCode = selectionCode;
    }
}
