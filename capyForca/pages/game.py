import pygame as pg
import back.defClass as classes
import back.defGame as dfGame
import back.defVisual as utils
import time as time
from pygame import *

def game(screen):
    
    #animation variable
    animation_file = []
    animation_steps = [2, 3, 2, 1]
    current_frame = 0
    frame_delay = 150 #milisseconds
    last_update = pg.time.get_ticks()
    x_pos = 0
    y_pos = utils.height - utils.frames[0].get_height()

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

    pause_game = classes.PauseGame()

    while run:
        for evento in pg.event.get():
            if evento.type == pg.QUIT:
                return 'sair'
            
            # TECLAS
            elif evento.type == pg.KEYDOWN:
                # PAUSE
                if evento.key == pg.K_SPACE:
                    pause_game.toggle_pause()

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

         # UPDATE FRAME DE ACORDO COM O TEMPO == CAPY ANDANDO
        if not pause and now - last_update > frame_delay:
            last_update = now
            current_frame = (current_frame + 1) % len(utils.frames)

        #DEFINIR PAUSE NO PAUSE
        if pause_game.paused:
            pause_action = pause_game.draw_pause_menu(screen)
            screen.blit(utils.stop_frame, (x_pos, y_pos))
            if pause_action == 'menu':
                run = False
                return 'menu'
            elif pause_action == 'options':
                return 'options'
            elif pause_action == 'return':
                pause_game.toggle_pause()  # Sai do estado de pausa
        else:
            x_pos += speed
            if x_pos > utils.width:  # Reset to the left side if it goes off the right edge
                x_pos = -utils.frames[0].get_width()
            screen.blit(utils.frames[current_frame], (x_pos, y_pos))

            # DEFINIÇÕES JOGO
            dfGame.word_game(screen, camufleido, font, utils.width)
            choice_word, lose = dfGame.random_word(words, choice_word, lose)
            camufleido = dfGame.camuflas(choice_word, camufleido, letters)
            letters, chance = dfGame.tentandus(letters, choice_word, letter, chance, speed)
            
            if chance == 0:
                if utils.botao(screen, 'REINICIAR', 260, 60, 200, 50, utils.purple, utils.white, utils.soft_green, acao='restart'):
                    dfGame.restart(camufleido, lose, chance, letters, letter, speed)
                    return 'choice'
        
        pg.display.flip()