package compass.example.com.finalgalleryapp.webservice;

import compass.example.com.finalgalleryapp.model.GalleryItems;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("bins/141s4e")
    Call<GalleryItems>  getGalleryItems();
}
