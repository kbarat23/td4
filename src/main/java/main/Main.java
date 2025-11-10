package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Application Java démarrée dans Docker ===");

        String dbUrl = System.getenv("DATABASE_URL");
        if (dbUrl == null) {
            dbUrl = "jdbc:postgresql://db:5432/demo";
        }

        // 🔁 Tentatives multiples pour attendre la base
        for (int i = 1; i <= 10; i++) {
            try (Connection conn = DriverManager.getConnection(dbUrl, "dev", "dev")) {
                System.out.println("✅ Connexion réussie à la base PostgreSQL !");
                break;
            } catch (SQLException e) {
                System.out.println("⏳ Tentative " + i + " : base non disponible, nouvel essai...");
                try { Thread.sleep(3000); } catch (InterruptedException ex) { break; }
            }
        }

        while (true) {
            try {
                Thread.sleep(5000);
                System.out.println("App Java toujours en exécution...");
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
