import pygame as pg
import pygame.mixer
import back.utilitys as utils

def options(screen):
    configuracoes = utils.carregar_configuracoes()
    volume = configuracoes['volume']
    luminosidade = configuracoes['luminosidade']

    # Aplicar configurações (exemplo: ajustar volume)
    pg.mixer.music.set_volume(volume)
    configuracoes = {"volume": volume, "luminosidade": luminosidade}
    
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

            # LUMINOSIDADE
            if utils.botao(screen, '25%', 150, 300, 100, 50, utils.white, utils.black, utils.soft_green):
                luminosidade = 25
                configuracoes["luminosidade"] = luminosidade
            elif utils.botao(screen, '50%', 260, 300, 100, 50, utils.white, utils.black, utils.soft_green):
                luminosidade = 50
                configuracoes["luminosidade"] = luminosidade
            elif utils.botao(screen, '75%', 370, 300, 100, 50, utils.white, utils.black, utils.soft_green):
                luminosidade = 75
                configuracoes["luminosidade"] = luminosidade
            elif utils.botao(screen, '100%', 480, 300, 100, 50, utils.white, utils.black, utils.soft_green):
                luminosidade = 100
                configuracoes["luminosidade"] = luminosidade

            # REINICIAR DADOS

            # RETORNO MENU
            if utils.botao(screen, 'voltar', 300, 400, 200, 50, utils.white, utils.black, utils.soft_green):
                print('MENU')
                return 'menu'
               
        
        pg.display.flip()
