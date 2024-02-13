package com.vedruna.ordunapenaev2;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Fragmento para modificar un elemento.
 */
public class ModifyElementFragment extends Fragment {

    // Variable estática para almacenar el ID del proyecto a modificar
    static int idPro;

    /**
     * Método llamado cuando la vista asociada al fragmento ha sido creada.
     * @param view La vista inflada del fragmento.
     * @param savedInstanceState Estado previamente guardado de la instancia.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener los argumentos pasados al fragmento
        Bundle args = getArguments();
        if (args != null) {
            // Acceder a los valores de los argumentos
            String argumentoNombre = args.getString("nombre_proyecto");
            String argDesc = args.getString("desc_proyecto");
            String argImg = args.getString("img_proyecto");
            EditText nombrePro = view.findViewById(R.id.txtIdProDelete);
            EditText descPro = view.findViewById(R.id.txtDescProEdit);
            EditText imgPro = view.findViewById(R.id.txtImgProEdit);
            nombrePro.setText(argumentoNombre);
            descPro.setText(argDesc);
            imgPro.setText(argImg);
            idPro = args.getInt("id_proyecto");
            EditText txtId = view.findViewById(R.id.txtId1ProDelete);
            txtId.setText(String.valueOf(idPro));
        }
    }

    /**
     * Método llamado para crear la vista asociada al fragmento.
     * @param inflater El inflater utilizado para inflar la vista.
     * @param container El contenedor en el que se inflará la vista.
     * @param savedInstanceState Estado previamente guardado de la instancia.
     * @return La vista inflada del fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_modify_element, container, false);

        // Obtener referencias a los elementos de la interfaz de usuario
        EditText txtId = view.findViewById(R.id.txtId1ProDelete);
        EditText txtNombre = view.findViewById(R.id.txtIdProDelete);
        EditText txtDesc = view.findViewById(R.id.txtDescProEdit);
        EditText txtImg = view.findViewById(R.id.txtImgProEdit);
        Button btnEditar = view.findViewById(R.id.btnEditar);
        Button btnCargar = view.findViewById(R.id.btnCargar);

        // Configurar el botón de editar
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comprobar si se han llenado todos los campos
                if (txtId.getText().toString().isEmpty() ||
                        txtNombre.getText().toString().isEmpty() ||
                        txtDesc.getText().toString().isEmpty() ||
                        txtImg.getText().toString().isEmpty()){

                    Toast.makeText(getContext(), "Debes cargar un proyecto",
                            Toast.LENGTH_SHORT).show();

                } else {
                    // Crear instancia de ApiService
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    // Crear objeto Project con los datos ingresados
                    Project p = new Project(idPro, txtNombre.getText().toString(),
                            txtDesc.getText().toString(), txtImg.getText().toString());
                    // Llamar al método de edición en la API
                    Call<Void> call = apiService.editPro(idPro, p);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Proyecto editado correctamente",
                                        Toast.LENGTH_SHORT).show();
                                // Navegar de regreso al fragmento de inicio
                                NavController navController = Navigation.findNavController
                                        (requireActivity(), R.id.nav_host_fragment);
                                navController.navigate(R.id.homeFragment);
                                BottomNavigationView bottomNavigationView = getActivity().
                                        findViewById(R.id.bottomNavigationView);
                                bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                            } else {
                                Toast.makeText(getContext(), "Error al editar el proyecto",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "Error de conexión",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // Configurar el botón de cargar
        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtId.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Debes introducir un ISPI!",
                            Toast.LENGTH_SHORT).show();
                } else if (txtId.getText().toString().matches("^[0-9]+$")){
                    idPro = Integer.parseInt(txtId.getText().toString());
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    Call<Project> call = apiService.getProjectById
                            (Long.parseLong(txtId.getText().toString()));

                    call.enqueue(new Callback<Project>(){
                        @Override
                        public void onResponse(Call<Project> call, Response<Project> response) {
                            if (response.isSuccessful()) {
                                Project p = response.body();
                                if (p==null){
                                    Toast.makeText(getContext(), "Error al obtener los datos",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // Mostrar los datos del proyecto obtenido
                                    txtId.setText(Integer.toString(p.getId()));
                                    txtNombre.setText(p.getNombre());
                                    txtDesc.setText(p.getDescripcion());
                                    txtImg.setText(p.getImagen());
                                }
                            } else {
                                Toast.makeText(getContext(), "Error al obtener los datos",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Project> call, Throwable t) {
                            // Manejar el fallo de la llamada a la API
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Error al obtener los datos, no se encontró " +
                            "el proyecto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
