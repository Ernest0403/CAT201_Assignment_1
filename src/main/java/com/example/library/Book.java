package com.example.library;

/**
 * Represents a book in the library.
 * Contains details like title, author, ISBN, availability, and borrower name.
 * Includes methods to borrow, return, and display book details.
 */
public class Book {
    // Fields representing the book's details
    private String title;        // Title of the book
    private String author;       // Author of the book
    private String ISBN;         // ISBN of the book
    private boolean isAvailable; // Indicates whether the book is available for borrowing
    private String borrowerName; // Name of the borrower if the book is borrowed


    // Constructor for new books ( default: Available and no borrower
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = true; // Default: Book is available
        this.borrowerName = "";  // Default: No borrower
    }

    // Constructor for books with full details (Used when loading from file)
    public Book(String title, String author, String ISBN, boolean isAvailable, String borrowerName) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = isAvailable;
        this.borrowerName = borrowerName;
    }


    /*
      Method to mark the book as borrowed and sets the borrower's name.
      Prints a message to indicate the borrowing status.
    */
    public void borrowBook(String borrowerName) {
        if (isAvailable) {
            if (borrowerName == null || borrowerName.trim().isEmpty()) {
                System.out.println("Borrower name cannot be empty.");
                return;
            }
            this.isAvailable = false; // Set the book to unavailable
            this.borrowerName = borrowerName; // Record the borrower's name
            System.out.println("The book '" + title + "' has been borrowed by: " + borrowerName);
        } else {
            System.out.println("The book '" + title + "' is currently unavailable (borrowed by " + this.borrowerName + ").");
        }
    }

    /*
      Method to mark the book as returned and clears the borrower's details.
      Print a message to indicate the return status.
     */
    public void returnBook() {
        if (!isAvailable) {
            System.out.println("The book '" + title + "' has been returned by: " + borrowerName);
            this.isAvailable = true; // Set the book to available
            this.borrowerName = "";  // Clear the borrower's name
        } else {
            System.out.println("The book '" + title + "' is already available in the library.");
        }
    }

    /*
      Method to display all the details of the book.
      Includes title, author, ISBN, availability, and borrower information (if applicable).
    */
    public void displayDetails() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + ISBN);
        System.out.println("Availability: " + (isAvailable ? "Available" : "Borrowed by " + borrowerName));
        System.out.println();
    }

    // ====== Getters ======

    // Method to return the title of book
    public String getTitle() {
        return title;
    }

    // Method to return the author of the book
    public String getAuthor() {
        return author;
    }

    // Method to return the ISBN of the book
    public String getISBN() {
        return ISBN;
    }


    // Method to return the availability status of the book
    public boolean isAvailable() {
        return isAvailable;
    }

    // Method to return the borrower name
    public String getBorrowerName() {
        return borrowerName;
    }

    // ====== Setters ======

    // Method to set the borrower name
    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    // Method to set the availability status of the book
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    // Method to set the title of the book
    public void setTitle(String title) {
        this.title = title;
    }

    // Method to set the author of the book
    public void setAuthor(String author) {
        this.author = author;
    }

    // Method to set the ISBN of the book
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}




