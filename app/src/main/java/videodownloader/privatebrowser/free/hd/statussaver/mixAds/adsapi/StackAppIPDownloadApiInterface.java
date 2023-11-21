package videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackAppIPDownloadApiInterface {

    @GET("AppAnalysis/api/demo_api.php")
    Call<Object> countDownload(@Query("package_name") String package_name, @Query("downloads") int downloads);

    @GET("AppAnalysis/api/demo_api.php")
    Call<Object> countImpression(@Query("package_name") String package_name, @Query("impression") int impression);

}
