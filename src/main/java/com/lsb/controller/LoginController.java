package com.lsb.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import com.lsb.api.Auth;
import com.lsb.util.Loader;

public class LoginController {
    @FXML
    TextField idInput;

    @FXML
    PasswordField passwordInput;

    @FXML
    Label stateLabel;
    
    @FXML
    Button loginButton;
    
    @FXML
    Button registerButton;

    @FXML
    private void login(ActionEvent e) throws IOException {
        final String id = idInput.getText();
        final String password = passwordInput.getText();

        try {
            Auth.login(id, password);
            Scene scene = new Scene(Loader.loadFXML("main"));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        }
        catch (Exception err) {
            System.err.println(err);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login error");
            alert.setHeaderText("Could not login");
            alert.setContentText("There was an error while signing in. Please try again later.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void openRegisterWindow(ActionEvent e) throws IOException {
        Scene scene = new Scene(Loader.loadFXML("register"));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
