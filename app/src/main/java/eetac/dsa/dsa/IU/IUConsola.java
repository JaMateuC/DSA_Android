package eetac.dsa.dsa.IU;

import eetac.dsa.dsa.Servidor.Controlador.Celda;
import eetac.dsa.dsa.Servidor.Controlador.Escenario;
import eetac.dsa.dsa.Mundo;

import java.awt.*;

public class IUConsola implements IUBase{

    char[][] consolaCaracteres;
    private static int ANCHO = 80;
    private static int ALTO = 20;
    private static int ANCHOMENU = 30;
    private static int ANCHODIALOGO = 7;

    IUMenu menu;

    public IUConsola() {
        this.consolaCaracteres = new char[ANCHO][ALTO];
        for(int x = 0;x<ANCHO;x++)
        {
            for(int y =0;y<ALTO;y++)
            {
                consolaCaracteres[x][y] = ' ';
            }
        }

        menu = new IUMenu(ALTO-ANCHODIALOGO-3);
    }

    public IUMenu getMenu() {
        return menu;
    }

    public void rendrizarEscenario() {

        /*Point personajePosicion = Mundo.getInstance().getPersonajesActivos().get(Mundo.getInstance().getNombrePersonaje()).getPosicion();
        Escenario escenario = Mundo.getInstance().getEscenario();

        int xOffset = (int)personajePosicion.getX()-(ANCHO-ANCHOMENU)/2;
        int yOffset = (int)personajePosicion.getY()-ALTO/2;

        int xmin = -xOffset;
        if(xmin<0)xmin=0;

        int ymin = -yOffset;
        if(ymin<0)ymin=0;
        int xmax = -xOffset+escenario.getAncho();
        if(xmax>=ANCHO-ANCHOMENU)xmax=ANCHO-ANCHOMENU;
        int ymax = -yOffset+escenario.getAlto();
        if(ymax>=ALTO)ymax= ALTO;

        for(int x = 0;x<ANCHO-ANCHOMENU;x++)
        {
            for(int y = 0;y<ALTO;y++)
            {
                if(x>=xmin&&x<xmax&&y>=ymin&&y<ymax)
                {
                    Celda c = escenario.getCelda(x+xOffset,y+yOffset);
                    if(c.getTipo().equals("Cesped"))
                        consolaCaracteres[x][y]='.';
                    if(c.getTipo().equals("Pared"))
                        consolaCaracteres[x][y]='#';
                    if(c.getTipo().equals("CambioDeEscenario"))
                        consolaCaracteres[x][y]='O';
                    if(c.getPersonajeEncima()!=null) {
                        if (c.getPersonajeEncima().getNombre().equals(Mundo.getInstance().getNombrePersonaje()))
                            consolaCaracteres[x][y] = 'P';
                        else
                            consolaCaracteres[x][y] = 'E';
                    }
                }
                else
                    consolaCaracteres[x][y]=' ';
            }
        }*/

    }

    public void renderizarMenu() {
        for(int x=ANCHO-ANCHOMENU-1;x<ANCHO;x++)
        {
            for(int y = 1;y<ALTO-1;y++)
            {
                consolaCaracteres[x][y]=' ';
            }
        }


        escrivirRafagaCaracteresH('-',0,0,ANCHO-1);
        escrivirRafagaCaracteresH('-',0,ALTO-1,ANCHO-1);
        escrivirRafagaCaracteresH('-',ANCHO-ANCHOMENU-1,ALTO-ANCHODIALOGO,ANCHOMENU);
        escrivirRafagaCaracteresV('|',0,0,ALTO);
        escrivirRafagaCaracteresV('|',ANCHO-1,0,ALTO);
        escrivirRafagaCaracteresV('|',ANCHO-ANCHOMENU-1,0,ALTO);

        String[] lista = menu.getElementosMostrados();

        for(int i =0;i<lista.length;i++)
        {
            escrivirA(lista[i], ANCHO - ANCHOMENU + 3, 1+i, ANCHOMENU - 3);
            consolaCaracteres[ANCHO - ANCHOMENU+1][1+menu.getPosCursor()] = '#';
        }


        escrivirRectandulo(menu.getInfoElemento(),ANCHO-ANCHOMENU,ALTO-ANCHODIALOGO+1,ANCHOMENU-2,ANCHODIALOGO-2);

    }

    private void escrivirRectandulo(String texto,int xInicial,int yInicial,int ancho,int alto)
    {
        int lineas = texto.length()/ancho;

        if(texto.length()%ancho!=0)
        {
            lineas++;
        }

        for(int y = yInicial;y<yInicial+alto&&y<yInicial+lineas;y++)
        {
            for(int x=xInicial;x<xInicial+ancho&&(y-yInicial)*ancho+(x-xInicial)<texto.length();x++)
            {
                consolaCaracteres[x][y] = texto.charAt((y-yInicial)*ancho+(x-xInicial));
            }
        }


    }

    private void escrivirA(String texto,int xInicial,int yInicial,int tamanyoMaximo)
    {
        for(int x = xInicial;x<xInicial+tamanyoMaximo-1&&x<xInicial+texto.length();x++)
        {
            consolaCaracteres[x][yInicial]= texto.charAt(x-xInicial);
        }
    }

    private void escrivirRafagaCaracteresH(char caracter,int xInicial,int yInicial,int tamanyoMaximo)
    {
        for(int x = xInicial;x<xInicial+tamanyoMaximo&&x<ANCHO;x++)
        {
            consolaCaracteres[x][yInicial]= caracter;
        }
    }

    private void escrivirRafagaCaracteresV(char caracter,int xInicial,int yInicial,int tamanyoMaximo)
    {
        for(int y = yInicial;y<yInicial+tamanyoMaximo&&y<ALTO;y++)
        {
            consolaCaracteres[xInicial][y]= caracter;
        }
    }


    @Override
    public void renderizar() {
        rendrizarEscenario();
        renderizarMenu();
        for(int y=0;y<ALTO;y++) {
            for (int x = 0; x < ANCHO; x++) {
                System.out.print(consolaCaracteres[x][y]);
            }
            System.out.print('\n');
        }

    }
}
