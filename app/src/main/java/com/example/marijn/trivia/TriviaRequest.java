package com.example.marijn.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class TriviaRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    // Notify the activity that instantiated the request through callback
    public interface Callback {
        void gotTrivia(ArrayList<Trivia> trivia);
        void gotTriviaError(String message);
    }

    // Create context object to send internet requests
    public TriviaRequest(Context context) {
        this.context = context;
    }

    // Method used to retrieve questions from API
    public void getTrivia(Callback activity, String category) {
        this.activity = activity;

        // Create a new request queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Create a JSON object request and add it to the queue
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://opentdb.com/api.php?amount=10&category="+category+"&type=multiple", null, this, this);
        queue.add(jsonObjectRequest);
    }

    @Override // Handle on API error response
    public void onErrorResponse(VolleyError error) {
        activity.gotTriviaError(error.getMessage());
        Log.d("goTriviaError", error.getMessage());
    }

    @Override // Handle on API response
    public void onResponse(JSONObject response) {
        try {
            // Instantiate array list
            ArrayList<Trivia> triviaArrayList = new ArrayList<>();

            JSONArray resultsArray = response.getJSONArray("results");

            // Loop over the JSON array and extract the strings in it
            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject resultsObject = resultsArray.getJSONObject(i);

                String difficulty = resultsObject.getString("difficulty");
                String question = resultsObject.getString("question");
                String correctAnswer = resultsObject.getString("correct_answer");
                String ID = String.valueOf(i);

                // Instantiate a second array list (used for the wrong answers per question)
                ArrayList<String> incAnsArrayList = new ArrayList<>();

                JSONArray incAnsArray = resultsObject.getJSONArray("incorrect_answers");

                // Loop over the incorrect answer JSON array and extract the strings
                for (int j = 0; j < incAnsArray.length(); j++) {
                    // Add the incorrect answers to the incAnsArrayList
                    incAnsArrayList.add(incAnsArray.getString(j));
                }

                // Add the information to the Trivia array list
                triviaArrayList.add(new Trivia(difficulty, question, correctAnswer, incAnsArrayList, ID));

            }
            // Pass the array list back to the activity that requested it
            activity.gotTrivia(triviaArrayList);

        } catch (JSONException e) {
            // If an error occurs, print the error
            e.printStackTrace();
        }
    }
}
