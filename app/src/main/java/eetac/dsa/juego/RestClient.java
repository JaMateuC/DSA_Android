package eetac.dsa.juego;

import android.content.Context;

import eetac.dsa.R;
import eetac.dsa.juego.root.Mundo;
import eetac.dsa.juego.root.ResponseRest;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.model.querysclient.QueryCambiarEscenario;
import eetac.dsa.model.querysclient.QueryUpdateUsuario;
import eetac.dsa.model.resultsserver.ResultCambiarEscenario;
import eetac.dsa.model.resultsserver.ResultLoginArgs;
import eetac.dsa.model.resultsserver.ResultadoAceptar;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private String BASE_URL;
    private static Retrofit retrofit = null;
    int key;
    ResponseRest mundo;
    Mundo.sincro getargs;

    public RestClient(int key, Context context, ResponseRest mundo,Mundo.sincro getargs) {
        this.key = key;
        BASE_URL = context.getString(R.string.URL_BASE);
        this.mundo = mundo;
        this.getargs = getargs;

    }

    public void cambiarMapa(int key,String nombreMapa,int x,int y)
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);

        QueryCambiarEscenario qCamEsc = new QueryCambiarEscenario(x,y,key,nombreMapa);

        Call<ResultCambiarEscenario> camEsc= apiService.cambiarMapa(qCamEsc);
        camEsc.enqueue(new Callback<ResultCambiarEscenario>()
        {
            @Override
            public void onResponse(Call<ResultCambiarEscenario> args, Response<ResultCambiarEscenario> response)
            {
                ResultCambiarEscenario rQE = response.body();
                mundo.onCambiarMapa(rQE.getEscenarioJSON(),rQE.getX(),rQE.getY());
            }

            @Override
            public void onFailure(Call<ResultCambiarEscenario> args, Throwable t)
            {
            }
        });
    }

    public void guardar(int key,String nombreEscenario,UsuarioJSON usuario)
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);

        QueryUpdateUsuario updateUsuario = new QueryUpdateUsuario();
        updateUsuario.setUsuarioJson(usuario);
        updateUsuario.setKey(key);
        updateUsuario.setNomEscenari(nombreEscenario);

        Call<ResultadoAceptar> camEsc= apiService.updateUsuario(updateUsuario);
        camEsc.enqueue(new Callback<ResultadoAceptar>()
        {
            @Override
            public void onResponse(Call<ResultadoAceptar> args, Response<ResultadoAceptar> response)
            {
            }

            @Override
            public void onFailure(Call<ResultadoAceptar> args, Throwable t)
            {
            }
        });
    }

    public void getLoginArgs(int key)
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);


        Call<ResultLoginArgs> loginArgs= apiService.getLoginArgs(key);
        loginArgs.enqueue(new Callback<ResultLoginArgs>()
        {
            @Override
            public void onResponse(Call<ResultLoginArgs> args, Response<ResultLoginArgs> response)
            {
                mundo.onGetLogingArgs(response.body());
                getargs.sincro();
            }

            @Override
            public void onFailure(Call<ResultLoginArgs> args, Throwable t)
            {
            }
        });
    }
}