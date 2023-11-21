package videodownloader.privatebrowser.free.hd.statussaver.network;

import videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Model.Caption;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_splash;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_video_link;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface HttpApis {

    @FormUrlEncoded
    @POST("allsocialsave/listvideolinks")
    Call<store_model_video_link> ListVideoLinks(@Header("sokey") String sokey, @Field("stamp") long stamp, @Field("link_site") String link_site, @Field("link_enc") String link_enc, @Field("link_type") String link_type, @Field("dkey") int dkey);

    @FormUrlEncoded
    @POST("allsocialsave/setapp")
    Call<store_model_splash> SetUp(@Header("sokey") String sokey, @Field("stamp") long stamp, @Field("dkey") int dkey, @Field("inssrc") String inssrc);

    @GET
    Call<String> findLocalVideo(@Url String url);

    @GET
    Call<String> findLocalVideo(@Header("Cookie") String cookies, @Url String url);

    @GET
    Call<String> initUrlfetch(@Url String url);

    @GET
    Call<String> initUrlfetch(@Url String url, @HeaderMap Map<String, String> headers);

    @GET("https://allsocialsaver.xyz/tsd/{id}.json")
    Call<Caption> listRepos(@Path("id") int id);
}
