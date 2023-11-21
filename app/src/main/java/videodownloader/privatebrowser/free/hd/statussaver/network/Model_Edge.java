package videodownloader.privatebrowser.free.hd.statussaver.network;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

@Keep
public class Model_Edge implements Serializable {
    @SerializedName("node")
    private Model_Node node;

    public Model_Node getNode() {
        return this.node;
    }

    public void setNode(Model_Node model_Node) {
        this.node = model_Node;
    }
}
