public class Book {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable;
    private String borrowerName;

    // Constructor
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = true;
        this.borrowerName = "";
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
            this.borrowerName = "";
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
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }
}

