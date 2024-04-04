package com.lsb.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.util.Date;
import java.util.Optional;

import org.json.JSONObject;

import com.lsb.api.Memo;
import com.lsb.util.ColorConverter;
import com.lsb.util.DateConverter;
import com.lsb.util.Net;

public class EditorController {
    @FXML
    TextField titleEditor;

    @FXML
    ColorPicker colorPicker;

    @FXML
    HTMLEditor htmlEditor;

    @FXML
    Label dateLabel;

    JSONObject memo;

    @FXML
    private void initialize() {
        memo = new JSONObject();
    }

    @FXML
    private void showDeleteWindow() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Deleting the memo");
        alert.setHeaderText("Confirm deleting the memo");
        alert.setContentText("Are you sure you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if (!memo.getString("_id").isBlank()) delete();
            
            Stage stage = (Stage) titleEditor.getScene().getWindow();
            stage.close();
        }
    }

    public void close() {
        save();
    }

    public void setMemo(String _id) {
        if (_id.equals("")) {
            createMemo();
            setTitle("");
            setArticle("");
            setDate(new Date());
        }

        else {
            memo = Memo.findByID(_id);

            JSONObject timestamp = memo.getJSONObject("timestamp");

            setTitle(memo.getString("title"));
            setArticle(memo.getString("article"));
            setColor(memo.getString("color"));
            setDate(DateConverter.toDate(timestamp.getLong("_seconds")));
        }
    }

    private void setArticle(String html) {
        htmlEditor.setHtmlText(html);
    }

    private void setTitle(String text) {
        titleEditor.setText(text);
    }

    private void setColor(String str) {
        Color color = ColorConverter.toColor(str);
        colorPicker.setValue(color);
    }

    private void setDate(Date d) {
        dateLabel.setText(DateConverter.toString(d));
    }

    private void createMemo() {
        memo.put("title", "");
        memo.put("article", "");
        memo.put("color", "#FFFFFF");
        memo.put("_id", "");
    }

    private void put(String title, String article, String color) {
        memo.put("title", title);
        memo.put("article", article);
        memo.put("color", color);
    }

    private void save() {
        String title = titleEditor.getText();
        String article = htmlEditor.getHtmlText();
        String color = ColorConverter.toHexString(colorPicker.getValue());

        if (!Net.isAvailable()) return;
        if (title.isBlank() || article.isBlank()) return;
        if (
            title.equals(memo.getString("title")) &&
            article.equals(memo.getString("article")) &&
            color.equals(memo.getString("color"))
            ) return;

        put(title, article, color);

        try {
            Memo.set(memo);
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    private void delete() {
        try {
            if (!Net.isAvailable()) return;
            
            Memo.delete(memo.getString("_id"));
            com.lsb.localDB.Memo.delete(memo.getString("_id"));
        }
        catch (Exception e) {
            System.err.println(e);
        }
        
    }
}