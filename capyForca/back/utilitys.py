import pygame as pg
from pygame.locals import *
import pages.options as options
import back.classesDefs as classes
import json
import os
import sys

pg.init()

# IMAGENS
height = 600
width = 800

screen = pg.display.set_mode((width, height), pg.RESIZABLE)
pg.display.set_caption("CAPYBARE")

#FONTE
font = pg.font.SysFont('courier new', 40)

#COLORS
soft_blue = (160,191,240)
soft_green = (152, 251, 203)
black = (0, 0, 0)
purple = (135, 30, 81)
white = (255, 255, 255)

# SPRITES
sprite_sheet_chap = pg.image.load('src/imgs/capy.png').convert_alpha()

sprite_sheet_capy = classes.SpriteSheet(sprite_sheet_chap)

frames = [sprite_sheet_capy.get_image(i, 32, 32, 5, purple) for i in range (0,4)]
stop_frame = sprite_sheet_capy.get_image(6.1, 32, 32, 5, purple)

# TEXTOS
def draw_text(screen, text, fonte, cor, x, y):
    img = fonte.render(text, True, white)
    screen.blit(img, (x,y))

def draw_restart(screen):
    button_x, button_y, button_width, button_height = 500, 100, 200, 65
    pg.draw.rect(screen, purple, (button_x, button_y, button_width, button_height))
    restart = font.render('RESTART', True, white)
    screen.blit(restart, (button_x + 15, button_y + 10))
    return button_x, button_y

# CAIXAS
def botao(screen, texto, pos_x, pos_y, largura, altura, cor_botao, cor_texto, cor_hover, acao=None, border_radius=4):
    mouse = pg.mouse.get_pos()
    clique = False
    
    # Verifica se o mouse está sobre o botão
    if pos_x + largura > mouse[0] > pos_x and pos_y + altura > mouse[1] > pos_y:
        pg.draw.rect(screen, cor_hover, (pos_x, pos_y, largura, altura), border_radius=border_radius)
        
        # Detecta o clique inicial
        for evento in pg.event.get():
            if evento.type == pg.MOUSEBUTTONDOWN and evento.button == 1:
                clique = True
    else:
        pg.draw.rect(screen, cor_botao, (pos_x, pos_y, largura, altura), border_radius=border_radius)

    texto_surface = font.render(texto, True, cor_texto)
    texto_rect = texto_surface.get_rect(center=(pos_x + largura // 2, pos_y + altura // 2))
    screen.blit(texto_surface, texto_rect)

    return clique

def checkBox(screen, configuracoes):
    pg.draw.rect(screen, white, (200, 150, 400, 300))
    # Desenha o texto
    texto_surface = font.render('Salvar alterações?', True, black)
    screen.blit(texto_surface, (200, 180))

    # Botões de confirmação (Sim, Não, Cancelar)    
    if botao(screen, 'Sim', 250, 300, 100, 50, purple, black, soft_green, acao='sair'):
        salvar_configuracoes(configuracoes)  
        print("Configurações salvas!")
        return 'sair' 
    elif botao(screen, 'Não', 450, 300, 100, 50, purple, black, soft_green, acao='sair'):
        return 'sair'  
    elif botao(screen, 'Cancelar', 300, 400, 200, 50, purple, black, soft_green, acao='options'):
        return 'options' 

def alert(screen, text, font, x, y, duracao=5000):
    tempo_inicial = pg.time.get_ticks()
    tempo_final = tempo_inicial + duracao
    
    # Exibe o alerta enquanto o tempo não expira
    while pg.time.get_ticks() < tempo_final:
        for evento in pg.event.get():
            if evento.type == pg.QUIT:
                pg.quit()
                return
        
        pg.draw.rect(screen, white, (x, y, 250, 100))
        
        alerta = font.render(text, True, black)
        
        texto_rect = alerta.get_rect(center=(x + 250 // 2, y + 100 // 2))
        screen.blit(alerta, texto_rect)
        
        pg.display.update()
        
        pg.time.Clock().tick(60)

def slider(screen, pos_x, pos_y, largura, valor):
    mouse = pg.mouse.get_pos()
    clique = pg.mouse.get_pressed()

    # Desenha a barra
    pg.draw.rect(screen, white, (pos_x, pos_y, largura, 10))

    # Verifica se o mouse está sobre o marcador ou arrastando
    pos_marcador = int(pos_x + (valor * largura))  # Posição inicial do marcador
    if pos_marcador - 8 <= mouse[0] <= pos_marcador + 8 and clique[0]:  # Clicar sobre o marcador
        novo_valor = (mouse[0] - pos_x) / largura  # Atualiza o valor baseado na posição do mouse
        novo_valor = max(0, min(1, novo_valor))  # Limita o valor entre 0 e 1
        valor = novo_valor  # Atualiza o valor para retornar
    
    pg.draw.circle(screen, purple, (int(pos_x + (valor * largura)), pos_y + 5), 8)
    
    return valor

# SALVAMENTO
def salvar_configuracoes(configuracoes, arquivo='config.json'):
    with open(arquivo, 'w') as f:
        json.dump(configuracoes, f, indent=4)

# Função para carregar as configurações
def carregar_configuracoes(arquivo='config.json'):
    try:
        with open(arquivo, 'r') as f:
            configuracoes = json.load(f)
            return configuracoes
    except FileNotFoundError:
        # Se o arquivo não existir, retornar valores padrão
        return {"volume": 1.0, "luminosidade": 100}