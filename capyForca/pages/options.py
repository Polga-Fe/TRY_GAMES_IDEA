import pygame as pg
import pygame.mixer
import back.defVisual as utils

def options(screen):
    configuracoes = utils.carregar_configuracoes()
    volume = configuracoes['volume']
    keyColor = configuracoes['keyColor']

    # Aplicar configurações (exemplo: ajustar volume)
    pg.mixer.music.set_volume(volume)
    configuracoes = {"volume": volume, "keyColor": keyColor}
    
    mostrando_caixa = False  # Controle para exibir ou não a caixa de confirmação

    run = True
    while run:
        for evento in pg.event.get():
            if evento.type == pg.QUIT:
                mostrando_caixa = True  # Mostra a caixa quando o usuário tenta fechar a janela
        
        screen.fill(utils.soft_green)

        # CAIXA DE CONFIRMACAO
        if mostrando_caixa:
            resultado = utils.checkBox(screen, configuracoes)
            if resultado == 'sair':
                return 'sair' 
            elif resultado == 'cancelar':
                mostrando_caixa = False 
                return 'options'
        else:
            # VOLUME
            volume = utils.slider(screen, 300, 200, 200, volume)
            configuracoes["volume"] = volume

            # keyColor
            if utils.botao(screen, ' ', 250, 300, 60, 60, utils.white, utils.white, utils.soft_green):
                keyColor = utils.white
                configuracoes["keyColor"] = keyColor
            elif utils.botao(screen, ' ', 322, 300, 60, 60, utils.black, utils.black, utils.soft_green):
                keyColor = utils.black
                configuracoes["keyColor"] = keyColor
            elif utils.botao(screen, ' ', 394, 300, 60, 60, utils.purple, utils.purple, utils.soft_green):
                keyColor = utils.purple
                configuracoes["keyColor"] = keyColor
            elif utils.botao(screen, ' ', 466, 300, 60, 60, utils.red, utils.red, utils.soft_green):
                keyColor = utils.red
                configuracoes["keyColor"] = keyColor

                 

            # REINICIAR DADOS

            # RETORNO MENU
            if utils.botao(screen, 'voltar', 300, 400, 200, 50, utils.white, utils.black, utils.soft_green):
                utils.salvar_configuracoes(configuracoes)
                return 'menu'
               
        
        pg.display.flip()
