package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelDataBio implements Serializable {
    @SerializedName("catagry_id")
    @Expose
    private Integer catagryId;
    @SerializedName("data")
    @Expose
    private String data;

    public Integer getCatagryId() {
        return this.catagryId;
    }

    public String getData() {
        return this.data;
    }

    public void setCatagryId(Integer catagryId) {
        this.catagryId = catagryId;
    }

    public void setData(String data) {
        this.data = data;
    }
}
