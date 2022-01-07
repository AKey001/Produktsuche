package de.produktsuche.backend.products;

import androidx.annotation.NonNull;

public class Product {
    private String id;
    private String market_id;
    private String name;
    private double price;
    private int quantity_available;
    private int quantity_reserved;
    private String market_name;
    private String market_location;
    private String status;
    private int count;

    public String getId() {
        return id;
    }

    public String getMarket_id() {
        return market_id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity_available() {
        return quantity_available;
    }

    public int getQuantity_reserved() {
        return quantity_reserved;
    }

    public String getMarket_name() {
        return market_name;
    }

    public String getMarket_location() {
        return market_location;
    }

    public String getStatus() {
        return status;
    }

    public int getCount() {
        return count;
    }

    @NonNull
    @Override
    public String toString() {
        return "id=" + id +
                " market_id=" + market_id +
                " name=" + name +
                " price=" + price +
                " quantity_available=" + quantity_available +
                " quantity_reserved=" + quantity_reserved +
                " market_name=" + market_name +
                " market_location=" + market_location;
    }



}
