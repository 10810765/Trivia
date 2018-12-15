package com.example.marijn.trivia;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
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

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class HighscoresRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    // Notify the activity that instantiated the request through callback
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

        // Create a new request queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Create a JSON array request and add it to the queue
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://ide50-xalterego.cs50.io:8080/list", this, this);
        queue.add(jsonArrayRequest);
    }

    @Override // Handle on API error response
    public void onErrorResponse(VolleyError error) {
        try {
            if (error instanceof ServerError) {
                activity.gotHighscoresError("Server currently unavailable.");
                Log.d("gotHighscoresError", error.getMessage());
            } else {
                activity.gotHighscoresError("An unknown error occured :/");
                Log.d("gotHighscoresError",  error.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // Handle on API response
    public void onResponse(JSONArray response) {
        try {
            // Instantiate array list
            ArrayList<Highscores> highscoresArrayList = new ArrayList<>();

            // Loop over the JSON array and extract the strings in it
            for (int i = 0; i < response.length(); i++) {

                JSONObject resultsObject = response.getJSONObject(i);

                String name = resultsObject.getString("name");
                String score = resultsObject.getString("score");

                // Add the information to the Highscores array list
                highscoresArrayList.add(new Highscores(name, score));

            }
            // Pass the array list back to the activity that requested it
            activity.gotHighscores(highscoresArrayList);

        } catch (JSONException e) {
            // If an error occurs, print the error
            e.printStackTrace();

        }
    }

    // Method that attempts to put a achieved highscore in a database
    // With help from: https://stackoverflow.com/questions/29442977/
    public void putHighscore(final String name, final String score) {

        // Create a new request queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Create a JSON string request
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, "https://ide50-xalterego.cs50.io:8080/list", new Response.Listener<String>() {

            @Override // Handle on API error response
            public void onResponse(String response) {
                // If successful noting has to be done
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try { // If the url is down, return message
                    if (error instanceof ServerError) {
                        activity.gotHighscoresError("Server currently unavailable.");
                        Log.d(" putHighscoreError", error.getMessage());
                    } else { // Else return different message
                        activity.gotHighscoresError("Unable to submit your score");
                        Log.d(" putHighscoreError", error.getMessage());
                    }
                } catch (Exception e) {
                    // If an error occurs, print the error
                    e.printStackTrace();
                }
            }
        }) { // Hash map of the parameters
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("score", score);
                return params;
            }
        };
        // Add the reqeust to the queue
        queue.add(MyStringRequest);
    }
}