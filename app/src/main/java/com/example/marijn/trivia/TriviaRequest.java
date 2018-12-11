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

public class TriviaRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    public interface Callback {
        void gotTrivia(ArrayList<Trivia> trivia);
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://opentdb.com/api.php?amount=10&category=18&type=multiple", null, this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotTriviaError(error.getMessage());
        Log.d("goTriviaError", error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {


        try {

            ArrayList<Trivia> triviaArrayList = new ArrayList<>();

            JSONArray resultsArray = response.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject resultsObject = resultsArray.getJSONObject(i);

                String difficulty = resultsObject.getString("difficulty");
                String question = resultsObject.getString("question");
                String correctAnswer = resultsObject.getString("correct_answer");
                String ID = String.valueOf(i);

                ArrayList<String> incAnsArrayList = new ArrayList<>();

                JSONArray incAnsArray = resultsObject.getJSONArray("incorrect_answers");

                for (int j = 0; j < incAnsArray.length(); j++) {
                    incAnsArrayList.add(incAnsArray.getString(j));
                }

                triviaArrayList.add(new Trivia(difficulty, question, correctAnswer, incAnsArrayList, ID));

            } activity.gotTrivia(triviaArrayList);

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
