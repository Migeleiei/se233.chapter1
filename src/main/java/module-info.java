module se233 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens se233.chapter1 to javafx.fxml;
    exports se233.chapter1;
}