import pygame as pg
import random
import back.classesDefs as classesDefs
import back.utilitys as utils
import time as time

#animation variable
animation_file = []
animation_steps = [2, 3, 2, 1]
current_frame = 0
frame_delay = 150 #milisseconds
last_update = pg.time.get_ticks()
x_pos = 0
y_pos = utils.height - utils.frames[0].get_height()

def word_game(screen, camufleido, font, width):
    word_surface = utils.font.render(camufleido, True, utils.white)
    word_width = word_surface.get_width()
    x = width - word_width - 32
    screen.blit(word_surface, (x, 200))

def random_word(words, choice_word, lose):
    if lose:
        word_n = random.randint(0, len(words)-1)
        choice_word = words[word_n]
        lose = False
    return choice_word, lose
    
def camuflas(choice_word, camufleido, tries):
    camufleido = choice_word
    for n in range(len(choice_word)):
        if choice_word[n] not in tries:
            camufleido = camufleido[:n]+ '*' + camufleido[n+1:]
    return camufleido
    
def tentandus(tries, choice_word, letter, chance, speed):
    if letter not in tries:
        tries.append(letter)
        if letter not in choice_word:
            chance -= 1
            speed -= 1
            print(f'chances: {chance}')
            print(f'tentadas: {tries}')
    return tries, chance

def pause(screen, speed, pause):
    if pause:
        utils.draw_text(screen, "PAUSE", utils.font, utils.white, 160, 250)
        screen.blit(utils.stop_frame, (x_pos, y_pos))  # Use the pause frame
    else:
        x_pos += speed
        if x_pos > utils.width:  # Reset to the left side if it goes off the right edge
            x_pos = -utils.frames[0].get_width()
        screen.blit(utils.frames[current_frame], (x_pos, y_pos))
        
def restart(camufleido, lose, chance, letter, letters, speed):
    letters = [' ', '-']
    lose = True
    chance = 0
    letter = ' '
    speed = 1.5
    print('Values reset')
    return lose, chance, letters, letter, speed


