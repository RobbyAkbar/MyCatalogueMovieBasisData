package es.esy.android_inyourhand.mycataloguemoviebasisdata;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.models.MovieItem;

import static es.esy.android_inyourhand.mycataloguemoviebasisdata.BuildConfig.API_KEY;
import static es.esy.android_inyourhand.mycataloguemoviebasisdata.BuildConfig.BASE_URL;

/**
  Created by robby on 01/01/18.
 */

public class MovieAsyncTask extends AsyncTaskLoader<ArrayList<MovieItem>> {

    private ArrayList<MovieItem> movieItems;
    private boolean aBoolean = false;
    private String lang, menu, extra;

    public MovieAsyncTask(final Context context, String menu, String language, String extra) {
        super(context);
        onContentChanged();
        this.menu = menu;
        this.lang =language;
        this.extra = extra;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()){
            forceLoad();
        } else if (aBoolean) {
            deliverResult(movieItems);
        }
    }

    @Override
    public void deliverResult(final ArrayList<MovieItem> data) {
        movieItems = data;
        aBoolean = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (aBoolean){
            movieItems = null;
            aBoolean =false;
        }
    }

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient syncHttpClient = new SyncHttpClient();
        final ArrayList<MovieItem> movieItems = new ArrayList<>();
        final String url = BASE_URL+menu+"?language="+lang+"&api_key="+API_KEY+extra;
        syncHttpClient.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
                Log.d("My Movie", url);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray movie = jsonObject.getJSONArray("results");
                    int total = jsonObject.getInt("total_results");
                    if(total > 0){
                        for (int i=0;i<movie.length();i++){
                            JSONObject list = movie.getJSONObject(i);
                            MovieItem movieItem = new MovieItem(list);
                            movieItems.add(movieItem);
                        }
                        Log.d("My Movie", result);
                    } else {
                        Log.d("My Movie", "Data film tidak ditemukan");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("My movie", "Error", error);
            }
        });
        return movieItems;
    }
}
