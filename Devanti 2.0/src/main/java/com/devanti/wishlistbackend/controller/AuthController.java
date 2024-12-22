package com.devanti.wishlistbackend.controller;

import com.devanti.wishlistbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> handleRegister(@RequestParam(required = false) String username,
                                                 @RequestParam(required = false) String password) {
        // Überprüfen, ob Felder leer sind
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body("Benutzername und Passwort dürfen nicht leer sein.");
        }

        // Benutzerregistrierung
        if (username.length() < 3 || password.length() < 6) {
            return ResponseEntity.badRequest().body("Benutzername muss mindestens 3 Zeichen und Passwort 6 Zeichen lang sein.");
        }

        if (userService.registerUser(username, password)) {
            return ResponseEntity.ok("Registrierung erfolgreich.");
        } else {
            return ResponseEntity.badRequest().body("Benutzername existiert bereits.");
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
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);

            return ResponseEntity.ok("Login erfolgreich.");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Ungültige Anmeldedaten.");
        }
    }
}

