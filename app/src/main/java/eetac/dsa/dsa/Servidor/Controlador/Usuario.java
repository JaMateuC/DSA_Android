package eetac.dsa.dsa.Servidor.Controlador;

import eetac.dsa.dsa.Flag;
import eetac.dsa.dsa.Servidor.MundoControlador;
import eetac.dsa.dsa.Servidor.ResultadoServidor;

/**
 * La Clase Usuario permite instanciar un personaje para el jugador
 */
public class Usuario extends Personaje
{
    private String email;
    private String password;

    public Usuario(String nombre, int x, int y, boolean genero, String email, String password)
    {
        super(nombre, x, y, genero);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean mover(int x, int y) {
        /*Celda cela = MundoControlador.getInstance().getSesion(getNombre()).getEscenario().getCelda(x,y);
        if(cela.accion(this))
        {
            if(cela.getTipo().equals("CambioDeEscenario"))
            {
                MundoControlador.getInstance().getSesion(getNombre()).getEscenario().getCelda((int)posicion.getX(),(int)posicion.getY()).setPersonajeEncima(this);
                return true;
            }
            MundoControlador.getInstance().getSesion(getNombre()).getEscenario().getCelda((int)posicion.getX(),(int)posicion.getY()).setPersonajeEncima(null);
            this.posicion.setLocation(x,y);
            return true;
        }
        else*/ return false;
    }


}
