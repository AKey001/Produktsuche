package de.produktsuche.backend.commons;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HTTPRequest {

    public JSONObject request(String url, Context context) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Log.d("JSON Response", response.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("REQUEST ERROR" , error.toString());
                }
        );
        requestQueue.add(objectRequest);
        return null;
    }

}
