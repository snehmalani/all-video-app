package videodownloader.privatebrowser.free.hd.statussaver.network;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

@Keep
public class ResponseModel implements Serializable {
    @SerializedName("graphql")
    private Graphql graphql;

    public Graphql getGraphql() {
        return this.graphql;
    }

    public void setGraphql(Graphql graphql2) {
        this.graphql = graphql2;
    }
}
