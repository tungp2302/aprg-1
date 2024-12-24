package com.devanti.wishlistbackend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors; // Optional, wenn Streams verwendet werden
import com.devanti.wishlistbackend.dto.WishlistItemRequest; // Import hinzufügen
import com.devanti.wishlistbackend.model.User;
import com.devanti.wishlistbackend.service.WishlistService;
import com.devanti.wishlistbackend.service.SharedWishlistService;
import com.devanti.wishlistbackend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserRepository userRepository;
    private final SharedWishlistService sharedWishlistService;

    public WishlistController(WishlistService wishlistService, UserRepository userRepository, SharedWishlistService sharedWishlistService) {
        this.wishlistService = wishlistService;
        this.userRepository = userRepository;
        this.sharedWishlistService = sharedWishlistService;
    }

    @GetMapping
    public String getWishlist(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails, Model model) {
        // Benutzer laden
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden: " + userDetails.getUsername()));

        // Wunschliste abrufen und vorbereiten
        List<Map<String, Object>> wishlist = wishlistService.getUserWishlist(user.getId()).stream()
                .map(item -> {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("itemName", item.getItemName());
                    itemMap.put("details", item.getDetails());
                    itemMap.put("id", item.getId()); // Sicherstellen, dass die ID verfügbar ist
                    itemMap.put("isLink", item.getDetails() != null && (item.getDetails().startsWith("http://") || item.getDetails().startsWith("https://")));
                    return itemMap;
                })
                .collect(Collectors.toList());

        // Wunschliste zum Model hinzufügen
        model.addAttribute("wishlist", wishlist);

        return "wishlist";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addItem(@RequestBody WishlistItemRequest request,
                                          @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden: " + userDetails.getUsername()));

        try {
            // Wunsch mit Zusatzinfos hinzufügen
            wishlistService.addItemToWishlist(user, request.getItemName(), request.getItemDetails());
            return ResponseEntity.ok("Wunsch hinzugefügt.");
        } catch (Exception e) {
            System.err.println("Fehler beim Hinzufügen: " + e.getMessage());
            return ResponseEntity.status(500).body("Fehler beim Hinzufügen.");
        }
    }

    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<String> removeItem(@RequestParam Long itemId) {
        try {
            wishlistService.removeItemFromWishlist(itemId);
            return ResponseEntity.ok("Wunsch entfernt.");
        } catch (Exception e) {
            System.err.println("Fehler beim Entfernen: " + e.getMessage());
            return ResponseEntity.status(500).body("Fehler beim Entfernen.");
        }
    }


    @PostMapping("/share")
    public String shareWishlist(@RequestParam String recipientEmail,
                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden: " + userDetails.getUsername()));

        sharedWishlistService.shareWishlist(user, recipientEmail);
        return "redirect:/wishlist";
    }
}
