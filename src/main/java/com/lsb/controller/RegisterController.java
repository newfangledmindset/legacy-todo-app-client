package com.lsb.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import com.lsb.api.Auth;
import com.lsb.exception.DuplicateIDException;
import com.lsb.util.Loader;

public class RegisterController {
    @FXML
    TextField idInput, nameInput;

    @FXML
    PasswordField passwordInput;

    @FXML
    Button submitButton;

    @FXML
    private void submit() {
        final String id = idInput.getText();
        final String password = passwordInput.getText();
        final String name = nameInput.getText();

        try {
            Auth.register(id, password, name);

            Scene scene = new Scene(Loader.loadFXML("main"));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) submitButton.getScene().getWindow();
            currentStage.close();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Signed up!");
            alert.setHeaderText("Signed up successfully");
            alert.setContentText("Welcome to Todo-App!");
            alert.showAndWait();
        }
        catch (DuplicateIDException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Sign up error");
            alert.setHeaderText("Could not sign up");
            alert.setContentText("The ID is used by someone.");
            alert.showAndWait();
        }
        catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Sign up error");
            alert.setHeaderText("Could not sign up");
            alert.setContentText("There was an error while signing up. Please try again later.");
            alert.showAndWait();
        }
    }
}
