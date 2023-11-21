package videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi;


import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IPAdsApiInterface {

    @POST("json?")
    Call<Object> getip(@Query("key") String str);

}
