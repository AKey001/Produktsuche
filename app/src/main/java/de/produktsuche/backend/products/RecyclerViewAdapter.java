package de.produktsuche.backend.products;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import de.produktsuche.R;
import de.produktsuche.backend.commons.PriceConverter;
import de.produktsuche.ui.common.SpinnerAdapter;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Product> items;
    private final PriceConverter priceConverter;
    private MaterialAlertDialogBuilder dialogBuilder;
    private final Activity activity;
    private final ListType type;
    private final RequestController requestController;

    public RecyclerViewAdapter(Activity activity, List<Product> items, ListType type) {
        this.items = items;
        this.priceConverter = new PriceConverter();
        this.activity = activity;
        this.type = type;
        this.requestController = new RequestController();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewProduct;
        private final TextView textViewStore;
        private final TextView textViewAvailability;
        private final TextView textViewPrice;
        private final CardView card;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewProduct = itemView.findViewById(R.id.textViewProduct);
            textViewStore = itemView.findViewById(R.id.textViewShop);
            textViewAvailability = itemView.findViewById(R.id.textViewAvailability);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            card = itemView.findViewById(R.id.card);
        }

        public TextView getTextViewProduct() {
            return textViewProduct;
        }

        public TextView getTextViewStore() {
            return textViewStore;
        }

        public TextView getTextViewAvailability() {
            return textViewAvailability;
        }

        public TextView getTextViewPrice() {
            return textViewPrice;
        }

        public CardView getCard() {
            return card;
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Product product = items.get(position);

        holder.getTextViewProduct().setText(product.getName());
        String store = product.getMarket_name() + " " + product.getMarket_location();
        holder.getTextViewStore().setText(store);

        if (type == ListType.RESERVE) {
            String status = "ausstehend";
            switch (product.getStatus()) {
                case "accepted":
                    status = product.getCount() + " reserviert";
                    break;
                case "rejected":
                    status = "abgelehnt";
                    break;
            }
            holder.getTextViewAvailability().setText(status);
        } else {
            String availability = "nicht " + type.getAdj();
            holder.getTextViewAvailability().setText(availability);
            if (product.getQuantity_available() > 0) {
                String noAvailability = product.getQuantity_available() + " " + type.getAdj();
                holder.getTextViewAvailability().setText(noAvailability);
            }
        }

        String price = priceConverter.convertPrice(product.getPrice());
        holder.getTextViewPrice().setText(price);

        holder.getCard().setOnClickListener(v -> {
            dialogBuilder = new MaterialAlertDialogBuilder(holder.getCard().getContext());

            if (type == ListType.SEARCH) {
                dialogBuilder.setTitle(product.getName());
                String[] options = {"beobachten", "reservieren"};
                dialogBuilder.setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            requestController.watchProduct(product.getId(), activity.getApplicationContext());
                            break;
                        case 1:
                            View dialogView = activity.getLayoutInflater().inflate(R.layout.popup_input, null, false);
                            MaterialAlertDialogBuilder dialogBuilder1 = new MaterialAlertDialogBuilder(activity);
                            dialogBuilder1.setView(dialogView);

                            List<String> counts = new ArrayList<>();
                            for (int i = 0; i < product.getQuantity_available(); i++) {
                                counts.add(String.valueOf(i + 1));
                            }

                            Spinner spinner = dialogView.findViewById(R.id.spinner);
                            SpinnerAdapter adapter = new SpinnerAdapter(activity, R.layout.spinner_item, R.id.item, counts);
                            spinner.setAdapter(adapter);

                            dialogBuilder1.setTitle("Reservierung");
                            dialogBuilder1.setMessage("Wie viele Artikel möchten Sie reservieren?");
                            dialogBuilder1.setNegativeButton("Abbrechen", null);
                            dialogBuilder1.setPositiveButton("Reservieren", (dialog1, which1) -> {

                                int count = spinner.getSelectedItemPosition();
                                count++;

                                requestController.reservateProduct(product.getId(), count, activity.getApplicationContext());

                            });
                            dialogBuilder1.create();
                            dialogBuilder1.show();
                            break;
                    }
                });
            } else {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                String account = sharedPreferences.getString("AccountUUID", null);

                dialogBuilder.setMessage(type.getQuestion());
                dialogBuilder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());
                dialogBuilder.setPositiveButton("Entfernen", (dialog, which) -> {
                    switch (type) {
                        case WATCH:
                            requestController.removeFromWatchlist(activity, account, product.getId());
                            items.remove(position);
                            notifyItemRemoved(position);
                            break;
                        case RESERVE:
                            requestController.removeFromReservations(activity, account, product.getId());
                            items.remove(position);
                            notifyItemRemoved(position);
                            break;
                    }
                });
            }
            dialogBuilder.create();
            dialogBuilder.show();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
