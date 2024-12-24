package com.devanti.wishlistbackend.service;

import com.devanti.wishlistbackend.model.User;
import com.devanti.wishlistbackend.model.WishlistItem;
import com.devanti.wishlistbackend.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public void addItemToWishlist(User user, String itemName, String itemDetails) {
        WishlistItem item = new WishlistItem();
        item.setUser(user); // Benutzer setzen
        item.setItemName(itemName); // Wunschname setzen

        // Zusatzinfos nur setzen, wenn sie vorhanden sind
        if (itemDetails != null && !itemDetails.isBlank()) {
            item.setDetails(itemDetails);
        }

        wishlistRepository.save(item); // Speichern in der Datenbank
    }

    public List<WishlistItem> getUserWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    public void removeItemFromWishlist(Long itemId) {
        wishlistRepository.deleteById(itemId);
    }

    public List<WishlistItem> getPublicWishlistItems() {
        // Beispielitems (alternativ: aus der Datenbank laden)
        WishlistItem item1 = new WishlistItem();
        item1.setItemName("Beispiel-Item 1");

        WishlistItem item2 = new WishlistItem();
        item2.setItemName("Beispiel-Item 2");

        WishlistItem item3 = new WishlistItem();
        item3.setItemName("Beispiel-Item 3");

        return List.of(item1, item2, item3);
    }

}
