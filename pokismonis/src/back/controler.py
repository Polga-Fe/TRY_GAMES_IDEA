import pygame as pg

# Tamanhos da tela e mapa
larg, alt = 800, 600
mapa_larg, mapa_alt = 1600,1200

# Variáveis de câmera
camera_x, camera_y = 0, 0 

# Posições iniciais do círculo
x = mapa_larg // 2
y = mapa_alt // 2
raio = 24                       # alterar conforme tamanho do sprite
velocidad = 5

# Controla movimentos do personagem
def movimentacao(teclas):
    global x, y, camera_x, camera_y  # Declara as variáveis globais
    
    if teclas[pg.K_w] and y - velocidad - raio >= 0:  
        y -= velocidad
    if teclas[pg.K_s] and y + velocidad + raio <= mapa_alt:
        y += velocidad
    if teclas[pg.K_a] and x - velocidad - raio >= 0:
        x -= velocidad
    if teclas[pg.K_d] and x + velocidad + raio <= mapa_larg:
        x += velocidad

    # Atualizar a posição da câmera para centralizar o jogador
    # Limitar a posição da câmera para que ela não ultrapasse as bordas do mapa
    camera_x = max(0, min(x - larg // 2, mapa_larg - larg))
    camera_y = max(0, min(y - alt // 2, mapa_alt - alt))