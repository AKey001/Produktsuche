package de.produktsuche.backend.products;

import de.produktsuche.R;

public enum ListType {
    SEARCH(R.layout.search_popup),
    WATCH(R.layout.watchlist_popup),
    RESERVE(R.layout.reservedlist_popup);

    private final int popupLayout;

    ListType(int popupLayout) {
        this.popupLayout = popupLayout;
    }

    public int getPopupLayout() {
        return popupLayout;
    }
}
