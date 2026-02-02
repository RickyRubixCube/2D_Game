module com.group.game.dashdash {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires java.desktop;

    // This opens your main code package to the engine
    opens com.group.game.dashdash to javafx.fxml, com.almasb.fxgl.core;

    // These open your asset folders so FXGL can "see" your files
    opens assets.music to com.almasb.fxgl.all, com.almasb.fxgl.core;
    opens assets.sounds to com.almasb.fxgl.all, com.almasb.fxgl.core;
    opens assets.textures to com.almasb.fxgl.all, com.almasb.fxgl.core;

    exports com.group.game.dashdash;
}