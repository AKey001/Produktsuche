package de.produktsuche.backend.commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JsonRequestHandler {

    public<T> void executeObjectRequest(Context context, String url, Map<String, T> param,
                                      int httpMethod, RequestOperationHandler requestOperationHandler) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(httpMethod, "https://produktsuche.dreamtexx.fun/api/" + url, new JSONObject(param),
                response -> {
                    Log.d("VOLLEY REQUEST Response", response.toString());
                    requestOperationHandler.execute(response);
                },
                error -> {
                    error.printStackTrace();
                    try {
                        Log.d("VOLLEY REQUEST ERROR" , new String(error.networkResponse.data, StandardCharsets.UTF_8));
                        Toast.makeText(context, "Verbindung fehlgeschlagen", Toast.LENGTH_LONG).show();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String accessToken = sharedPreferences.getString("access_token", "");

                Map<String, String> header = new HashMap<>();
                header.put("Authorization" ,"Bearer " + accessToken);
                return header;
            }
        };
        requestQueue.add(objectRequest);

    }

    public  void executeArrayRequest(Context context, String url, Map<String, String> param,
                                int httpMethod, RequestOperationHandler requestOperationHandler) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(httpMethod, "https://produktsuche.dreamtexx.fun/api/" + url, null,
                response -> {
                    Log.d("VOLLEY REQUEST Response", response.toString());
                    requestOperationHandler.execute(response);
                },
                error -> {
                    error.printStackTrace();
                    try {
                        Log.d("VOLLEY REQUEST ERROR" , new String(error.networkResponse.data, StandardCharsets.UTF_8));
                        Toast.makeText(context, "Verbindung fehlgeschlagen", Toast.LENGTH_LONG).show();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String accessToken = sharedPreferences.getString("access_token", "");

                Map<String, String> header = new HashMap<>();
                header.put("Authorization" ,"Bearer " + accessToken);
                return header;
            }
        };
        requestQueue.add(objectRequest);

    }



}
