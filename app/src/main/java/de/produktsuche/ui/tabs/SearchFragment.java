package de.produktsuche.ui.tabs;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import de.produktsuche.R;
import de.produktsuche.backend.products.ListType;
import de.produktsuche.backend.products.RequestController;

public class SearchFragment extends Fragment {
    private MaterialAlertDialogBuilder dialogbuilder;
    private View dialogView;
    private RecyclerView recyclerView;
    private TextView info;
    RequestController requestController;
    private String city = null;
    private String lowest = null;
    private String highest = null;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.content_list, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        info = root.findViewById(R.id.info);
        info.setText("Noch nichts gesucht.\nSuche nach Produkten, indem du oben auf die Lupe klickst.");
        info.setVisibility(View.VISIBLE);
        progressBar = root.findViewById(R.id.progressBar);

        requestController = new RequestController();

        dialogbuilder = new MaterialAlertDialogBuilder(requireContext());
        dialogbuilder.setTitle("Filter")
                .setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Ok", (dialog, which) -> {
                    TextView city = dialogView.findViewById(R.id.query);
                    TextView lowest = dialogView.findViewById(R.id.lowest);
                    TextView highest = dialogView.findViewById(R.id.highest);

                    if (city == null && city.getText().toString().trim().equals("")) {
                        this.city = null;
                    } else {
                        this.city =  city.getText().toString();
                    }
                    if (city.length() < 1 && lowest.getText().toString().trim().equals("")) {
                        this.lowest = null;
                    } else {
                        this.lowest = lowest.getText().toString();
                    }
                    if (city.length() < 1 && highest.getText().toString().trim().equals("")) {
                        this.highest = null;
                    } else {
                        this.highest = highest.getText().toString();
                    }
                });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                String url = "items?";

                Log.d("REQUEST", "1");

                if (query.equals("")) {
                    url += "query=.";
                } else  {
                    try {
                        url += "query=" + URLEncoder.encode(query, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        url += "query=.";
                    }
                }
                if (city != null) {
                    try {
                        url += "&city=" + URLEncoder.encode(city, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                if (lowest != null) {
                    try {
                        url += "&lowest=" + URLEncoder.encode(lowest, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                if (highest != null) {
                    try {
                        url += "&highest=" + URLEncoder.encode(highest, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("REQUEST", url);

                requestController.loadProductsWithFilter(requireActivity(), url, recyclerView, progressBar, info, ListType.SEARCH);
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

        if (item.getItemId() == R.id.action_filter) {
            dialogView = requireActivity().getLayoutInflater().inflate(R.layout.popup_filter, null, false);
            dialogbuilder.setView(dialogView);
            dialogbuilder.create();
            dialogbuilder.show();
        }
        return true;
    }
}