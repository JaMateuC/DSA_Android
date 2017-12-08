package eetac.dsa.dsa.Servidor.Model.dao;

public class ObjetoDAO extends DAO{

    @OrderFields(indice = 0)
    String tipo;
    @OrderFields(indice = 1)
    String extra;
    @OrderFields(indice = 2)
    String nombreUsuario;

    public ObjetoDAO() { }

    @OrderGetMethods(indice = 0)
    public String getTipo() {
        return tipo;
    }

    @OrderSetMethods(indice = 0)
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @OrderGetMethods(indice = 1)
    public String getExtra() {
        return extra;
    }

    @OrderSetMethods(indice = 1)
    public void setExtra(String extra) {
        this.extra = extra;
    }

    @OrderGetMethods(indice = 2)
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    @OrderSetMethods(indice = 2)
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

}
