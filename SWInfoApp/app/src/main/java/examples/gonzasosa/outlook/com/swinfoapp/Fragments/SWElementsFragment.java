package examples.gonzasosa.outlook.com.swinfoapp.Fragments;

import android.content.Context;
import android.net.Uri;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

import examples.gonzasosa.outlook.com.swinfoapp.Interfaces.OnElementTouchedListener;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiFilms;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiPeople;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiPlanet;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiSpecies;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiStarships;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiVehicles;
import examples.gonzasosa.outlook.com.swinfoapp.R;

public class SWElementsFragment extends Fragment {
    private OnElementTouchedListener myListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swelements, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnElementTouchedListener) {
            myListener = (OnElementTouchedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnElementTouchedListener first!");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity myActivity=getActivity();
        if(myActivity==null)
            return;

        RecyclerView myRecyclerView=myActivity.findViewById(R.id.rvSWList);
        if(myRecyclerView==null)
            return;

        String[] elementsArray=getResources().getStringArray(R.array.elements);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setAdapter(new SWListadoAdapter(getContext(), elementsArray, myListener));
    }
}

class SWListadoAdapter extends RecyclerView.Adapter<SWListadoViewHolder>{
    private Context myContext;
    private String [] elementsArray;
    private OnElementTouchedListener myListener;

    public SWListadoAdapter(Context myContext, String [] elementsArray, OnElementTouchedListener myListener){
        this.myContext=myContext;
        this.elementsArray=elementsArray;
        this.myListener=myListener;
    }

    @NonNull
    @Override
    public SWListadoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rowView = LayoutInflater.from (myContext).inflate (R.layout.sw_list_element, viewGroup, false);
        return new SWListadoViewHolder (rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull SWListadoViewHolder swListadoViewHolder, int i) {
        swListadoViewHolder.bind(elementsArray[i]);

        swListadoViewHolder.itemView.setOnClickListener(view -> {
            if (myListener != null)
                myListener.onElementTouched(i);
        });
    }

    @Override
    public int getItemCount() {
        return elementsArray.length;
    }
}

class SWListadoViewHolder extends RecyclerView.ViewHolder{
    private TextView textView;

    SWListadoViewHolder (@NonNull View itemView) {
        super (itemView);
        textView = itemView.findViewById (R.id.tvElement);
    }

    public void bind (String text) {
        textView.setText (text);
    }
}
