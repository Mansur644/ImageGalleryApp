package compass.example.com.finalgalleryapp.webservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWebservice {

    private static final String baseUrl = "https://api.myjson.com/";


    private static Retrofit retrofit = null;

    public static Retrofit getInstance() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
