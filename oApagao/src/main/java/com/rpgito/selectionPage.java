package com.rpgito;

import com.back.GameController;
import com.back.Personx;
import com.back.players.Dude;
import com.back.players.Owlet;
import com.back.players.Pink;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class selectionPage {

    @FXML
    private ImageView pinkMonsterImage;
    @FXML
    private ImageView owletMonsterImage;
    @FXML
    private ImageView dudeMonsterImage;

    @FXML
    private Label pinkMonsterName;
    @FXML
    private Label pinkMonsterHealth;
    @FXML
    private Label pinkMonsterMana;
    @FXML
    private Label pinkMonsterAtk;
    @FXML
    private Label pinkMonsterDef;

    @FXML
    private Label owletMonsterName;
    @FXML
    private Label owletMonsterHealth;
    @FXML
    private Label owletMonsterMana;
    @FXML
    private Label owletMonsterAtk;
    @FXML
    private Label owletMonsterDef;

    @FXML
    private Label dudeMonsterName;
    @FXML
    private Label dudeMonsterHealth;
    @FXML
    private Label dudeMonsterMana;
    @FXML
    private Label dudeMonsterAtk;
    @FXML
    private Label dudeMonsterDef;

    @FXML
    private Button selectPinkMonsterButton;
    @FXML
    private Button selectOwletMonsterButton;
    @FXML
    private Button selectDudeMonsterButton;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        // Carregar e definir informações dos personagens
        setCharacter(new Pink(100, 150), pinkMonsterImage, pinkMonsterName, pinkMonsterHealth, pinkMonsterMana, pinkMonsterAtk, pinkMonsterDef);
        setCharacter(new Owlet(200, 250), owletMonsterImage, owletMonsterName, owletMonsterHealth, owletMonsterMana, owletMonsterAtk, owletMonsterDef);
        setCharacter(new Dude(300, 350), dudeMonsterImage, dudeMonsterName, dudeMonsterHealth, dudeMonsterMana, dudeMonsterAtk, dudeMonsterDef);

        // Configurar ações dos botões
        selectPinkMonsterButton.setOnAction(event -> selectCharacter(new Pink(100, 150)));
        selectOwletMonsterButton.setOnAction(event -> selectCharacter(new Owlet(200, 250)));
        selectDudeMonsterButton.setOnAction(event -> selectCharacter(new Dude(300, 350)));

        // Configurar o botão de voltar
        backButton.setOnAction(event -> App.switchToOpeningScene());
    }

    private void setCharacter(Personx character, ImageView imageView, Label nameLabel, Label healthLabel, Label manaLabel, Label atkLabel, Label defLabel) {
        if (character == null) {
            throw new IllegalArgumentException("O personagem não pode ser nulo.");
        }

        String imagePath = "/imgs/players/profile/" + character.getName() + "_Monster.png";
        Image characImage = new Image(getClass().getResourceAsStream(imagePath));
        imageView.setImage(characImage);

        nameLabel.setText("NOME: " + character.getName());
        healthLabel.setText("VIDA: " + character.getHealth());
        manaLabel.setText("MANA: " + character.getMana());
        atkLabel.setText("ATAQUE: " + character.getAtk());
        defLabel.setText("DEFESA: " + character.getDef());
    }

    private void selectCharacter(Personx character) {
        if (character == null) {
            System.out.println("Personagem não encontrado.");
            return;
        }

            GameController.setCharacter(character);
            GameController.updateBarraVida();

        App.switchToGameScene();
    }
}
