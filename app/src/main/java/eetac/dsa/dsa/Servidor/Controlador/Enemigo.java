package eetac.dsa.dsa.Servidor.Controlador;
import java.util.Random;
/**
 * La clase Enemigo contiene los parametros para instanciar a un enemigo en el juego
 */
public class Enemigo extends Personaje
{
    public Enemigo(String nombre, int x, int y, boolean genero)
    {
        super(nombre, x, y, genero);
    }

    public int Random()
    {
        Random random = new Random();
        int num = random.nextInt(4);
        return num;
    }
}
