package videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AdsApiInterface {

    @POST("JUNE/AllDownloder/videodownloader.privatebrowser.free.hd.statussaver.php")
    Call<Object> getid(@Query("ip") String str);

}
