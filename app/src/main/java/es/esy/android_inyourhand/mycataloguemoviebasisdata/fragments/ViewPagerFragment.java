package es.esy.android_inyourhand.mycataloguemoviebasisdata.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Locale;

import es.esy.android_inyourhand.mycataloguemoviebasisdata.MovieAsyncTask;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.R;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.adapters.MovieAdapter;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.models.MovieItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieAdapter = new MovieAdapter(getActivity());
        String text = getArguments().getString("Menu");
        if (text != null) {
            if (text.equals("movie/now_playing")){
                getActivity().getSupportLoaderManager().initLoader(0, null, this);
            } else if (text.equals("movie/upcoming")){
                getActivity().getSupportLoaderManager().initLoader(1, null, this);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String text = getArguments().getString("Menu");
        if (text != null) {
            if (text.equals("movie/now_playing")){
                getActivity().getSupportLoaderManager().restartLoader(0, null, this);
            } else if (text.equals("movie/upcoming")){
                getActivity().getSupportLoaderManager().restartLoader(1, null, this);
            }
        }
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
        String language = Locale.getDefault().getLanguage();
        String text = getArguments().getString("Menu");
        return new MovieAsyncTask(getActivity(), text, language, "");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        movieAdapter.setMovieItems(data);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        movieAdapter.clearMovie();
    }
}
