package eetac.dsa;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIservice
{
    @POST("/new")
    Call<Usuario>  registroUser(@Body String newUser);


    @GET("/test")
    Call<String> test ();
}
