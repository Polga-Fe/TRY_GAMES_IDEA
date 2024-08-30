package com.back;

import java.io.InputStream;

import com.back.monstros.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GameController {

    private static Image mapLevel;
    private static Image mapAtual;
    private static Image mapLevel_01;
    // private static Image mapLevel_02;
    // private static Image mapBoss_01;

    private static Image levelIcon01;
    private static Image levelIcon02;
    // private static Image bossIcon01;

    private static final double ICON_LEVEL_01_X = 125;
    private static final double ICON_LEVEL_01_Y = 210;
    private static final double ICON_LEVEL_02_X = 330;
    private static final double ICON_LEVEL_02_Y = 390;

    // private static final double ICON_BOSSLEVEL_01_X = 100;
    // private static final double ICON_BOSSLEVEL_01_Y = 580;

    private static double playerX = 400;
    private static double playerY = 300;
    private static double speed = 2.0;

    private static final int FRAME_WIDTH = 32;
    private static final int FRAME_HEIGHT = 32;
    private static final int FRAME_COUNT = 6;

    private static long lastUpdate = 0;
    private static int frameIndex = 0;

    private static final double LIGHT_RADIUS = 50; // Raio da área de visibilidade

    private static boolean moving = false;
    private static boolean isAttacking = false;

    private static SpriteSheet spritePlayerWalking;
    private static SpriteSheet spritePlayerAttack;
    private static Monstritos monstrito;
    private static Boss boss;
    private static Personx selectedCharacter;

    @FXML
    private static ProgressBar healthBar;

    private static List<Monstritos> monstros = new ArrayList<>();

    private static int currentLevel = 0;
    private static boolean dialogShown = false;

    public static void startGame(Canvas gameCanvas, ProgressBar healthBar) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        GameController.healthBar = healthBar;

        try {
            mapLevel = new Image(GameController.class.getResourceAsStream("/imgs/map/mapLevel.png"));
            mapLevel_01 = new Image(GameController.class.getResourceAsStream("/imgs/map/mapLevel_01.png"));
            //mapLevel_02 = new Image(GameController.class.getResourceAsStream("/imgs/map/mapLevel_02.png"));
            // mapBoss_01 = new Image(GameController.class.getResourceAsStream("/imgs/map/mapBoss_01.png"));

            levelIcon01 = new Image(GameController.class.getResourceAsStream("/imgs/level_icons/level_01.png"));
            levelIcon02 = new Image(GameController.class.getResourceAsStream("/imgs/level_icons/level_02.png"));

            // bossIcon01 = new Image(GameController.class.getResourceAsStream("/imgs/level_icons/boss_01.png"));

            System.out.println("Imagens carregadas com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagens: " + e.getMessage());
        }

        mapAtual = mapLevel;
        updateSprite();

        // Inicialize o monstro
        monstrito = new Monstritos("/imgs/monsters/chomp.png", 32, 32, 0.5, 10.0, 10.0);
        boss = new Boss("/imgs/monsters/momo.png", 32, 32, 1, 790, 580);


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (moving && now - lastUpdate >= 200_000_000) {
                    frameIndex = (frameIndex + 1) % FRAME_COUNT;
                    lastUpdate = now;
                }

                WritableImage frameImage = isAttacking
                    ? spritePlayerAttack.getFrame(frameIndex, 1.0, Color.TRANSPARENT)
                    : spritePlayerWalking.getFrame(frameIndex, 1.0, Color.TRANSPARENT);

                try {
                    render(gc, frameImage);

                    for (Monstritos monstrito : monstros) {
                        monstrito.setTarget(playerX, playerY);
                        monstrito.update(now);
                        if (monstritosAlive() == false){
                            adicionaBoss();
                        }
                    }
                    verificarAtaque(gc); // Verificar colisão e ataque

                    if (selectedCharacter.getHealth() <= 0) {
                        gameOver(gc);
                        stop();
                        return; // Para a execução do jogo
                    }

                    if (currentLevel == 0) {
                        if (colisaoNivel(ICON_LEVEL_01_X, ICON_LEVEL_01_Y, levelIcon01)) {
                            if (!dialogShown) {
                                dialogShown = true;
                                javafx.application.Platform.runLater(() -> {
                                    showLevelSelectionDialog(() -> {
                                        currentLevel = 1;
                                        mapAtual = mapLevel_01;
                                        geraMonstros(10);
                                    });
                                });
                            }
                        } else if (colisaoNivel(ICON_LEVEL_02_X, ICON_LEVEL_02_Y, levelIcon02)) {
                            if (!dialogShown) {
                                dialogShown = true;
                                javafx.application.Platform.runLater(() -> {
                                    showLevelSelectionDialog(() -> {
                                        // currentLevel = 2;
                                        // mapAtual = mapLevel_02;
                                        // geraMonstros(20);
                                        System.out.println("NÍVEL EM MANUTENÇÃO");
                                    });
                                });
                            }
                        } else {
                            dialogShown = false;
                        }
                    } else if ( currentLevel == 1 ) {
                        if (monstritosAlive() == false) {
                            if (boss == null) {
                                adicionaBoss(); // Adiciona o boss
                            } else if (nivelCompleto()) {
                                showVictoryMessage();
                                retornarTelaEntrada();
                                stop();
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao renderizar: " + e.getMessage());
                }
            }
        };
        timer.start();
    }

    public static void updateBarraVida() {
        if (healthBar == null) {
            // Inicializa a healthBar se estiver nula
            healthBar = new ProgressBar(); // Supondo que você esteja usando ProgressBar para a barra de vida
            System.out.println("Barra de vida foi inicializada.");
        }

        selectedCharacter = getCharacter();
        if (selectedCharacter == null) {
            throw new IllegalArgumentException("O personagem não pode ser nulo.");
        }

        double healthPercentage = selectedCharacter.getHealth() / (double) selectedCharacter.getMaxHealth();
        healthBar.setProgress(healthPercentage);
    }



    private static void render(GraphicsContext gc, WritableImage frameImage) {
    double canvasWidth = gc.getCanvas().getWidth();
    double canvasHeight = gc.getCanvas().getHeight();

    // Limpa o canvas
    gc.clearRect(0, 0, canvasWidth, canvasHeight);

    // Desenha o mapa
    gc.drawImage(mapAtual, 0, 0, canvasWidth, canvasHeight);

    if (mapAtual == mapLevel) {
        // Desenha os ícones do nível
        gc.drawImage(levelIcon01, ICON_LEVEL_01_X, ICON_LEVEL_01_Y);
        gc.drawImage(levelIcon02, ICON_LEVEL_02_X, ICON_LEVEL_02_Y);
    }
    // Desenha o monstrito, se ele existir
    for (Monstritos monstrito : monstros) {
        monstrito.draw(gc);
    }

    if (boss != null){
        boss.draw(gc);
    } else {
        System.out.println("imagem não nula, mas não");
    }

    // Cria uma imagem de máscara
    WritableImage maskImage = new WritableImage((int) canvasWidth, (int) canvasHeight);
    PixelWriter maskWriter = maskImage.getPixelWriter();

    // Preenche a máscara com a cor preta
    for (int y = 0; y < maskImage.getHeight(); y++) {
        for (int x = 0; x < maskImage.getWidth(); x++) {
            maskWriter.setColor(x, y, Color.BLACK);
        }
    }

    // Calcula a área iluminada ao redor do personagem
    double centerX = playerX + FRAME_WIDTH / 2;
    double centerY = playerY + FRAME_HEIGHT / 2;

    for (int y = (int) Math.max(0, centerY - LIGHT_RADIUS); y < Math.min(canvasHeight, centerY + LIGHT_RADIUS); y++) {
        for (int x = (int) Math.max(0, centerX - LIGHT_RADIUS); x < Math.min(canvasWidth, centerX + LIGHT_RADIUS); x++) {
            double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
            if (distance <= LIGHT_RADIUS) {
                maskWriter.setColor(x, y, Color.TRANSPARENT);
            }
        }
    }

    // Desenha a máscara no canvas
    gc.drawImage(maskImage, 0, 0);

    // Desenha a imagem do personagem
    gc.drawImage(frameImage, playerX, playerY);

    // Atualiza barra de vida
    updateBarraVida();
}


    public static void updateSprite() {
        if (selectedCharacter == null) {
        throw new IllegalStateException("Nenhum personagem selecionado.");
    }
    String nam = "";
        switch (selectedCharacter.getName()) {
            case "Pink":
                nam = "player_1";
                break;
            case "Owlet":
                nam = "player_2";
                break;
            case "Dude":
                nam = "player_3";
                break;
            default:
                System.out.println("Personagem desconhecido: " + selectedCharacter.getName());
                return;
        }
    try {
        // Verifique o nome do personagem e ajuste o caminho do arquivo se necessário
        String walkingPath = "/imgs/players/walking/" + nam + ".png";
        String attackPath = "/imgs/players/attack/" + nam + ".png";

        InputStream walkingStream = GameController.class.getResourceAsStream(walkingPath);
        InputStream attackStream = GameController.class.getResourceAsStream(attackPath);

        if (walkingStream == null) {
            System.err.println("Erro ao carregar SpriteSheet para caminhada: Caminho não encontrado: " + walkingPath);
        } else {
            spritePlayerWalking = new SpriteSheet(new Image(walkingStream), FRAME_WIDTH, FRAME_HEIGHT);
        }

        if (attackStream == null) {
            System.err.println("Erro ao carregar SpriteSheet para ataque: Caminho não encontrado: " + attackPath);
        } else {
            spritePlayerAttack = new SpriteSheet(new Image(attackStream), FRAME_WIDTH, FRAME_HEIGHT);
        }
    } catch (Exception e) {
        System.err.println("Erro ao carregar SpriteSheet: " + e.getMessage());
        e.printStackTrace();
    }
}


    public static void keyTecladoPress(KeyCode keyCode) {
        double canvasWidth = 800;
        double canvasHeight = 600;

        switch (keyCode) {
            case LEFT:
                playerX -= speed;
                moving = true;
                break;
            case RIGHT:
                playerX += speed;
                moving = true;
                break;
            case UP:
                playerY -= speed;
                moving = true;
                break;
            case DOWN:
                playerY += speed;
                moving = true;
                break;
            case SPACE:
                setAttack(true);
            default:
                moving = false;
                break;
        }
        playerX = Math.max(0, Math.min(playerX, canvasWidth - FRAME_WIDTH));
        playerY = Math.max(0, Math.min(playerY, canvasHeight - FRAME_HEIGHT));
    }

    public static void keyTecladoSolto(KeyCode keyCode) {
        if (keyCode == KeyCode.LEFT || keyCode == KeyCode.RIGHT || keyCode == KeyCode.UP || keyCode == KeyCode.DOWN) {
            moving = false;
        }
        if (keyCode == KeyCode.SPACE){
            setAttack(false);
        }
    }

    private static boolean colisaoNivel(double iconX, double iconY, Image levelIcon) {
        double iconWidth = levelIcon.getWidth();
        double iconHeight = levelIcon.getHeight();

        return playerX < iconX + iconWidth &&
                playerX + FRAME_WIDTH > iconX &&
                playerY < iconY + iconHeight &&
                playerY + FRAME_HEIGHT > iconY;
    }

    private static void showLevelSelectionDialog(Runnable onLevelSelected) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Seleção de Nível");
    alert.setHeaderText("Você encontrou um novo nível!");
    alert.setContentText("Deseja entrar neste nível?");

    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            onLevelSelected.run();
        }
    });
    }

    public static void setCharacter(Personx character) {
        if (character == null) {
            throw new IllegalArgumentException("O personagem não pode ser nulo.");
        }
        selectedCharacter = character;
        updateSprite();
    }

    public static Personx getCharacter() {
        return selectedCharacter;
    }

    public static int geraMonstros(int quantidade){
        Random random = new Random();
        int mapWidth = 800; // Largura do mapa, ajustar conforme necessário
        int mapHeight = 600; // Altura do mapa, ajustar conforme necessário

        for (int i = 0; i < quantidade; i++) {
            double x = random.nextDouble() * (mapWidth - 32); // Gera coordenadas aleatórias para X
            double y = random.nextDouble() * (mapHeight - 32); // Gera coordenadas aleatórias para Y

        Monstritos monstrito = new Monstritos("/imgs/monsters/chomp.png", 32, 32, 0.5, x, y);
        monstros.add(monstrito);
    }
        return quantidade;
    }

    public static boolean monstritosAlive() {
        boolean state = false;

        for (Monstritos monstrito : monstros){
            if (monstrito != null && monstrito.getHealth() > 0){
                state = true;
            }
        }
        return state;
    }

    public static void adicionaBoss(){
        if (boss == null){
            boss = new Boss("/imgs/monsters/momo.png", 32, 32, 1, 790, 580);
            boss.setTarget(playerX, playerY);
        }
    }

    public static boolean bossAlive() {
        return boss != null && boss.getHealth() > 0;
    }

    private static boolean nivelCompleto() {
        return !monstritosAlive() && !bossAlive();
    }

    private static void retornarTelaEntrada() {
        currentLevel = 0;
        mapAtual = mapLevel;
        playerX = 400;
        playerY = 300;
    }

    private static void showVictoryMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vitória");
        alert.setHeaderText(null);
        alert.setContentText("Parabéns! Você derrotou o Boss e venceu o nível!");
        alert.showAndWait();
    }

    public static void gameOver(GraphicsContext gc) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        // Limpa o canvas
        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        // Desenha a mensagem de Game Over
        gc.setFill(Color.BLACK);
        gc.setFont(javafx.scene.text.Font.font("Courie New", 40));
        gc.fillText("G A M E   O V E R", canvasWidth / 2 - 150, canvasHeight / 2);

    }

    public static void setAttack(boolean attack) {
        isAttacking = attack;
    }

    public static int getDamageChar(){
        int damageChar;

        if (selectedCharacter.getAtk() <= monstrito.getDef()){
            damageChar = 1;
        } else {
            damageChar = selectedCharacter.getAtk() - monstrito.getDef();
        }

        return damageChar;
    }

    public static int getDamageMonster(){
        int damageMonster;

        if (monstrito.getAtk() <= selectedCharacter.getDef()){
            damageMonster = 1;
        } else {
            damageMonster = monstrito.getAtk() - selectedCharacter.getDef();
        }
        return damageMonster;
    }

    private static void verificarAtaque(GraphicsContext gc) {
        if (!isAttacking || monstrito == null || selectedCharacter == null) {
            return;
        }

        for (Monstritos monstrito : monstros) {
            if (monstrito == null || monstrito.getHealth() <= 0) {
                continue; // Pula monstros já derrotados
            }

            double distX = playerX - monstrito.getX();
            double distY = playerY - monstrito.getY();
            double distance = Math.sqrt(distX * distX + distY * distY);

            System.out.println("Distância calculada: " + distance);
            System.out.println("vida personagem: " + selectedCharacter.getHealth());

            if (distance < 50) { // Distância de ataque personagem
                monstrito.setHealth(monstrito.getHealth() - getDamageChar());
                System.out.println("Dano aplicado ao monstrito. Vida restante: " + monstrito.getHealth());

                if (distance <= 25) { // Distância de ataque monstro
                    selectedCharacter.setHealth(selectedCharacter.getHealth() - getDamageMonster());
                    System.out.println("Dano aplicado ao player. Vida restante: " + selectedCharacter.getHealth());
                    }
                    if (selectedCharacter.getHealth() <= 0) {
                        System.out.println("Player derrotado!");
                        gameOver(gc);
                    }
                if (monstrito.getHealth() <= 0) {
                    System.out.println("Monstrito derrotado!");
                    monstros.remove(monstrito);
                }
            }
        }
        updateBarraVida();
        setAttack(false);
    }
}


