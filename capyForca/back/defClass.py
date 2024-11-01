import random
from random import randint
import pygame as pg
import back.defVisual as utils
from pygame import *

# CREATE CAPY
class SpriteSheet():
    def __init__(self, image):
        self.sheet = image
    
    def get_image(self, frame, height, width, scale, colour):
        image = pg.Surface((height, width)).convert_alpha()
        image.blit(self.sheet, (0,0), ((frame*height), 0, height, width))
        image = pg.transform.scale(image,(height*scale, width*scale))
        image.set_colorkey(colour)
        return image

class PauseGame:
    def __init__(self):
        self.paused = False

    def toggle_pause(self):
        self.paused = not self.paused

    def draw_pause_menu(self, screen):
        if self.paused:
            # Desenha o texto "PAUSED" na tela
            utils.draw_text(screen, "PAUSED", utils.font, utils.white, 160, 250)

            # Dicionário de ações para cada botão do menu
            acoes = {
                'return': 'return',
                'config': 'options',
                'return menu': 'menu'
            }

            # Desenha os botões e verifica se algum é clicado
            for i, (texto, acao) in enumerate(acoes.items()):
                if utils.botao(screen, texto, 300, 200 + i * 100, 200, 50, utils.purple, utils.white, utils.soft_blue, acao):
                    return acao
