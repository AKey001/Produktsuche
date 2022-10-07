package de.produktsuche.backend.products;

public enum ListType {
    SEARCH("vorhanden", ""),
    WATCH("vorhanden", "Von der Watchlist entfernen?"),
    RESERVE("reserviert", "Reservierung entfernen?");

    private final String adj;
    private final String question;

    ListType(String adj, String question) {
        this.adj = adj;
        this.question = question;
    }

    public String getAdj() {
        return adj;
    }

    public String getQuestion() {
        return question;
    }
}
