package com.rpgito;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class optionsPage {

    @FXML
    private Slider volumeSlider;

    @FXML
    private void initialize() {
        // Configurar o volume inicial do slider
        volumeSlider.setValue(50); // Apenas um valor inicial fictício

        // Listener do slider, sem funcionalidade de áudio
        volumeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            // Aqui seria ajustado o volume do áudio se estivesse implementado
            System.out.println("Volume ajustado para: " + newValue);
        });
    }

    @FXML
    private void returnOpening() {
        App.switchToOpeningScene();
    }
}
