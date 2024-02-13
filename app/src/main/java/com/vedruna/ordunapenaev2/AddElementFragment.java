package com.vedruna.ordunapenaev2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragmento para añadir un proyecto
 */
public class AddElementFragment extends Fragment {


    /**
     * RecyclerView para mostrar los proyectos
     */
    private RecyclerView recyclerView;

    /**
     * ProjectAdapter para adaptar el contenido de los proyectos de manera personalizada
     */
    private ProjectAdapter adapter;

    /**
     * Método llamado para crear la vista del fragmento.
     * @param inflater El inflater utilizado para inflar la vista.
     * @param container El contenedor en el que se inflará la vista.
     * @param savedInstanceState Estado previamente guardado de la instancia.
     * @return La vista inflada del fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Vista inflada del fragmento
         */
        View view = inflater.inflate(R.layout.fragment_add_element, container, false);

        /**
         * Elementos obtenidos de la vista `fragment_add_element.xml`
         */
        Button btnAdd = view.findViewById(R.id.btnEditar);
        EditText txtNombre = view.findViewById(R.id.txtIdProDelete);
        EditText txtDesc = view.findViewById(R.id.txtDescProEdit);
        EditText txtImg = view.findViewById(R.id.txtImgProEdit);

        /**
         * Asignación función onClick al botón de añadir proyectos
         */
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comprobación campos rellenos

                if (txtNombre.getText().toString().isEmpty() ||
                        txtDesc.getText().toString().isEmpty() ||
                        txtImg.getText().toString().isEmpty() ){

                    Toast.makeText(getContext(), "Debes rellenar todos los campos!",
                            Toast.LENGTH_SHORT).show();

                } else {

                    // Creación servicio de la Api
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);

                    // Creamos un objeto de la entidad `Project.java` para mandarlo en la petición
                    Project p = new Project(0, txtNombre.getText().toString(),
                            txtDesc.getText().toString(), txtImg.getText().toString());

                    // Llamada a la Api
                    Call<Void> call = apiService.addProjects(p);

                    call.enqueue(new Callback<Void>() {

                        // En caso de que exista respuesta
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            // Respuesta satisfactoria
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Proyecto añadido correctamente",
                                        Toast.LENGTH_SHORT).show();

                                NavController navController = Navigation.
                                        findNavController(requireActivity(),
                                                R.id.nav_host_fragment);

                                navController.navigate(R.id.homeFragment);

                                BottomNavigationView bottomNavigationView = getActivity().
                                        findViewById(R.id.bottomNavigationView);

                                bottomNavigationView.setSelectedItemId(R.id.navigation_home);

                            // Respuesta inválida
                            } else {
                                Toast.makeText(getContext(), "Error al añadir los datos",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }

                        // En caso de que falle la llamada
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "Error de conexión",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        return view;
    }




}