package eetac.dsa.dsa.IU;

import eetac.dsa.dsa.Mundo;
import eetac.dsa.dsa.Servidor.Controlador.Personaje;

public class IUMenu implements IUMenuBase{

    int posCursor;

    int maxCursor;

    int offset;

    char estado;

    int alto;

    public IUMenu(int alto) {
        this.maxCursor = 2;
        estado='M';
        posCursor=0;
        this.alto = alto;
        offset= 0;
    }

    @Override
    public int getPosCursor() {
        return posCursor;
    }

    @Override
    public void cursorUp() {
        if(estado=='M')
        {
            posCursor--;
            if(posCursor<0)
            {
                posCursor=maxCursor;
            }
        }
        if(estado=='I')
        {
            posCursor--;
            if(posCursor<0)
            {
                posCursor=maxCursor;
            }
        }
    }

    @Override
    public void cursorDown() {

        /*Personaje p = Mundo.getInstance().getProtagonista();
        if(estado=='M')
        {
            posCursor++;
            if(posCursor>maxCursor)
            {
                posCursor=0;
            }
        }
        if(estado=='I')
        {
            if(posCursor>=maxCursor-1)
            {
                if(offset+alto<p.getInventario().obtenerTamaño())
                {
                    offset++;

                    if(p.getInventario().obtenerTamaño()-offset<alto)
                        maxCursor=p.getInventario().obtenerTamaño()-offset;
                    else maxCursor = alto;
                    posCursor= maxCursor-1;
                }
                else{
                    offset=0;
                    posCursor=0;
                }
            }
            else
            {
                posCursor++;
            }
        }*/
    }

    @Override
    public String getInfoElemento() {
        /*String rel = "";
        Personaje p = Mundo.getInstance().getProtagonista();

        if(estado=='M')
        {
            if(posCursor ==0)
                rel = "Muestra el Inventario del protagonista";
            if(posCursor ==1)
                rel = "Muestra los monstruos que tiene un jugador y sus caracteristicas";
            if(posCursor ==2)
                rel = "Salir del juego";
        }

        if(estado=='I')
        {
            rel = p.getInventario().buscarObjeto(offset+posCursor).getDescripcion();
        }


        return rel;*/
        return null;
    }

    @Override
    public String[] getElementosMostrados() {
        String[] out =null;
        if(estado=='M')
        {
            out = new String[3];
            out[0] = "Inventario";
            out[1] = "Lista De Monstruos";
            out[2] = "Salir";
        }

        if(estado=='I')
        {
            /*Personaje p=Mundo.getInstance().getProtagonista();
            if(p.getInventario().obtenerTamaño()-offset<alto)
                maxCursor=p.getInventario().obtenerTamaño()-offset;
            else maxCursor = alto;
            out = new String[maxCursor];
            for(int i =offset;i<offset+maxCursor;i++)
            {
                out[i-offset] = p.getInventario().buscarObjeto(i).getNombre();
            }*/
        }

        return out;
    }

    @Override
    public void entrar() {
        /*Personaje p = Mundo.getInstance().getProtagonista();
        if(estado=='M')
        {
            switch (posCursor)
            {
                case 0:
                    estado = 'I';
                    posCursor = 0;
                    offset =0;
                    if(p.getInventario().obtenerTamaño()-offset<alto)
                        maxCursor=p.getInventario().obtenerTamaño()-offset;
                    else maxCursor = alto;
                    break;

                case 1:
                    estado = 'm';
                    posCursor = 0;
                    break;

                case 2:
                    posCursor = 0;
                    break;
            }
        }*/
    }
}
