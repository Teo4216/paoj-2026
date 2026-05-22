package com.pao.laboratory12;

import com.pao.laboratory12.model.*;
import com.pao.laboratory12.repository.*;
import com.pao.laboratory12.service.AuditService;
import com.pao.laboratory12.service.LibraryService;
import com.pao.laboratory12.util.DatabaseConnection;

import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // pregatim tabelele pt h2 in memory
        try (Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement()) {
            stmt.execute("CREATE TABLE author (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(200), country VARCHAR(100))");
            stmt.execute("CREATE TABLE book (id BIGINT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(300), author_id BIGINT, available INT DEFAULT 1, FOREIGN KEY (author_id) REFERENCES author(id))");
            stmt.execute("CREATE TABLE reader (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(200), email VARCHAR(200))");
            stmt.execute("CREATE TABLE loan (id BIGINT AUTO_INCREMENT PRIMARY KEY, book_id BIGINT, reader_id BIGINT, loan_date VARCHAR(20), return_date VARCHAR(20), FOREIGN KEY (book_id) REFERENCES book(id), FOREIGN KEY (reader_id) REFERENCES reader(id))");
        }

        AuditService audit = AuditService.getInstance();
        AuthorRepository authorRepo = new AuthorRepository();
        BookRepository bookRepo = new BookRepository();
        ReaderRepository readerRepo = new ReaderRepository();
        LoanRepository loanRepo = new LoanRepository();
        LibraryService libraryService = LibraryService.getInstance();

        System.out.println("=== BIBLIOTECA JDBC — Demo Lab12 ===\n");

        Author author = new Author("Gabriel Garcia Marquez", "CO");
        authorRepo.save(author);
        audit.log("add_author");
        System.out.println("1. Autor adaugat: " + author);

        Book book1 = new Book("100 de ani de singuratate", author.getId());
        Book book2 = new Book("Dragostea in vremea holerei", author.getId());
        bookRepo.save(book1);
        bookRepo.save(book2);
        audit.log("add_book");
        System.out.println("2. Carti adaugate: " + book1 + ", " + book2);

        Reader reader = new Reader("Ion Popescu", "ion.popescu@email.com");
        readerRepo.save(reader);
        audit.log("add_reader");
        System.out.println("3. Cititor adaugat: " + reader);

        List<Book> allBooks = bookRepo.findAll();
        audit.log("list_books");
        System.out.println("4. Toate cartile (" + allBooks.size() + "):");
        allBooks.forEach(b -> System.out.println("   " + b));


        bookRepo.findById(book1.getId()).ifPresentOrElse(
                b -> System.out.println("5. Carte gasita: " + b),
                () -> System.out.println("5. Carte negasita.")
        );
        audit.log("find_book_by_id");

        book1.setTitle("100 de ani de singuratate (Ed. speciala)");
        bookRepo.update(book1);
        audit.log("update_book");
        System.out.println("6. Carte actualizata: " + book1);

        long loanId = libraryService.borrowBook(reader.getId(), book1.getId());
        audit.log("borrow_book");
        System.out.println("7. Imprumut creat cu ID=" + loanId);

        libraryService.returnBook(loanId);
        audit.log("return_book");
        System.out.println("8. Carte returnata.");

        List<String> activeLoans = libraryService.getActiveLoansWithDetails();
        audit.log("report_active_loans");
        System.out.println("9. Imprumuturi active: " + (activeLoans.isEmpty() ? "niciun" : ""));
        activeLoans.forEach(s -> System.out.println("   " + s));

        loanRepo.delete(loanId);
        readerRepo.delete(reader.getId());
        audit.log("delete_reader");
        System.out.println("10. Cititor sters cu ID=" + reader.getId());

        System.out.println("\n=== Demo finalizat. Verifica audit.csv ===");
        DatabaseConnection.getInstance().close();
    }
}