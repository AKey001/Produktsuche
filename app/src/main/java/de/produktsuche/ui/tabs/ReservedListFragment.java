package de.produktsuche.ui.tabs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import de.produktsuche.R;
import de.produktsuche.backend.products.ListType;
import de.produktsuche.backend.products.RequestController;

public class ReservedListFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.content_list, container, false);

        TextView info = root.findViewById(R.id.info);
        info.setText("Noch nichts reserviert. \n Wechsle in den Suchtab, um Produkte hinzuzufügen.");
        info.setVisibility(View.VISIBLE);
        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        RequestController requestController = new RequestController();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String account = sharedPreferences.getString("AccountUUID", null);

        if (account == null) {
            NavHostFragment.findNavController(ReservedListFragment.this)
                    .navigate(R.id.action_navigation_reserved_list_to_navigation_login);
        } else {
            String url = "accounts/" + account + "/reservations";
            Log.d("REQUEST url", url);
            requestController.loadProductsWithFilter(requireActivity(), url, recyclerView, progressBar, info, ListType.RESERVE);
        }


        return root;
    }


}
