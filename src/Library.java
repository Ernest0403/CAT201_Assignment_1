import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    ArrayList<Book> books = new ArrayList<>();

    public void add_book(Book book){
        books.add(book);
        System.out.print("New book added: \n");
    }

    public void search_book(){
        System.out.print("Please select the searching method: \n");
        System.out.print("1. By title. \n2. By author \n 3.By ISBN \n");
        Scanner myObj = new Scanner(System.in);
        int choice = myObj.nextInt();
        switch(choice){
            case 1:{
                search_title();
            }
            case 2:{
                search_author();
            }
            case 3:{
                search_isbn();
            }
        }
    }

    public void search_title(){
        System.out.print("Please enter the title: ");
        Scanner myObj = new Scanner(System.in);
        String title = myObj.nextLine();
        //search title codes
    }
    public void search_author(){
        System.out.print("Please enter the author: ");
        Scanner myObj = new Scanner(System.in);
        String author = myObj.nextLine();
        //search author codes
    }
    public void search_isbn(){
        System.out.print("Please enter the ISBN: ");
        Scanner myObj = new Scanner(System.in);
        String isbn = myObj.nextLine();
        //search ISBN codes
    }
}