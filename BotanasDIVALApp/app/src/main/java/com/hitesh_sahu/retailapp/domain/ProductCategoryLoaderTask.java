

package com.hitesh_sahu.retailapp.domain;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.utils.Utils;
import com.hitesh_sahu.retailapp.utils.Utils.AnimationType;
import com.hitesh_sahu.retailapp.views.activities.ECartHomeActivity;
import com.hitesh_sahu.retailapp.views.adapter.CategoryListAdapter;
import com.hitesh_sahu.retailapp.views.adapter.CategoryListAdapter.OnItemClickListener;
import com.hitesh_sahu.retailapp.views.fragment.ProductOverviewFragment;


/**
 * The Class ImageLoaderTask.
 */
public class ProductCategoryLoaderTask extends AsyncTask<String, Void, Void> {

    private static final int NUMBER_OF_COLUMNS = 2;
    private Context context;
    private RecyclerView recyclerView;
    private TextView banner;

    public ProductCategoryLoaderTask(RecyclerView listView, Context context, TextView banner) {

        this.recyclerView = listView;
        this.context = context;
        this.banner=banner;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        if (null != ((ECartHomeActivity) context).getProgressBar())
            ((ECartHomeActivity) context).getProgressBar().setVisibility(
                    View.VISIBLE);

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (null != ((ECartHomeActivity) context).getProgressBar())
            ((ECartHomeActivity) context).getProgressBar().setVisibility(
                    View.GONE);

        if (recyclerView != null) {
            CategoryListAdapter simpleRecyclerAdapter = new CategoryListAdapter(
                    context);

            recyclerView.setAdapter(simpleRecyclerAdapter);

            simpleRecyclerAdapter
                    .SetOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {

                            Utils.switchFragmentWithAnimation(
                                    R.id.frag_container,
                                    new ProductOverviewFragment(),
                                    ((ECartHomeActivity) context), null,
                                    AnimationType.SLIDE_LEFT);

                        }
                    });
        }

    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

}