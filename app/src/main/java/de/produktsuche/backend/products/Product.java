package de.produktsuche.backend.products;

import androidx.annotation.NonNull;

public class Product {
    private String id;
    private String marketId;
    private String name;
    private double price;
    private int quantityAvailable;
    private int quantityReserved;
    private String marketName;
    private String marketLocation;
    private String status;
    private int count;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public String getMarketName() {
        return marketName;
    }

    public String getMarketLocation() {
        return marketLocation;
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
                " market_id=" + marketId +
                " name=" + name +
                " price=" + price +
                " quantity_available=" + quantityAvailable +
                " quantity_reserved=" + quantityReserved +
                " market_name=" + marketName +
                " market_location=" + marketLocation;
    }
}
