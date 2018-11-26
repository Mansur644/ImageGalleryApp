package compass.example.com.finalgalleryapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import compass.example.com.finalgalleryapp.R;
import compass.example.com.finalgalleryapp.activity.ImageDetailActivity;
import compass.example.com.finalgalleryapp.fragments.StampFragment;
import compass.example.com.finalgalleryapp.model.GalleryItems;
import compass.example.com.finalgalleryapp.utils.AppUtils;
import compass.example.com.finalgalleryapp.utils.Constants;
import okhttp3.internal.Util;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    ArrayList<GalleryItems.GalleryDetail> galleryItems;
    private Context mContext;

    int size = 0;
    int width = 0;
    int height = 0;

    public GalleryAdapter(Context context, ArrayList<GalleryItems.GalleryDetail> items) {
        this.mContext = context;
        this.galleryItems = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_gallery_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final GalleryItems.GalleryDetail data = galleryItems.get(position);

        Picasso.with(mContext).load(data.getImageUrl()).into(holder.imgImage);
        holder.txtTitle.setText(data.getImageTitle());
        holder.linearLayout.setTag(data);

       /* Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getBitmapFromURL(data.getImageUrl());
                    holder.txtSize.setText("Size:  " + String.valueOf(size / 1024) + " kb");
                    holder.txtDimention.setText("Dimention:  " + String.valueOf(width) + " x " + String.valueOf(height));
                } catch (Exception e) {

                }
            }
        });
        thread.start();*/

        Picasso.with(mContext).load(data.getImageUrl()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.txtSize.setText("Size:  " + String.valueOf(bitmap.getByteCount() / 1024) + " kb");
                holder.txtDimention.setText("Dimention:  " + String.valueOf(bitmap.getWidth()) + " x " + String.valueOf( bitmap.getHeight()));

            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageDetailActivity.class);
                intent.putExtra("GALLERY_DETAILS", (GalleryItems.GalleryDetail) v.getTag());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgImage;
        public TextView txtTitle;
        public TextView txtSize;
        public TextView txtDimention;
        public LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.row_image);
            txtTitle = itemView.findViewById(R.id.row_title);
            txtSize = itemView.findViewById(R.id.row_size);
            txtDimention = itemView.findViewById(R.id.row_dimention);
            linearLayout = itemView.findViewById(R.id.row_linearlayout);
        }
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            size = myBitmap.getByteCount();
            width = myBitmap.getWidth();
            height = myBitmap.getHeight();
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

}
