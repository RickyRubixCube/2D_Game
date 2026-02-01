module com.group.game.dashdash {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires annotations;


    // This allows FXGL to see your code
    opens com.group.game.dashdash to javafx.fxml, com.almasb.fxgl.core;

    // This allows FXGL to see your music files
    // Use 'all' instead of just 'core' to be safe with version 17.3
    opens assets.music to com.almasb.fxgl.all, com.almasb.fxgl.core;

    exports com.group.game.dashdash;
}