package com.back;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class SpriteSheet {
    private final Image spriteSheet;
    private final int frameWidth;
    private final int frameHeight;

    public SpriteSheet(Image spriteSheet, int frameWidth, int frameHeight) {
        this.spriteSheet = spriteSheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }

    public WritableImage getFrame(int index, double scale, javafx.scene.paint.Color backgroundColor) {
        int columns = (int) (spriteSheet.getWidth() / frameWidth);
        int row = index / columns;
        int col = index % columns;
        PixelReader pixelReader = spriteSheet.getPixelReader();
        WritableImage frame = new WritableImage(pixelReader, col * frameWidth, row * frameHeight, frameWidth, frameHeight);
        return frame;
    }

    public WritableImage getFrameFromRow(int rowIndex, int frameIndex, double scale, javafx.scene.paint.Color backgroundColor) {
        int columns = (int) (spriteSheet.getWidth() / frameWidth);
        PixelReader pixelReader = spriteSheet.getPixelReader();
        WritableImage frame = new WritableImage(pixelReader, frameIndex * frameWidth, rowIndex * frameHeight, frameWidth, frameHeight);
        return frame;
    }
}
