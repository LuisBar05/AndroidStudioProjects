package examples.gonzasosa.outlook.com.swinfoapp.Fragments;

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

import com.google.gson.Gson;

import java.util.ArrayList;

import examples.gonzasosa.outlook.com.swinfoapp.Interfaces.OnDownloadFinishedListener;
import examples.gonzasosa.outlook.com.swinfoapp.MainActivity;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiPlanet;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiPlanetsHeader;
import examples.gonzasosa.outlook.com.swinfoapp.R;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.URLS;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.DownloadAsyncTask;

public class BaseApiFragment extends Fragment {
    ArrayList<SWApiPlanet> foo = new ArrayList<>();
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_baseapi, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity activity = getActivity ();
        if (activity == null)
            return;

        recyclerView = activity.findViewById (R.id.baseApiList);
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));

//      llamada asíncrona para descargar el primer conjunto de resultados utilizando la URL base.
//      Se muestran las diferentes formas de llamar al constructor de la clase pasando un objeto del tipo esperado
//      *****************************Referencia de método*************************************
        new DownloadAsyncTask (this::parseJSON).execute (URLS.SW_API_PLANETS_URL);

    }

    //función recursiva que nos permite recorrer los conjuntos de resultados adicionales, si estos existen
    private void parseJSON (String json) {
        //los datos descargados en formato JSON se convierten en su representación java utilizando la clase especificada
        SWApiPlanetsHeader planets = new Gson ().fromJson (json, SWApiPlanetsHeader.class);

        if (planets == null) return;

        if (planets.next != null) {
            //si existe otro conjunto de resultados, se descarga de forma asíncrona
            new DownloadAsyncTask (this::parseJSON).execute (planets.next);
        } else {
            //cuando ya no hay más conjuntos de resultados en el campo next, los obtenidos se pasan al adapter del recyclerview
            foo.addAll (planets.results);
            recyclerView.setAdapter (new BaseApiAdapter (foo));
        }

        foo.addAll (planets.results);
        //Log.i (URLS.TAG, "" + planets.results.size ());
    }
}


class BaseApiAdapter extends RecyclerView.Adapter<BaseApiAdapter.BaseApiViewHolder> {

    private ArrayList<SWApiPlanet> data;

    BaseApiAdapter (ArrayList<SWApiPlanet> d) {
        data = d;
    }

    @NonNull
    @Override
    public BaseApiViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.list_item, parent, false);

        return new BaseApiViewHolder (view);
    }

    @Override
    public void onBindViewHolder (@NonNull BaseApiViewHolder holder, int position) {
        SWApiPlanet planet = data.get (position);
        holder.setData (planet.name, planet.diameter, planet.population);
    }

    @Override
    public int getItemCount () {
        return data.size ();
    }

    class BaseApiViewHolder extends RecyclerView.ViewHolder {
        TextView tvData1, tvData2, tvData3;

        BaseApiViewHolder (View itemView) {
            super (itemView);

            tvData1 = itemView.findViewById (R.id.tvData1);
            tvData2 = itemView.findViewById (R.id.tvData2);
            tvData3 = itemView.findViewById (R.id.tvData3);
        }

        void setData (String data1, String data2, String data3) {
            tvData1.setText (data1);
            tvData2.setText (data2);
            tvData3.setText (data3);
        }
    }
}
