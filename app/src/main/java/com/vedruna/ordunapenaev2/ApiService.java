package com.vedruna.ordunapenaev2;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interfaz que define los métodos para realizar llamadas a la API.
 */
public interface ApiService {

    /**
     * Obtiene la lista de proyectos desde la API.
     * @return Una llamada que devuelve una lista de proyectos.
     */
    @GET("api")
    Call<List<Project>> getProjects();

    /**
     * Obtiene un proyecto específico por su ID desde la API.
     * @param id El ID del proyecto.
     * @return Una llamada que devuelve un proyecto.
     */
    @GET("api/{id}")
    Call<Project> getProjectById(@Path("id") long id);

    /**
     * Agrega un proyecto a la API.
     * @param p El proyecto a añadir.
     * @return Una llamada que no devuelve nada.
     */
    @POST("api/insert")
    Call<Void> addProjects(@Body Project p);

    /**
     * Inicio de sesión con la API.
     * @param u El usuario a comprobar.
     * @return Booleano indicando si es correcto.
     */
    @POST("api/login")
    Call<Boolean> login(@Body User u);

    /**
     * Elimina un proyecto de la API por su ID.
     * @param id El ID del proyecto a eliminar.
     * @return Una llamada que no devuelve nada.
     */
    @DELETE("/api/delete{id}")
    Call<Void> deletePro(@Path("id") long id);

    /**
     * Edita un proyecto en la API por su ID.
     * @param id El ID del proyecto a editar.
     * @param p Los datos actualizados del proyecto.
     * @return Una llamada que no devuelve nada.
     */
    @PUT("/api/edit{id}")
    Call<Void> editPro(@Path("id") long id, @Body Project p);

}
