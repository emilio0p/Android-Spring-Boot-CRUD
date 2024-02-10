package com.vedruna.ordunapenaev2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.util.List;


public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private List<Project> projectList;
    private Context context;

    public ProjectAdapter(List<Project> projectList) {
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

        /*
        Picasso.get().load(project.getImagen())
                .transform(new RoundRectTransformation(25f))
                .into(holder.imageView);*/




        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes iniciar una transacción de fragmento para mostrar el fragmento deseado

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aqui habria que mostrar el fragment de eliminar
            }
        });
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
