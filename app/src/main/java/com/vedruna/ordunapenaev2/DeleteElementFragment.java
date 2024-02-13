package com.vedruna.ordunapenaev2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
 * Fragmento para eliminar un elemento.
 */
public class DeleteElementFragment extends Fragment {

    /**
     * Método llamado cuando la vista ha sido creada.
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
            int argId = args.getInt("id_proyecto");
            EditText idPro = view.findViewById(R.id.txtIdProDelete);
            idPro.setText(String.valueOf(argId));

        }
    }

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
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_delete_element, container, false);

        // Obtener referencias a los elementos de la interfaz de usuario
        Button btnYes = view.findViewById(R.id.btnYesDelete);
        Button btnNo = view.findViewById(R.id.btnNoDelete);
        EditText txtId = view.findViewById(R.id.txtIdProDelete);

        // Asignar un listener al botón de eliminar
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Comprobar si el campo de texto está vacío
                if (txtId.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Debes rellenar todos los campos!",
                        Toast.LENGTH_SHORT).show();

            } else if (txtId.getText().toString().matches("^[0-9]+$")){

                    // Crear instancia del servicio de la API
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);

                    // Llamar al método para eliminar el proyecto
                    Call<Void> call = apiService.deletePro(Long.parseLong(txtId.getText().
                            toString()));

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Proyecto eliminado correctamente",
                                    Toast.LENGTH_SHORT).show();

                            NavController navController = Navigation.
                                    findNavController(requireActivity(), R.id.nav_host_fragment);

                            navController.navigate(R.id.homeFragment);

                            BottomNavigationView bottomNavigationView = getActivity().
                                    findViewById(R.id.bottomNavigationView);

                            bottomNavigationView.setSelectedItemId(R.id.navigation_home);

                        } else {
                            Toast.makeText(getContext(), "Error al eliminar el proyecto",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            } else {
                    Toast.makeText(getContext(), "ISPI inválido", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        // Asignar un listener al botón de cancelar
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "No has eliminado el proyecto",
                        Toast.LENGTH_SHORT).show();

                NavController navController = Navigation.findNavController(requireActivity(),
                        R.id.nav_host_fragment);

                navController.navigate(R.id.homeFragment);

                BottomNavigationView bottomNavigationView = getActivity().
                        findViewById(R.id.bottomNavigationView);

                bottomNavigationView.setSelectedItemId(R.id.navigation_home);
            }
        });

        return view; // Devolver la vista inflada del fragmento
    }
}