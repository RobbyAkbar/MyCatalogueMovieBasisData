package es.esy.android_inyourhand.mycataloguemoviebasisdata.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Locale;

import es.esy.android_inyourhand.mycataloguemoviebasisdata.MainActivity;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.MovieAsyncTask;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.R;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.adapters.MovieAdapter;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.models.MovieItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    private Toolbar toolbar;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private String search_title;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupNavigationDrawer(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieAdapter = new MovieAdapter(getActivity());
        search_title = "&query=Captain America";
        getActivity().getSupportLoaderManager().initLoader(2, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.search_movie));
        mainActivity.setSupportActionBar(toolbar);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(2, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setMaxWidth(R.dimen.search_max_width);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search_title = "&query="+query;
                submit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void submit() {
        getActivity().getSupportLoaderManager().restartLoader(2, null, this);
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
        String language = Locale.getDefault().getLanguage();
        String text = "search/movie";
        return new MovieAsyncTask(getActivity(), text, language, search_title);
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
