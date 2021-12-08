package de.produktsuche.backend.commons;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

public class JsonObjectRequestHandler {

    public  void executeRequestAndLoadView(Context context, String url, Map<String, String> filter,
                                           RequestOperationHandler requestOperationHandler) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(filter),
                response -> {
                    Log.d("JSON Response", response.toString());
                    requestOperationHandler.execute(response);
                },
                error -> {
                    Log.d("REQUEST ERROR" , error.toString());
                    //TODO Alert Error

                }
        );
        requestQueue.add(objectRequest);

    }

}
