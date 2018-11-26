package compass.example.com.finalgalleryapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import compass.example.com.finalgalleryapp.R;
import compass.example.com.finalgalleryapp.adapter.GalleryAdapter;
import compass.example.com.finalgalleryapp.model.GalleryItems;
import compass.example.com.finalgalleryapp.webservice.ApiInterface;
import compass.example.com.finalgalleryapp.webservice.RetrofitWebservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NormalFragment extends Fragment {

    RecyclerView normalRecyclerView;
    GalleryAdapter adapter;
    private static ArrayList<GalleryItems.GalleryDetail> mGalleryItems;

    public static Fragment getInstance(ArrayList<GalleryItems.GalleryDetail> galleryItems) {
        mGalleryItems = galleryItems;
        NormalFragment fragment = new NormalFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.normal_fragment, container, false);

        normalRecyclerView = (RecyclerView) view.findViewById(R.id.normal_recyclerview);
        normalRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        adapter = new GalleryAdapter(getActivity(), mGalleryItems);
        normalRecyclerView.setAdapter(adapter);

        return view;
    }
}
