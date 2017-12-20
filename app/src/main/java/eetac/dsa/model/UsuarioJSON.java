package eetac.dsa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import eetac.dsa.Controlador.Usuario;

public class UsuarioJSON implements Serializable{
    private String nombre;
    private String password;
    private String email;
    private int x;
    private int y;
    private boolean genero;
    private int key;
    private ArrayList<ObjetoJSON> inventario;
    private ArrayList<MonstruoJSON> monstruos;

    public ArrayList<ObjetoJSON> getInventariol() {
        return inventario;
    }

    public void setInventario(ArrayList<ObjetoJSON> inventario) {
        this.inventario = inventario;
    }

    public ArrayList<MonstruoJSON> getMonstruosl() {
        return monstruos;
    }

    public void setMonstruosl(ArrayList<MonstruoJSON> monstruosl) {
        this.monstruos = monstruosl;
    }



    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGenero() {
        return genero;
    }

    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    public UsuarioJSON() {
    }

    public UsuarioJSON(String nombre, String password) {
        this.password = password;
        this.nombre = nombre;
        monstruos = new ArrayList<MonstruoJSON>();
    }

    public UsuarioJSON(String nombre, String password, String email, boolean genero) {
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.genero = genero;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public String Ranking(){
        return "nombre: "+ this.nombre+ " Numero de Monstruos: " + monstruos.size();
    }


    public Usuario toUsario()throws Exception
    {
        Usuario usuario = new Usuario(nombre,x,y,genero,email,password);
        for(int i =0;i< monstruos.size();i++)
        {
            usuario.añadirMonstruo(monstruos.get(i).toMonstruo());
        }
        for(int i =0;i<inventario.size();i++)
        {
            usuario.getInventario().añadirObeto(inventario.get(i).toObjeto());
        }
        return usuario;
    }

    /*public void fromUsuario(Usuario usuario) throws Exception
    {
        this.email = usuario.getEmail();
        this.genero = usuario.getGenero();
        this.nombre = usuario.getNombre();
        this.password = usuario.getPassword();
        if(usuario.getPosicion()!=null) {
            this.x = (int) usuario.getPosicion().getX();
            this.y = (int) usuario.getPosicion().getY();
        }
        this.inventario = new ObjetoJSON[usuario.getInventario().obtenerTamaño()];
        this.monstruo = new MonstruoJSON[usuario.getLista_montruos().getTamaño()];
        for(int i =0;i<inventario.length;i++)
        {
            ObjetoJSON obj = new ObjetoJSON();
            obj.fromObjeto(usuario.getInventario().buscarObjeto(i));
            inventario[i] = obj;
        }

        for(int i =0;i<monstruo.length;i++)
        {
            MonstruoJSON obj = new MonstruoJSON();
            obj.fromMonstruo(usuario.getLista_montruos().getMonstruo(i));
            monstruo[i] = obj;
        }
    }*/
}
