import pygame as pg
import back.defVisual as utils 

def level(screen):

    run = True

    while run:
        for evento in pg.event.get():
            if evento.type == pg.QUIT:
                return 'sair'  
        
        screen.fill(utils.soft_green)

        acao = {
            'FACIL': 'start',
            'MEDIO': 'start',
            'DIFICIL': 'start'
            }

        for i, (texto, acao) in enumerate(acao.items()):
            if utils.botao(screen, texto, 300, 200 + i*100, 200, 50, utils.purple, utils.white, utils.soft_blue, acao):
                return acao
            
            elif utils.botao(screen, 'return', 450, 150, 200, 50, utils.white, utils.black, utils.purple):
                return 'menu'
            
        pg.display.flip()
