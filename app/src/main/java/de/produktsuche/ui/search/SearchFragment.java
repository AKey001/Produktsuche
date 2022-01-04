package de.produktsuche.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import de.produktsuche.R;
import de.produktsuche.backend.products.ListType;
import de.produktsuche.backend.products.Product;
import de.produktsuche.backend.products.RecyclerViewAdapter;

public class SearchFragment extends Fragment {
    private MaterialAlertDialogBuilder dialogbuilder;
    private View dialogView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.content_list, container, false);

        dialogbuilder = new MaterialAlertDialogBuilder(getContext());
        dialogbuilder.setTitle("Filter")
                .setNegativeButton("Abbrechen", (dialog, which) -> {
                        TextView view = (TextView) dialogView.findViewById(R.id.textView4);
                        Toast.makeText(getContext(), view.getText(), Toast.LENGTH_SHORT).show();
                })
                .setPositiveButton("Ok", (dialog, which) -> {
                    dialog.dismiss();
                });

        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setName("Fritz-Spritz Apfelschorle");
        product1.setStore("Kaufland Weinhübel");
        product1.setAmount(50);
        product1.setPrice(1.6);
        Product product2 = new Product();
        product2.setName("Fritz-Spritz Traubenschorle");
        product2.setStore("Kaufland Weinhübel");
        product2.setAmount(0);
        product2.setPrice(1.6);
        Product product3 = new Product();
        product3.setName("Fritz-Spritz Rhabarberschorle");
        product3.setStore("Kaufland Weinhübel");
        product3.setAmount(72);
        product3.setPrice(1.6);

        products.add(product1);
        products.add(product2);
        products.add(product3);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), products, ListType.SEARCH);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);




        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(getContext(), query + " searched", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_filter:

                dialogView = getActivity().getLayoutInflater().inflate(R.layout.popup, null, false);
                dialogbuilder.setView(dialogView);
                dialogbuilder.create();
                dialogbuilder.show();

                break;
        }
        return true;
    }
}