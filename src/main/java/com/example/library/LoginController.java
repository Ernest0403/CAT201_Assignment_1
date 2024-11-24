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

public class LoginController {
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField inputUserID;

    @FXML
    private TextField inputUserPW;

    private String UserID;

    public void LoginSystem(ActionEvent event) throws IOException {
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

            Parent root = loader.load();
            LibraryController libController = loader.getController();
            libController.displayUser(UserID);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
