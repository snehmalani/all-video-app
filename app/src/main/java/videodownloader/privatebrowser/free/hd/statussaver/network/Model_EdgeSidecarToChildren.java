package videodownloader.privatebrowser.free.hd.statussaver.network;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

@Keep
public class Model_EdgeSidecarToChildren implements Serializable {
    @SerializedName("edges")
    private List<Model_Edge> edges;

    public List<Model_Edge> getEdges() {
        return this.edges;
    }

    public void setEdges(List<Model_Edge> list) {
        this.edges = list;
    }
}
