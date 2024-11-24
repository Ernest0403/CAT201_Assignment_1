package com.example.library;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable;
    private String borrowerName;

    /**
     * Constructor for new books (Default: Available and no borrower).
     *
     * @param title  The title of the book.
     * @param author The author of the book.
     * @param ISBN   The ISBN of the book.
     */
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = true; // Default: Book is available
        this.borrowerName = ""; // Default: No borrower
    }

    /**
     * Constructor for books with full details (Used when loading from a file).
     *
     * @param title        The title of the book.
     * @param author       The author of the book.
     * @param ISBN         The ISBN of the book.
     * @param isAvailable  Whether the book is available.
     * @param borrowerName The name of the borrower (if borrowed).
     */
    public Book(String title, String author, String ISBN, boolean isAvailable, String borrowerName) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = isAvailable;
        this.borrowerName = borrowerName;
    }

    /**
     * Borrows the book. Updates its availability and borrower details.
     *
     * @param borrowerName The name of the borrower.
     */
    public void borrowBook(String borrowerName) {
        if (isAvailable) {
            if (borrowerName == null || borrowerName.trim().isEmpty()) {
                System.out.println("Borrower name cannot be empty.");
                return;
            }
            this.isAvailable = false;
            this.borrowerName = borrowerName;
            System.out.println("The book '" + title + "' has been borrowed by: " + borrowerName);
        } else {
            System.out.println("The book '" + title + "' is currently unavailable (borrowed by " + this.borrowerName + ").");
        }
    }

    /**
     * Returns the book. Clears borrower details and marks it as available.
     */
    public void returnBook() {
        if (!isAvailable) {
            System.out.println("The book '" + title + "' has been returned by: " + borrowerName);
            this.isAvailable = true;
            this.borrowerName = ""; // Clear borrower name
        } else {
            System.out.println("The book '" + title + "' is already available in the library.");
        }
    }

    /**
     * Displays the details of the book.
     */
    public void displayDetails() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + ISBN);
        System.out.println("Availability: " + (isAvailable ? "Available" : "Borrowed by " + borrowerName));
        System.out.println();
    }

    // ====== Getters ======

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    // ====== Setters ======

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}



