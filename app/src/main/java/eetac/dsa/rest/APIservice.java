package eetac.dsa.rest;

import java.util.ArrayList;

import eetac.dsa.model.KeyUser;
import eetac.dsa.model.MonstruoJSON;
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
    @POST("new/user")
    Call<KeyUser> registro(@Body UsuarioJSON usuario);

    @POST("auth/login")
    Call<KeyUser> login(@Body UsuarioJSON usuario);

    @GET("user/profile/{nombre}")
    Call<UsuarioJSON> profile(@Path("nombre") String nombre);

    @GET("user/listamonstruo/{nombre}")
    Call<ArrayList<MonstruoJSON>> listaM(@Path("nombre") String nombre);

    @GET("user/ranking")
    Call<ArrayList<UsuarioJSON>> listaR();

    @GET("user/getLoginArgs/{id}")
    Call<ResultLoginArgs> getLoginArgs(@Path("id") int key);

    @POST("user/cambiarEscenario")
    Call<ResultCambiarEscenario> cambiarMapa(@Body QueryCambiarEscenario queryCambiarEscenario);

    @POST("user/updateUsuario")
    Call<ResultadoAceptar> updateUsuario(@Body QueryUpdateUsuario qUpUsuario);
}