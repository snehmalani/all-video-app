package videodownloader.privatebrowser.free.hd.statussaver.whatstool;

import androidx.annotation.Keep;
import java.io.Serializable;

@Keep
public class WaModel implements Serializable {
    public final String fileName;
    public final String fileUri;

    public WaModel(String fileName, String fileUri) {
        this.fileName = fileName;
        this.fileUri = fileUri;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileUri() {
        return this.fileUri;
    }
}
