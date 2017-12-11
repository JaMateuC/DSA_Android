package eetac.dsa.model;

import com.google.gson.annotations.SerializedName;

public class UserNew
{
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;
    @SerializedName("genero")
    private boolean genero;

    public UserNew() { }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email;  }

    public boolean isGenero() { return genero; }

    public void setGenero(boolean b) { this.genero = genero; }
}