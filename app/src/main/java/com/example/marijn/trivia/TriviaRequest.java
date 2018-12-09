package com.example.marijn.trivia;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TriviaRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    public interface Callback {
        void gotTrivia(ArrayList<String> questions);
        void gotTriviaError(String message);
    }

    // Create context object to send internet requests
    public TriviaRequest(Context context) {
        this.context = context;
    }
    // Method used to retrieve questions from API
    public void getTrivia(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/categories", null, this, this);
        queue.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        ArrayList<String> questionsArrayList = new ArrayList<>();

        try {

            JSONArray categories = response.getJSONArray("questions");

            for (int i = 0; i < categories.length(); i++) {
                questionsArrayList.add(categories.getString(i));
                activity.gotTrivia(questionsArrayList);
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
