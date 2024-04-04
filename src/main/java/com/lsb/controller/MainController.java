package com.lsb.controller;

import java.io.IOException;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import netscape.javascript.JSObject;

import com.lsb.api.Memo;
import com.lsb.util.Net;

public class MainController{
    @FXML
    TextField searchField;

    @FXML
    WebView webView;

    private JSBridge jsBridge;

    @FXML
    private void initialize() {
        refresh();
    }

    @FXML
    private void search(ActionEvent e) {
        String keyword = searchField.getText();

        if (keyword.isBlank() || keyword.isEmpty()) Memo.reset();
        else Memo.search(keyword);

        show();
    }

    public void refresh() {
        Memo.refresh();
        show();
    }


    private void show() {
        final WebEngine engine = webView.getEngine();

        engine.setJavaScriptEnabled(true);
        engine.setUserStyleSheetLocation(getClass().getResource("/com/lsb/mainView.css").toString());
        engine.documentProperty().addListener((v, o, n) -> {
            if (n != null) {
                Document document = engine.getDocument();
                Element board = document.getElementById("container");
                
                while(board.getFirstChild() != null) {
                    board.removeChild(board.getFirstChild());
                }
                
                jsBridge = new JSBridge();


                if (Net.isAvailable()) {
                    Element newMemo = document.createElement("div");
                    newMemo.setAttribute("class", "content-item new");
                    newMemo.setTextContent("New memo...");
                    board.appendChild(newMemo);
                    newMemo.setAttribute("onclick", "app.click('')");
                }
                
                
                for (Object obj : Memo.get()) {
                    JSONObject body = (JSONObject) obj;

                    String title = body.getString("title");
                    String article = body.getString("article");
                    String color = body.getString("color");
                    String _id = body.getString("_id");

                    Element content = document.createElement("div");
                    content.setAttribute("id", _id);
                    content.setAttribute("class", "content-item");
                    content.setAttribute("style", "background-color: " + color + ";");

                    Element contentTitle = document.createElement("div");
                    contentTitle.setAttribute("id", _id + "-title");
                    contentTitle.setAttribute("class", "content-item-title");

                    Element contentArticle = document.createElement("div");
                    contentArticle.setAttribute("id", _id + "-article");
                    contentArticle.setAttribute("class", "content-item-article");

                    Element hr = document.createElement("hr");
                    
                    content.appendChild(contentTitle);
                    content.appendChild(hr);
                    content.appendChild(contentArticle);

                    board.appendChild(content);
                    
                    engine.executeScript(
                        "document.getElementById('" + _id + "-title')"
                        + ".innerHTML = '" + title + "';"
                        +
                        "document.getElementById('" + _id + "-article')"
                        + ".innerHTML = '" + article + "';"
                    );

                    content.setAttribute("onclick", "app.click('" + _id + "')");
                }

                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("app", jsBridge);
            }
        });
        
        engine.loadContent("<html>"
            + "<body><div id='container' onclick='\"java.click()\"'></div></body>"
            + "</html>");
    }

    public class JSBridge {
        public void click(String _id) throws IOException {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lsb/editor.fxml")); 

                Parent root = (Parent)fxmlLoader.load();          
                EditorController controller = fxmlLoader.<EditorController>getController();
                
                controller.setMemo(_id);

                Scene scene = new Scene(root); 
                Stage stage = new Stage();

                stage.setScene(scene);    
                stage.setOnHidden(e -> {
                    controller.close();
                    refresh();
                });
                stage.show();
            }
            catch (Exception e) {
                System.err.println(e);
            }
             
        }
    }
}