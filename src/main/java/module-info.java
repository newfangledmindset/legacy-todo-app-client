module com.lsb {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jdk.jsobject;
    requires org.json;
    requires org.jsoup;
    requires org.xerial.sqlitejdbc;

    opens com.lsb.controller to javafx.fxml, javafx.web;
    
    exports com.lsb;
}
