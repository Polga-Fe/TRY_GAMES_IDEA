import random
from random import randint
import pygame as pg
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
