package eetac.dsa.juego.Controlador.Monstruos;

import eetac.dsa.juego.Controlador.Ataque;
import eetac.dsa.juego.Controlador.Ejemplos.AtaqueEjemplo;
import eetac.dsa.juego.Controlador.Monstruo;

public class MonstruoEjemplo extends Monstruo
{
    public MonstruoEjemplo(int nivel,int experiencia)
    {
        super(20, 5, 3, "MonstruoEjemplo", 30,nivel);
        setExperiencia(experiencia);

        getLista_ataques().añadirAtaque(new Ataque("patada",1.5f));
        getLista_ataques().añadirAtaque(new Ataque("puñetazo",1.1f));
        getLista_ataques().añadirAtaque(new AtaqueEjemplo());
    }
}
