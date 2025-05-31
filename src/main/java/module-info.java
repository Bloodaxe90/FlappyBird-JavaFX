module com.example.flappybirdjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.flappybirdjavafx to javafx.fxml;
    exports com.example.flappybirdjavafx;
}