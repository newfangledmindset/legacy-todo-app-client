package com.lsb.util;

import java.io.IOException;

import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import com.lsb.App;

public class Loader {
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
