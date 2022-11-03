package de.produktsuche.backend.commons;


public class PriceConverter {

    public String convertPrice(double price) {
        double roundedPrice = Math.round(price * 100.0)/100.0;
        String[] priceSplit = String.valueOf(roundedPrice).split("\\.");
        boolean round = priceSplit[1].length() < 2;
        String priceEuro = String.valueOf(roundedPrice);
        if (round) {
            priceEuro += "0";
        }
        priceEuro += " €";
        return priceEuro;
    }
}
