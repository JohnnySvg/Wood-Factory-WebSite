package com.lemn.sitelemn.config;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.lemn.sitelemn.entity.Product;
import com.lemn.sitelemn.entity.User;
import com.lemn.sitelemn.repository.ProductRepository;
import com.lemn.sitelemn.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@example.com");
            admin.setFullName("Administrator");
            admin.setPhone("0123456789");
            admin.setAddress("Adresa Admin");
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("Admin user created.");
        }
        
        if (productRepository.count() == 0) {
            List<Product> products = List.of(
                // Mese
                new Product("Masă rustică din stejar", "Masă solidă pentru sufragerie", "mese", 1200, 10, "/images/1.jpg"),
                new Product("Masă extensibilă modernă", "Perfectă pentru spații mici", "mese", 950, 8, "/images/2.jpg"),
                new Product("Masă rotundă lemn masiv", "Eleganță și durabilitate", "mese", 1300, 5, "/images/3.jpg"),

                // Scaune
                new Product("Scaun lemn natur", "Scaun ergonomic cu spătar curbat", "scaune", 400, 20, "/images/4.jpg"),
                new Product("Scaun Tapițat", "Confortabil, tapițerie textilă.", "scaune", 450, 15, "/images/5.jpg"),
                new Product("Bibliotecă Mare", "6 rafturi, stil industrial.", "biblioteci", 1000, 5, "/images/6.jpg"),
                new Product("Bibliotecă Albă", "Design scandinav simplu.", "biblioteci", 950, 4, "/images/7.jpg"),
                new Product("Raft Lemn Natur", "Pentru decor sau depozitare.", "rafturi", 350, 12, "/images/8.jpg"),
                new Product("Raft de Colț", "Ideal pentru spații mici.", "rafturi", 300, 10, "/images/9.jpg"),
                new Product("Pat Dublu", "Cadru din lemn masiv.", "paturi", 2200, 3, "/images/10.jpg"),
                new Product("Pat Single", "Pat de o persoană cu sertare.", "paturi", 1800, 6, "/images/11.jpg"),
                new Product("Fotoliu Lemn", "Fotoliu clasic cu brațe.", "fotolii", 700, 9, "/images/12.jpg"),
                new Product("Fotoliu Modern", "Design ergonomic.", "fotolii", 780, 7, "/images/13.jpg"),
                new Product("Ușă Interior", "Ușă din lemn stratificat.", "usi", 950, 10, "/images/14.jpg"),
                new Product("Ușă Clasică", "Model cu vitralii.", "usi", 1200, 6, "/images/15.jpg"),
                new Product("Parchet Stejar", "Parchet laminat natural.", "parchet lemn", 89.99, 30, "/images/16.jpg"),
                new Product("Parchet Gri", "Parchet laminat gri urban.", "parchet lemn", 99.99, 25, "/images/17.jpg"),
            

                // Grinzi
                new Product("Grindă stejar 4m", "Pentru structuri solide", "grinzi", 280, 30, "/images/18.jpg"),
                new Product("Grindă fag laminat", "Stabilitate crescută", "grinzi", 320, 25, "/images/19.jpg"),
                new Product("Grindă pin uscat", "Ușor și rezistent", "grinzi", 190, 40, "/images/20.jpg"),

                // Placaj lemn
                new Product("Placaj mesteacăn 10mm", "Pentru mobilier", "placaj lemn", 120, 50, "/images/21.jpg"),
                new Product("Placaj plop 15mm", "Ușor, ideal pentru tăblii", "placaj lemn", 100, 60, "/images/22.jpg"),
                new Product("Placaj stejar marin", "Rezistent la umiditate", "placaj lemn", 150, 30, "/images/23.jpg"),
                new Product("Placaj multistrat 18mm", "Ideal pentru pardoseli", "placaj lemn", 170, 35, "/images/24.jpg"),

                // MDF
                new Product("Placă MDF albă 18mm", "Pentru mobilă modernă", "mdf", 140, 40, "/images/25.jpg"),

                // OSB
                new Product("Placă OSB 3 12mm", "Utilizare generală", "osb", 90, 70, "26.jpg"),
                new Product("Placă OSB 3 15mm", "Ideal pentru pereți interiori", "osb", 110, 60, "/images/27.jpg"),
                new Product("Placă OSB 4 18mm", "Pentru acoperișuri", "osb", 130, 50, "/images/28.jpg")
            );

            productRepository.saveAll(products);
            System.out.println("Sample products added.");
        }
       
    }
}
