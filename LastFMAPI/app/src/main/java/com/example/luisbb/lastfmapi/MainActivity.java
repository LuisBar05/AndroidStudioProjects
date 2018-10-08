package com.example.luisbb.lastfmapi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.luisbb.lastfmapi.Fragments.TopArtistsFragment;
import com.example.luisbb.lastfmapi.Fragments.TopTracksFragment;

public class MainActivity extends AppCompatActivity{
    FragmentPagerAdapter myAdapterPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager=findViewById(R.id.viewPager);
        myAdapterPager=new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myAdapterPager);


    }

    public static class PagerAdapter extends FragmentPagerAdapter{
        private static int NUM_ITEMS=2;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    return new TopArtistsFragment();
                case 1:
                    return new TopTracksFragment();
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}
