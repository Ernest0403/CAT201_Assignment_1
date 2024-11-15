import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        // Creating a book instance
//        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "1234567890");
//
//        // Displaying book details
//        book1.displayDetails();
//
//        // Borrowing the book
//        book1.borrowBook("Alice");
//
//        // Displaying book details again to see changes
//        book1.displayDetails();
//
//        // Trying to borrow the book again
//        book1.borrowBook("Bob");
//
//        // Returning the book
//        book1.returnBook();
//
//        // Displaying book details to confirm it's available
//        book1.displayDetails();

        Library library = new Library();
        System.out.print("A library is created.\n");

        String filename = "src/Lib1.txt";
        library.ReadLibrary(filename);


        library.displayAllBook();

    }
}