package com.example.luisbb.firebaseauthdatabase.Fragments;

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
import com.example.luisbb.firebaseauthdatabase.Models.SongDetails;
import com.example.luisbb.firebaseauthdatabase.R;
import com.example.luisbb.firebaseauthdatabase.SongsDataBaseActivity;

import java.util.ArrayList;

public class PlaylistFragment extends Fragment {

    public static PlaylistFragment newInstance(ArrayList<SongDetails> myListSongs){
        PlaylistFragment mFragment=new PlaylistFragment();
        Bundle b=new Bundle();
        b.putSerializable(SongsDataBaseActivity.KEY, myListSongs);
        mFragment.setArguments(b);

        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity myActivity=getActivity();
        if(myActivity==null)
            return;

        RecyclerView myRecyclerView=myActivity.findViewById(R.id.recyclerView);
        if(myRecyclerView==null)
            return;

        Bundle myBundle=getArguments();

        if(myBundle==null)
            return;

        ArrayList<SongDetails> mPlaylist= (ArrayList<SongDetails>) myBundle.getSerializable(SongsDataBaseActivity.KEY);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setAdapter(new SongsListAdapter(getContext(), mPlaylist));
    }
}

class SongsListAdapter extends RecyclerView.Adapter<SongsListViewHolder>{
    private Context myContext;
    private ArrayList<SongDetails> elementsArray;

    public SongsListAdapter(Context myContext, ArrayList<SongDetails> elementsArray){
        this.myContext=myContext;
        this.elementsArray=elementsArray;
    }

    @NonNull
    @Override
    public SongsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rowView = LayoutInflater.from (myContext).inflate (R.layout.list_songs, viewGroup, false);
        return new SongsListViewHolder (rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsListViewHolder songsListViewHolder, int i) {
        songsListViewHolder.bind(elementsArray.get(i));
    }

    @Override
    public int getItemCount() {
        return elementsArray.size();
    }
}

class SongsListViewHolder extends RecyclerView.ViewHolder{
    private TextView tvTrack, tvAlbum, tvAuthor, tvCompany, tvComposer, tvCover, tvTitle, tvYear;

    SongsListViewHolder (@NonNull View itemView) {
        super (itemView);
        tvTrack= itemView.findViewById (R.id.tvTrack);
        tvAlbum=itemView.findViewById(R.id.tvAlbum);
        tvAuthor=itemView.findViewById(R.id.tvAuthor);
        tvCompany=itemView.findViewById(R.id.tvCompany);
        tvComposer=itemView.findViewById(R.id.tvComposer);
        tvCover=itemView.findViewById(R.id.tvCover);
        tvTitle=itemView.findViewById(R.id.tvTitle);
        tvYear=itemView.findViewById(R.id.tvYear);
    }

    public void bind (SongDetails infoSong) {
        tvTrack.setText (infoSong.getSongId());
        tvAlbum.setText("Album: "+infoSong.getAlbum());
        tvAuthor.setText("Author: "+infoSong.getAuthor());
        tvCompany.setText("Company: "+infoSong.getCompany());
        tvComposer.setText("Composer: "+infoSong.getComposer());
        tvCover.setText("Cover: "+infoSong.getCover());
        tvTitle.setText("Title: "+infoSong.getTitle());
        tvYear.setText("Album: "+infoSong.getYear());
    }
}
