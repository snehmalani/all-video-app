package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Store_model_prox implements Serializable {
    @SerializedName("is_connected")
    @Expose
    private Integer isConnected;
    @SerializedName("p_cnt")
    @Expose
    private String pCnt;
    @SerializedName("p_ds")
    @Expose
    private String pDs;
    @SerializedName("p_fl")
    @Expose
    private String pFl;
    @SerializedName("p_ip")
    @Expose
    private String pIp;
    @SerializedName("p_pro")
    @Expose
    private Integer pPro;
    @SerializedName("p_pt")
    @Expose
    private String pPt;
    @SerializedName("p_pw")
    @Expose
    private String pPw;
    @SerializedName("p_un")
    @Expose
    private String pUn;

    public Integer getIsConnected() {
        if (this.isConnected == null) {
            this.isConnected = 0;
        }
        return this.isConnected;
    }

    public String getpCnt() {
        return this.pCnt;
    }

    public String getpDs() {
        return this.pDs;
    }

    public String getpFl() {
        return this.pFl;
    }

    public String getpIp() {
        return this.pIp;
    }

    public Integer getpPro() {
        return this.pPro;
    }

    public String getpPt() {
        return this.pPt;
    }

    public String getpPw() {
        return this.pPw;
    }

    public String getpUn() {
        return this.pUn;
    }

    public void setIsConnected(Integer isConnected) {
        this.isConnected = isConnected;
    }

    public void setpCnt(String pCnt) {
        this.pCnt = pCnt;
    }

    public void setpFl(String pFl) {
        this.pFl = pFl;
    }

    public void setpIp(String pIp) {
        this.pIp = pIp;
    }

    public void setpPro(Integer pPro) {
        this.pPro = pPro;
    }

    public void setpPt(String pPt) {
        this.pPt = pPt;
    }

    public void setpPw(String pPw) {
        this.pPw = pPw;
    }

    public void setpUn(String pUn) {
        this.pUn = pUn;
    }
}
