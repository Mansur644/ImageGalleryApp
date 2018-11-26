package compass.example.com.finalgalleryapp.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;

import java.net.URL;

import compass.example.com.finalgalleryapp.activity.NormalActivity;

public class AppUtils {

    public static ProgressDialog progressDoalog;

    public static void alertDialogShow(Context context, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getImageType(String imageUrl) {
        String result = "";
        try {
            URL url = new URL(imageUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            bmp.getWidth();
            int imgHeight = bmp.getHeight();
            if (imgHeight <= 100) {
                return Constants.STAMP;
            } else if (imgHeight <= 200) {
                return Constants.SSLC;
            } else if (imgHeight <= 225) {
                return Constants.NORMAL;
            }
            else if (imgHeight <=250) {
                return Constants.PANCARD;
            }
            else if (imgHeight >= 800) {
                return Constants.PASSPORT;
            }
        } catch (Exception e){
            return result;
        }
        return result;
    }

    public static void showProgressDialog(Context context, String msg) {
        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMax(100);
        progressDoalog.setMessage(msg);
        progressDoalog.show();
    }

    public static void dismissDialog() {
        if(progressDoalog.isShowing()) {
            progressDoalog.dismiss();
        }
    }
}
