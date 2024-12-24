package com.devanti.wishlistbackend.controller;

import com.devanti.wishlistbackend.model.Product;
import com.devanti.wishlistbackend.repository.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OutfitsController {

    private final ProductRepository productRepository;

    public OutfitsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/outfits")
    public String showOutfitsPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() &&
                !authentication.getName().equals("anonymousUser");

        model.addAttribute("isLoggedIn", isLoggedIn); // Anmeldestatus zum Model hinzufügen

        // Indikator für die Popup-Meldung setzen, wenn nicht eingeloggt
        if (!isLoggedIn) {
            model.addAttribute("showPopup", true); // Indiz, dass die Popup-Meldung angezeigt werden soll
        }

        // Produkte für Outfits abrufen
        List<Product> outfitProducts1 = productRepository.findAllByOutfitId(1);
        List<Product> outfitProducts2 = productRepository.findAllByOutfitId(2);

        model.addAttribute("outfitProducts1", outfitProducts1);
        model.addAttribute("outfitProducts2", outfitProducts2);

        return "outfits"; // Rückgabe der outfits-Vorlage
    }
}