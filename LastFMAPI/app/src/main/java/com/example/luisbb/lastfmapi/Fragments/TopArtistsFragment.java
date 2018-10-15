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

import com.example.luisbb.lastfmapi.Models.LastFMArtist;
import com.example.luisbb.lastfmapi.Models.LastFMTopArtistsHandler;
import com.example.luisbb.lastfmapi.Models.LastFMTopArtistsResponse;
import com.example.luisbb.lastfmapi.R;
import com.example.luisbb.lastfmapi.Utils.LastFMConstants;
import com.example.luisbb.lastfmapi.Utils.LastFMService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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

        ArrayList<LastFMArtist> myArtists=new ArrayList<>();

        Retrofit myRetroFit=new Retrofit.Builder()
                .baseUrl(LastFMConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LastFMService lastFMService= myRetroFit.create(LastFMService.class);
        Call<LastFMTopArtistsResponse> foo=lastFMService.getTopArtists(LastFMConstants.TOP_ITEMS_LIMIT, LastFMConstants.API_KEY, LastFMConstants.REQUEST_FORMAT);

        Callback<LastFMTopArtistsResponse> cbHandler=new Callback<LastFMTopArtistsResponse>() {
            @Override
            public void onResponse(Call<LastFMTopArtistsResponse> call, Response<LastFMTopArtistsResponse> response) {
                if(!response.isSuccessful())
                    return;

                LastFMTopArtistsHandler lastFMTopArtistsHandler=response.body().getTopArtists();
                myArtists.addAll(lastFMTopArtistsHandler.getArtists());
            }

            @Override
            public void onFailure(Call<LastFMTopArtistsResponse> call, Throwable t) {

            }
        };

        foo.enqueue(cbHandler);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setAdapter(new ElementsListAdapter(getContext(), myArtists));
    }
}

class ElementsListAdapter extends RecyclerView.Adapter<ElementsListViewHolder>{
    private Context myContext;
    private ArrayList<LastFMArtist> elementsArray;

    public ElementsListAdapter(Context myContext, ArrayList<LastFMArtist> elementsArray){
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
        elementsListViewHolder.bind(elementsArray.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return elementsArray.size();
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
