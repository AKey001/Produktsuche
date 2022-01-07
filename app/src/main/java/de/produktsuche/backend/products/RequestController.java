package de.produktsuche.backend.products;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.produktsuche.backend.commons.JsonRequestHandler;

public class RequestController {
    private JsonRequestHandler jsonRequestHandler;

    public RequestController() {
        this.jsonRequestHandler = new JsonRequestHandler();
    }

    public void loadProductsWithFilter(Activity activity,
                                       String url, RecyclerView view, ProgressBar progressBar, TextView info, ListType type) {

        Context context = activity.getApplicationContext();

        JsonRequestHandler jsonRequestHandler = new JsonRequestHandler();
        jsonRequestHandler.executeArrayRequest(context, url, new HashMap<>(), Request.Method.GET, (response) -> {

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Product>>() {}.getType();
            List<Product> products = gson.fromJson(response.toString(), listType);

            Log.d("VOLLEY REQUEST Response Products", response.toString());
            Log.d("VOLLEY REQUEST Response amount products", String.valueOf(products.size()));

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(activity, products, type);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            view.setLayoutManager(layoutManager);
            view.setAdapter(adapter);
            if (products.size() > 0) {
                info.setVisibility(View.INVISIBLE);
            }
            progressBar.setVisibility(View.GONE);
        });

    }


    public void login(HashMap<String, String> params, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        params.put("fcm_token", sharedPreferences.getString("fcm_token", ""));

        jsonRequestHandler.executeObjectRequest(context, "auth/login", params, Request.Method.POST, response -> {
            try {
                JSONObject jsonObject1 = new JSONObject(response.toString());

                sharedPreferences.edit().putString("access_token", jsonObject1.getString("access_token")).apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    // https://1codejam.dreamtexx.fun/api/

    public void register(HashMap<String, String> params, Context context) {
        jsonRequestHandler.executeObjectRequest(context, "auth/register", params, Request.Method.POST, response -> {
            Log.d("VOLLEY REQUEST Response", response.toString());
            try {
                JSONObject jsonObject = new JSONObject(response.toString());

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                sharedPreferences.edit().putString("AccountUUID", jsonObject.getString("id")).apply();

                login(params, context);

            } catch (JSONException e) {
                e.printStackTrace();

            }
        });

    }


    public void watchProduct(String accountID, Context context) {
        jsonRequestHandler.executeObjectRequest(context, "items/" + accountID + "/observations",
                new HashMap<>(), Request.Method.POST, response -> Log.d("VOLLEY REQUEST watch response",
                        response.toString()));
    }

    public void reservateProduct(String accountID, int count, Context context) {
        Map<String, Integer> params = new HashMap<>();
        params.put("count", count);
        jsonRequestHandler.executeObjectRequest(context, "items/" + accountID + "/reservations",
                params, Request.Method.POST, response -> Log.d("VOLLEY REQUEST watch response",
                        response.toString()));
    }

    public void removeFromWatchlist(Activity activity, String accountID, String productID) {
        String url = "accounts/" + accountID + "/observations/" + productID;

        Log.d("VOLLEY REQUEST remove path", url);
        jsonRequestHandler.executeObjectRequest(activity.getApplicationContext(), url, new HashMap<>(), Request.Method.DELETE, response -> {

        });
    }

    public void removeFromReservations(Activity activity, String accountID, String productID) {
        String url = "accounts/" + accountID + "/reservations/" + productID;
        Log.d("VOLLEY REQUEST remove path", url);

        jsonRequestHandler.executeObjectRequest(activity.getApplicationContext(), url, new HashMap<>(), Request.Method.DELETE, response -> {

        });
    }


}
