package com.devanti.wishlistbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist_items")
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Verknüpfung zum registrierten Benutzer

    public boolean isLink() {
        return details != null && (details.startsWith("http://") || details.startsWith("https://"));
    }

    @Column(nullable = false)
    private String itemName;
    @Lob
    private String details; // Neues Feld für Zusatzinfos

    // Getters und Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // Getter und Setter
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


}
