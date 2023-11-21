package videodownloader.privatebrowser.free.hd.statussaver.mixAds.adsapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StackAppIPDownloadApiClient {

    static String BASE_URL1 = "https://appanalyticscv.com/";
    static Gson gson = new GsonBuilder().setLenient().create();
    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL1)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.readTimeout(5, TimeUnit.MINUTES).connectTimeout(5, TimeUnit.MINUTES).build())
                    .build();
        }
        return retrofit;
    }


}
