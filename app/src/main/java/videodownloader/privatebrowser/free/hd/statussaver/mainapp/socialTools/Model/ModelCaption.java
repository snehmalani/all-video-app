package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelCaption implements Serializable {
    @SerializedName("image")
    @Expose
    private String Iamge;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("newsubcategories")
    @Expose
    private List<Newsubcategory> newsubcategories = null;

    public static class Newsubcategory implements Serializable {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        public boolean setSelected = false;

        public Integer getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public boolean isSetSelected() {
            return this.setSelected;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSetSelected(boolean setSelected) {
            this.setSelected = setSelected;
        }
    }

    public String getIamge() {
        return this.Iamge;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Newsubcategory> getNewsubcategories() {
        return this.newsubcategories;
    }

    public void setIamge(String iamge) {
        this.Iamge = iamge;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNewsubcategories(List<Newsubcategory> newsubcategories) {
        this.newsubcategories = newsubcategories;
    }
}
