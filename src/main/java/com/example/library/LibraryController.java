package com.example.library;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class LibraryController {
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, String> availabilityColumn;
    @FXML
    private TableColumn<Book, String> borrowerNameColumn; // New column for Borrower Name

    private final Library library = new Library(); // Library class to manage books
    private final ObservableList<Book> bookData = FXCollections.observableArrayList();

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void LogoutSystem(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login-scene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        //Link columns to book properties
        titleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        authorColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));
        isbnColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getISBN()));
        availabilityColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().isAvailable() ? "Available" : "Borrowed"));
        borrowerNameColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().isAvailable() ? "" : data.getValue().getBorrowerName()));

        // Connect table to observable list
        bookTable.setItems(bookData);
    }

    @FXML
    private void onAddBook() {
        Dialog<Book> dialog = createBookDialog(null); // Pass `null` for a new book
        Optional<Book> result = dialog.showAndWait();

        result.ifPresent(book -> {
            library.addBook(book);
            bookData.add(book);
        });
    }

    @FXML
    private void onModifyBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("No Book Selected", "Please select a book to modify.");
            return;
        }

        Dialog<Book> dialog = createBookDialog(selectedBook); // Pre-fill the dialog
        Optional<Book> result = dialog.showAndWait();

        result.ifPresent(updatedBook -> {
            selectedBook.setTitle(updatedBook.getTitle());
            selectedBook.setAuthor(updatedBook.getAuthor());
            selectedBook.setISBN(updatedBook.getISBN());
            bookTable.refresh(); // Refresh the table to display updated data
        });
    }

    @FXML
    private void onBorrowBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null || !selectedBook.isAvailable()) {
            showAlert("Invalid Action", "Please select an available book to borrow.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Borrow Book");
        dialog.setHeaderText("Enter Borrower Name:");
        dialog.setContentText("Borrower:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(borrower -> {
            if(borrower.isEmpty()) {
                showAlert("Invalid Borrower", "Please fill in the name of the borrower.");
            };
            selectedBook.borrowBook(borrower);
            bookTable.refresh();
        });
    }

    @FXML
    private void onReturnBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null || selectedBook.isAvailable()) {
            showAlert("Invalid Action", "Please select a borrowed book to return.");
            return;
        }

        selectedBook.returnBook();
        bookTable.refresh();
    }

    @FXML
    private void onSaveLibrary() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                library.saveLibrary(file.getPath());
                showAlert("Success", "Library data saved successfully!");
            } catch (Exception e) {
                showAlert("Error", "Failed to save library: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onLoadLibrary() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                library.loadLibrary(file.getPath());
                bookData.setAll(library.getBooks()); // Update table with loaded books
                showAlert("Success", "Library data loaded successfully!");
            } catch (Exception e) {
                showAlert("Error", "Failed to load library: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onSearchBook() {
        // Display a dialog to ask for the search type and query
        ChoiceDialog<String> searchTypeDialog = new ChoiceDialog<>("Title", "Title", "Author", "ISBN");
        searchTypeDialog.setTitle("Search Book");
        searchTypeDialog.setHeaderText("Search for a Book");
        searchTypeDialog.setContentText("Search by:");

        Optional<String> searchTypeResult = searchTypeDialog.showAndWait();
        if (searchTypeResult.isEmpty()) {
            return; // If no type is selected, exit the method
        }

        String searchType = searchTypeResult.get(); // Get the selected search type

        // Display a TextInputDialog for the search query
        TextInputDialog queryDialog = new TextInputDialog();
        queryDialog.setTitle("Search Book");
        queryDialog.setHeaderText("Search for a Book");
        queryDialog.setContentText("Enter the " + searchType + ":");

        Optional<String> queryResult = queryDialog.showAndWait();
        if (queryResult.isEmpty()) {
            return; // If no query is entered, exit the method
        }

        String query = queryResult.get().trim(); // Get the entered query and trim spaces

        // Search for the book in the ObservableList
        for (Book book : bookData) {
            boolean match = switch (searchType.toLowerCase()) {
                case "title" -> book.getTitle().equalsIgnoreCase(query);
                case "author" -> book.getAuthor().equalsIgnoreCase(query); // Check for author match
                case "isbn" -> book.getISBN().equalsIgnoreCase(query); // Check for ISBN match
                default -> false;
            };

            if (match) {
                // Highlight the found book in the TableView
                bookTable.getSelectionModel().select(book);
                bookTable.scrollTo(book); // Ensure the selected book is visible
                showAlert("Book Found", "Found: " + book.getTitle() + " by " + book.getAuthor());
                return; // Exit after finding the first match
            }
        }

        // If no match is found
        showAlert("No Book Found", "No book matches the query.");
    }


    private Dialog<Book> createBookDialog(Book book) {
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle(book == null ? "Add Book" : "Modify Book");

        // Create input fields
        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField isbnField = new TextField();

        if (book != null) { // Pre-fill with existing data
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            isbnField.setText(book.getISBN());
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Author:"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("ISBN:"), 0, 2);
        grid.add(isbnField, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                if(titleField.getText().isEmpty() || authorField.getText().isEmpty() || isbnField.getText().isEmpty()) {
                    showAlert("Warning: Empty data", "Please fill in all the data.");
                    return null;
                }
                else if(Long.parseLong(isbnField.getText().trim()) < 9780000000000L || Long.parseLong(isbnField.getText().trim()) > 9799999999999L) {
                    showAlert("Warning: Invalid ISBN", "Please fill in valid ISBN.");
                    return null;
                }
                else {
                    return new Book(titleField.getText(), authorField.getText(), isbnField.getText());
                }
            }
            return null;
        });

        return dialog;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}