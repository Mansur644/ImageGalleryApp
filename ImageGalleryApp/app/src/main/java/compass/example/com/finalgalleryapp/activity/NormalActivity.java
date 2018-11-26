package compass.example.com.finalgalleryapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import compass.example.com.finalgalleryapp.R;
import compass.example.com.finalgalleryapp.adapter.GalleryAdapter;
import compass.example.com.finalgalleryapp.model.GalleryItems;
import compass.example.com.finalgalleryapp.utils.AppUtils;
import compass.example.com.finalgalleryapp.utils.Constants;
import compass.example.com.finalgalleryapp.webservice.ApiInterface;
import compass.example.com.finalgalleryapp.webservice.RetrofitWebservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NormalActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    GalleryAdapter adapter;
    GalleryItems galleryItems;
    Toolbar toolbar;
    HashMap<String, List<GalleryItems.GalleryDetail>> imageResultset;
    List<GalleryItems.GalleryDetail> stampList, sslcList, normalList, pancardList, passportList;
    boolean isMenuEnable = true;
    MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(getString(R.string.image_gallery));
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.rc_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(NormalActivity.this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        if (AppUtils.isNetworkAvailable(NormalActivity.this)) {
            showNormalView();
        } else {
            AppUtils.alertDialogShow(NormalActivity.this, "Please, Check Internet Connection");
        }
    }


    private void showNormalView() {

        AppUtils.showProgressDialog(NormalActivity.this, "Wait for Loading..");
        ApiInterface apiInterface = RetrofitWebservice.getInstance().create(ApiInterface.class);
        Call<GalleryItems> call = apiInterface.getGalleryItems();
        call.enqueue(new Callback<GalleryItems>() {
            @Override
            public void onResponse(Call<GalleryItems> call, Response<GalleryItems> response) {
                if (response.isSuccessful()) {
                    galleryItems = response.body();
                    adapter = new GalleryAdapter(NormalActivity.this, galleryItems.getGalleryItems());
                    mRecyclerView.setAdapter(adapter);
                    // adapter.notifyDataSetChanged();
                    AppUtils.dismissDialog();
                    filterImages(galleryItems);
                }
                Log.d("Response", String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<GalleryItems> call, Throwable t) {
                Log.d("Response", t.getMessage());
                AppUtils.dismissDialog();
                AppUtils.alertDialogShow(NormalActivity.this, "Internet connection is slow");
            }
        });
    }

    private void filterImages(final GalleryItems galleryItems) {

        imageResultset = new HashMap<>();
        stampList = new ArrayList<>();
        sslcList = new ArrayList<>();
        normalList = new ArrayList<>();
        pancardList = new ArrayList<>();
        passportList = new ArrayList<>();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (GalleryItems.GalleryDetail imageDetails : galleryItems.getGalleryItems()) {
                        if (Constants.STAMP.equals(AppUtils.getImageType(imageDetails.getImageUrl()))) {
                            stampList.add(imageDetails);
                        } else if (Constants.SSLC.equals(AppUtils.getImageType(imageDetails.getImageUrl()))) {
                            sslcList.add(imageDetails);
                        } else if (Constants.NORMAL.equals(AppUtils.getImageType(imageDetails.getImageUrl()))) {
                            normalList.add(imageDetails);
                        } else if (Constants.PANCARD.equals(AppUtils.getImageType(imageDetails.getImageUrl()))) {
                            pancardList.add(imageDetails);
                        } else if (Constants.PASSPORT.equals(AppUtils.getImageType(imageDetails.getImageUrl()))) {
                            passportList.add(imageDetails);
                        }
                    }
                    imageResultset.put("stamp", stampList);
                    imageResultset.put("sslc", sslcList);
                    imageResultset.put("normal", normalList);
                    imageResultset.put("pancard", pancardList);
                    imageResultset.put("passport", passportList);

                    if (!isMenuEnable) {
                        item.setVisible(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.option_menu, menu);

        item = menu.findItem(R.id.tabsView);
        if (isMenuEnable) {
            item.setVisible(false);
            isMenuEnable = false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.normalView:
                startActivity(new Intent(NormalActivity.this, NormalActivity.class));
                break;

            case R.id.tabsView:
                Intent intent = new Intent(NormalActivity.this, TabsActivity.class);
                intent.putExtra("OBJ", imageResultset);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
