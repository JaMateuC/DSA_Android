package eetac.dsa.rest;

import eetac.dsa.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIservice
{
    @POST("/new")
    Call<Usuario>  registroUser(@Body String newUser);


    @GET("/test")
    Call<String> test ();
}
