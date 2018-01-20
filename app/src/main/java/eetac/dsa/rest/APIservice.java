package eetac.dsa.rest;

import java.util.ArrayList;

import eetac.dsa.juego.Controlador.Usuario;
import eetac.dsa.model.KeyUser;
import eetac.dsa.model.MonstruoJSON;
import eetac.dsa.model.ObjetoJSON;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.model.querysclient.QueryCambiarEscenario;
import eetac.dsa.model.querysclient.QueryUpdateUsuario;
import eetac.dsa.model.resultsserver.ResultCambiarEscenario;
import eetac.dsa.model.resultsserver.ResultLoginArgs;
import eetac.dsa.model.resultsserver.ResultadoAceptar;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIservice
{
    //Registra un nuevo usuario en el servidor
    @POST("new/user")
    Call<KeyUser> registro(@Body UsuarioJSON usuario);

    //Autentifica a un usuario ya registrado
    @POST("auth/login")
    Call<KeyUser> login(@Body UsuarioJSON usuario);

    //Devuelve el perfil de un usuario
    @GET("user/profile/{nombre}")
    Call<UsuarioJSON> profile(@Path("nombre") String nombre);

    @POST("user/profile/fields")
    Call<UsuarioJSON> uptadeFields(@Body UsuarioJSON user);

    //Envia el perfil de un usuario modificado
    @POST("user/profile/update")
    Call<ResultadoAceptar> updateUsuario(@Body QueryUpdateUsuario updateUsuario);

    //Devuelve el ranking de usuarios
    @GET("user/ranking")
    Call<ArrayList<UsuarioJSON>> listaRanking();

    //Devuelve la lista de monstruos de un usuario
    @GET("user/listaMonstruo/{nombre}")
    Call<ArrayList<MonstruoJSON>> listaMonstruos(@Path("nombre") String nombre);

    //Devuelve la lista de objetos de un usuario
    @GET("user/listaInventario/{nombre}")
    Call<ArrayList<ObjetoJSON>> listaObjetos(@Path("nombre") String nombre);

    @GET("user/getLoginArgs/{id}")
    Call<ResultLoginArgs> getLoginArgs(@Path("id") int key);

    //Solicita un nuevo escenario para jugar
    @POST("user/cambiarEscenario")
    Call<ResultCambiarEscenario> cambiarMapa(@Body QueryCambiarEscenario queryCambiarEscenario);

    //Un usuario cierra su sesión
    @GET("auth/logout/{key}")
    Call<String> closeSesion(@Path("key") int key);

    //Da de baja el usuario
    @POST("user/delete")
    Call<KeyUser> deleteUser(@Body UsuarioJSON user);
}