package es.esy.android_inyourhand.mycataloguemoviebasisdata.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import es.esy.android_inyourhand.mycataloguemoviebasisdata.MainActivity;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.R;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.adapters.FavouriteAdapter;

import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    private Toolbar toolbar;
    private MainActivity mainActivity;
    private Cursor list;
    private FavouriteAdapter adapter;
    private RecyclerView rvNotes;
    private ProgressBar progressBar;

    public FavouriteFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.favourite_movie));
        mainActivity.setSupportActionBar(toolbar);

        rvNotes = view.findViewById(R.id.recycler_view);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNotes.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progress_bar);

        adapter = new FavouriteAdapter(getContext());
        adapter.setListMovies(list);
        rvNotes.setAdapter(adapter);

        new LoadMovieAsync().execute();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync().execute();
    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);

            list = notes;
            adapter.setListMovies(list);
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0){
                showSnackbarMessage();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showSnackbarMessage(){
        Snackbar.make(rvNotes, R.string.no_favourite, Snackbar.LENGTH_SHORT).show();
    }

}
