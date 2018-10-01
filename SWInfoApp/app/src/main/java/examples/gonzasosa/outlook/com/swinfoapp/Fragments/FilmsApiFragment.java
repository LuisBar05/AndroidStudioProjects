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

import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiFilms;
import examples.gonzasosa.outlook.com.swinfoapp.Models.SWApiFilmsHeader;
import examples.gonzasosa.outlook.com.swinfoapp.R;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.DownloadAsyncTask;
import examples.gonzasosa.outlook.com.swinfoapp.Utils.URLS;

public class FilmsApiFragment extends Fragment {
    ArrayList<SWApiFilms> foo = new ArrayList<>();
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

        new DownloadAsyncTask (this::parseJSON).execute (URLS.SW_API_FILMS);

    }

    private void parseJSON (String json) {
        SWApiFilmsHeader film= new Gson ().fromJson (json, SWApiFilmsHeader.class);

        if (film == null) return;

        if (film.next != null) {
            new DownloadAsyncTask (this::parseJSON).execute (film.next);
        } else {
            foo.addAll (film.results);
            recyclerView.setAdapter (new FilmsApiAdapter (foo));
        }

        foo.addAll (film.results);
    }
}


class FilmsApiAdapter extends RecyclerView.Adapter<FilmsApiAdapter.FilmsApiViewHolder> {

    private ArrayList<SWApiFilms> data;

    FilmsApiAdapter (ArrayList<SWApiFilms> d) {
        data = d;
    }

    @NonNull
    @Override
    public FilmsApiViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.films_list, parent, false);

        return new FilmsApiViewHolder (view);
    }

    @Override
    public void onBindViewHolder (@NonNull FilmsApiViewHolder holder, int position) {
        SWApiFilms film= data.get (position);
        holder.setData (film);
    }

    @Override
    public int getItemCount () {
        return data.size ();
    }

    class FilmsApiViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvEpisode, tvCrawl, tvDirector, tvProducer, tvRelease, tvCreated, tvEdited, tvUrl;

        FilmsApiViewHolder (View itemView) {
            super (itemView);
            tvTitle = itemView.findViewById (R.id.tvFilmTitle);
            tvEpisode = itemView.findViewById (R.id.tvFilmEp);
            tvCrawl = itemView.findViewById (R.id.tvFilmCrawl);
            tvDirector = itemView.findViewById (R.id.tvFilmDir);
            tvProducer = itemView.findViewById (R.id.tvFilmProd);
            tvRelease = itemView.findViewById (R.id.tvFilmRelease);
            tvCreated= itemView.findViewById (R.id.tvFilmCreated);
            tvEdited= itemView.findViewById (R.id.tvFilmEdited);
            tvUrl = itemView.findViewById (R.id.tvFilmUrl);
        }

        void setData (SWApiFilms film) {
            tvTitle.setText (film.title);
            tvEpisode.setText (film.episode_id);
            tvCrawl.setText (film.opening_crawl);
            tvDirector.setText (film.director);
            tvProducer.setText (film.producer);
            tvRelease.setText (film.release_date);
            tvCreated.setText (film.created);
            tvEdited.setText (film.edited);
            tvUrl.setText (film.url);
        }
    }
}
