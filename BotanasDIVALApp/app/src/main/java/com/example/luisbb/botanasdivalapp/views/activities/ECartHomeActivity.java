/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.example.luisbb.botanasdivalapp.views.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.luisbb.botanasdivalapp.R;
import com.example.luisbb.botanasdivalapp.models.DataCenter;
import com.example.luisbb.botanasdivalapp.models.Money;
import com.example.luisbb.botanasdivalapp.models.Products;
import com.example.luisbb.botanasdivalapp.utils.Utils;
import com.example.luisbb.botanasdivalapp.utils.Utils.AnimationType;
import com.example.luisbb.botanasdivalapp.views.fragment.HomeFragment;
import com.wang.avi.AVLoadingIndicatorView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;

public class ECartHomeActivity extends AppCompatActivity {
    private static final String TAG = ECartHomeActivity.class.getSimpleName();
    private int itemCount = 0;
    private BigDecimal checkoutAmount = new BigDecimal(BigInteger.ZERO);
    private DrawerLayout mDrawerLayout;

    private TextView checkOutAmount, itemCountTextView;
    private TextView offerBanner;
    private AVLoadingIndicatorView progressBar;

    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecart);

        itemCount = DataCenter.getCenterRepository().getListOfProductsInShoppingList()
                .size();

        offerBanner = findViewById(R.id.new_offers_banner);

        itemCountTextView = findViewById(R.id.item_count);
        itemCountTextView.setSelected(true);
        itemCountTextView.setText(String.valueOf(itemCount));

        checkOutAmount = findViewById(R.id.checkout_amount);
        checkOutAmount.setSelected(true);
        checkOutAmount.setText(Money.getMoneyInstance(checkoutAmount).toString());
        offerBanner.setSelected(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        progressBar = (AVLoadingIndicatorView) findViewById(R.id.loading_bar);

        checkOutAmount.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Utils.vibrate(getApplicationContext());

                Utils.switchContent(R.id.frag_container,
                        Utils.SHOPPING_LIST_TAG, ECartHomeActivity.this,
                        AnimationType.SLIDE_UP);

            }
        });


        if (itemCount != 0) {
            for (Products product : DataCenter.getCenterRepository()
                    .getListOfProductsInShoppingList()) {

                updateCheckOutAmount(
                        BigDecimal.valueOf(Long.valueOf(product.getPrice())),
                        true);
            }
        }

        findViewById(R.id.item_counter).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Utils.vibrate(getApplicationContext());
                        Utils.switchContent(R.id.frag_container,
                                Utils.SHOPPING_LIST_TAG,
                                ECartHomeActivity.this, AnimationType.SLIDE_UP);

                    }
                });

        findViewById(R.id.checkout).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Utils.vibrate(getApplicationContext());

                        showPurchaseDialog();

                    }
                });

        Utils.switchFragmentWithAnimation(R.id.frag_container,
                new HomeFragment(), this, Utils.HOME_FRAGMENT,
                AnimationType.SLIDE_UP);

        toggleBannerVisibility();

        mNavigationView
                .setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.home:

                                mDrawerLayout.closeDrawers();

                                Utils.switchContent(R.id.frag_container,
                                        Utils.HOME_FRAGMENT,
                                        ECartHomeActivity.this,
                                        AnimationType.SLIDE_LEFT);

                                return true;

                            case R.id.my_cart:

                                mDrawerLayout.closeDrawers();

                                Utils.switchContent(R.id.frag_container,
                                        Utils.SHOPPING_LIST_TAG,
                                        ECartHomeActivity.this,
                                        AnimationType.SLIDE_LEFT);
                                return true;

                            case R.id.apriori_result:
                                return true;


                            case R.id.contact_us:

                                mDrawerLayout.closeDrawers();

                                Utils.switchContent(R.id.frag_container,
                                        Utils.CONTACT_US_FRAGMENT,
                                        ECartHomeActivity.this,
                                        AnimationType.SLIDE_LEFT);
                                return true;

                            case R.id.settings:

                                mDrawerLayout.closeDrawers();

                                Utils.switchContent(R.id.frag_container,
                                        Utils.SETTINGS_FRAGMENT_TAG,
                                        ECartHomeActivity.this,
                                        AnimationType.SLIDE_LEFT);
                                return true;
                            default:
                                return true;
                        }
                    }
                });

    }

    public AVLoadingIndicatorView getProgressBar() {
        return progressBar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateItemCount(boolean ifIncrement) {
        if (ifIncrement) {
            itemCount++;
            itemCountTextView.setText(String.valueOf(itemCount));

        } else {
            itemCountTextView.setText(String.valueOf(itemCount <= 0 ? 0
                    : --itemCount));
        }

        toggleBannerVisibility();
    }

    public void updateCheckOutAmount(BigDecimal amount, boolean increment) {

        if (increment) {
            checkoutAmount = checkoutAmount.add(amount);
        } else {
            if (checkoutAmount.signum() == 1)
                checkoutAmount = checkoutAmount.subtract(amount);
        }

        checkOutAmount.setText(Money.getMoneyInstance(checkoutAmount).toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
     * Toggles Between Offer Banner and Checkout Amount. If Cart is Empty SHow
     * Banner else display total amount and item count
     */
    public void toggleBannerVisibility() {
        if (itemCount == 0) {

            findViewById(R.id.checkout_item_root).setVisibility(View.GONE);
            findViewById(R.id.new_offers_banner).setVisibility(View.VISIBLE);

        } else {
            findViewById(R.id.checkout_item_root).setVisibility(View.VISIBLE);
            findViewById(R.id.new_offers_banner).setVisibility(View.GONE);
        }
    }

    /*
     * get total checkout amount
     */
    public BigDecimal getCheckoutAmount() {
        return checkoutAmount;
    }

    /*
     * Get Number of items in cart
     */
    public int getItemCount() {
        return itemCount;
    }

    /*
     * Get Navigation drawer
     */
    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }


    public void showPurchaseDialog() {

        AlertDialog.Builder exitScreenDialog = new AlertDialog.Builder(ECartHomeActivity.this, R.style.PauseDialog);

        exitScreenDialog.setTitle("CONFIRMAR ORDEN")
                .setMessage("¿Te gustaría realizar este pedido?");
        exitScreenDialog.setCancelable(true);

        exitScreenDialog.setPositiveButton(
                "Realizar Pedido",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //finish();
                        dialog.cancel();

                        ArrayList<String> productId = new ArrayList<String>();

                        for (Products productFromShoppingList : DataCenter.getCenterRepository().getListOfProductsInShoppingList()) {

                            //add product ids to array
                            productId.add(productFromShoppingList.getProductKey());
                        }

                        //pass product id array to Apriori ALGO
                        DataCenter.getCenterRepository()
                                .addToItemSetList(new HashSet<>(productId));



                        //clear all list item
                        DataCenter.getCenterRepository().getListOfProductsInShoppingList().clear();



                        itemCount = 0;
                        itemCountTextView.setText(String.valueOf(0));
                        checkoutAmount = new BigDecimal(BigInteger.ZERO);
                        checkOutAmount.setText(Money.getMoneyInstance(checkoutAmount).toString());

                        toggleBannerVisibility();
                        findViewById(R.id.default_no_data).setVisibility(View.VISIBLE);

                    }
                });

        exitScreenDialog.setNegativeButton(
                "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        exitScreenDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(itemCount==0){
                    Snackbar.make(ECartHomeActivity.this.getWindow().getDecorView().findViewById(android.R.id.content)
                            , "¡Pedido Realizado Exitosamente!", Snackbar.LENGTH_LONG)
                            .setDuration(6000)
                            .show();
                }

            }
        });

        AlertDialog alertMe = exitScreenDialog.create();
        alertMe.show();

    }

}
