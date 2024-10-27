import pygame as pg
import back.classesDefs as classesDefs
import back.gameDefs as gameDefs
import back.utilitys as utils
import time as time
from pygame import *

def game(screen):
    
    speed = 1.5
    pontuacao = 0
    chance = 5

    words = ['capy', 'eleph']
    choice_word = ''
    camufleido = ''
    letter = ''
    letters = [' ', '-']
    tries = [' ', '-']

    lose = True
    pause = False
    run = True
    time = pg.time.Clock()

    while run:
        for evento in pg.event.get():
            if evento.type == pg.QUIT:
                return 'sair'
            
            # TECLAS
            elif evento.type == pg.KEYDOWN:
                # PAUSE
                if evento.key == pg.K_SPACE:
                    pause = not pause
                    #if pause:

                    print(f'pause: {pause}')
                # LEITURA DE LETRAS
                elif evento.key != pg.K_SPACE:
                    letter = pg.key.name(evento.key)
                    print(letter)

            # REDIMENSIONA PALAVRA NA TELA SEMPRE QUE REDIMENSIONA A TELA
            elif evento.type == VIDEORESIZE:
                utils.width, utils.height = evento.size
                utils.screen = pg.display.set_mode((utils.width, utils.height), pg.RESIZABLE)

        screen.fill(utils.soft_blue)
        time.tick(50)

        now = pg.time.get_ticks()

        # DEFINIÇÕES JOGO
        gameDefs.word_game(screen, camufleido, font, utils.width)
        choice_word, lose = gameDefs.random_word(words, choice_word, lose)
        camufleido = gameDefs.camuflas(choice_word, camufleido, letters)
        letters, chance = gameDefs.tentandus(letters, choice_word, letter, chance, speed)
        
        if chance == 0:
            if utils.botao(screen, 'REINICIAR', 260, 60, 200, 50, utils.purple, utils.white, utils.soft_green, acao='restart'):
                gameDefs.restart(camufleido, lose, chance, letters, letter, speed)
                if 'restart':
                    return 'start'
        
        pg.display.flip()