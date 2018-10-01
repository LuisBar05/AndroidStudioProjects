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

import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiSpecies;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiSpeciesHeader;
import examples.gonzasosa.outlook.com.swinfoapp.R;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.DownloadAsyncTask;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.URLS;

public class SpeciesApiFragment extends Fragment {
    ArrayList<SWApiSpecies> foo = new ArrayList<>();
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

        new DownloadAsyncTask (this::parseJSON).execute (URLS.SW_API_SPECIES);

    }

    private void parseJSON (String json) {
        SWApiSpeciesHeader specie= new Gson ().fromJson (json, SWApiSpeciesHeader.class);

        if (specie == null) return;

        if (specie.next != null) {
            new DownloadAsyncTask (this::parseJSON).execute (specie.next);
        } else {
            foo.addAll (specie.results);
            recyclerView.setAdapter (new SpeciesApiAdapter (foo));
        }

        foo.addAll (specie.results);
    }
}


class SpeciesApiAdapter extends RecyclerView.Adapter<SpeciesApiAdapter.SpeciesApiViewHolder> {

    private ArrayList<SWApiSpecies> data;

    SpeciesApiAdapter (ArrayList<SWApiSpecies> d) {
        data = d;
    }

    @NonNull
    @Override
    public SpeciesApiViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.species_list, parent, false);

        return new SpeciesApiViewHolder (view);
    }

    @Override
    public void onBindViewHolder (@NonNull SpeciesApiViewHolder holder, int position) {
        SWApiSpecies specie= data.get (position);
        holder.setData (specie);
    }

    @Override
    public int getItemCount () {
        return data.size ();
    }

    class SpeciesApiViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvClass, tvDesignation, tvHeight, tvHair, tvSkin, tvEye, tvLifespan,
                tvLanguage, tvCreated, tvEdited, tvUrl;

        SpeciesApiViewHolder (View itemView) {
            super (itemView);
            tvName = itemView.findViewById (R.id.tvSpecieName);
            tvClass = itemView.findViewById (R.id.tvSpecieClass);
            tvDesignation = itemView.findViewById (R.id.tvSpecieDesignation);
            tvHeight = itemView.findViewById (R.id.tvSpecieHeight);
            tvHair = itemView.findViewById (R.id.tvSpecieHair);
            tvSkin = itemView.findViewById (R.id.tvSpecieSkin);
            tvEye = itemView.findViewById (R.id.tvSpecieEye);
            tvLifespan = itemView.findViewById (R.id.tvSpecieLifespan);
            tvLanguage = itemView.findViewById (R.id.tvSpecieLanguage);
            tvCreated= itemView.findViewById (R.id.tvSpecieCreated);
            tvEdited= itemView.findViewById (R.id.tvSpecieEdited);
            tvUrl = itemView.findViewById (R.id.tvSpecieUrl);
        }

        void setData (SWApiSpecies specie) {
            tvName.setText (specie.name);
            tvClass.setText (specie.classification);
            tvDesignation.setText (specie.designation);
            tvHeight.setText (specie.average_height);
            tvHair.setText (specie.hair_colors);
            tvSkin.setText (specie.skin_colors);
            tvEye.setText (specie.eye_colors);
            tvLifespan.setText (specie.average_lifespan);
            tvLanguage.setText (specie.language);
            tvCreated.setText (specie.created);
            tvEdited.setText (specie.edited);
            tvUrl.setText (specie.url);
        }
    }
}
