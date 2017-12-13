package eetac.dsa.rest;

import java.util.ArrayList;

import eetac.dsa.Controlador.Monstruo;
import eetac.dsa.model.KeyUser;
import eetac.dsa.model.MonstruoJSON;
import eetac.dsa.model.UsuarioJSON;
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

    @GET("user/listamonstruo/{nombre}")
    Call<ArrayList<MonstruoJSON>> listaM(@Path("nombre") String nombre);

    @GET("user/ranking")
    Call<ArrayList<UsuarioJSON>> listaR();


}