package com.devanti.wishlistbackend.controller;

import com.devanti.wishlistbackend.model.Product;
import com.devanti.wishlistbackend.repository.ProductRepository;
import com.devanti.wishlistbackend.service.WishlistService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LandingController {

    private final WishlistService wishlistService;
    private final ProductRepository productRepository;

    public LandingController(WishlistService wishlistService, ProductRepository productRepository) {
        this.wishlistService = wishlistService;
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String showLandingPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() &&
                !authentication.getName().equals("anonymousUser");

        model.addAttribute("isLoggedIn", isLoggedIn);

        List<Product> products = productRepository.findAllByOutfitId(1); // Outfit 1
        List<Product> products2 = productRepository.findAllByOutfitId(2); // Outfit 2

        System.out.println("Produkte für Outfit 1: " + products);
        System.out.println("Produkte für Outfit 2: " + products2);

        model.addAttribute("products", products);
        model.addAttribute("products2", products2);

        return "landing";
    }

    @GetMapping("/trendy-shoes")
    public String showTrendyShoesPage(Model model) {
        // Überprüfen, ob der Benutzer eingeloggt ist
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() &&
                !authentication.getName().equals("anonymousUser");

        model.addAttribute("isLoggedIn", isLoggedIn); // Anmeldestatus zum Model hinzufügen

        // Schuhe aus der Datenbank abrufen
        List<Product> schuhe = productRepository.findAllByCategory("schuhe");

        // Daten an das Model übergeben
        model.addAttribute("schuhe", schuhe);
        return "trendyShoes"; // Rückgabe der trendyShoes-Sicht
    }
}