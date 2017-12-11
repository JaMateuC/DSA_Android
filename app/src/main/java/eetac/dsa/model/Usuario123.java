package eetac.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario123 implements Serializable
{
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("genero")
    @Expose
    private boolean genero;

    public String getEmail() {  return email;   }

    public void setEmail(String email) {    this.email = email; }

    public String getPassword() {   return password;    }

    public void setPassword(String password) {  this.password = password;   }

    public boolean isGenero() {
        return genero;
    }

    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    public Usuario123(String email, String password, boolean genero) {
        this.email = email;
        this.password = password;
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", genero=" + genero +
                '}';
    }
}
