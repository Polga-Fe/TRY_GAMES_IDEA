private static void render(GraphicsContext gc, WritableImage frameImage) {

    // Desenhar o mapa desfocado
    gc.setEffect(gaussianBlur);
    gc.drawImage(mapAtual, 0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    gc.setEffect(null);  // Remover o efeito para o restante dos elementos

    // Criar uma máscara preta sobre todo o mapa
    Canvas maskCanvas = new Canvas(gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    GraphicsContext maskGC = maskCanvas.getGraphicsContext2D();
    maskGC.setFill(Color.BLACK);

    maskGC.fillRect(0, 0, maskCanvas.getWidth(), maskCanvas.getHeight());

    // Limpar a área ao redor do personagem para simular a luz
    maskGC.setFill(Color.TRANSPARENT);
    maskGC.setEffect(null); // Sem desfoque para a área iluminada

    // Desenhar a área iluminada como um círculo
    maskGC.setFill(Color.BLACK);
    maskGC.fillOval(playerX + FRAME_WIDTH / 2 - LIGHT_RADIUS,
                    playerY + FRAME_HEIGHT / 2 - LIGHT_RADIUS,
                    2 * LIGHT_RADIUS, 2 * LIGHT_RADIUS);

    // Adicionar um efeito de desfoque à área iluminada
    maskGC.setEffect(new GaussianBlur(BLUR_RADIUS));
    maskGC.fillOval(playerX + FRAME_WIDTH / 2 - LIGHT_RADIUS,
                    playerY + FRAME_HEIGHT / 2 - LIGHT_RADIUS,
                    2 * LIGHT_RADIUS, 2 * LIGHT_RADIUS);

    // Remover o efeito de desfoque
    maskGC.setEffect(null);

    // Desenhar a máscara preta com a área iluminada sobre o mapa
    gc.drawImage(maskCanvas.snapshot(null, null), 0, 0);

    // Desenhar o personagem na tela
    gc.drawImage(frameImage, playerX, playerY);
}

    private void selectCharacter(Personx character) {
        if (character == null) {
            System.out.println("Personagem não encontrado.");
            return;
        }

        String characterSpriteSheet = "";
        switch (character.getName()) {
            case "Pink":
                characterSpriteSheet = "player_1-walking";
                break;
            case "Owlet":
                characterSpriteSheet = "player_2-walking";
                break;
            case "Dude":
                characterSpriteSheet = "player_3-walking";
                break;
        }
        GameController.selectedCharacter = characterSpriteSheet;

        // Atualizar coordenadas do personagem selecionado (caso necessário)
        System.out.println("Coordenadas de " + character.getName() + ": X=" + character.getX() + ", Y=" + character.getY());

        App.switchToGameScene();

3. Geração de Monstros em Padrões ou Regiões Específicas

        public class GameController {
    private static final Random random = new Random();

    public static void startGame(Canvas gameCanvas) {
        // Inicializa o jogo
        // ...

        // Gera monstros em regiões específicas
        for (int i = 0; i < NUM_MONSTROS; i++) {
            int x, y;
            if (i % 2 == 0) {
                // Região 1
                x = random.nextInt((int) (gameCanvas.getWidth() / 2));
                y = random.nextInt((int) (gameCanvas.getHeight() / 2));
            } else {
                // Região 2
                x = random.nextInt((int) (gameCanvas.getWidth() / 2)) + (int) (gameCanvas.getWidth() / 2);
                y = random.nextInt((int) (gameCanvas.getHeight() / 2)) + (int) (gameCanvas.getHeight() / 2);
            }

            double speed = 1 + random.nextDouble() * 2;
            int spriteIndex = random.nextInt(3);

            String spritePath = switch (spriteIndex) {
                case 0 -> "/imgs/monsters/monster1.png";
                case 1 -> "/imgs/monsters/monster2.png";
                default -> "/imgs/monsters/monster3.png";
            };

            Monstritos monstro = new Monstritos(spritePath, 100, 100, 2, speed, x, y, 2);
            monstros.add(monstro);
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGameLogic();
                render(gc);
            }
        };
        timer.start();
    }
}

4. Geração de Monstros com Base em Eventos

public class GameController {
    private static List<Monstritos> monstros = new ArrayList<>();
    private static final Random random = new Random();

    public static void startGame(Canvas gameCanvas) {
        // Inicializa o jogo
        // ...

        // Gera monstros inicialmente
        generateMonsters();

        AnimationTimer timer = new AnimationTimer() {
            private long lastMonsterGeneration = 0;
            private final long monsterGenerationInterval = 5000000000L; // 5 segundos

            @Override
            public void handle(long now) {
                updateGameLogic();
                render(gc);

                // Gera novos monstros a cada intervalo
                if (now - lastMonsterGeneration > monsterGenerationInterval) {
                    generateMonsters();
                    lastMonsterGeneration = now;
                }
            }
        };
        timer.start();
    }

    private static void generateMonsters() {
        int numMonstros = random.nextInt(5) + 1; // Gera entre 1 e 5 monstros
        for (int i = 0; i < numMonstros; i++) {
            int x = random.nextInt((int) gameCanvas.getWidth());
            int y = random.nextInt((int) gameCanvas.getHeight());
            double speed = 1 + random.nextDouble() * 2;
            int spriteIndex = random.nextInt(3);

            String spritePath = switch (spriteIndex) {
                case 0 -> "/imgs/monsters/monster1.png";
                case 1 -> "/imgs/monsters/monster2.png";
                default -> "/imgs/monsters/monster3.png";
            };

            Monstritos monstro = new Monstritos(spritePath, 100, 100, 2, speed, x, y, 2);
            monstros.add(monstro);
        }
    }
}

private static void handleAttack() {
        if (isMonstroInRange(monstrito)) {
            int playerAttack = getPlayerAttack();
            int monstroDef = monstrito.getMonsterDef();
            int damage = playerAttack - monstroDef;
            if (damage > 0) {
                monstrito.receiveDamage(damage);
            }
        }
    }

    private static boolean isMonstroInRange(Monstritos monstro) {
        double distance = Math.sqrt(Math.pow(monstro.getX() - playerX, 2) + Math.pow(monstro.getY() - playerY, 2));
        return distance < LIGHT_RADIUS - 10; // Distância para verificar se o monstro está no alcance
    }

    private static int getPlayerAttack() {
        // Implementar a lógica para calcular o ataque do jogador
        return 10; // Valor de ataque padrão
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
