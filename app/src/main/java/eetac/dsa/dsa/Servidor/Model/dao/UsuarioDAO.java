package eetac.dsa.dsa.Servidor.Model.dao;

public class UsuarioDAO {

    @OrderFields(indice = 0)
    private String nombre;
    @OrderFields(indice = 1)
    private String password;
    @OrderFields(indice = 2)
    private int x;
    @OrderFields(indice = 3)
    private int y;
    @OrderFields(indice = 4)
    private boolean genero;


    public UsuarioDAO() {
    }

    @OrderGetMethods(indice = 0)
    public String getNombre() {
        return nombre;
    }

    @OrderSetMethods(indice = 0)
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @OrderGetMethods(indice = 1)
    public String getPassword() {
        return password;
    }

    @OrderSetMethods(indice = 1)
    public void setPassword(String password) {
        this.password = password;
    }

    @OrderGetMethods(indice = 2)
    public int getX() {
        return x;
    }

    @OrderSetMethods(indice = 2)
    public void setX(int x) {
        this.x = x;
    }

    @OrderGetMethods(indice = 3)
    public int getY() {
        return y;
    }

    @OrderSetMethods(indice = 3)
    public void setY(int y) {
        this.y = y;
    }

    @OrderGetMethods(indice = 4)
    public boolean isGenero() {
        return genero;
    }

    @OrderSetMethods(indice = 4)
    public void setGenero(boolean genero) {
        this.genero = genero;
    }
}
