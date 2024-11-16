public class Book {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable;
    private String borrowerName;

    // Constructor for new books (default: available and no borrower)
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = true; // Default: book is available
        this.borrowerName = ""; // Default: no borrower
    }

    // Constructor for books with full details (used when loading from file)
    public Book(String title, String author, String ISBN, boolean isAvailable, String borrowerName) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = isAvailable;
        this.borrowerName = borrowerName;
    }

    // Method to borrow the book
    public void borrowBook(String borrowerName) {
        if (isAvailable) {
            this.isAvailable = false;
            this.borrowerName = borrowerName;
            System.out.println(borrowerName + " has borrowed the book: " + title);
        } else {
            System.out.println("The book '" + title + "' is currently unavailable.");
        }
    }

    // Method to return the book
    public void returnBook() {
        if (!isAvailable) {
            System.out.println(borrowerName + " has returned the book: " + title);
            this.isAvailable = true;
            this.borrowerName = ""; // Clear borrower name when the book is returned
        } else {
            System.out.println("The book '" + title + "' is already available in the library.");
        }
    }

    // Method to display book details
    public void displayDetails() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + ISBN);
        System.out.println("Availability: " + (isAvailable ? "Available" : "Borrowed by " + borrowerName));
        System.out.println();
    }

    // Getters for accessing book properties
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

    // Setter for borrower name (used for file loading)
    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    // Setter for availability (used for file loading)
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Setter for author
    public void setAuthor(String author) {
        this.author = author;
    }

    // Setter for ISBN
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}



