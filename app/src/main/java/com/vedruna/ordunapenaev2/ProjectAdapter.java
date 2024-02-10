package com.vedruna.ordunapenaev2;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;


import java.util.List;


public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private List<Project> projectList;
    private Context context;

    public int pos;

    public ProjectAdapter(List<Project> projectList) {
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_project, parent, false);

        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnDelete = view.findViewById(R.id.btnDelete);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Project project = projectList.get(pos);
                // Obtener el NavController desde el View raíz del fragmento actual
                NavController navController = Navigation.findNavController(v);

                // Crear un Bundle para los argumentos y agregar los valores necesarios
                Bundle args = new Bundle();
                args.putString("nombre_proyecto", project.getNombre());
                args.putString("desc_proyecto", project.getDescripcion());
                args.putString("img_proyecto", project.getImagen());

                // Navegar al destino deseado junto con los argumentos
                navController.navigate(R.id.modifyElementFragment, args);

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aqui habria que mostrar el fragment de eliminar
            }
        });


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView")
    int position) {
        pos = position;
        Project project = projectList.get(position);

        holder.textViewIdProyecto.setText("ISPI: " + project.getId());
        holder.textViewNombre.setText(project.getNombre());
        holder.textViewDescripcion.setText(project.getDescripcion());

        // Cargar la imagen utilizando Picasso

        Picasso.get().load(project.getImagen())
                .placeholder(R.drawable.ic_home)
                .error(R.drawable.ic_home)
                .transform(new RoundRectTransformation(30,0))
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewIdProyecto;
        TextView textViewNombre;
        TextView textViewDescripcion;
        ImageView imageView;

        Button btnEdit;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIdProyecto = itemView.findViewById(R.id.textViewIdProyecto);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            imageView = itemView.findViewById(R.id.imageView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
