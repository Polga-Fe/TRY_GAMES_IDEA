import pygame as pg
import sys

import src.back.controler as controler
import src.styles.color_style as color_style
import src.styles.images as images

pg.init()

tela = pg.display.set_mode(( controler.larg, controler.alt ))
pg.display.set_caption('pokismonis')

mapa_atual = pg.transform.scale(images.mapa_01, (controler.mapa_larg, controler.mapa_alt))

playando = True

while playando:
    for evento in pg.event.get():
        if evento.type == pg.QUIT:
            playando = False

    # Preenche o fundo o mapa
    tela.blit(mapa_atual, (-controler.camera_x, -controler.camera_y))

    # Obter as teclas pressionadas
    teclas = pg.key.get_pressed()
    
    # Movimentação do personagem
    controler.movimentacao(teclas)

    # Desenha o círculo
    pg.draw.circle(tela, color_style.green, (controler.x - controler.camera_x, controler.y - controler.camera_y), controler.raio)

    # Atualiza a tela
    pg.display.flip()

    # Controla a taxa de quadros por segundo
    pg.time.Clock().tick(60)

# Finaliza o Pygame
pg.quit()
sys.exit()