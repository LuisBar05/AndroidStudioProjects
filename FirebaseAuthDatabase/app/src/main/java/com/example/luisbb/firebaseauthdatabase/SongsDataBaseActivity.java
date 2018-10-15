package com.example.luisbb.firebaseauthdatabase;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.luisbb.firebaseauthdatabase.Fragments.PlaylistFragment;
import com.example.luisbb.firebaseauthdatabase.Models.SongDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SongsDataBaseActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private ArrayList<SongDetails> myListSongs;
    public static final String KEY="PLAYLIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_data_base);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        mDatabaseRef=database.getReference("songs");
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myListSongs= new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    SongDetails mSongData = child.getValue(SongDetails.class);
                    mSongData.setSongId(child.getKey());
                    myListSongs.add(mSongData);
                }

                getSupportFragmentManager()
                        .beginTransaction ()
                        .replace (R.id.rootContainer, PlaylistFragment.newInstance(myListSongs))
                        .setTransition (FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit ();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
