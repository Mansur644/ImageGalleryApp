package compass.example.com.finalgalleryapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import compass.example.com.finalgalleryapp.R;
import compass.example.com.finalgalleryapp.model.GalleryItems;

public class ImageDetailActivity extends AppCompatActivity {

    ImageView mImage;
    TextView mTitle;
    GalleryItems.GalleryDetail items;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        mImage = (ImageView) findViewById(R.id.detail_activity_image);
        mTitle = (TextView) findViewById(R.id.detail_activity_title);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(getString(R.string.image_detaisl));
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageDetailActivity.this, NormalActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        if (getIntent().getExtras() != null) {
            items = (GalleryItems.GalleryDetail) getIntent().getSerializableExtra("GALLERY_DETAILS");
            mTitle.setText(items.getImageTitle());
            Picasso.with(ImageDetailActivity.this).load(items.getImageUrl()).into(mImage);
        }
    }

    public void imgRotate(View view) {
        mImage.setRotation(mImage.getRotation() + 90);
    }
}
