package eetac.dsa.rest;

import eetac.dsa.model.KeyUser;
import eetac.dsa.model.UsuarioJSON;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIclient
{
    private static Retrofit retrofit = null;
    private String BASE_URL = "http://192.168.1.8:8080/myapp/";
    private int key;

    public int IniciarSesion(String nombre, String password)
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);

        //JSON que enviamos al servido
        final UsuarioJSON usuario = new UsuarioJSON(nombre, password);

        Call<KeyUser> login = apiService.login(usuario);
        login.enqueue(new Callback<KeyUser>()
        {
            @Override
            public void onResponse(Call<KeyUser> login, Response<KeyUser> response) {  key = response.body().getKey();  }

            @Override
            public void onFailure(Call<KeyUser> login, Throwable t) {  key = 0;  }
        });

        return key;
    }
}
