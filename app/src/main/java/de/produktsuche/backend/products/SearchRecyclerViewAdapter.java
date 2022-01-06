package de.produktsuche.backend.products;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import de.produktsuche.R;
import de.produktsuche.backend.commons.PriceConverter;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {
    private final List<Product> items;
    private PriceConverter priceConverter;
    private View dialogView;
    private MaterialAlertDialogBuilder dialogbuilder;
    private final Activity activity;
    

    public SearchRecyclerViewAdapter(Activity activity, List<Product> products) {
        this.activity = activity;
        items = products;
        this.priceConverter = new PriceConverter();
        
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = items.get(position);

        holder.getTextViewProduct().setText(product.getName());
        holder.getTextViewStore().setText(product.getStore());
        if (product.getAvailability() > 0) {
            holder.getTextViewAvailability().setText(product.getAvailability() + " reserviert");
        } else {
            holder.getTextViewAvailability().setText("noch nicht bestätigt");
        }
        String price = priceConverter.convertPrice(product.getPrice());
        holder.getTextViewPrice().setText(price);

        holder.getCard().setOnClickListener(v -> {
            dialogbuilder = new MaterialAlertDialogBuilder(holder.getCard().getContext());
            dialogbuilder.setNegativeButton("Abbrechen", (dialog, which) -> {dialog.dismiss();});
            dialogView = activity.getLayoutInflater().inflate(R.layout.search_popup, null, false);
            dialogbuilder.setView(dialogView);
            dialogbuilder.create();
            dialogbuilder.show();
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
