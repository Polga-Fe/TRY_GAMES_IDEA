import pygame as pg
import back.defVisual as utils

def menu(screen):

    run = True
    while run:
        for evento in pg.event.get():
            if evento.type == pg.QUIT:
                return 'sair'
            
        screen.fill(utils.soft_blue)

        acao = {
                'INICIAR': 'choice',
                'OPÇÕES': 'options',
                'SAIR': 'sair'
            }

        for i, (texto, acao) in enumerate(acao.items()):
            if utils.botao(screen, texto, 300, 200 + i*100, 200, 50, utils.purple, utils.white, utils.soft_blue, acao):
                return acao
        
        
        pg.display.flip()
