package eetac.dsa.rest;

import eetac.dsa.model.Testeo;
import eetac.dsa.model.Usuario123;
import eetac.dsa.model.UsuarioJSON;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIservice
{
    @GET("logtest")
    Call<Testeo>  logUser();

    @POST("test")
    Call<Testeo> getTest (@Body String value);

    @GET("usuario/{mail}/{pass}") //logiin
    Call<UsuarioJSON> testandroid(@Path("mail") String nombre, @Path("pass") String pass);

    @POST("test")
    Call<Testeo> test2(@Body Testeo test);

    @POST("registro") //registro
    Call<UsuarioJSON> registro(@Body UsuarioJSON u);





}