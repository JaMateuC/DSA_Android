package eetac.dsa.juego.Controlador.Monstruos;

import eetac.dsa.juego.Controlador.Ataque;
import eetac.dsa.juego.Controlador.Ejemplos.AtaqueEjemplo;
import eetac.dsa.juego.Controlador.Monstruo;
import eetac.dsa.juego.Controlador.ataque.Pisoton;
import eetac.dsa.juego.Controlador.ataque.Placaje;
import eetac.dsa.juego.Controlador.ataque.SaltoAereo;

public class MonstruoEjemplo extends Monstruo
{
    public MonstruoEjemplo(int nivel,int experiencia)
    {
        super(20, 2, 1, "MonstruoEjemplo", 30,nivel);
        setExperiencia(experiencia);

        getLista_ataques().añadirAtaque(new Pisoton());
        getLista_ataques().añadirAtaque(new Placaje());
        getLista_ataques().añadirAtaque(new SaltoAereo());
        getLista_ataques().añadirAtaque(new AtaqueEjemplo());
    }
}
