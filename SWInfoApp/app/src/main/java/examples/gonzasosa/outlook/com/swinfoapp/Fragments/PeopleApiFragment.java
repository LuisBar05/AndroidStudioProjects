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
import examples.gonzasosa.outlook.com.swinfoapp.R;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.DownloadAsyncTask;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.URLS;

public class PeopleApiFragment extends Fragment {
    ArrayList<SWApiPeople> foo = new ArrayList<>();
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

        new DownloadAsyncTask (this::parseJSON).execute (URLS.SW_API_PEOPLE_URL);

    }

    private void parseJSON (String json) {
        SWApiPeopleHeader people= new Gson ().fromJson (json, SWApiPeopleHeader.class);

        if (people == null) return;

        if (people.next != null) {
            new DownloadAsyncTask (this::parseJSON).execute (people.next);
        } else {
            foo.addAll (people.results);
            recyclerView.setAdapter (new PeopleApiAdapter (foo));
        }

        foo.addAll (people.results);
    }
}


class PeopleApiAdapter extends RecyclerView.Adapter<PeopleApiAdapter.PeopleApiViewHolder> {

    private ArrayList<SWApiPeople> data;

    PeopleApiAdapter (ArrayList<SWApiPeople> d) {
        data = d;
    }

    @NonNull
    @Override
    public PeopleApiViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.people_list, parent, false);

        return new PeopleApiViewHolder (view);
    }

    @Override
    public void onBindViewHolder (@NonNull PeopleApiViewHolder holder, int position) {
        SWApiPeople people= data.get (position);
        holder.setData (people);
    }

    @Override
    public int getItemCount () {
        return data.size ();
    }

    class PeopleApiViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvHeight, tvMass, tvHair, tvSkin, tvEye, tvBirth, tvGender, tvCreated, tvEdited, tvUrl;

        PeopleApiViewHolder (View itemView) {
            super (itemView);
            tvName = itemView.findViewById (R.id.tvPeopleName);
            tvHeight = itemView.findViewById (R.id.tvPeopleHeight);
            tvMass= itemView.findViewById (R.id.tvPeopleMass);
            tvHair = itemView.findViewById (R.id.tvPeopleHair);
            tvSkin = itemView.findViewById (R.id.tvPeopleSkin);
            tvEye= itemView.findViewById (R.id.tvPeopleEye);
            tvBirth = itemView.findViewById (R.id.tvPeopleBirth);
            tvGender = itemView.findViewById (R.id.tvPeopleGender);
            tvCreated= itemView.findViewById (R.id.tvPeopleCreated);
            tvEdited= itemView.findViewById (R.id.tvPeopleEdited);
            tvUrl = itemView.findViewById (R.id.tvPeopleUrl);
        }

        void setData (SWApiPeople people) {
            tvName.setText (people.name);
            tvHeight.setText (people.height);
            tvMass.setText (people.mass);
            tvHair.setText (people.hair_color);
            tvSkin.setText (people.skin_color);
            tvEye.setText (people.eye_color);
            tvBirth.setText (people.birth_year);
            tvGender.setText (people.gender);
            tvCreated.setText (people.created);
            tvEdited.setText (people.edited);
            tvUrl.setText (people.url);
        }
    }
}
