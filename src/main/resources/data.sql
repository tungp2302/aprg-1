-- Manuelles Erstellen der 'users' Tabelle
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY, -- ID wird automatisch generiert
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL
);

-- Manuelles Erstellen der 'wishlist_items' Tabelle
CREATE TABLE IF NOT EXISTS wishlist_items (
                                              id BIGINT AUTO_INCREMENT PRIMARY KEY, -- ID wird automatisch generiert
                                              user_id BIGINT NOT NULL,
                                              item_name VARCHAR(255) NOT NULL,
                                              details CLOB,
                                              FOREIGN KEY (user_id) REFERENCES users(id),
                                          link CLOB

);

-- Neue Tabelle für geteilte Wunschlisten
CREATE TABLE IF NOT EXISTS shared_wishlists (
                                                id BIGINT AUTO_INCREMENT PRIMARY KEY, -- ID wird automatisch generiert
                                                owner_id BIGINT NOT NULL, -- Benutzer, dem die Wunschliste gehört
                                                recipient_email VARCHAR(255) NOT NULL, -- E-Mail des Empfängers
                                                shared_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Zeitpunkt des Teilens
                                                FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS product (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255),
                                       price VARCHAR(255),
                                       image_url VARCHAR(255),
                                       link VARCHAR(255),
                                       outfit_id INTEGER,
                                        category VARCHAR(255)
);

-- Outfit 1 Produkte
INSERT INTO product (name, price, image_url, link, outfit_id, category) VALUES
                                                                  ('Vertere Berlin RACING JACKET KUNSTLEDER', '169.95', 'css/images/jacke1.jpg', 'https://www.zalando.de/vertere-berlin-racing-jacket-unisex-kunstlederjacke-black-ven21003y-q11.html', 1, 'jacke'),
                                                                  ('Calvin Klein POPLIN STRETCH MODERN FIT', '45.00', 'css/images/hemd1.jpg', 'https://www.zalando.de/calvin-klein-businesshemd-bright-white-6ca22d05p-a11.html', 1, 'oberteil'),
                                                                  ('BOSS TIE', '69.95', 'css/images/accessoire1.jpg', 'https://www.zalando.de/boss-tie-krawatte-black-bb152r09y-q11.html', 1, 'accessoire'),
                                                                  ('PULL&BEAR Super Baggy Washed Finish Jeans', '49.99', 'css/images/hose1.jpg', 'https://www.zalando.de/pullandbear-super-baggy-jeans-relaxed-fit-dark-grey-puc22g0tw-c12.html', 1, 'hose'),
                                                                  ('Zign Studio LEATHER Schnürer', '63.95', 'css/images/schuhe1.jpg', 'https://www.zalando.de/zign-studio-leather-schnuerer-black-zi412m00a-q11.html', 1, 'schuhe');

-- Outfit 2 Produkte
INSERT INTO product (name, price, image_url, link, outfit_id, category) VALUES
                                                        ('New Era Mütze', '21.40', 'css/images/muetze2.jpg', 'https://www.zalando.de/new-era-muetze-blauweiss-ne352o012-k11.html', 2, 'accessoire'),
                                                        ('Adidas SHORT PUFFER', '71.95', 'css/images/jacke2.jpg', 'https://www.zalando.de/adidas-originals-short-puffer-winterjacke-black-ad121u04h-q11.html', 2, 'jacke'),
                                                        ('Jaded London TRIBAL AIRBURSH STAR JOGGER', '80.99', 'css/images/hose2.jpg', 'https://www.zalando.de/jaded-london-tribal-airbrush-star-jogger-unisex-jogginghose-grey-tribal-jl0210024-c11.html', 2, 'hose'),
                                                        ('Timberland STONE STREET BOAT', '159.95', 'css/images/schuhe2.jpg', 'https://www.zalando.de/timberland-stone-street-boat-schnuerpumps-natural-ti111e03z-a11.html', 2,'schuhe');

INSERT INTO product (name, price, image_url, link, category) VALUES
                                                                 ('Nike Cortez', '98.95', 'css/images/cortez.jpg', 'https://www.zalando.de/nike-sportswear-cortez-se-unisex-sneaker-low-metallic-cool-greyblackdark-brown-ni115o074-d11.html', 'schuhe'),
                                                                 ('Adidas Samba', '119.95', 'css/images/samba.jpg', 'https://www.zalando.de/adidas-originals-samba-og-w-sneaker-low-night-indigocream-whitealumina-ad111a2o4-k11.html', 'schuhe'),
                                                                 ('Puma Speedcat', '109.95', 'css/images/speedcat.jpg', 'https://www.zalando.de/puma-speedcat-unisex-sneaker-low-all-time-redwhite-pu115o0wm-g11.html', 'schuhe');
