import random
from random import randint
import pygame as pg
from pygame import *

# CREATE CAPY
class Capy():
    def __init__(self, image):
        self.sheet = image
    
    def get_image(self, frame, height, width, scale, colour):
        image = pg.Surface((height, width)).convert_alpha()
        image.blit(self.sheet, (0,0), ((frame*height), 0, height, width))
        image = pg.transform.scale(image,(height*scale, width*scale))
        image.set_colorkey(colour)

        return image

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
            chance += 1
            speed -= 1
    return tries, chance

def Restart(camufleido, lose, chance, letter, letters, click, mouse_position_x, mouse_position_y, speed):
    if click[0]:
        if mouse_position_x >= 500 and mouse_position_x <= 700 and mouse_position_y >= 100 and mouse_position_y <= 165:
            print('Restart button clicked')
            letters = [' ', '-']
            lose = True
            chance = 0
            letter = ' '
            speed = 1.5
            print('Values reset')
    return lose, chance, letters, letter, speed
