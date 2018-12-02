
package com.hitesh_sahu.retailapp.views.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.models.DataCenter;
import com.hitesh_sahu.retailapp.models.Money;
import com.hitesh_sahu.retailapp.models.Products;
import com.hitesh_sahu.retailapp.utils.ColorGenerator;
import com.hitesh_sahu.retailapp.utils.Utils;
import com.hitesh_sahu.retailapp.utils.Utils.AnimationType;
import com.hitesh_sahu.retailapp.views.activities.ECartHomeActivity;
import com.hitesh_sahu.retailapp.views.adapter.SimilarProductsPagerAdapter;
import com.hitesh_sahu.retailapp.views.customview.ClickableViewPager;
import com.hitesh_sahu.retailapp.views.customview.ClickableViewPager.OnItemClickListener;
import com.hitesh_sahu.retailapp.views.customview.TextDrawable;
import com.hitesh_sahu.retailapp.views.customview.TextDrawable.IBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

// TODO: Auto-generated Javadoc

/**
 * Fragment that appears in the "content_frame", shows a animal.
 */
public class ProductDetailsFragment extends Fragment {

    private int productListNumber;
    private ImageView itemImage;
    private TextView itemSellPrice;
    private TextView itemName;
    private TextView quantity;
    private TextView itemdescription;
    private IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private String subcategoryKey;
    private boolean isFromCart;
    private ClickableViewPager similarProductsPager;
    private ClickableViewPager topSellingPager;
    private Toolbar mToolbar;

    /**
     * Instantiates a new product details fragment.
     */

    public ProductDetailsFragment(){

    }

    @SuppressLint("ValidFragment")
    public ProductDetailsFragment(String subcategoryKey, int productNumber,
                                  boolean isFromCart) {

        this.subcategoryKey = subcategoryKey;
        this.productListNumber = productNumber;
        this.isFromCart = isFromCart;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_product_detail,
                container, false);

        mToolbar = rootView.findViewById(R.id.htab_toolbar);
        if (mToolbar != null) {
            ((ECartHomeActivity) getActivity()).setSupportActionBar(mToolbar);
        }

