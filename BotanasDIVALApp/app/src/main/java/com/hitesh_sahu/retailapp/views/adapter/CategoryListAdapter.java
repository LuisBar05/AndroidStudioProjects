

package com.hitesh_sahu.retailapp.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.models.DataCenter;
import com.hitesh_sahu.retailapp.models.Categories;
import com.hitesh_sahu.retailapp.utils.ColorGenerator;
import com.hitesh_sahu.retailapp.views.customview.TextDrawable;
import com.hitesh_sahu.retailapp.views.customview.TextDrawable.IBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hitesh Sahu (hiteshsahu.com)
 */
public class CategoryListAdapter extends
        RecyclerView.Adapter<CategoryListAdapter.VersionViewHolder> {

    public static List<Categories> categoryList = new ArrayList<>();
    private OnItemClickListener clickListener;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private String ImageUrl;
    private Context context;

    public CategoryListAdapter(Context context) {

        categoryList = DataCenter.getCenterRepository().getListOfCategory();

        this.context = context;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_category_list, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder,
                                 int categoryIndex) {

        versionViewHolder.itemName.setText(categoryList.get(categoryIndex)
                .getName());

        versionViewHolder.itemDesc.setText(categoryList.get(categoryIndex)
                .getDescription());

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
                .endConfig().roundRect(10);

        drawable = mDrawableBuilder.build(String.valueOf(categoryList
                        .get(categoryIndex).getName().charAt(0)),
                mColorGenerator.getColor(categoryList.get(categoryIndex)
                        .getName()));

        ImageUrl = categoryList.get(categoryIndex).getImageUrl();

        Glide.with(context).load(ImageUrl).placeholder(drawable)
                .error(drawable).animate(R.anim.base_slide_right_in)
                .centerCrop().into(versionViewHolder.imagView);

    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    public void SetOnItemClickListener(
            final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemName, itemDesc, itemCost, availability, quanitity,
                addItem, removeItem;
        ImageView imagView;

        public VersionViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.item_name);

            itemDesc = (TextView) itemView.findViewById(R.id.item_short_desc);

            itemName.setSelected(true);

            imagView = ((ImageView) itemView.findViewById(R.id.imageView));

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }

}
