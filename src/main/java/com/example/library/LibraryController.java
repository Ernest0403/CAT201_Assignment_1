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


// Main controller class to manage the library GUI and logic
public class LibraryController {
    // UI elements for managing the book table and other components
    @FXML private TableView<Book> bookTable; // Table displaying book information
    @FXML private TableColumn<Book, String> titleColumn; // Column for book titles
    @FXML private TableColumn<Book, String> authorColumn; // Column for authors
    @FXML private TableColumn<Book, String> isbnColumn; // Column for ISBNs
    @FXML private TableColumn<Book, String> availabilityColumn; // Column for availability status
    @FXML private TableColumn<Book, String> borrowerNameColumn; // New column for borrower names
    @FXML private Label UserDisplay; // Label to display user information

    private final Library library = new Library(); // Instance of the library to manage books
    private final ObservableList<Book> bookData = FXCollections.observableArrayList(); // Observable list for table data

    // Navigation-related attributes
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Handles logout and switches back to the login scene
    public void LogoutSystem(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login-scene.fxml")); // Load login scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get current stage
        scene = new Scene(root); // Create new scene
        stage.setScene(scene); // Set the scene
        stage.show(); // Show the updated stage
    }

    // Initialization logic for the controller
    @FXML public void initialize() {
        // Map table columns to book properties
        titleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        authorColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));
        isbnColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getISBN()));
        availabilityColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().isAvailable() ? "Available" : "Borrowed"));
        borrowerNameColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().isAvailable() ? "" : data.getValue().getBorrowerName()));

        bookTable.setItems(bookData); // Bind the observable list to the table
    }

    // Event handler for adding a new book
    @FXML private void onAddBook() {
        Dialog<Book> dialog = createBookDialog(null); // Create dialog for new book
        Optional<Book> result = dialog.showAndWait(); // Wait for user input

        result.ifPresent(book -> {
            library.addBook(book); // Add to library
            bookData.add(book); // Update observable list
        });
    }

    // Event handler for modifying a selected book
    @FXML private void onModifyBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem(); // Get selected book
        if (selectedBook == null) {
            showAlert("No Book Selected", "Please select a book to modify."); // Notify if no book selected
            return;
        }

        Dialog<Book> dialog = createBookDialog(selectedBook); // Create pre-filled dialog
        Optional<Book> result = dialog.showAndWait();

        result.ifPresent(updatedBook -> {
            // Update book details
            selectedBook.setTitle(updatedBook.getTitle());
            selectedBook.setAuthor(updatedBook.getAuthor());
            selectedBook.setISBN(updatedBook.getISBN());
            bookTable.refresh(); // Refresh table to show updated details
        });
    }

    // Event handler for borrowing a book
    @FXML private void onBorrowBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null || !selectedBook.isAvailable()) {
            showAlert("Invalid Action", "Please select an available book to borrow."); // Notify invalid action
            return;
        }

        TextInputDialog dialog = new TextInputDialog(); // Dialog for borrower's name
        dialog.setTitle("Borrow Book");
        dialog.setHeaderText("Enter Borrower Name:");
        dialog.setContentText("Borrower:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(borrower -> {
            if (borrower.isEmpty()) {
                showAlert("Invalid Borrower", "Please fill in the name of the borrower."); // Validate input
                return;
            }
            selectedBook.borrowBook(borrower); // Mark book as borrowed
            bookTable.refresh(); // Update table
        });
    }

    // Event handler for returning a borrowed book
    @FXML private void onReturnBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null || selectedBook.isAvailable()) {
            showAlert("Invalid Action", "Please select a borrowed book to return."); // Notify invalid action
            return;
        }

        selectedBook.returnBook(); // Mark book as returned
        bookTable.refresh(); // Update table
    }

    // Save the current library data to a file
    @FXML private void onSaveLibrary() {
        FileChooser fileChooser = new FileChooser(); // File chooser for saving
        File file = fileChooser.showSaveDialog(null); // Open save dialog
        if (file != null) {
            try {
                library.saveLibrary(file.getPath()); // Save library data
                showAlert("Success", "Library data saved successfully!");
            } catch (Exception e) {
                showAlert("Error", "Failed to save library: " + e.getMessage()); // Handle errors
            }
        }
    }

    // Load library data from a file
    @FXML private void onLoadLibrary() {
        FileChooser fileChooser = new FileChooser(); // File chooser for loading
        File file = fileChooser.showOpenDialog(null); // Open load dialog
        if (file != null) {
            try {
                library.loadLibrary(file.getPath()); // Load library data
                bookData.setAll(library.getBooks()); // Update table data
                showAlert("Success", "Library data loaded successfully!");
            } catch (Exception e) {
                showAlert("Error", "Failed to load library: " + e.getMessage()); // Handle errors
            }
        }
    }

    // Search for a book based on user-specified criteria
    @FXML private void onSearchBook() {
        // Dialog for search type
        ChoiceDialog<String> searchTypeDialog = new ChoiceDialog<>("Title", "Title", "Author", "ISBN");
        searchTypeDialog.setTitle("Search Book");
        searchTypeDialog.setHeaderText("Search for a Book");
        searchTypeDialog.setContentText("Search by:");

        Optional<String> searchTypeResult = searchTypeDialog.showAndWait(); // Wait for user selection
        if (searchTypeResult.isEmpty()) {
            return; // Exit if no search type selected
        }

        // Dialog for search query
        TextInputDialog queryDialog = new TextInputDialog();
        queryDialog.setTitle("Search Book");
        queryDialog.setHeaderText("Search for a Book");
        queryDialog.setContentText("Enter the " + searchTypeResult.get() + ":");

        Optional<String> queryResult = queryDialog.showAndWait();
        if (queryResult.isEmpty()) {
            return; // Exit if no query provided
        }

        String query = queryResult.get().trim(); // Trim input query

        // Search and highlight matching book
        for (Book book : bookData) {
            boolean match = switch (searchTypeResult.get().toLowerCase()) {
                case "title" -> book.getTitle().equalsIgnoreCase(query);
                case "author" -> book.getAuthor().equalsIgnoreCase(query);
                case "isbn" -> book.getISBN().equalsIgnoreCase(query);
                default -> false;
            };

            if (match) {
                bookTable.getSelectionModel().select(book); // Select the matched book
                bookTable.scrollTo(book); // Ensure visibility
                showAlert("Book Found", "Found: " + book.getTitle() + " by " + book.getAuthor());
                return;
            }
        }

        showAlert("No Book Found", "No book matches the query."); // Notify if no match found
    }

    // Utility to create a dialog for adding or modifying books
    private Dialog<Book> createBookDialog(Book book) {
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle(book == null ? "Add Book" : "Modify Book");

        // Fields for book details
        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField isbnField = new TextField();

        // Pre-fill fields for modification
        if (book != null) {
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

        // Convert user input to a Book instance
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Validate inputs
                if (titleField.getText().isEmpty() || authorField.getText().isEmpty() || isbnField.getText().isEmpty()) {
                    showAlert("Warning: Empty data", "Please fill in all the data.");
                    return null;
                }
                // Validate the ISBN input, make sure that the user enters a valid ISBN
                if (Long.parseLong(isbnField.getText().trim()) < 9780000000000L ||
                        Long.parseLong(isbnField.getText().trim()) > 9799999999999L) {
                    showAlert("Warning: Invalid ISBN", "Please fill in valid ISBN.");
                    return null;
                }
                return new Book(titleField.getText(), authorField.getText(), isbnField.getText());
            }
            return null;
        });

        return dialog;
    }

    // Utility to display alert messages
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Display user information as the title
    public void displayUser(String UserID) {
        UserDisplay.setText("Welcome back! " + UserID); // Display a personalized welcome message
    }
}
