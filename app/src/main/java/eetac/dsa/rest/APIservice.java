package eetac.dsa.rest;

import eetac.dsa.model.KeyUser;
import eetac.dsa.model.UsuarioJSON;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIservice
{
    @POST("new/user")
    Call<KeyUser> registro(@Body UsuarioJSON usuario);

    @POST("auth/login")
    Call<KeyUser> login(@Body UsuarioJSON usuario);
}