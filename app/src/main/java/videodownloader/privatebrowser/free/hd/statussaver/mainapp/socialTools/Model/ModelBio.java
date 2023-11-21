package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelBio implements Serializable {
    @SerializedName("catagry_id")
    @Expose
    private Integer catagryId;
    @SerializedName("catagry_image")
    @Expose
    private String catagryImage;
    @SerializedName("catagry_name")
    @Expose
    private String catagryName;

    public Integer getCatagryId() {
        return this.catagryId;
    }

    public String getCatagryImage() {
        return this.catagryImage;
    }

    public String getCatagryName() {
        return this.catagryName;
    }

    public void setCatagryId(Integer catagryId) {
        this.catagryId = catagryId;
    }

    public void setCatagryImage(String catagryImage) {
        this.catagryImage = catagryImage;
    }

    public void setCatagryName(String catagryName) {
        this.catagryName = catagryName;
    }
}
