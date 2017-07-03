package com.gb.statistics.features.ai.window;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalWindow {

    private Stage stage;

    public ModalWindow(String title, Stage mainStage, Parent parent, int width, int height) {
        Image ico = new Image("/com.gb.statistics.features.images/icon.jpg");
        stage = new Stage();
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(false);
        stage.setScene(new Scene(parent));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.setTitle(title);
        stage.getIcons().add(ico);
    }

    public Stage getStage() {
        return stage;
    }
}
