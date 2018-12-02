/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.example.luisbb.botanasdivalapp.views.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.models.DataCenter;
import com.hitesh_sahu.retailapp.models.Money;
import com.hitesh_sahu.retailapp.models.Products;
import com.hitesh_sahu.retailapp.utils.ColorGenerator;
import com.hitesh_sahu.retailapp.utils.Utils;
import com.hitesh_sahu.retailapp.views.activities.ECartHomeActivity;
import com.hitesh_sahu.retailapp.views.customview.ItemTouchHelperViewHolder;
import com.hitesh_sahu.retailapp.views.customview.OnStartDragListener;
import com.hitesh_sahu.retailapp.views.customview.TextDrawable;
import com.hitesh_sahu.retailapp.views.customview.TextDrawable.IBuilder;
import com.hitesh_sahu.retailapp.views.fragment.MyCartFragment;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to
 * respond to move and dismiss events from a
 * {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Hitesh Sahu (hiteshsahu.com)
 */
public class ShoppingListAdapter extends
        RecyclerView.Adapter<ShoppingListAdapter.ItemViewHolder> implements
        ItemTouchHelperAdapter {

    private static OnItemClickListener clickListener;
    private final OnStartDragListener mDragStartListener;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private String ImageUrl;
    private List<Products> productList;
    private Context context;

    public ShoppingListAdapter(Context context,
                               OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;

        this.context = context;

        productList = DataCenter.getCenterRepository().getListOfProductsInShoppingList();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_product_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.itemName.setText(productList.get(position).getName());

        holder.itemDesc.setText(productList.get(position).getDescription());

        String sellCostString = Money.getMoneyInstance(
                BigDecimal.valueOf(Long.valueOf(productList.get(position)
                        .getPrice()))).toString()
                + "  ";

//        String buyMRP = Money.getMoneyInstance(
//                BigDecimal.valueOf(Long.valueOf(productList.get(position)
//                        .getMRP()))).toString();
//
//        String costString = sellCostString + buyMRP;
//
//        holder.itemCost.setText(costString, BufferType.SPANNABLE);
//
//        Spannable spannable = (Spannable) holder.itemCost.getText();
//
//        spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
//                costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
                .endConfig().roundRect(10);

        drawable = mDrawableBuilder.build(String.valueOf(productList
                .get(position).getName().charAt(0)), mColorGenerator
                .getColor(productList.get(position).getName()));

        ImageUrl = productList.get(position).getImageUrl();

        holder.quanitity.setText(DataCenter.getCenterRepository()
                .getListOfProductsInShoppingList().get(position).getQuantity());

        Glide.with(context).load(ImageUrl).placeholder(drawable)
                .error(drawable).animate(R.anim.base_slide_right_in)
                .centerCrop().into(holder.imagView);

        // Start a drag whenever the handle view it touched
        holder.imagView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

        holder.addItem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                DataCenter
                        .getCenterRepository()
                        .getListOfProductsInShoppingList()
                        .get(position)
                        .setQuantity(
                                String.valueOf(

                                        Integer.valueOf(DataCenter
                                                .getCenterRepository().getListOfProductsInShoppingList()
                                                .get(position).getQuantity()) + 1));

                holder.quanitity.setText(DataCenter.getCenterRepository()
                        .getListOfProductsInShoppingList().get(position).getQuantity());

                Utils.vibrate(context);

                ((ECartHomeActivity) context).updateCheckOutAmount(
                        BigDecimal.valueOf(Long.valueOf(DataCenter
                                .getCenterRepository().getListOfProductsInShoppingList()
                                .get(position).getPrice())), true);

            }
        });

        holder.removeItem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (Integer.valueOf(DataCenter.getCenterRepository()
                        .getListOfProductsInShoppingList().get(position).getQuantity()) > 0) {

                    DataCenter
                            .getCenterRepository()
                            .getListOfProductsInShoppingList()
                            .get(position)
                            .setQuantity(
                                    String.valueOf(

                                            Integer.valueOf(DataCenter
                                                    .getCenterRepository()
                                                    .getListOfProductsInShoppingList().get(position)
                                                    .getQuantity()) - 1));

                    holder.quanitity.setText(DataCenter
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(position).getQuantity());

                    ((ECartHomeActivity) context).updateCheckOutAmount(
                            BigDecimal.valueOf(Long.valueOf(DataCenter
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(position).getPrice())), false);

                    Utils.vibrate(context);
                } else if (Integer.valueOf(DataCenter.getCenterRepository()
                        .getListOfProductsInShoppingList().get(position).getQuantity()) == 1) {
                    ((ECartHomeActivity) context).updateItemCount(false);

                    ((ECartHomeActivity) context).updateCheckOutAmount(
                            BigDecimal.valueOf(Long.valueOf(DataCenter
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(position).getPrice())), false);

                    DataCenter.getCenterRepository().getListOfProductsInShoppingList()
                            .remove(position);

                    if (Integer.valueOf(((ECartHomeActivity) context)
                            .getItemCount()) == 0) {

                        MyCartFragment.updateMyCartFragment(false);

                    }

                    Utils.vibrate(context);

                }

            }
        });
    }

    @Override
    public void onItemDismiss(int position) {

        ((ECartHomeActivity) context).updateItemCount(false);

        ((ECartHomeActivity) context).updateCheckOutAmount(
                BigDecimal.valueOf(Long.valueOf(DataCenter
                        .getCenterRepository().getListOfProductsInShoppingList().get(position)
                        .getPrice())), false);

        DataCenter.getCenterRepository().getListOfProductsInShoppingList().remove(position);

        if (((ECartHomeActivity) context).getItemCount() == 0) {

            MyCartFragment.updateMyCartFragment(false);

        }

        Utils.vibrate(context);

        // productList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        Collections.swap(productList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return productList.size();

    }

    public void SetOnItemClickListener(
            final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    /**
     * Simple example of a view holder that implements
     * {@link ItemTouchHelperViewHolder} and has a "handle" view that initiates
     * a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder, OnClickListener {

        // public final ImageView handleView;

        TextView itemName, itemDesc, itemCost, availability, quanitity,
                addItem, removeItem;
        ImageView imagView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // handleView = (ImageView) itemView.findViewById(R.id.handle);

            itemName = (TextView) itemView.findViewById(R.id.item_name);

            itemDesc = (TextView) itemView.findViewById(R.id.item_short_desc);

            itemCost = (TextView) itemView.findViewById(R.id.item_price);

            availability = (TextView) itemView
                    .findViewById(R.id.iteam_avilable);

            quanitity = (TextView) itemView.findViewById(R.id.iteam_amount);

            itemName.setSelected(true);

            imagView = ((ImageView) itemView.findViewById(R.id.product_thumb));

            addItem = ((TextView) itemView.findViewById(R.id.add_item));

            removeItem = ((TextView) itemView.findViewById(R.id.remove_item));

            itemView.setOnClickListener(this);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

        @Override
        public void onClick(View v) {

            clickListener.onItemClick(v, getPosition());
        }
    }
}
