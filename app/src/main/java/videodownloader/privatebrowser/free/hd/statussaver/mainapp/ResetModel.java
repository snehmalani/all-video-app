package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class ResetModel {
    @SerializedName("isdone")
    @Expose
    private Integer isdone;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getIsdone() {
        return this.isdone;
    }

    public String getStatus() {
        return this.status;
    }
}
