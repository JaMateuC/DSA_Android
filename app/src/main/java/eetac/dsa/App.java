package eetac.dsa;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import eetac.dsa.dsa.Servidor.ResultadoServidor;

/**
 * Created by JesusLigero on 08/12/2017.
 */

public class App {
    public static final String API_URL = "http://localhost:8080";






    public interface GitHub {
        @GET("/myapp/json/logging")
        Call<List<Producto>> productos();




    }

    public static void main( String[] args ) throws IOException
    {


        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(httpClient.build())
                .build();


        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        // Create a call instance for looking up Retrofit contributors.
        //Call<List<Contributor>> call = github.contributors("RouteInjector", "route-injector");
        Call<List<Producto>> call2 = github.productos();

        // Fetch and print a list of the contributors to the library.
        //List<Contributor> contributors = call.execute().body();
        List<Producto> productoscoll = call2.execute().body();
        /*for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ") "+ contributor.avatar_url);
        }*/

        for (Producto p : productoscoll) {
            System.out.println(p.nombre + " (" + p.precio + ")");
        }

        //System.out.println(productoscoll.nombre);

    }

}
