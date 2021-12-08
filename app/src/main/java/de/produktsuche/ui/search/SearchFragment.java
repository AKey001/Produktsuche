package de.produktsuche.ui.search;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import de.produktsuche.R;

public class SearchFragment extends Fragment {
    private MaterialAlertDialogBuilder dialogbuilder;
    private View dialogView;
    private LayoutInflater dialogInflater;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.content_list, container, false);


        dialogInflater = getLayoutInflater();
        dialogView = dialogInflater.inflate(R.layout.popup, null);
        dialogbuilder = new MaterialAlertDialogBuilder(getContext());
        dialogbuilder.setTitle("Filter")
                .setNegativeButton("Abbrechen", (dialog, which) -> {})
                .setPositiveButton("Ok", (dialog, which) -> {});
        dialogbuilder.setView(dialogView);
        dialogbuilder.create();
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
                Toast.makeText(getContext(), query + " searched", Toast.LENGTH_SHORT).show();
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

                dialogbuilder.show();

                break;
        }
        return true;
    }
}