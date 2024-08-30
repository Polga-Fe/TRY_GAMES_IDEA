package com.rpgito;

import com.back.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class openingPage {
    @FXML
    private void switchToSelection() {
        App.switchToSelectionScene();
    }

    @FXML
    private void switchToOptions() {
        App.switchToOptionsScene();
    }

    @FXML
    private void exitGame() {
        Stage stage = App.getPrimaryStage();
        stage.close();
    }
}
