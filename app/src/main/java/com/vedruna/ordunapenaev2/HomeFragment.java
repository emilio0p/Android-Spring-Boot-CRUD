package com.vedruna.ordunapenaev2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragmento para mostrar la lista de proyectos en la pantalla de inicio.
 */
public class HomeFragment extends Fragment {

    // RecyclerView para mostrar la lista de proyectos
    private RecyclerView recyclerView;

    // Adaptador para el RecyclerView
    private ProjectAdapter adapter;

    /**
     * Método llamado para crear la vista del fragmento.
     * @param inflater El inflater utilizado para inflar la vista.
     * @param container El contenedor en el que se inflará la vista.
     * @param savedInstanceState Estado previamente guardado de la instancia.
     * @return La vista inflada del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Obtener una referencia al RecyclerView desde la vista inflada
        recyclerView = view.findViewById(R.id.recyclerView);

        // Configurar el layout manager del RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener los datos de la API y configurar el RecyclerView
        fetchDataFromApi();

        return view; // Devolver la vista inflada del fragmento
    }

    /**
     * Método para obtener los datos de la API y configurar el RecyclerView.
     */
    private void fetchDataFromApi() {
        // Crear una instancia del servicio de la API
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Realizar una llamada a la API para obtener la lista de proyectos
        Call<List<Project>> call = apiService.getProjects();

        // Manejar la respuesta de la llamada asíncrona
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                // Verificar si la respuesta fue exitosa
                if (response.isSuccessful()) {
                    // Obtener la lista de proyectos de la respuesta
                    List<Project> projectList = response.body();

                    // Crear un adaptador con la lista de proyectos y establecerlo en RecyclerView
                    adapter = new ProjectAdapter(projectList);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Mostrar un mensaje de error si la respuesta no fue exitosa
                    Toast.makeText(getContext(), "Error al obtener los datos",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                // Mostrar un mensaje de error en caso de fallo de conexión
                Toast.makeText(getContext(), "Error de conexión",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
