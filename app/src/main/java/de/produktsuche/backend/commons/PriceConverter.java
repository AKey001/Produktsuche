package de.produktsuche.backend.commons;

public class PriceConverter {

    public String convertPrice(double price) {
        double roundedPrice = Math.round(price * 100.0)/100.0;

        boolean round = roundedPrice % 0.1 == 0;

        String priceEuro = String.valueOf(roundedPrice);
        if (round) {
            priceEuro += "0";
        }

        priceEuro += " €";

        return priceEuro;
    }

}
