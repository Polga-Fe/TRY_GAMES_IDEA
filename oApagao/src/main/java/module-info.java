module com.rpgito {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.rpgito to javafx.fxml;
    opens com.back to javafx.fxml;

    exports com.rpgito;
    exports com.back;
}
