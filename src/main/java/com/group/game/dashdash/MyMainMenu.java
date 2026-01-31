package com.group.game.dashdash;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MyMainMenu extends FXGLMenu {

    public MyMainMenu() {
        super(MenuType.MAIN_MENU);

        Rectangle bg = new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight(), Color.BLACK);
        getContentRoot().getChildren().add(bg);

        Text title = FXGL.getUIFactoryService().newText("DASH DASH", Color.WHITE, 60);
        title.setTranslateX(FXGL.getAppWidth() / 2.0 - 150);
        title.setTranslateY(150);
        getContentRoot().getChildren().add(title);

        // --- ENDLESS MODE BUTTON ---
        var btnEndless = FXGL.getUIFactoryService().newButton("ENDLESS MODE");
        btnEndless.setTranslateX(FXGL.getAppWidth() / 2.0 - 100);
        btnEndless.setTranslateY(300);
        btnEndless.setOnAction(e -> {
            FXGL.set("mode", GameMode.Endless);
            fireNewGame();
        });

        // --- CLASSIC MODE (LEVELS) ---
        Text classicText = FXGL.getUIFactoryService().newText("CLASSIC LEVELS", Color.GRAY, 30);
        classicText.setTranslateX(FXGL.getAppWidth() / 2.0 - 100);
        classicText.setTranslateY(400);

        getContentRoot().getChildren().addAll(btnEndless, classicText);

        // Create 3 Level Buttons
        for (int i = 1; i <= 3; i++) {
            int levelNum = i;
            var btnLevel = FXGL.getUIFactoryService().newButton("Level " + levelNum);
            btnLevel.setTranslateX(FXGL.getAppWidth() / 2.0 - 250 + (i * 120));
            btnLevel.setTranslateY(450);

            btnLevel.setOnAction(e -> {
                FXGL.set("mode", GameMode.Classic);
                FXGL.set("level", levelNum);
                fireNewGame();
            });

            getContentRoot().getChildren().add(btnLevel);
        }

        // --- DEBUG SOUND TEST BUTTON ---
        // Add this to verify if the engine can play your files
// Locate the DEBUG button section in MyMainMenu constructor
        var btnDebug = FXGL.getUIFactoryService().newButton("DEBUG: PLAY TTEN");
        btnDebug.setTranslateX(20);
        btnDebug.setTranslateY(FXGL.getAppHeight() - 60);
        btnDebug.setOnAction(e -> {
            var loader = FXGL.getAssetLoader();
            try {
                var music = loader.loadMusic("TTEN.wav");
                if (music == null) {
                    System.out.println("Loader returned NULL - File definitely missing.");
                } else {
                    FXGL.getAudioPlayer().playMusic(music);
                    System.out.println("Debug: Success - Music object created!");
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        });
        getContentRoot().getChildren().add(btnDebug);
    }
}