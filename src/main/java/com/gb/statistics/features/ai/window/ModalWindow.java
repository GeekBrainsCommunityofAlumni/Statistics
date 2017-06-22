package com.gb.statistics.features.ai.window;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalWindow {

    private Stage stage;

    public ModalWindow(String title, Stage mainStage, Parent parent, int width, int height) {
        stage = new Stage();
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(false);
        stage.setScene(new Scene(parent));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.setTitle(title);
    }

    public Stage getStage() {
        return stage;
    }
}
