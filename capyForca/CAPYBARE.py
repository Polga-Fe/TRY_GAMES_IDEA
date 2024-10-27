import os
from random import randint
from sys import exit
import pygame as pg
from pygame.locals import *
import back.classesDefs as classesDefs

pg.init()

#TELA
height = 600
width= 800

screen = pg.display.set_mode((width, height), pg.RESIZABLE)
pg.display.set_caption("CAPYBARE")

#CHAMAR PASTAS
sprite_sheet_image = pg.image.load('src/imgs/capy.png').convert_alpha()

#FONTE
font = pg.font.SysFont('courier new', 40)

#COLORS
soft_blue = (160,191,240)
preto = (0, 0, 0)
purple = (135, 30, 81)
branco = (255, 255, 255)

def draw_text(text, fonte, branco, x, y):
    img = fonte.render(text, True, branco)
    screen.blit(img, (x,y))

def draw_restart(screen):
    button_x, button_y, button_width, button_height = 500, 100, 200, 65
    pg.draw.rect(screen, purple, (button_x, button_y, button_width, button_height))
    restart = font.render('RESTART', True, branco)
    screen.blit(restart, (button_x + 15, button_y + 10))
    return button_x, button_y


def word_game(screen, camufleido, font, width):
    word_surface = font.render(camufleido, True, branco)
    word_width = word_surface.get_width()
    x = width - word_width - 32
    screen.blit(word_surface, (x, 200))

sprite_sheet = classesDefs.Capy(sprite_sheet_image)

frames = [sprite_sheet.get_image(i, 32, 32, 5, purple) for i in range (0,4)]
stop_frame = sprite_sheet.get_image(6.1, 32, 32, 5, purple)

#animation variable
animation_file = []
animation_steps = [2, 3, 2, 1]
current_frame = 0
frame_delay = 150 #milisseconds
last_update = pg.time.get_ticks()
x_pos = 0
y_pos = height - frames[0].get_height()
speed = 1.5

# game variable
point = 0
words = ['capy', 'elephanto']
choice_word = ''
letters = [' ','-']
letter = ''
camufleido = ''
lose = True
last_click = False
chance = 0
tries = [' ','-']

# general variable
pause = False
run = True
time = pg.time.Clock()

while run:
    # EVENTOS DO JOGO
    for event in pg.event.get():
        if event.type == QUIT:
            pg.quit()
            exit()
        
        # CLIQUE EM TECLAS
        elif event.type == pg.KEYDOWN:
            # PAUSE
            if event.key == pg.K_SPACE:
                pause = not pause
            # LEITURA DE LETRAS
            elif event.key != K_SPACE:
                letter = pg.key.name(event.key)
                print(letter)

        # REDIMENSIONA PALAVRA NA TELA SEMPRE QUE REDIMENSIONA A TELA
        elif event.type == VIDEORESIZE:
            width, height = event.size
            screen = pg.display.set_mode((width, height), pg.RESIZABLE)

        # CONSIDERA CLICK DO MOUSE PARA CHAMAR RESTART
        elif event.type == pg.MOUSEBUTTONDOWN:
            if 500 <= mouse_position_x <= 700 and 100 <= mouse_position_y <= 165:
                lose, chance, letters, letter, speed = classesDefs.Restart(camufleido, lose, chance, letter, letters, click, mouse_position_x, mouse_position_y, speed)
    
    #background
    screen.fill(soft_blue)
    time.tick(50)

    # Get current time
    now = pg.time.get_ticks()

    # UPDATE FRAME DE ACORDO COM O TEMPO == CAPY ANDANDO
    if not pause and now - last_update > frame_delay:
        last_update = now
        current_frame = (current_frame + 1) % len(frames)

    #DEFINIR PAUSE NO PAUSE
    if pause:
        draw_text(f"PAUSE", font, branco, 160, 250)
        screen.blit(stop_frame, (x_pos, y_pos))  # Use the pause frame
    else:
        x_pos += speed
        if x_pos > width:  # Reset to the left side if it goes off the right edge
            x_pos = -frames[0].get_width()
        screen.blit(frames[current_frame], (x_pos, y_pos))

    # Declarando variavel da posição do mouse
    mouse = pg.mouse.get_pos()
    mouse_position_x = mouse[0]
    mouse_position_y = mouse[1]

    # Declarando variavel do click do mouse
    click = pg.mouse.get_pressed()

    # DEFINIÇÕES JOGO
    choice_word, lose = classesDefs.random_word(words, choice_word, lose)
    camufleido = classesDefs.camuflas(choice_word, camufleido, letters)
    letters, chance = classesDefs.tentandus(letters, choice_word, letter, chance, speed)
    word_game(screen, camufleido, font, width)
    lose, chance, letters, letter, speed = classesDefs.Restart(camufleido, lose, chance, letter, letters, click, mouse_position_x, mouse_position_y, speed)

    # SE ERROS == X , APARECER BOTAO RESTART NA TELA
    if chance == 2:
        button_x, button_y = draw_restart(screen)
    
    # Click Last Status
    last_click = click[0]

    pg.display.update()
