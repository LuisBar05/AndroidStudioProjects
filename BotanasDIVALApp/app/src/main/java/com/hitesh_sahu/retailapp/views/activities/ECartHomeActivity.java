

package com.hitesh_sahu.retailapp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.models.DataCenter;
import com.hitesh_sahu.retailapp.models.Money;
import com.hitesh_sahu.retailapp.models.Products;
import com.hitesh_sahu.retailapp.utils.Utils;
import com.hitesh_sahu.retailapp.utils.Utils.AnimationType;
import com.hitesh_sahu.retailapp.views.fragment.HomeFragment;
import com.wang.avi.AVLoadingIndicatorView;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ECartHomeActivity extends AppCompatActivity {
    private static final String TAG = ECartHomeActivity.class.getSimpleName();
    private static int itemCount = 0;
    private static BigDecimal checkoutAmount = new BigDecimal(BigInteger.ZERO);
    private DrawerLayout mDrawerLayout;
    private static TextView itemCountTextView;
    private static TextView checkOutAmount;
    private TextView offerBanner;
    private AVLoadingIndicatorView progressBar;
    static final String CHECKOUT_AMOUNT_KEY="CheckOutAmountKey";

    private NavigationView mNavigationView;

    public static void setCheckoutAmount(BigDecimal checkoutAmount) {
        ECartHomeActivity.checkoutAmount = checkoutAmount;
    }

    public static TextView getItemCountTextView() {
        return itemCountTextView;
    }

    public static void setItemCountTextView(TextView itemCountTextView) {
        ECartHomeActivity.itemCountTextView = itemCountTextView;
    }

    public static TextView getCheckOutAmount() {
        return checkOutAmount;
    }

    public static void setCheckOutAmount(TextView checkOutAmount) {
        ECartHomeActivity.checkOutAmount = checkOutAmount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecart);

        itemCount = DataCenter.getCenterRepository().getListOfProductsInShoppingList()
                .size();

        offerBanner = findViewById(R.id.new_offers_banner);

        setItemCountTextView((TextView) findViewById(R.id.item_count));
        getItemCountTextView().setSelected(true);
        getItemCountTextView().setText(String.valueOf(itemCount));

        setCheckOutAmount((TextView) findViewById(R.id.checkout_amount));
        getCheckOutAmount().setSelected(true);
        getCheckOutAmount().setText(Money.getMoneyInstance(getCheckoutAmount()).toString());
        offerBanner.setSelected(true);

        mDrawerLayout = findViewById(R.id.nav_drawer);
        mNavigationView = findViewById(R.id.nav_view);

        progressBar = findViewById(R.id.loading_bar);

        getCheckOutAmount().setOnClickListener(new OnClickListener() {

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

                        Intent intent = new Intent(getApplicationContext(),
                                OrderDetailActivity.class);
                        intent.putExtra(CHECKOUT_AMOUNT_KEY, ((TextView) findViewById(R.id.checkout_amount)).getText().toString());
                        startActivity(intent);

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
            getItemCountTextView().setText(String.valueOf(itemCount));

        } else {
            getItemCountTextView().setText(String.valueOf(itemCount <= 0 ? 0
                    : --itemCount));
        }

        toggleBannerVisibility();
    }

    public void updateCheckOutAmount(BigDecimal amount, boolean increment) {

        if (increment) {
            setCheckoutAmount(getCheckoutAmount().add(amount));
        } else {
            if (getCheckoutAmount().signum() == 1)
                setCheckoutAmount(getCheckoutAmount().subtract(amount));
        }

        getCheckOutAmount().setText(Money.getMoneyInstance(getCheckoutAmount()).toString());
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
    public static BigDecimal getCheckoutAmount() {
        return checkoutAmount;
    }

    /*
     * Get Number of items in cart
     */
    public static int getItemCount() {
        return itemCount;
    }

    /*
     * Set Number of items in cart
     */
    public static void setItemCount(int itemCountCart) {
        itemCount=itemCountCart;
    }

    /*
     * Get Navigation drawer
     */
    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }




}
