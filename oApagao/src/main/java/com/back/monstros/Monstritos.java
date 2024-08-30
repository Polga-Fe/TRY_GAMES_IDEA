package com.back.monstros;

import com.back.Monstrx;
import com.back.SpriteSheet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Monstritos extends Monstrx {
    private final SpriteSheet spriteSheet;
    private final double speed;
    private double x;
    private double y;
    private double targetX;
    private double targetY;

    private int currentFrame = 0;
    private double animationTime = 0;
    private final double animationSpeed = 0.2; // Tempo entre frames em segundos
    private final int numFrames = 4; // Número de frames na animação

    public Monstritos(String imagePath, int frameWidth, int frameHeight, double speed, double startX, double startY) {
        super("monstrito", 50, 50, 50);
        Image spriteSheetImage = new Image(getClass().getResourceAsStream(imagePath));
        this.spriteSheet = new SpriteSheet(spriteSheetImage, frameWidth, frameHeight);
        this.speed = speed;
        this.x = startX;
        this.y = startY;
    }

    // Métodos corrigidos
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setTarget(double x, double y) {
        this.targetX = x;
        this.targetY = y;
    }

    public void update(double deltaTime) {
        // Atualiza a posição com base no target
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            dx /= distance;
            dy /= distance;

            x += dx * speed;
            y += dy * speed;
        }

        // Atualiza a animação
        animationTime += deltaTime;
        if (animationTime >= animationSpeed) {
            animationTime = 0;
            currentFrame = (currentFrame + 1) % numFrames;
        }
    }

    public void draw(GraphicsContext gc) {
        // Desenha o frame atual da animação
        WritableImage frame = spriteSheet.getFrameFromRow(0, currentFrame, 1.0, Color.TRANSPARENT);
        if (frame != null){
            gc.drawImage(frame, x, y);
        } else {
            System.out.println("nao vai ta dando");
        }
    }
}
