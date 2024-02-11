package com.vedruna.ordunapenaev2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {


    @GET("api")
    Call<List<Project>> getProjects();

    @POST("api/insert")
    Call<Void> addProjects(@Body Project p);

    @DELETE("/api/delete{id}")
    Call<Void> deletePro(@Path("id") long id);

    @PUT("/api/edit{id}")
    Call<Void> editPro(@Path("id") long id, @Body Project p);

}
