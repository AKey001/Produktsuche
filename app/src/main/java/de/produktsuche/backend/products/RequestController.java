package de.produktsuche.backend.products;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.produktsuche.backend.commons.JsonObjectRequestHandler;

public class RequestController {

    public void loadProductsWithFilter(HashMap<String, String> filter, Context context, String url,
                                       RecyclerView view) {

        JsonObjectRequestHandler jsonObjectRequestHandler = new JsonObjectRequestHandler();
        jsonObjectRequestHandler.executeRequestAndLoadView(context, url, filter, (response) -> {
            List<Product> products = new ArrayList<>();

            //TODO convert response to product list

            RecyclerView.Adapter adapter = new RecyclerViewAdapter(products);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            view.setLayoutManager(layoutManager);
            view.setAdapter(adapter);

        });

    }


}