        if (mToolbar != null) {
            ((ECartHomeActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationIcon(R.drawable.ic_drawer);

        }

        mToolbar.setTitleTextColor(Color.WHITE);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ECartHomeActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        ((ECartHomeActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        similarProductsPager = rootView
                .findViewById(R.id.similar_products_pager);

        topSellingPager = rootView
                .findViewById(R.id.top_selleing_pager);

        itemSellPrice = rootView
                .findViewById(R.id.category_discount);

        quantity = (TextView) rootView.findViewById(R.id.iteam_amount);

        itemName = (TextView) rootView.findViewById(R.id.product_name);

        itemdescription = rootView
                .findViewById(R.id.product_description);

        itemImage = rootView.findViewById(R.id.product_image);

        fillProductData();

        rootView.findViewById(R.id.add_item).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (isFromCart) {

                            //Update Quantity on shopping List
                            DataCenter
                                    .getCenterRepository()
                                    .getListOfProductsInShoppingList()
                                    .get(productListNumber)
                                    .setQuantity(DataCenter
                                                    .getCenterRepository()
                                                    .getListOfProductsInShoppingList()
                                                    .get(productListNumber)
                                                            .getQuantity() + 1);


                            //Update Ui
                            quantity.setText(DataCenter
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(productListNumber).getQuantity());

                            Utils.vibrate(getActivity());

                            //Update checkout amount on screen
                            ((ECartHomeActivity) getActivity()).updateCheckOutAmount(
                                    BigDecimal.valueOf(Long
                                            .valueOf(DataCenter
                                                    .getCenterRepository()
                                                    .getListOfProductsInShoppingList()
                                                    .get(productListNumber)
                                                    .getPrice())), true);

                        } else {

                            // current object
                            Products tempObj = DataCenter
                                    .getCenterRepository().getMapOfProductsInCategory()
                                    .get(subcategoryKey).get(productListNumber);

                            // if current object is lready in shopping list
                            if (DataCenter.getCenterRepository()
                                    .getListOfProductsInShoppingList().contains(tempObj)) {

                                // get position of current item in shopping list
                                int indexOfTempInShopingList = DataCenter
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .indexOf(tempObj);

                                // increase quantity of current item in shopping
                                // list
                                if (Integer.parseInt(tempObj.getQuantity())== 0) {

                                    ((ECartHomeActivity) getContext())
                                            .updateItemCount(true);

                                }

                                // update quantity in shopping list
                                DataCenter
                                        .getCenterRepository()
                                        .getListOfProductsInShoppingList()
                                        .get(indexOfTempInShopingList)
                                        .setQuantity(tempObj.getQuantity() + 1);

                                // update checkout amount
                                ((ECartHomeActivity) getContext()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Long
                                                .valueOf(DataCenter
                                                        .getCenterRepository()
                                                        .getMapOfProductsInCategory()
                                                        .get(subcategoryKey)
                                                        .get(productListNumber)
                                                        .getPrice())), true);

                                // update current item quantity
                                quantity.setText(tempObj.getQuantity());

                            } else {

                                ((ECartHomeActivity) getContext())
                                        .updateItemCount(true);

                                tempObj.setQuantity(String.valueOf(1));

                                quantity.setText(tempObj.getQuantity());

                                DataCenter.getCenterRepository()
                                        .getListOfProductsInShoppingList().add(tempObj);

                                ((ECartHomeActivity) getContext()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Long
                                                .valueOf(DataCenter
                                                        .getCenterRepository()
                                                        .getMapOfProductsInCategory()
                                                        .get(subcategoryKey)
                                                        .get(productListNumber)
                                                        .getPrice())), true);

                            }

                            Utils.vibrate(getContext());

                        }
                    }

                });

        rootView.findViewById(R.id.remove_item).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (isFromCart)

                        {

                            if (Integer.valueOf(DataCenter
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(productListNumber).getQuantity()) > 0) {

                                DataCenter
                                        .getCenterRepository()
                                        .getListOfProductsInShoppingList()
                                        .get(productListNumber)
                                        .setQuantity(String.valueOf(Integer.valueOf(DataCenter
                                                        .getCenterRepository()
                                                        .getListOfProductsInShoppingList()
                                                        .get(productListNumber)
                                                        .getQuantity()) - 1));

                                quantity.setText(DataCenter
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .get(productListNumber).getQuantity());

                                ((ECartHomeActivity) getActivity()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Long
                                                .valueOf(DataCenter
                                                        .getCenterRepository()
                                                        .getListOfProductsInShoppingList()
                                                        .get(productListNumber)
                                                        .getPrice())), false);

                                Utils.vibrate(getActivity());
                            } else if (Integer.valueOf(DataCenter
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(productListNumber).getQuantity()) == 1) {
                                ((ECartHomeActivity) getActivity())
                                        .updateItemCount(false);

                                ((ECartHomeActivity) getActivity()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Long
                                                .valueOf(DataCenter
                                                        .getCenterRepository()
                                                        .getListOfProductsInShoppingList()
                                                        .get(productListNumber)
                                                        .getPrice())), false);

                                DataCenter.getCenterRepository()
                                        .getListOfProductsInShoppingList()
                                        .remove(productListNumber);

                                if (((ECartHomeActivity) getActivity())
                                        .getItemCount() == 0) {

                                    MyCartFragment.updateMyCartFragment(false);

                                }

                                Utils.vibrate(getActivity());

                            }

                        } else {

                            Products tempObj = DataCenter
                                    .getCenterRepository().getMapOfProductsInCategory()
                                    .get(subcategoryKey).get(productListNumber);

                            if (DataCenter.getCenterRepository()
                                    .getListOfProductsInShoppingList().contains(tempObj)) {

                                int indexOfTempInShopingList = DataCenter
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .indexOf(tempObj);

                                if (Integer.valueOf(tempObj.getQuantity()) != 0) {

                                    DataCenter
                                            .getCenterRepository()
                                            .getListOfProductsInShoppingList()
                                            .get(indexOfTempInShopingList)
                                            .setQuantity(String.valueOf(Integer.valueOf(tempObj.getQuantity()) - 1));

                                    ((ECartHomeActivity) getContext()).updateCheckOutAmount(
                                            BigDecimal.valueOf(Long
                                                    .valueOf(DataCenter
                                                            .getCenterRepository()
                                                            .getMapOfProductsInCategory()
                                                            .get(subcategoryKey)
                                                            .get(productListNumber)
                                                            .getPrice())),
                                            false);

                                    quantity.setText(DataCenter
                                            .getCenterRepository()
                                            .getListOfProductsInShoppingList()
                                            .get(indexOfTempInShopingList)
                                            .getQuantity());

                                    Utils.vibrate(getContext());

                                    if (Integer.valueOf(DataCenter
                                            .getCenterRepository()
                                            .getListOfProductsInShoppingList()
                                            .get(indexOfTempInShopingList)
                                            .getQuantity()) == 0) {

                                        DataCenter
                                                .getCenterRepository()
                                                .getListOfProductsInShoppingList()
                                                .remove(indexOfTempInShopingList);

                                        ((ECartHomeActivity) getContext())
                                                .updateItemCount(false);

                                    }

                                }

                            }

                        }

                    }

                });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (isFromCart) {

                        Utils.switchContent(R.id.frag_container,
                                Utils.SHOPPING_LIST_TAG,
                                getActivity(),
                                AnimationType.SLIDE_UP);
                    } else {

                        Utils.switchContent(R.id.frag_container,
                                Utils.PRODUCT_OVERVIEW_FRAGMENT_TAG,
                                getActivity(),
                                AnimationType.SLIDE_RIGHT);
                    }

                }
                return true;
            }
        });

        if (isFromCart) {

            similarProductsPager.setVisibility(View.GONE);

            topSellingPager.setVisibility(View.GONE);

        } else {
            showRecomondation();
        }

        return rootView;
    }

    private void showRecomondation() {

        SimilarProductsPagerAdapter mCustomPagerAdapter = new SimilarProductsPagerAdapter(
                getActivity(), subcategoryKey);

        similarProductsPager.setAdapter(mCustomPagerAdapter);

        similarProductsPager.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                productListNumber = position;

                fillProductData();

                Utils.vibrate(getActivity());

            }
        });

        topSellingPager.setAdapter(mCustomPagerAdapter);

        topSellingPager.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                productListNumber = position;

                fillProductData();

                Utils.vibrate(getActivity());

            }
        });
    }

    public void fillProductData() {

        if (!isFromCart) {


            //Fetch and display item from Gloabl Data Model

            itemName.setText(DataCenter.getCenterRepository()
                    .getMapOfProductsInCategory().get(subcategoryKey).get(productListNumber)
                    .getName());

            quantity.setText(DataCenter.getCenterRepository()
                    .getMapOfProductsInCategory().get(subcategoryKey).get(productListNumber)
                    .getQuantity());

            itemdescription.setText(DataCenter.getCenterRepository()
                    .getMapOfProductsInCategory().get(subcategoryKey).get(productListNumber)
                    .getDescription());

            String sellCostString = Money.getMoneyInstance(
                    BigDecimal.valueOf(Long.valueOf(DataCenter
                            .getCenterRepository().getMapOfProductsInCategory()
                            .get(subcategoryKey).get(productListNumber)
                            .getPrice()))).toString()
                    + "  ";

//            String buyMRP = Money.getMoneyInstance(
//                    BigDecimal.valueOf(Long.valueOf(DataCenter
//                            .getCenterRepository().getMapOfProductsInCategory()
//                            .get(subcategoryKey).get(productListNumber)
//                            .getMRP()))).toString();
//
//            String costString = sellCostString + buyMRP;
//
//            itemSellPrice.setText(costString, BufferType.SPANNABLE);
//
//            Spannable spannable = (Spannable) itemSellPrice.getText();
//
//            spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
//                    costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mDrawableBuilder = TextDrawable.builder().beginConfig()
                    .withBorder(4).endConfig().roundRect(10);

            drawable = mDrawableBuilder.build(
                    String.valueOf(DataCenter.getCenterRepository()
                            .getMapOfProductsInCategory().get(subcategoryKey)
                            .get(productListNumber).getName().charAt(0)),
                    mColorGenerator.getColor(DataCenter
                            .getCenterRepository().getMapOfProductsInCategory()
                            .get(subcategoryKey).get(productListNumber)
                            .getName()));

            Picasso.get()
                    .load(DataCenter.getCenterRepository().getMapOfProductsInCategory()
                            .get(subcategoryKey).get(productListNumber)
                            .getImageUrl()).placeholder(drawable)
                    .error(drawable).fit().centerCrop()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(itemImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get()
                                    .load(DataCenter.getCenterRepository()
                                            .getMapOfProductsInCategory()
                                            .get(subcategoryKey)
                                            .get(productListNumber)
                                            .getImageUrl())
                                    .placeholder(drawable).error(drawable)
                                    .fit().centerCrop().into(itemImage);
                        }

                    });

