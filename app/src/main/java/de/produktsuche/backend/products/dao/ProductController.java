package de.produktsuche.backend.products.dao;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class ProductController {

    public void loadProductsWithFilter(HashMap<String, String> filter, Context context, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(filter),
                response -> {
                    Log.d("JSON Response", response.toString());
                },
                error -> {
                    Log.d("REQUEST ERROR" , error.toString());
                }
        );
        requestQueue.add(objectRequest);
    }


}
