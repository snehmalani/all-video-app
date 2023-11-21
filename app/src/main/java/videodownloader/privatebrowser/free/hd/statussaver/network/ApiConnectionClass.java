package videodownloader.privatebrowser.free.hd.statussaver.network;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiConnectionClass {
    private static final String PATH = "https://allsocialsaver.xyz/";
    private static OkHttpClient.Builder builder;
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (builder == null) {
            OkHttpClient.Builder builder2 = new OkHttpClient.Builder();
            builder = builder2;
            TimeUnit timeUnit = TimeUnit.SECONDS;
            builder2.connectTimeout(33L, timeUnit);
            builder.readTimeout(33L, timeUnit);
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(PATH).client(builder.build()).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
