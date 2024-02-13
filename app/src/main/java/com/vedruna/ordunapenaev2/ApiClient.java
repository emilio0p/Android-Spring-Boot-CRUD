package com.vedruna.ordunapenaev2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase para obtener una instancia de Retrofit para realizar llamadas a la API.
 */
public class ApiClient {

    // URL base de la API
    private static final String BASE_URL = "http://192.168.153.1:8080/";

    // Instancia de Retrofit

    /**
     * Método para obtener una instancia de Retrofit.
     * @return Instancia de Retrofit configurada para la URL base especificada.
     */
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        // Si la instancia de Retrofit es nula, crear una nueva instancia
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit; // Devolver la instancia de Retrofit
    }
}

