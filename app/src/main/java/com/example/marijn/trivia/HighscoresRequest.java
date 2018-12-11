package com.example.marijn.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HighscoresRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    public interface Callback {
        void gotHighscores(ArrayList<Highscores> highscores);
        void gotHighscoresError(String message);
    }

    // Create context object to send internet requests
    public HighscoresRequest(Context context) {
        this.context = context;
    }

    // Method used to retrieve scores from API
    public void getHighscores(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://ide50-xalterego.cs50.io:8080/list", this, this);
        queue.add(jsonArrayRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotHighscoresError(error.getMessage());
        Log.d("gotHighscoresError", error.getMessage());
    }

    @Override
    public void onResponse(JSONArray response) {

        try {

            ArrayList<Highscores> highscoresArrayList = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {

                JSONObject resultsObject = response.getJSONObject(i);

                String name = resultsObject.getString("name");
                String score = resultsObject.getString("score");

                highscoresArrayList.add(new Highscores(name, score));

            } activity.gotHighscores(highscoresArrayList);

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public void putHighscore(final String name, final String score) {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, "https://ide50-xalterego.cs50.io:8080/list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Nothing
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("putHighscoreError", error.getMessage());
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("score", score);
                return params;
            }
        };

        queue.add(MyStringRequest);
    }
}