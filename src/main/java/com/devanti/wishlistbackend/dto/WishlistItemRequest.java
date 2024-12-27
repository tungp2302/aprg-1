package com.devanti.wishlistbackend.dto; // Stellen Sie sicher, dass das Paket korrekt ist

public class WishlistItemRequest {
    private String itemName;
    private String itemDetails; // Optionales Feld für Zusatzinfos
    private String imageUrl;

    // Getter und Setter
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(String itemDetails) {
        this.itemDetails = itemDetails;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

