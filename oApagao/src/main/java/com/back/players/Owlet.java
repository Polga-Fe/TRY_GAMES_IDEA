package com.back.players;

import com.back.Personx;
import com.back.SpriteSheet;

import javafx.scene.image.Image;

public class Owlet extends Personx {
    private SpriteSheet spriteSheetWalking;
    private SpriteSheet spriteSheetAttack;

    public Owlet(double x, double y) {
        super("Owlet", x, y, 150, 150, 80, 40, 60);
        Image imageWalking = new Image(getClass().getResourceAsStream("/imgs/players/walking/player_2.png"));
        Image imageAttack = new Image(getClass().getResourceAsStream("/imgs/players/attack/player_2.png"));
        if (imageWalking.isError() || imageAttack.isError()) {
            System.err.println("Erro ao carregar a imagem para Owlet.");
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
