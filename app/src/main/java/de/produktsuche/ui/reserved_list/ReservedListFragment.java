package de.produktsuche.ui.reserved_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.produktsuche.R;

import de.produktsuche.backend.products.Product;
import de.produktsuche.backend.products.ReservedlistRecyclerViewAdapter;

public class ReservedListFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.content_list, container, false);

        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setName("Rauch Eistee Pfirsich");
        product1.setStore("Kaufland Weinhübel");
        product1.setAmount(6);
        product1.setPrice(2.99);
        Product product2 = new Product();
        product2.setName("Red Bull Winteredition");
        product2.setStore("Kaufland Weinhübel");
        product2.setAmount(0);
        product2.setPrice(1.71);
        Product product3 = new Product();
        product3.setName("Fritz-Spritz Rhabarberschorle");
        product3.setStore("Kaufland Weinhübel");
        product3.setAmount(6);
        product3.setPrice(1.6);

        products.add(product1);
        products.add(product2);
        products.add(product3);


        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        ReservedlistRecyclerViewAdapter adapter = new ReservedlistRecyclerViewAdapter(getActivity(), products);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return root;
    }


}
