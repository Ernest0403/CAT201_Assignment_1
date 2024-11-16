import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Library library = new Library();
        System.out.println("A library is created.");

        String filename = "src/Lib1.txt";
        library.ReadLibrary(filename);

        boolean quit = false;

        while (!quit) {
            System.out.println("\nMenu:");
            System.out.println("1. Display all books");
            System.out.println("2. Search for a book");
            System.out.println("3. Modify book details");
            System.out.println("4. Save library data");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            Scanner myObj = new Scanner(System.in);
            int choice = myObj.nextInt();

            switch (choice) {
                case 1 -> library.displayAllBook();
                case 2 -> library.search_book();
                case 3 -> library.modifyBook();
                case 4 -> library.saveLibrary(filename);
                case 5 -> quit = true;
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }

        System.out.println("Exiting the library system. Goodbye!");
    }
}


