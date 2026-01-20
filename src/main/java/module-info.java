module com.example._d_game {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example._d_game to javafx.fxml;
    exports com.example._d_game;
}