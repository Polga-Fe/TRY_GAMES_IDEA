package com.back.players;

import com.back.Personx;
import com.back.SpriteSheet;

import javafx.scene.image.Image;

public class Dude extends Personx {
    private SpriteSheet spriteSheetWalking;
    private SpriteSheet spriteSheetAttack;

    public Dude(double x, double y) {
        super("Dude", x, y, 100, 100, 70, 45, 45);
        Image imageWalking = new Image(getClass().getResourceAsStream("/imgs/players/walking/player_3.png"));
        Image imageAttack = new Image(getClass().getResourceAsStream("/imgs/players/attack/player_3.png"));
        if (imageWalking.isError() || imageAttack.isError()) {
            System.err.println("Erro ao carregar a imagem para Dude.");
        }
        this.spriteSheetWalking = new SpriteSheet(imageWalking, 32, 32);
        this.spriteSheetAttack = new SpriteSheet(imageAttack, 32, 32);
    }

    public SpriteSheet getSpriteSheetWalking() {
        return spriteSheetWalking;
    }

    public SpriteSheet getSpriteSheetAttack() {
        return spriteSheetAttack;
    }
}
