package com.pao.laboratory12.exercise1;

import com.pao.laboratory12.model.Author;
import com.pao.laboratory12.repository.AuthorRepository;
import com.pao.laboratory12.util.DatabaseConnection;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            try (Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement()) {
                stmt.execute("CREATE TABLE author (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(200), country VARCHAR(100))");
                System.out.println("✅ Baza de date conectata si tabelul creat.");
            }

            AuthorRepository repo = new AuthorRepository();
            repo.save(new Author("Mihai Eminescu", "Romania"));

            System.out.println(" Autor inserat cu succes!");
            repo.findAll().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}