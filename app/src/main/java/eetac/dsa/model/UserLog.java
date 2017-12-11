package eetac.dsa.model;

import com.google.gson.annotations.SerializedName;

public class UserLog
{
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("password")
    private String password;

    public UserLog() { }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
