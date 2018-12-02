/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.example.luisbb.botanasdivalapp.views.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.models.Categories;
import com.hitesh_sahu.retailapp.models.DataCenter;
import com.hitesh_sahu.retailapp.models.Products;
import com.hitesh_sahu.retailapp.utils.Utils;
import com.hitesh_sahu.retailapp.utils.Utils.AnimationType;
import com.hitesh_sahu.retailapp.views.activities.ECartHomeActivity;
import com.hitesh_sahu.retailapp.views.adapter.ProductsInCategoryPagerAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ProductOverviewFragment extends Fragment {

    // SimpleRecyclerAdapter adapter;
    private KenBurnsView header;
    private Bitmap bitmap;
    private Toolbar mToolbar;
    private ViewPager viewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TabLayout tabLayout;
    private DatabaseReference mDatabaseRefProd;
    ArrayList<Categories> mListCat;
    ArrayList<Products> mListProd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_category_details,
                container, false);

        getActivity().setTitle("Productos");

        mDatabaseRefProd=FirebaseDatabase.getInstance().getReference().child("Products");
        mListCat=DataCenter.getCenterRepository().getListOfCategory();
        mListProd=new ArrayList<>();

        mDatabaseRefProd.addValueEventListener(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    Products mData= child.getValue(Products.class);
                    mData.setProductKey(child.getKey());
                    mListProd.add(mData);
                }

                ConcurrentHashMap<String, ArrayList<Products>> productMap = new ConcurrentHashMap<>();
                ArrayList<Products> filterProducts=new ArrayList<>();
                List<String> setProducts=new ArrayList<>();

                for(Products mProd: mListProd){
                    setProducts.add(mProd.getName());
                }

                HashSet<String> mHashSet = new HashSet<>(setProducts);
                setProducts.clear();
                setProducts.addAll(mHashSet);

                for(String mProd: setProducts){
                    for(Products mProd2: mListProd){
                        if(mProd.equals(mProd2.getName())) {
                            filterProducts.add(mProd2);
                        }
                    }
                    productMap.put(mProd, filterProducts);
                    filterProducts=new ArrayList<>();
                }
                DataCenter.getCenterRepository().setMapOfProductsInCategory(productMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        // TODO We Can use Async task But pallete creation is problemitic job
        // will
        // get back to it later

        // new ProductLoaderTask(null, getActivity(), viewPager, tabLayout);

        // Volley can be used here very efficiently but Fake JSON creation is
        // time consuming Leain it now

        viewPager = (ViewPager) view.findViewById(R.id.htab_viewpager);

        collapsingToolbarLayout = (CollapsingToolbarLayout) view
                .findViewById(R.id.htab_collapse_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        header = (KenBurnsView) view.findViewById(R.id.htab_header);
        header.setImageResource(R.drawable.header);

        tabLayout = (TabLayout) view.findViewById(R.id.htab_tabs);

        mToolbar = (Toolbar) view.findViewById(R.id.htab_toolbar);
        if (mToolbar != null) {
            ((ECartHomeActivity) getActivity()).setSupportActionBar(mToolbar);
        }

        if (mToolbar != null) {
            ((ECartHomeActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationIcon(R.drawable.ic_drawer);

        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ECartHomeActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        setUpUi();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    Utils.switchContent(R.id.frag_container,
                            Utils.HOME_FRAGMENT,
                            ((ECartHomeActivity) (getContext())),
                            AnimationType.SLIDE_RIGHT);

                }
                return true;
            }
        });

        return view;
    }

    private void setUpUi() {

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        bitmap = BitmapFactory
                .decodeResource(getResources(), R.drawable.header);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {

                int vibrantColor = palette.getVibrantColor(R.color.primary_500);
                int vibrantDarkColor = palette
                        .getDarkVibrantColor(R.color.primary_700);
                collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                collapsingToolbarLayout
                        .setStatusBarScrimColor(vibrantDarkColor);
            }
        });

        tabLayout
                .setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {

                        viewPager.setCurrentItem(tab.getPosition());

                        switch (tab.getPosition()) {
                            case 0:

                                header.setImageResource(R.drawable.header);

                                bitmap = BitmapFactory.decodeResource(
                                        getResources(), R.drawable.header);

                                Palette.from(bitmap).generate(
                                        new Palette.PaletteAsyncListener() {
                                            @SuppressWarnings("ResourceType")
                                            @Override
                                            public void onGenerated(Palette palette) {

                                                int vibrantColor = palette
                                                        .getVibrantColor(R.color.primary_500);
                                                int vibrantDarkColor = palette
                                                        .getDarkVibrantColor(R.color.primary_700);
                                                collapsingToolbarLayout
                                                        .setContentScrimColor(vibrantColor);
                                                collapsingToolbarLayout
                                                        .setStatusBarScrimColor(vibrantDarkColor);
                                            }
                                        });
                                break;
                            case 1:

                                header.setImageResource(R.drawable.header);

                                bitmap = BitmapFactory.decodeResource(
                                        getResources(), R.drawable.header);

                                Palette.from(bitmap).generate(
                                        new Palette.PaletteAsyncListener() {
                                            @SuppressWarnings("ResourceType")
                                            @Override
                                            public void onGenerated(Palette palette) {

                                                int vibrantColor = palette
                                                        .getVibrantColor(R.color.primary_500);
                                                int vibrantDarkColor = palette
                                                        .getDarkVibrantColor(R.color.primary_700);
                                                collapsingToolbarLayout
                                                        .setContentScrimColor(vibrantColor);
                                                collapsingToolbarLayout
                                                        .setStatusBarScrimColor(vibrantDarkColor);
                                            }
                                        });

                                break;
                            case 2:

                                header.setImageResource(R.drawable.header);

                                Bitmap bitmap = BitmapFactory.decodeResource(
                                        getResources(), R.drawable.header);

                                Palette.from(bitmap).generate(
                                        new Palette.PaletteAsyncListener() {
                                            @SuppressWarnings("ResourceType")
                                            @Override
                                            public void onGenerated(Palette palette) {

                                                int vibrantColor = palette
                                                        .getVibrantColor(R.color.primary_500);
                                                int vibrantDarkColor = palette
                                                        .getDarkVibrantColor(R.color.primary_700);
                                                collapsingToolbarLayout
                                                        .setContentScrimColor(vibrantColor);
                                                collapsingToolbarLayout
                                                        .setStatusBarScrimColor(vibrantDarkColor);
                                            }
                                        });

                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

    }

    private void setupViewPager(ViewPager viewPager) {
        ProductsInCategoryPagerAdapter adapter = new ProductsInCategoryPagerAdapter(
                getActivity().getSupportFragmentManager());
        Set<String> keys = DataCenter.getCenterRepository().getMapOfProductsInCategory()
                .keySet();

        for (String string : keys) {
            adapter.addFrag(new ProductListFragment(string), string);
        }

        viewPager.setAdapter(adapter);
//		viewPager.setPageTransformer(true,
//				Utils.currentPageTransformer(getActivity()));
    }

}