//            LabelView label = new LabelView(getActivity());
//
//            label.setText(DataCenter.getCenterRepository().getMapOfProductsInCategory()
//                    .get(subcategoryKey).get(productListNumber).getDiscount());
//            label.setBackgroundColor(0xffE91E63);
//
//            label.setTargetView(itemImage, 10, LabelView.Gravity.RIGHT_TOP);
        } else {


            //Fetch and display products from Shopping list

            itemName.setText(DataCenter.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber).getName());

            quantity.setText(DataCenter.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber).getQuantity());

            itemdescription.setText(DataCenter.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber).getDescription());

            String sellCostString = Money.getMoneyInstance(
                    BigDecimal.valueOf(Long.valueOf(DataCenter
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(productListNumber).getPrice()))).toString()
                    + "  ";

//            String buyMRP = Money.getMoneyInstance(
//                    BigDecimal.valueOf(Long.valueOf(DataCenter
//                            .getCenterRepository().getListOfProductsInShoppingList()
//                            .get(productListNumber).getMRP()))).toString();
//
//            String costString = sellCostString + buyMRP;
//
//            itemSellPrice.setText(costString, BufferType.SPANNABLE);
//
//            Spannable spannable = (Spannable) itemSellPrice.getText();
//
//            spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
//                    costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mDrawableBuilder = TextDrawable.builder().beginConfig()
                    .withBorder(4).endConfig().roundRect(10);

            drawable = mDrawableBuilder.build(
                    String.valueOf(DataCenter.getCenterRepository()
                            .getListOfProductsInShoppingList().get(productListNumber)
                            .getName().charAt(0)),
                    mColorGenerator.getColor(DataCenter
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(productListNumber).getName()));

            Picasso.get()
                    .load(DataCenter.getCenterRepository()
                            .getListOfProductsInShoppingList().get(productListNumber)
                            .getImageUrl()).placeholder(drawable)
                    .error(drawable).fit().centerCrop()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(itemImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            // Try again online if cache failed

                            Picasso.get()
                                    .load(DataCenter.getCenterRepository()
                                            .getListOfProductsInShoppingList()
                                            .get(productListNumber)
                                            .getImageUrl())
                                    .placeholder(drawable).error(drawable)
                                    .fit().centerCrop().into(itemImage);
                        }
                    });

//            LabelView label = new LabelView(getActivity());
//
//            label.setText(DataCenter.getCenterRepository()
//                    .getListOfProductsInShoppingList().get(productListNumber).getDiscount());
//            label.setBackgroundColor(0xffE91E63);
//
//            label.setTargetView(itemImage, 10, LabelView.Gravity.RIGHT_TOP);

        }
    }


}
