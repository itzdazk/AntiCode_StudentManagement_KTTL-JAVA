module com.example.studentscoresmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.studentscoresmanagement to javafx.fxml;
    exports com.example.studentscoresmanagement;
}