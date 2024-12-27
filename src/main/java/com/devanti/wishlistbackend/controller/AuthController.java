package com.devanti.wishlistbackend.controller;

import com.devanti.wishlistbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> handleRegister(@RequestParam(required = false) String username,
                                                              @RequestParam(required = false) String password) {
        Map<String, String> response = new HashMap<>();

        // Check if fields are empty
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            response.put("message", "Benutzername und Passwort dürfen nicht leer sein.");
            return ResponseEntity.badRequest().body(response);
        }

        // User registration validation
        if (username.length() < 3 || password.length() < 6) {
            response.put("message", "Benutzername muss mindestens 3 Zeichen und Passwort 6 Zeichen lang sein.");
            return ResponseEntity.badRequest().body(response);
        }

        // Registration logic
        if (userService.registerUser(username, password)) {
            response.put("message", "Registrierung erfolgreich.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Benutzername existiert bereits.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> handleLogin(@RequestParam(required = false) String username,
                                              @RequestParam(required = false) String password) {
        // Überprüfen, ob Felder leer sind
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body("Benutzername und Passwort dürfen nicht leer sein.");
        }

        try {
            // Generieren des Authentifizierungs-Token
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

            // Authentifizieren des Tokens
            Authentication authentication = authenticationManager.authenticate(token);

            // Setzen des SecurityContext mit der authentifizierten Benutzersitzung
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok("Login erfolgreich.");

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Ungültige Anmeldedaten.");
        }
    }
}

