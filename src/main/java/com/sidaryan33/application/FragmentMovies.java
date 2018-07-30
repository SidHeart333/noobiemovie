package com.sidaryan33.application;


import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.sidaryan33.application.Keys.EndpointBoxOffice.*;
import static com.android.volley.Response.*;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMovies#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMovies extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<Movies> listMovies = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView listMoviesHits;
    private AdapterMovies adapterMovies;
    SwipeRefreshLayout mSwipe;

    public FragmentMovies() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMovies.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMovies newInstance(String param1, String param2) {
        FragmentMovies fragment = new FragmentMovies();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        sendJSONRequest();

    }

    private void sendJSONRequest() {

        Toast.makeText(getActivity(), "Getting Started", Toast.LENGTH_SHORT).show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://api.themoviedb.org/3/movie/popular?api_key=cc4b67c52acb514bdf4931f7cedfd12b",
                (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.e("DDD", response.toString());
                try {
                    try {
                        listMovies = parseJSONRequest(response);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    adapterMovies.setMovies(listMovies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error In Volley", error.getMessage());
            }
        });
        requestQueue.add(request);

    }

    private ArrayList<Movies> parseJSONRequest(JSONObject response) throws JSONException, ParseException {
        ArrayList<Movies> listMovies = new ArrayList<>();
        JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);

        long id = -1;
        String title = "NA";
        String releaseDate = "NA";
        double rating = -1.0;
        double popular = -1.0;
        String synopsis = "NA";
        String urlThumbnail = "NA";
        String urlPoster = "NA";

        for (int i = 0; i < arrayMovies.length(); i++) {
            JSONObject currentMovies = arrayMovies.getJSONObject(i);
            if (currentMovies.has(KEY_ID) && !currentMovies.isNull(KEY_ID)) {
                id = currentMovies.getLong(KEY_ID);
            }
            if (currentMovies.has(KEY_TITLE) && !currentMovies.isNull(KEY_TITLE)) {
                title = currentMovies.getString(KEY_TITLE);
            }
            if (currentMovies.has(KEY_RELEASE_DATES) && !currentMovies.isNull(KEY_RELEASE_DATES)) {
                releaseDate = currentMovies.getString(KEY_RELEASE_DATES);
            }
            if (currentMovies.has(KEY_RATINGS) && !currentMovies.isNull(KEY_RATINGS)) {
                rating = currentMovies.getDouble(KEY_RATINGS);
            }
            if (currentMovies.has(KEY_POPULARITY) && !currentMovies.isNull(KEY_POPULARITY)) {
                popular = currentMovies.getDouble(KEY_POPULARITY);
            }
            if (currentMovies.has(KEY_SYNOPSIS) && !currentMovies.isNull(KEY_SYNOPSIS)) {
                synopsis = currentMovies.getString(KEY_SYNOPSIS);
            }
            if (currentMovies.has(KEY_POSTERS) && !currentMovies.isNull(KEY_POSTERS)) {
                urlPoster = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + currentMovies.getString(KEY_POSTERS);
            }
            if (currentMovies.has(KEY_THUMBNAIL) && !currentMovies.isNull(KEY_THUMBNAIL)) {
                urlThumbnail = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + currentMovies.getString(KEY_THUMBNAIL);
            }
            Movies movies = new Movies();
            movies.setId(id);
            movies.setTitle(title);
            Date date = null;
            try {
                date = dateFormat.parse(releaseDate);
            } catch (ParseException e) {
            }
            movies.setReleaseDate(date);
            movies.setPopularity(popular);
            movies.setRatings(rating);
            movies.setSynopsis(synopsis);
            movies.setUrlPoster(urlPoster);
            movies.setUrlThumbnail(urlThumbnail);

            if (id != -1 && !title.equals("NA")) {
                listMovies.add(movies);
            }
        }
        return listMovies;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        listMoviesHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        listMoviesHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterMovies = new AdapterMovies(getActivity());
        listMoviesHits.setAdapter(adapterMovies);
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipeMovieHits);
        sendJSONRequest();
        return view;

    }

}
