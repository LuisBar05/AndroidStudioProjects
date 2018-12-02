package com.example.luisbb.botanasdivalapp.domain;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.luisbb.botanasdivalapp.models.Categories;

import java.util.ArrayList;

public class FirebaseDatabaseHandler extends AsyncTask<String, Void, Void> {
    private DatabaseReference mDatabaseRef;
    ArrayList<Categories> mListData;

    public FirebaseDatabaseHandler(String path, Context context){
        mListData=new ArrayList<>();
        mDatabaseRef=FirebaseDatabase.getInstance().getReference(path);
    }

    public ArrayList<Categories> readData(){

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    Categories mData= child.getValue(Categories.class);
                    mListData.add(mData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return mListData;
    }

    @Override
    protected Void doInBackground(String... strings) {
        return null;
    }
}
