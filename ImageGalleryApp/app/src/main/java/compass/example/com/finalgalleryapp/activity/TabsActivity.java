package compass.example.com.finalgalleryapp.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import compass.example.com.finalgalleryapp.R;
import compass.example.com.finalgalleryapp.fragments.NormalFragment;
import compass.example.com.finalgalleryapp.fragments.PanCardFragment;
import compass.example.com.finalgalleryapp.fragments.PassportFragment;
import compass.example.com.finalgalleryapp.fragments.SSLCFragment;
import compass.example.com.finalgalleryapp.fragments.StampFragment;
import compass.example.com.finalgalleryapp.model.GalleryItems;
import compass.example.com.finalgalleryapp.utils.Constants;

public class TabsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(getString(R.string.image_tabs));
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabsActivity.this, NormalActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        HashMap<String, ArrayList<GalleryItems.GalleryDetail>> items = (HashMap<String, ArrayList<GalleryItems.GalleryDetail>>) getIntent().getSerializableExtra("OBJ");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(StampFragment.getInstance(items.get(Constants.STAMP)), "Stamp");
        adapter.addFragment(SSLCFragment.getInstance(items.get(Constants.SSLC)), "SSLC");
        adapter.addFragment(NormalFragment.getInstance(items.get(Constants.NORMAL)), "Normal");
        adapter.addFragment(PanCardFragment.getInstance(items.get(Constants.PANCARD)), "Pan Card");
        adapter.addFragment(PassportFragment.getInstance(items.get(Constants.PASSPORT)), "Passport");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
