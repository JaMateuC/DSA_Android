package eetac.dsa.juego.Controlador;


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




}
