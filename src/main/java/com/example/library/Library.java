package com.example.library;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    ArrayList<Book> books = new ArrayList<>();

    public void add_book(Book book) {
        books.add(book);
    }

    public void displayAllBook() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            for (Book book : books) {
                book.displayDetails();
            }
        }
    }

    public void search_book() {
        boolean quit = false;
        while (!quit) {
            System.out.print("Please select the searching method: \n");
            System.out.print("1. By title.\n2. By author\n3. By ISBN\n4. Quit searching\nPlease enter your choice: ");
            Scanner myObj = new Scanner(System.in);
            int choice = myObj.nextInt();
            myObj.nextLine(); // Clear newline character

            switch (choice) {
                case 1 -> search_title();
                case 2 -> search_author();
                case 3 -> search_isbn();
                case 4 -> quit = true;
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public void search_title() {
        System.out.print("Please enter the title: ");
        Scanner myObj = new Scanner(System.in);
        String title = myObj.nextLine();

        boolean found = false;
        for (Book book : books) {
            if (title.equalsIgnoreCase(book.getTitle())) {
                book.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with the title: " + title);
        }
    }

    public void search_author() {
        System.out.print("Please enter the author: ");
        Scanner myObj = new Scanner(System.in);
        String author = myObj.nextLine();

        boolean found = false;
        for (Book book : books) {
            if (author.equalsIgnoreCase(book.getAuthor())) {
                book.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with the author: " + author);
        }
    }

    public void search_isbn() {
        System.out.print("Please enter the ISBN: ");
        Scanner myObj = new Scanner(System.in);
        String isbn = myObj.nextLine();

        boolean found = false;
        for (Book book : books) {
            if (isbn.equals(book.getISBN())) {
                book.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with the ISBN: " + isbn);
        }
    }

    public void modifyBook() {
        System.out.print("Please enter the title of the book you want to modify: ");
        Scanner myObj = new Scanner(System.in);
        String title = myObj.nextLine();

        Book foundBook = null;
        for (Book book : books) {
            if (title.equalsIgnoreCase(book.getTitle())) {
                foundBook = book;
                break;
            }
        }

        if (foundBook == null) {
            System.out.println("No books found with the title: " + title);
            return;
        }

        System.out.println("Book found. Current details:");
        foundBook.displayDetails();

        int choice = 0;
        while (true) {
            try {
                System.out.println("What would you like to modify?");
                System.out.println("1. Title\n2. Author\n3. ISBN\n4. Availability\n5. Borrower Name");
                System.out.print("Please enter your choice: ");
                choice = myObj.nextInt();
                myObj.nextLine(); // Clear newline character
                if (choice >= 1 && choice <= 5) break; // Valid choice
                System.out.println("Invalid choice! Please enter a number between 1 and 5.");
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                myObj.nextLine(); // Clear invalid input
            }
        }

        switch (choice) {
            case 1 -> {
                System.out.print("Enter new title: ");
                String newTitle = myObj.nextLine();
                foundBook.setTitle(newTitle);
            }
            case 2 -> {
                System.out.print("Enter new author: ");
                String newAuthor = myObj.nextLine();
                foundBook.setAuthor(newAuthor);
            }
            case 3 -> {
                System.out.print("Enter new ISBN: ");
                String newISBN = myObj.nextLine();
                foundBook.setISBN(newISBN);
            }
            case 4 -> {
                System.out.print("Enter availability (Available/Borrowed): ");
                String availability = myObj.nextLine();
                if (availability.equalsIgnoreCase("Available")) {
                    foundBook.setAvailable(true);
                    foundBook.setBorrowerName(""); // Clear borrower name if available
                } else if (availability.equalsIgnoreCase("Borrowed")) {
                    foundBook.setAvailable(false);
                    System.out.print("Enter borrower name (leave blank if unknown): ");
                    String borrowerName = myObj.nextLine();
                    if (borrowerName.isEmpty()) {
                        System.out.println("No borrower name entered. Setting availability to 'Available'.");
                        foundBook.setAvailable(true);
                    } else {
                        foundBook.setBorrowerName(borrowerName);
                    }
                } else {
                    System.out.println("Invalid input for availability! No changes made.");
                }
            }
            case 5 -> {
                if (foundBook.isAvailable()) {
                    System.out.println("The book is not borrowed. Borrower name cannot be modified.");
                    return; // Return to the main options
                }
                System.out.print("Enter new borrower name: ");
                String borrowerName = myObj.nextLine();
                foundBook.setBorrowerName(borrowerName);
            }
            default -> System.out.println("Invalid choice!");
        }

        System.out.println("Book details updated successfully.");
    }



    public void ReadLibrary(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines

                String[] parts = line.split(",", -1); // -1 to retain empty trailing fields
                if (parts.length < 5) {
                    System.out.println("Skipping invalid line (incorrect format): " + line);
                    continue;
                }

                String title = parts[0].trim();
                String author = parts[1].trim();
                String isbn = parts[2].trim();
                String availability = parts[3].trim();
                String borrowerName = parts[4].trim();

                boolean isAvailable = availability.equalsIgnoreCase("Available");

                Book tempBook = new Book(title, author, isbn, isAvailable, borrowerName);
                books.add(tempBook);
            }
            System.out.println("Library data loaded successfully from: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename + ". Starting with an empty library.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveLibrary(String filename) {
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
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}





