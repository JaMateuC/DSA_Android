package eetac.dsa.dsa.Servidor.Model.dao;

public class MapaDAO {

    @OrderFields(indice = 0)
    private String nom;
    @OrderFields(indice = 1)
    private String direccioFile;
    @OrderFields(indice = 2)
    private String nomUsuario;

    public MapaDAO() {
    }

    @OrderGetMethods(indice = 0)
    public String getNom() {
        return nom;
    }

    @OrderSetMethods(indice = 0)
    public void setNom(String nom) {
        this.nom = nom;
    }

    @OrderGetMethods(indice = 1)
    public String getDireccioFile() {
        return direccioFile;
    }

    @OrderSetMethods(indice = 1)
    public void setDireccioFile(String direccioFile) {
        this.direccioFile = direccioFile;
    }

    @OrderGetMethods(indice = 2)
    public String getNomUsuario() {
        return nomUsuario;
    }

    @OrderSetMethods(indice = 2)
    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }
}
