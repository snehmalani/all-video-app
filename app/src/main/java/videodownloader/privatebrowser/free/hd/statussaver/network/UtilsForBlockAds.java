package videodownloader.privatebrowser.free.hd.statussaver.network;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UtilsForBlockAds extends Worker {
    private static final int MAX_FILE_SIZE = 10485760;
    public static final String[] adblkList = {"https://easylist.to/easylist/easyprivacy.txt", "https://easylist.to/easylist/fanboy-social.txt", "https://easylist.to/easylist/easylist.txt", "https://pgl.yoyo.org/adservers/serverlist.php?hostformat=hosts&showintro=1&mimetype=plaintext"};

    public UtilsForBlockAds(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private void downloadUrl(String url, File outputFile) {
        byte[] bArr = new byte[16384];
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                int i = 0;
                while (i < MAX_FILE_SIZE) {
                    try {
                        int read = bufferedInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        bufferedOutputStream.write(bArr, 0, read);
                        i += read;
                    } catch (Throwable th) {
                        try {
                            bufferedOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                bufferedOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            httpURLConnection.disconnect();
        } catch (Exception unused) {
        }
    }

    @Override
    @NonNull
    public ListenableWorker.Result doWork() {
        int i = 0;
        try {
            DclassApp.isBlockFunLoaded = false;
            File externalFilesDir = getApplicationContext().getExternalFilesDir("app_adblocklist");
            while (true) {
                String[] strArr = adblkList;
                if (i >= strArr.length) {
                    break;
                }
                String str = strArr[i];
                downloadUrl(str, new File(externalFilesDir, "hosts-" + i + ".txt"));
                i++;
            }
            DclassApp.isBlockFunLoaded = true;
        } catch (Exception unused) {
            ListenableWorker.Result.retry();
        }
        return ListenableWorker.Result.success();
    }
}
