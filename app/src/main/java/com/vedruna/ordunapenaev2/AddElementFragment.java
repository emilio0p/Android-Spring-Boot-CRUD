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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddElementFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProjectAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_element, container, false);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        EditText txtNombre = view.findViewById(R.id.txtNombrePro);
        EditText txtDesc = view.findViewById(R.id.txtDescPro);
        EditText txtImg = view.findViewById(R.id.txtImgPro);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comprobación campos rellenos
                if (txtNombre.getText().toString().isEmpty() || txtDesc.getText().toString().isEmpty()
                        || txtImg.getText().toString().isEmpty() ){
                    Toast.makeText(getContext(), "Debes rellenar todos los campos!",
                            Toast.LENGTH_SHORT).show();
                } else {


                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    Project p = new Project(0, txtNombre.getText().toString(),
                            txtDesc.getText().toString(), txtImg.getText().toString());
                    Call<Void> call = apiService.addProjects(p);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Proyecto añadido correctamente",
                                        Toast.LENGTH_SHORT).show();
                                NavController navController = Navigation.
                                        findNavController(requireActivity(), R.id.nav_host_fragment);
                                navController.navigate(R.id.homeFragment);
                                BottomNavigationView bottomNavigationView = getActivity().
                                        findViewById(R.id.bottomNavigationView);
                                bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                            } else {
                                Toast.makeText(getContext(), "Error al añadir los datos",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        return view;
    }




}