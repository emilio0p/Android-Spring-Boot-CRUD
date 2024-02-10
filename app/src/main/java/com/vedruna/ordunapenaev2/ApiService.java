package com.vedruna.ordunapenaev2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    @GET("api")
    Call<List<Project>> getProjects();

    @POST("api/insert")
    Call<Void> addProjects(@Body Project p);
}
