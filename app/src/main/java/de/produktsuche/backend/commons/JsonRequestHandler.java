package de.produktsuche.backend.commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class JsonRequestHandler {
    private final String URL = "http://193.174.103.48:8080/api/";

    public<T> void executeObjectRequest(Context context, String path, Map<String, T> param, int httpMethod,
                                        RequestOperationHandler requestOperationHandler,
                                        RequestOperationHandler requestOperationHandlerError) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(httpMethod, URL + path, new JSONObject(param),
                response -> {
                    Log.d("VOLLEY REQUEST Response", response.toString());
                    requestOperationHandler.execute(response);
                },
                error -> {
                    requestOperationHandlerError.execute(error);
                    error.printStackTrace();
                    try {
                        Log.d("VOLLEY REQUEST ERROR" , new String(error.networkResponse.data, StandardCharsets.UTF_8));
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

    public  void executeArrayRequest(Context context, String path, int httpMethod,
                                     RequestOperationHandler requestOperationHandler, RequestOperationHandler requestOperationHandlerError) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(httpMethod, URL + path, null,
                response -> {
                    Log.d("VOLLEY REQUEST Response", response.toString());
                    requestOperationHandler.execute(response);
                },
                error -> {
                    requestOperationHandlerError.execute(error);
                    error.printStackTrace();
                    try {
                        Log.d("VOLLEY REQUEST ERROR" , new String(error.networkResponse.data, StandardCharsets.UTF_8));
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
