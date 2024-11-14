import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    ArrayList<Book> books = new ArrayList<>();

    public void add_book(Book book){
        books.add(book);
    }

    public void search_book(){
        boolean quit = false;
        while(quit == false){
            System.out.print("Please select the searching method: \n");
            System.out.print("1. By title. \n2. By author \n3. By ISBN \n4. Quit library\nPlease enter your choice:\n");
            Scanner myObj = new Scanner(System.in);
            int choice = myObj.nextInt();
            switch(choice){
                case 1:{
                    search_title();
                    break;
                }
                case 2:{
                    search_author();
                    break;
                }
                case 3:{
                    search_isbn();
                    break;
                }
                case 4:{
                    quit = true;
                }
            }
        }

    }

    public void search_title() {
        System.out.print("Please enter the title: ");
        Scanner myObj = new Scanner(System.in);
        String title = myObj.nextLine();
        int counter = 1; // Start from 1 for a user-friendly sequence

        boolean found = false; // Track if any book was found

        for (int i = 0; i < books.size(); i++) {
            if (title.equals(books.get(i).getTitle())) { // Use equals for string comparison
                System.out.println("Book found #" + counter);
                books.get(i).displayDetails();
                counter++;
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with the title: " + title);
        }
    }
    public void search_author(){
        System.out.print("Please enter the author: ");
        Scanner myObj = new Scanner(System.in);
        String author = myObj.nextLine();
        int counter = 1; // Start from 1 for a user-friendly sequence

        boolean found = false; // Track if any book was found

        for (int i = 0; i < books.size(); i++) {
            if (author.equals(books.get(i).getAuthor())) { // Use equals for string comparison
                System.out.println("Book found #" + counter);
                books.get(i).displayDetails();
                counter++;
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with the title: " + author);
        }
    }
    public void search_isbn(){
        System.out.print("Please enter the ISBN: ");
        Scanner myObj = new Scanner(System.in);
        String isbn = myObj.nextLine();
        int counter = 1; // Start from 1 for a user-friendly sequence

        boolean found = false; // Track if any book was found

        for (int i = 0; i < books.size(); i++) {
            if (isbn.equals(books.get(i).getISBN())) { // Use equals for string comparison
                System.out.println("Book found #" + counter);
                books.get(i).displayDetails();
                counter++;
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with the title: " + isbn);
        }
    }
}