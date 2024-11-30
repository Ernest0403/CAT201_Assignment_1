package com.example.library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private final List<Book> books = new ArrayList<>();

    //Adds a new book to the library.
    public void addBook(Book book) {
        books.add(book);
    }

    //Retrieves all books in the library.
    public List<Book> getBooks() {
        return new ArrayList<>(books); // Return a copy to prevent modification
    }

//     Searches for a book in the library based on a query.
//     The search query (e.g., title, author, or ISBN).
//     The type of search ("title", "author", or "isbn").
//     The function returns the first book that matches the query, or null if no match is found.
    public Book searchBook(String query, String type) {
        return switch (type.toLowerCase()) {
            case "title" -> books.stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(query))
                    .findFirst()
                    .orElse(null);
            case "author" -> books.stream()
                    .filter(book -> book.getAuthor().equalsIgnoreCase(query))
                    .findFirst()
                    .orElse(null);
            case "isbn" -> books.stream()
                    .filter(book -> book.getISBN().equalsIgnoreCase(query))
                    .findFirst()
                    .orElse(null);
            default -> {
                System.out.println("Invalid search type! Use 'title', 'author', or 'isbn'.");
                yield null;
            }
        };
    }


    //Loads library data from a CSV file.
    //filename refers to the file path of the CSV file.
    public void loadLibrary(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            books.clear(); // Clear existing data
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1); // Handle empty trailing fields
                if (parts.length < 5) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String title = parts[0].trim();
                String author = parts[1].trim();
                String isbn = parts[2].trim();
                boolean isAvailable = parts[3].trim().equalsIgnoreCase("Available");
                String borrowerName = parts[4].trim();

                books.add(new Book(title, author, isbn, isAvailable, borrowerName));
            }

            System.out.println("Library data loaded successfully from: " + filename);
        }
    }

    // Saves library data to a CSV file.
    public void saveLibrary(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                writer.write(String.join(",",
                        book.getTitle(),
                        book.getAuthor(),
                        book.getISBN(),
                        book.isAvailable() ? "Available" : "Borrowed",
                        book.getBorrowerName()));
                writer.newLine();
            }
            System.out.println("Library data saved successfully to: " + filename);
        }
    }

//     Modifies the details of a book in the library based on its title.
//     The function returns True if the book was successfully updated, false otherwise.
    public boolean modifyBook(String title, Book newBookData) {
        Book bookToModify = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (bookToModify != null) {
            bookToModify.setTitle(newBookData.getTitle());
            bookToModify.setAuthor(newBookData.getAuthor());
            bookToModify.setISBN(newBookData.getISBN());
            bookToModify.setAvailable(newBookData.isAvailable());
            bookToModify.setBorrowerName(newBookData.getBorrowerName());
            return true;
        }
        return false;
    }


//     Borrows a book from the library.
//     The function returns True if the book was successfully borrowed, false otherwise.

    public boolean borrowBook(String title, String borrowerName) {
        Book bookToBorrow = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title) && book.isAvailable())
                .findFirst()
                .orElse(null);

        if (bookToBorrow != null) {
            bookToBorrow.borrowBook(borrowerName);
            return true;
        }
        return false;
    }

    //Returns a book to the library.
    public boolean returnBook(String title) {
        Book bookToReturn = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title) && !book.isAvailable())
                .findFirst()
                .orElse(null);

        if (bookToReturn != null) {
            bookToReturn.returnBook();
            return true;
        }
        return false;
    }
}





