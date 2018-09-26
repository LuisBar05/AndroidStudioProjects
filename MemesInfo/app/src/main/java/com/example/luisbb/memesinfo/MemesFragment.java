package com.example.luisbb.memesinfo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MemesFragment extends Fragment {
    private OnMemeTouchedListener myListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach (context);

        if (context instanceof OnMemeTouchedListener) {
            myListener = (OnMemeTouchedListener) context;
        } else {
            throw new ClassCastException ("Must implement OnMemeTouchedListener first!");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memes, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity myActivity=getActivity();
        if(myActivity==null)
            return;

        RecyclerView myRecyclerView= myActivity.findViewById(R.id.rvList);
        if(myRecyclerView==null)
            return;

        String [] namesArray=getResources().getStringArray(R.array.names);
        TypedArray memesImagesArray=getResources().obtainTypedArray((R.array.images));
        String [] descriptionsArray= getResources().getStringArray(R.array.descriptions);
        String [] urlsArray= getResources().getStringArray(R.array.urls);

        myRecyclerView.setLayoutManager (new GridLayoutManager(getContext(), 2));
        myRecyclerView.setAdapter (new  ListadoAdapter (getContext (), namesArray, memesImagesArray, descriptionsArray,
                urlsArray, myListener));
    }

}

class ListadoAdapter extends RecyclerView.Adapter<ListadoViewHolder> {
    private Context context;
    private String [] namesArray;
    private String [] urlsArray;
    private String [] descriptionsArray;
    private TypedArray memesImagesArray;
    private OnMemeTouchedListener myListener;

    ListadoAdapter (Context context, String [] namesArray, TypedArray memesImagesArray, String [] descriptionsArray,
                    String [] urlsArray, OnMemeTouchedListener myListener) {
        this.context = context;
        this.namesArray=namesArray;
        this.memesImagesArray=memesImagesArray;
        this.descriptionsArray=descriptionsArray;
        this.urlsArray=urlsArray;
        this.myListener=myListener;
    }

    @NonNull
    @Override
    public ListadoViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        View rowView = LayoutInflater.from (context).inflate (R.layout.list_data, viewGroup, false);
        return new ListadoViewHolder (rowView);
    }

    @Override
    public void onBindViewHolder (@NonNull ListadoViewHolder listadoViewHolder, int i) {
        listadoViewHolder.bind (namesArray[i], memesImagesArray.getDrawable(i));

        listadoViewHolder.itemView.setOnClickListener (view -> {
            if (myListener != null)
                myListener.onMemeTouched (namesArray[i], memesImagesArray.getDrawable(i), descriptionsArray[i], urlsArray[i]);
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}

class ListadoViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private ImageView imageView;

    ListadoViewHolder (@NonNull View itemView) {
        super (itemView);
        textView = itemView.findViewById (R.id.listadoItem);
        imageView=itemView.findViewById(R.id.imageList);
    }

    public void bind (String text, Drawable draw) {
        textView.setText (text);
        imageView.setImageDrawable(draw);
    }
}
