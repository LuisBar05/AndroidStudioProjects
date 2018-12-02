/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.example.luisbb.botanasdivalapp.views.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisbb.botanasdivalapp.R;
import com.example.luisbb.botanasdivalapp.models.DataCenter;
import com.example.luisbb.botanasdivalapp.models.Money;
import com.example.luisbb.botanasdivalapp.utils.ColorGenerator;
import com.example.luisbb.botanasdivalapp.views.customview.TextDrawable;
import com.example.luisbb.botanasdivalapp.views.customview.TextDrawable.IBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

/**
 * @author Hitesh Sahu (hiteshsahu.com)
 */
public class SimilarProductsPagerAdapter extends PagerAdapter {

    /**
     * The m context.
     */
    Context mContext;

    /**
     * The m layout inflater.
     */
    LayoutInflater mLayoutInflater;

    private String productCategory;

    private ImageView imageView;

    private IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    /**
     * Instantiates a new home slides pager adapter.
     *
     * @param context the context
     */
    public SimilarProductsPagerAdapter(Context context, String productCategory) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.productCategory = productCategory;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {

        if (null != DataCenter.getCenterRepository().getMapOfProductsInCategory()
                && null != DataCenter.getCenterRepository().getMapOfProductsInCategory()
                .get(productCategory)) {
            return DataCenter.getCenterRepository().getMapOfProductsInCategory()
                    .get(productCategory).size();
        }

        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
     * java.lang.Object)
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup
     * , int)
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_category_list, container,
                false);

        imageView = itemView.findViewById(R.id.imageView);

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
                .endConfig().roundRect(10);

        drawable = mDrawableBuilder.build(
                String.valueOf(DataCenter.getCenterRepository()
                        .getMapOfProductsInCategory().get(productCategory).get(position)
                        .getName().charAt(0)),

                mColorGenerator.getColor(DataCenter.getCenterRepository()
                        .getMapOfProductsInCategory().get(productCategory).get(position)
                        .getName()));

        final String ImageUrl = DataCenter.getCenterRepository().getMapOfProductsInCategory()
                .get(productCategory).get(position).getImageUrl();

        Picasso.get().load(ImageUrl).placeholder(drawable)
                .error(drawable).fit().centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        // Try again online if cache failed

                        Picasso.get().load(ImageUrl)
                                .placeholder(drawable).error(drawable).fit()
                                .centerCrop().into(imageView);

                    }
                });

        ((TextView) itemView.findViewById(R.id.item_name))
                .setText(DataCenter.getCenterRepository().getMapOfProductsInCategory()
                        .get(productCategory).get(position).getName());

        ((TextView) itemView.findViewById(R.id.item_short_desc))
                .setText(DataCenter.getCenterRepository().getMapOfProductsInCategory()
                        .get(productCategory).get(position).getDescription());

        ((TextView) itemView.findViewById(R.id.category_discount))
                .setText(Money.getMoneyInstance(
                        BigDecimal.valueOf(Long.valueOf(DataCenter
                                .getCenterRepository().getMapOfProductsInCategory()
                                .get(productCategory).get(position)
                                .getPrice()))).toString());

//        LabelView label = new LabelView(mContext);
//        label.setText(DataCenter.getCenterRepository().getMapOfProductsInCategory()
//                .get(productCategory).get(position).getDiscount());
//        label.setBackgroundColor(0xffE91E63);
//        label.setTargetView(itemView.findViewById(R.id.imageView), 10,
//                LabelView.Gravity.RIGHT_TOP);

        container.addView(itemView);

        return itemView;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#destroyItem(android.view.ViewGroup,
     * int, java.lang.Object)
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.View,
     * int, java.lang.Object)
     */
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#finishUpdate(android.view.View)
     */
    @Override
    public void finishUpdate(View arg0) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#instantiateItem(android.view.View,
     * int)
     */
    @Override
    public Object instantiateItem(View arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#restoreState(android.os.Parcelable,
     * java.lang.ClassLoader)
     */
    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#saveState()
     */
    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#getPageWidth(int)
     */
    @Override
    public float getPageWidth(int position) {
        return (0.5f);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#startUpdate(android.view.View)
     */
    @Override
    public void startUpdate(View arg0) {
        // TODO Auto-generated method stub

    }
}