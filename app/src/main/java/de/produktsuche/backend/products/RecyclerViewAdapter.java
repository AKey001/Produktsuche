package de.produktsuche.backend.products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.produktsuche.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Product> items;

    public RecyclerViewAdapter(List<Product> products) {
        items = products;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewProduct;
        private final TextView textViewStore;
        private final TextView textViewAvailability;
        private final TextView textViewPrice;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewProduct = itemView.findViewById(R.id.textViewProduct);
            textViewStore = itemView.findViewById(R.id.textViewShop);
            textViewAvailability = itemView.findViewById(R.id.textViewAvailability);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
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
        holder.getTextViewPrice().setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
