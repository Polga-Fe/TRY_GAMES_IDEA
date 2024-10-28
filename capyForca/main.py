import os
import sys
import pygame as pg

import back.utilitys as utils

import pages.menu as menu
from pages.menu import menu

import pages.options as options
from pages.options import options

import pages.level as level
from pages.level import level

import pages.game as game
from pages.game import game

from sys import exit
from pygame.locals import *


screen = utils.screen

def main():

    cena_atual = 'menu'

   # Dicionário que mapeia cenas para funções correspondentes
    cenas = {
        'menu': menu,
        'options': options,
        'choice': level,
        'start': game
    }

    while True:
        print(f'Executando cena: {cena_atual}')

        if cena_atual == 'sair':
            pg.quit()
            sys.exit()

        # Verifica se a cena atual está no dicionário
        if cena_atual in cenas:
            cena_atual = cenas[cena_atual](screen)
        else:
            print(f"Cena inválida: {cena_atual}")
            break

if __name__ == "__main__":
    main()


        