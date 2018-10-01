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

import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiVehicles;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiVehiclesHeader;
import examples.gonzasosa.outlook.com.swinfoapp.R;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.DownloadAsyncTask;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.URLS;

public class VehiclesApiFragment extends Fragment {
    ArrayList<SWApiVehicles> foo = new ArrayList<>();
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

        new DownloadAsyncTask (this::parseJSON).execute (URLS.SW_API_VEHICLES);

    }

    private void parseJSON (String json) {
        SWApiVehiclesHeader vehicle= new Gson ().fromJson (json, SWApiVehiclesHeader.class);

        if (vehicle == null) return;

        if (vehicle.next != null) {
            new DownloadAsyncTask (this::parseJSON).execute (vehicle.next);
        } else {
            foo.addAll (vehicle.results);
            recyclerView.setAdapter (new VehiclesApiAdapter (foo));
        }

        foo.addAll (vehicle.results);
    }
}


class VehiclesApiAdapter extends RecyclerView.Adapter<VehiclesApiAdapter.VehiclesApiViewHolder> {

    private ArrayList<SWApiVehicles> data;

    VehiclesApiAdapter (ArrayList<SWApiVehicles> d) {
        data = d;
    }

    @NonNull
    @Override
    public VehiclesApiViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.vehicles_list, parent, false);

        return new VehiclesApiViewHolder (view);
    }

    @Override
    public void onBindViewHolder (@NonNull VehiclesApiViewHolder holder, int position) {
        SWApiVehicles vehicle= data.get (position);
        holder.setData (vehicle);
    }

    @Override
    public int getItemCount () {
        return data.size ();
    }

    class VehiclesApiViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvModel, tvManufacturer, tvCost, tvLength, tvSpeed, tvCrew, tvPass, tvCargo, tvCons,
                    tvClass, tvCreated, tvEdited, tvUrl;

        VehiclesApiViewHolder (View itemView) {
            super (itemView);
            tvName = itemView.findViewById (R.id.tvVehName);
            tvModel = itemView.findViewById (R.id.tvVehModel);
            tvManufacturer = itemView.findViewById (R.id.tvVehManufacturer);
            tvCost = itemView.findViewById (R.id.tvVehCost);
            tvLength = itemView.findViewById (R.id.tvVehLength);
            tvSpeed = itemView.findViewById (R.id.tvVehSpeed);
            tvCrew = itemView.findViewById (R.id.tvVehCrew);
            tvPass = itemView.findViewById (R.id.tvVehPass);
            tvCargo = itemView.findViewById (R.id.tvVehCargo);
            tvCons = itemView.findViewById (R.id.tvVehCons);
            tvClass = itemView.findViewById (R.id.tvVehClass);
            tvCreated= itemView.findViewById (R.id.tvVehCreated);
            tvEdited= itemView.findViewById (R.id.tvVehEdited);
            tvUrl = itemView.findViewById (R.id.tvVehUrl);
        }

        void setData (SWApiVehicles vehicle) {
            tvName.setText (vehicle.name);
            tvModel.setText (vehicle.model);
            tvManufacturer.setText (vehicle.manufacturer);
            tvCost.setText (vehicle.cost_in_credits);
            tvLength.setText (vehicle.length);
            tvSpeed.setText (vehicle.max_atmosphering_speed);
            tvCrew.setText (vehicle.crew);
            tvPass.setText (vehicle.passengers);
            tvCargo.setText (vehicle.cargo_capacity);
            tvCons.setText (vehicle.consumables);
            tvClass.setText(vehicle.vehicle_class);
            tvCreated.setText (vehicle.created);
            tvEdited.setText (vehicle.edited);
            tvUrl.setText (vehicle.url);
        }
    }
}
