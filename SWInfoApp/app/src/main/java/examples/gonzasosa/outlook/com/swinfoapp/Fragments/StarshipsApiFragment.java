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

import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiPeople;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiPeopleHeader;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiStarships;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiStarshipsHeader;
import examples.gonzasosa.outlook.com.swinfoapp.R;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.DownloadAsyncTask;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.URLS;

public class StarshipsApiFragment extends Fragment {
    ArrayList<SWApiStarships> foo = new ArrayList<>();
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

        new DownloadAsyncTask (this::parseJSON).execute (URLS.SW_API_STARSHIPS_URL);

    }

    private void parseJSON (String json) {
        SWApiStarshipsHeader starship= new Gson ().fromJson (json, SWApiStarshipsHeader.class);

        if (starship == null) return;

        if (starship.next != null) {
            new DownloadAsyncTask (this::parseJSON).execute (starship.next);
        } else {
            foo.addAll (starship.results);
            recyclerView.setAdapter (new StarshipsApiAdapter (foo));
        }

        foo.addAll (starship.results);
    }
}


class StarshipsApiAdapter extends RecyclerView.Adapter<StarshipsApiAdapter.StarshipsApiViewHolder> {

    private ArrayList<SWApiStarships> data;

    StarshipsApiAdapter (ArrayList<SWApiStarships> d) {
        data = d;
    }

    @NonNull
    @Override
    public StarshipsApiViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.starships_list, parent, false);

        return new StarshipsApiViewHolder (view);
    }

    @Override
    public void onBindViewHolder (@NonNull StarshipsApiViewHolder holder, int position) {
        SWApiStarships starship= data.get (position);
        holder.setData (starship);
    }

    @Override
    public int getItemCount () {
        return data.size ();
    }

    class StarshipsApiViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvModel, tvManufacturer, tvCost, tvLength, tvSpeed, tvCrew, tvPass, tvCargo, tvCons, tvHyper,
                tvMGLT, tvClass, tvCreated, tvEdited, tvUrl;

        StarshipsApiViewHolder (View itemView) {
            super (itemView);
            tvName = itemView.findViewById (R.id.tvStarshipName);
            tvModel = itemView.findViewById (R.id.tvStarshipModel);
            tvManufacturer = itemView.findViewById (R.id.tvStarshipManufacturer);
            tvCost = itemView.findViewById (R.id.tvStarshipCost);
            tvLength = itemView.findViewById (R.id.tvStarshipLength);
            tvSpeed = itemView.findViewById (R.id.tvStarshipSpeed);
            tvCrew = itemView.findViewById (R.id.tvStarshipCrew);
            tvPass = itemView.findViewById (R.id.tvStarshipPass);
            tvCargo = itemView.findViewById (R.id.tvStarshipCargo);
            tvCons = itemView.findViewById (R.id.tvStarshipCons);
            tvHyper = itemView.findViewById (R.id.tvStarshipHyper);
            tvMGLT = itemView.findViewById (R.id.tvStarshipMGLT);
            tvClass = itemView.findViewById (R.id.tvStarshipClass);
            tvCreated= itemView.findViewById (R.id.tvStarshipCreated);
            tvEdited= itemView.findViewById (R.id.tvStarshipEdited);
            tvUrl = itemView.findViewById (R.id.tvStarshipUrl);
        }

        void setData (SWApiStarships starship) {
            tvName.setText (starship.name);
            tvModel.setText (starship.model);
            tvManufacturer.setText (starship.manufacturer);
            tvCost.setText (starship.cost_in_credits);
            tvLength.setText (starship.length);
            tvSpeed.setText (starship.max_atmosphering_speed);
            tvCrew.setText (starship.crew);
            tvPass.setText (starship.passengers);
            tvCargo.setText (starship.cargo_capacity);
            tvCons.setText (starship.consumables);
            tvHyper.setText (starship.hyperdrive_rating);
            tvMGLT.setText (starship.MGLT);
            tvClass.setText(starship.starship_class);
            tvCreated.setText (starship.created);
            tvEdited.setText (starship.edited);
            tvUrl.setText (starship.url);
        }
    }
}
