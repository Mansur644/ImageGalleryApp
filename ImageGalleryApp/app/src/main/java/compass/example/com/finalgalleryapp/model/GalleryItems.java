package compass.example.com.finalgalleryapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GalleryItems implements Serializable {

    @SerializedName("galleryItems")
    @Expose
    private ArrayList<GalleryDetail> galleryItems = null;

    public ArrayList<GalleryDetail> getGalleryItems() {
        return galleryItems;
    }

    public void setGalleryItems(ArrayList<GalleryDetail> galleryItems) {
        this.galleryItems = galleryItems;
    }

    public static class GalleryDetail implements Serializable {

        @SerializedName("imageTitle")
        @Expose
        private String imageTitle;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;

        public String getImageTitle() {
            return imageTitle;
        }

        public void setImageTitle(String imageTitle) {
            this.imageTitle = imageTitle;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

    }
}
