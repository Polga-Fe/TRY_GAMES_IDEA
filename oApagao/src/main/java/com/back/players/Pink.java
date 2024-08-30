package com.back.players;

import com.back.Personx;
import com.back.SpriteSheet;

import javafx.scene.image.Image;

public class Pink extends Personx {
    private SpriteSheet spriteSheetWalking;
    private SpriteSheet spriteSheetAttack;

    public Pink(double x, double y) {
        super("Pink", x, y, 80, 80, 75, 70, 30);
        Image imageWalking = new Image(getClass().getResourceAsStream("/imgs/players/walking/player_1.png"));
        Image imageAttack = new Image(getClass().getResourceAsStream("/imgs/players/attack/player_1.png"));
        if (imageWalking.isError() || imageAttack.isError()) {
            System.err.println("Erro ao carregar a imagem para Pink.");
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
