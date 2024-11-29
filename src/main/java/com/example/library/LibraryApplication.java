package com.example.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point for the Library Management Application.
 * This class extends the JavaFX `Application` class and is responsible for
 * launching the application, initializing the UI, and setting up the main stage.
 */
public class LibraryApplication extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param stage The primary stage for the application where scenes are set.
     * @throws IOException If there is an issue loading the FXML resource.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file for the login scene
        Parent root = FXMLLoader.load(getClass().getResource("login-scene.fxml"));

        // Create a new scene with the loaded FXML
        Scene scene = new Scene(root);

        // Set the scene on the primary stage
        stage.setScene(scene);

        // Show the primary stage
        stage.show();
    }

    /**
     * Main method, serves as the program's entry point.
     *
     * @param args Command-line arguments passed to the program.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();
    }
}
