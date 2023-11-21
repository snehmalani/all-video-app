package videodownloader.privatebrowser.free.hd.statussaver.network;

public interface ListnerForUrlChange {
    void notifyUrlChanged(String url, int tabId, boolean isOnlyUpdateToolbar, String rawUrl);
}
