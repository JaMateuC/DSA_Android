package eetac.dsa.rest;

import eetac.dsa.model.KeyLog;
import eetac.dsa.model.UserLog;
import eetac.dsa.model.UsuarioJSON;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIservice
{
    @GET("usuario/{mail}/{pass}") //logiin
    Call<UsuarioJSON> testandroid(@Path("mail") String nombre, @Path("pass") String pass);

    @POST("registro") //registro
    Call<UsuarioJSON> registro(@Body UsuarioJSON u);

    //Función que envia un UserLog y devuelve una key de acceso del servidor
    @POST("auth/login")
    Call<KeyLog> login(@Body UserLog userLog);
}