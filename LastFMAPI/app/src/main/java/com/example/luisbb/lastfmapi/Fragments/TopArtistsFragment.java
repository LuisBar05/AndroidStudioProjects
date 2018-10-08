package com.example.luisbb.lastfmapi.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luisbb.lastfmapi.R;


public class TopArtistsFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity myActivity=getActivity();
        if(myActivity==null)
            return;

        RecyclerView myRecyclerView=myActivity.findViewById(R.id.rvList);
        if(myRecyclerView==null)
            return;

        String[] elementsArray=getResources().getStringArray(R.array.elements);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setAdapter(new ElementsListAdapter(getContext(), elementsArray));
    }
}

class ElementsListAdapter extends RecyclerView.Adapter<ElementsListViewHolder>{
    private Context myContext;
    private String [] elementsArray;

    public ElementsListAdapter(Context myContext, String [] elementsArray){
        this.myContext=myContext;
        this.elementsArray=elementsArray;
    }

    @NonNull
    @Override
    public ElementsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rowView = LayoutInflater.from (myContext).inflate (R.layout.top_artists_list_elements, viewGroup, false);
        return new ElementsListViewHolder (rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementsListViewHolder elementsListViewHolder, int i) {
        elementsListViewHolder.bind(elementsArray[i]);
    }

    @Override
    public int getItemCount() {
        return elementsArray.length;
    }
}

class ElementsListViewHolder extends RecyclerView.ViewHolder{
    private TextView textView;

    ElementsListViewHolder (@NonNull View itemView) {
        super (itemView);
        textView = itemView.findViewById (R.id.tvElement);
    }

    public void bind (String text) {
        textView.setText (text);
    }
}
