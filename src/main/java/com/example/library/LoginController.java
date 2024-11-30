package com.example.library;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

// Controller for log in scene
public class LoginController {
    private Stage stage;
    private Scene scene;

    // UI elements for managing the log in features
    @FXML
    private TextField inputUserID;

    @FXML
    private TextField inputUserPW;

    private String UserID;

    // Function to load the library scene after log in
    public void LoginSystem(ActionEvent event) throws IOException {
        // Valid the login information
        if(inputUserID.getText().isEmpty() && inputUserPW.getText().isEmpty()) {
            showAlert("Warning: Invalid User ID and Password", "Please fill in a valid User ID and Password");
        }
        else if(inputUserID.getText().isEmpty())
            showAlert("Warning: Invalid User ID", "Please fill in a valid User ID");
        else if (inputUserPW.getText().isEmpty()) {
            showAlert("Warning: Invalid User Password", "Please fill in a valid User Password");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("library-view.fxml"));

            UserID = inputUserID.getText();

            // Switch scene to the library scene
            Parent root = loader.load();
            LibraryController libController = loader.getController();
            libController.displayUser(UserID);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow(); //it retrieves the scene associate with the node and cast the window to the stage.
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    // Alert function if invalid input is detected
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
