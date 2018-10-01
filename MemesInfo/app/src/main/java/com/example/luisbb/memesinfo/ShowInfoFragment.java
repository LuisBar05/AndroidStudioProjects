package com.example.luisbb.memesinfo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ShowInfoFragment extends Fragment {
    private String name, description, url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        name=getArguments().getString("MemeName");
        description=getArguments().getString("MemeDescription");
        url=getArguments().getString("MemeUrl");
        return inflater.inflate(R.layout.fragment_show_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView nameText=getView().findViewById(R.id.memeName);
        ImageView imageView=getView().findViewById(R.id.imageMeme);
        TextView descriptionText=getView().findViewById(R.id.descriptionMeme);
        TextView urlText=getView().findViewById(R.id.urlMeme);

        nameText.setText(name);
        imageView.setImageDrawable(getResources().obtainTypedArray(R.array.images).getDrawable(getArguments().getInt("MemeImage")));
        descriptionText.setText(description);
        urlText.setText(getArguments().getString("MemeUrl"));
    }
}
