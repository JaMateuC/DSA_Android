package eetac.dsa.rest;

import eetac.dsa.model.Testeo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIservice
{
    @GET("logtest")
    Call<Testeo>  logUser();

    @POST("test")
    Call<Testeo> getTest (@Body String value);
}