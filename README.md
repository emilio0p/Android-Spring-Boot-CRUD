# Proyecto Android & Spring Boot

Este proyecto consiste en una aplicación Android desarrollada en Android Studio que utiliza una API propia para la gestión de proyectos de software. A continuación se detallan los componentes obligatorios y algunas funcionalidades adicionales implementadas para obtener la puntuación máxima de 10 según las opciones proporcionadas en el enunciado.

## Índice
1. [Api utilizada](#api-utilizada)

2. [Requisitos mínimos](#✅-requisitos-mínimos-cumplidos)

    - [Activities y Fragments](#activities-y-fragments)
    - [Componentes UI](#componentes-ui)
    - [Barra de Navegación Inferior](#barra-de-navegación-inferior)
    - [Toasts](#toasts)
    - [Comentarios Javadoc](#comentarios-javadoc)

3. [Activities (Pantallas)](#📱-activities-pantallas)

    - [Activity Login](#activity-login)
    - [Activity Home](#activity-home)
    - [Activity Añadir Elemento](#activity-añadir-elemento)
    - [Activity Modificar Elemento](#activity-modificar-elemento)
    - [Activity Eliminar Elemento](#activity-eliminar-elemento)
    - [Activity Cerrar Sesión](#activity-cerrar-sesión)

4. [Funcionalidades adicionales](#💡-funcionalidades-adicionales-implementadas)
5. [Versiones y dependencias](#versiones-y-dependencias)
    - [Dependencias](#dependencias)
6. [Observaciones](#observaciones)

## API utilizada

Para la realización de este proyecto se ha utilizado una [API propia 
](https://github.com/emilio0p/BasicSpringBootAPIH2) básica, la cual utiliza una base de datos H2 embebida. Dispone de 2 entidades, `Usuario` y `Proyecto`.

**Enlace** : https://github.com/emilio0p/BasicSpringBootAPIH2

## ✅ Requisitos Mínimos Cumplidos

### Activities y Fragments

La aplicación cuenta con las siguientes actividades y fragments:
- Actividad de login.
- Actividad principal que incluye:
  - Fragmento "Home".
  - Fragmento para crear un nuevo elemento.
  - Fragmento para modificar un elemento existente.
  - Fragmento para eliminar un elemento.

### Componentes UI

- Se han integrado botones para facilitar la navegación y realizar acciones como iniciar sesión, crear, modificar y eliminar elementos.
- Campos de texto se utilizan para introducir información relevante, como nombre y descripción de un elemento.
- La lista en el fragmento Home muestra los elementos del proyecto con su nombre y una breve descripción.

### Barra de Navegación Inferior

Se ha implementado una barra de navegación inferior para facilitar la transición entre los fragments. Además, incluye un botón para cerrar la sesión y volver a la actividad de login.

### Toasts

- Se utilizan Toasts para notificar al usuario sobre acciones realizadas, como la creación, modificación o eliminación de elementos.
- También se notifica mediante Toast si el inicio de sesión es correcto o incorrecto.

### Comentarios Javadoc

El código está debidamente comentado utilizando el formato Javadoc para facilitar su comprensión y mantenimiento.

## 📱 Activities (Pantallas)

## **Activity Login** 
En la clase `Login.java`, se implementa la funcionalidad para iniciar sesión tanto con credenciales de la API, como con Google.

1. **Configuración de la Interfaz de Usuario (UI)**:

   La interfaz de usuario se define en el archivo XML `activity_login.xml`. Contiene campos de texto para ingresar el usuario y la contraseña, además de un botón para iniciar sesión y otro para iniciar sesión con Google.

   ```xml
        <EditText
            android:id="@+id/txtUser"
            .../>

        <EditText
            android:id="@+id/txtPassword"
            .../>

        <Button
            android:id="@+id/btnLogin"
            .../>

        <Button
            android:id="@+id/btnLoginGoogle"
            .../>
   ```
**Iniciar Sesión con Google:**

Se configuran las opciones de inicio de sesión con Google en el método `onCreate()` utilizando la clase `GoogleSignInOptions` y `GoogleSignInClient.`
Se implementa el método `signIn()` para iniciar sesión con Google.
Cuando el usuario hace clic en el botón de "Iniciar con Google", se llama al método `signIn()`.
```java
        private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    ...

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
                Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso",
                        Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Algo ha salido mal",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
```
**Inicio de Sesión API:**

Cuando el usuario hace clic en el botón "LOGIN", se ejecuta el método `iniciarSesion()`.
Este método obtiene el usuario y la contraseña ingresados por el usuario.
Luego, se realiza una llamada a la API para verificar las credenciales del usuario.
Si las credenciales son válidas, se navega a la siguiente actividad (`FragmentsActivity`).
Si las credenciales son inválidas, se muestra un mensaje de error y se limpian los campos de texto.

```java
public void iniciarSesion(View view) {
        String user = txtUser.getText().toString();
        String pass = txtPassword.getText().toString();

        if (user.isEmpty() || pass.isEmpty()){
            // Comprobación campos rellenos
        } else {
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            User u = new User(0L,user,pass);
            Call<Boolean> call = apiService.login(u);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()){
                        Boolean respuesta = response.body();
                        if (Boolean.TRUE.equals(respuesta)){
                            // Si el login es correcto navega al Home
                            navigateToSecondActivity();
                        } else {
                            // Si el login es incorrecto
                            Toast.makeText(getApplicationContext(), "Credenciales incorrectas!",
                                    Toast.LENGTH_SHORT).show();
                            // Reinicia los campos de texto
                            txtUser.setText("");
                            txtPassword.setText("");
                        }
                    }

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Algo no ha salido bien...",
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
```
### CAPTURA DE PANTALLA
![Login Activity](https://i.imgur.com/ta4MKi5.png)

## **Activity Home**

En la clase `HomeFragment.java`, se muestra una lista de proyectos en la pantalla de inicio. Aquí tienes una explicación detallada:

1. **Configuración de la Interfaz de Usuario (UI)**:

   La interfaz de usuario se define en el archivo XML `fragment_home.xml`. Contiene un `RecyclerView` para mostrar la lista de proyectos y un `TextView` para el título.

   ```xml
        ...

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            .../>

        ...
   ```

**Configuración del RecyclerView:**

En el método `onCreateView()`, se infla la vista del fragmento y se configura el `RecyclerView`.
Se establece un LinearLayoutManager para el `RecyclerView`.

```java
...

recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

...
```

**Obtener Datos de la API:**

En el método `fetchDataFromApi()`, se obtienen los datos de la API mediante una llamada asíncrona.
Se crea una instancia del servicio de la API y se realiza una llamada para obtener la lista de proyectos.
Se maneja la respuesta de la llamada en los métodos `onResponse()` y `onFailure()`.
```java
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
```
### CAPTURA DE PANTALLA
![Home Fragment](https://i.imgur.com/d9CJe0I.png)

## **Activity Añadir Elemento**
En la clase `AddElementFragment.java`, se implementa la funcionalidad para añadir un nuevo proyecto. Aquí tienes una explicación paso a paso:

1. **Configuración de la Interfaz de Usuario (UI)**:

   La interfaz de usuario se define en el archivo XML `fragment_add_element.xml`. Contiene campos de texto para ingresar el nombre, la descripción y la URL de la imagen del proyecto, además de un botón para añadir el proyecto.

   ```xml
    ...

   <TextView
            android:id="@+id/txtTituloEdit"
            .../>

        <EditText
            android:id="@+id/txtIdProDelete"
            ../>

        <EditText
            android:id="@+id/txtDescProEdit"
            .../>

        <EditText
            android:id="@+id/txtImgProEdit"
            .../>

        <Button
            android:id="@+id/btnEditar"
            .../>

        ...
   ```

**Manejo del Evento onClick del Botón de Añadir:**

Se obtienen referencias a los elementos de la interfaz de usuario en el método `onCreateView()`.
Se asigna una función onClick al botón de añadir proyectos para manejar el evento de clic.
Cuando el usuario hace clic en el botón, se verifica si todos los campos están completos.
Si están completos, se crea un objeto Project con los datos ingresados y se realiza una llamada a la API para añadir el proyecto.
Si la llamada es exitosa, se muestra un mensaje de éxito y se navega de vuelta a la pantalla de inicio (`HomeFragment`).
Si hay algún error en la llamada, se muestra un mensaje de error.

```java
btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtNombre.getText().toString().isEmpty() ||
                        txtDesc.getText().toString().isEmpty() ||
                        txtImg.getText().toString().isEmpty() ){

                    // Comprobación campos rellenos

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
```
**Navegación a la Pantalla de Inicio:**

Después de añadir exitosamente un proyecto, se utiliza `NavController` para navegar de vuelta a la pantalla de inicio (`HomeFragment`).
Se selecciona el ítem correspondiente en la barra de navegación inferior para asegurar que la pantalla de inicio esté activa.

```java
NavController navController = Navigation.findNavController(requireActivit(),R.id.nav_host_fragment);

   navController.navigate(R.id.homeFragment);

    BottomNavigationView bottomNavigationView = getActivity().
            findViewById(R.id.bottomNavigationView);

    bottomNavigationView.setSelectedItemId(R.id.navigation_home);
```
### CAPTURA DE PANTALLA
![New Element Fragment](https://i.imgur.com/Y4YZdwx.png)

## **Activity Modificar Elemento**
En la clase `ModifyElementFragment.java`, se implementa la funcionalidad para modificar un proyecto existente. Aquí tienes una explicación paso a paso:

**Configuración de la Interfaz de Usuario (UI)**:

   La interfaz de usuario se define en el archivo XML `fragment_modify_element.xml`. Contiene campos de texto para mostrar y editar el ID, el nombre, la descripción y la URL de la imagen del proyecto, además de botones para cargar un proyecto existente y editar sus detalles.

   ```xml
        ...

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="150dp"
            android:gravity="center"
            android:orientation="horizontal">

            <EditText
                android:id="@+id/txtId1ProDelete"
                .../>

            <Button
                android:id="@+id/btnCargar"
                ... />
        </LinearLayout>

        <EditText
            android:id="@+id/txtIdProDelete"
           .../>

        <EditText
            android:id="@+id/txtDescProEdit"
            .../>

        <EditText
            android:id="@+id/txtImgProEdit"
            .../>

        <Button
            android:id="@+id/btnEditar"
            ... />

    </LinearLayout>

</FrameLayout>
   ```

**Obtención de Datos del Proyecto Existente:**

Se utiliza el método `onViewCreated()` para obtener los argumentos pasados al fragmento, que contienen los detalles del proyecto a modificar.
Si hay argumentos válidos, se muestran los detalles del proyecto en los campos de texto correspondientes.

```java
// Botón editar
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

// Botón cargar
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
```

### CAPTURA DE PANTALLA
![Edit Element Fragment](https://i.imgur.com/ODCs7q3.png)

## **Activity Eliminar Elemento**
En la clase `DeleteElementFragment.java`, se implementa la funcionalidad para eliminar un proyecto existente. Aquí tienes una explicación paso a paso:

**Configuración de la Interfaz de Usuario (UI)**:

   La interfaz de usuario se define en el archivo XML `fragment_delete_element.xml`. Contiene un campo de texto para introducir el ID del proyecto que se desea eliminar, así como botones para confirmar o cancelar la acción.

   ```xml
...

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <TextView
            android:id="@+id/textView"
            ... />

        <TextView
            android:id="@+id/textView2"
            ... />

        <EditText
            android:id="@+id/txtIdProDelete"
            ... />

        <LinearLayout
            ...>

            <Button
                android:id="@+id/btnYesDelete"
                ... />

            <Button
                android:id="@+id/btnNoDelete"
                ... />

        </LinearLayout>
    </LinearLayout>

</FrameLayout>
   ```

**Obtención de Datos del Proyecto a Eliminar:**

Se utiliza el método `onViewCreated()` para obtener los argumentos pasados al fragmento, que contiene el ID del proyecto a eliminar.
Si hay un ID válido, se muestra en el campo de texto correspondiente.
```java
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
```
**Manejo del Evento onClick de los Botones:**

Se configuran dos botones: uno para confirmar la eliminación del proyecto y otro para cancelar la acción.
Cuando se hace clic en el botón "SÍ", se envía una solicitud a la API para eliminar el proyecto.
Se muestra un mensaje de éxito o error dependiendo del resultado de la solicitud de eliminación.
Cuando se hace clic en el botón "NO", se cancela la acción y se redirige al usuario de regreso al fragmento de inicio.
```java
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
```

### CAPTURA DE PANTALLA
![Delete Element Fragment](https://i.imgur.com/ClMYlwF.png)

## **Activity Cerrar Sesión**

El fragmento `SignOutFragment` está diseñado para permitir que el usuario cierre sesión en la aplicación. A continuación se explica cómo funciona:

1. **Configuración de la Interfaz de Usuario (UI)**:

   - La interfaz de usuario se define en el archivo XML `fragment_sign_out.xml`.
   - Muestra un mensaje de confirmación para cerrar sesión y proporciona dos botones: "SÍ" y "NO" para confirmar o cancelar la acción.

   ```xml
    ...

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:id="@+id/textView"
            ... />

        <LinearLayout
            ...>

            <Button
                android:id="@+id/btnYesSignOut"
                ... />

            <Button
                android:id="@+id/btnNoSignOut"
                ... />

    ...
    ```

**Configuración de Google Sign-In:**

Se configuran las opciones de inicio de sesión de Google utilizando `GoogleSignInOptions`.
Se crea un cliente de inicio de sesión de Google (`GoogleSignInClient`) utilizando las opciones configuradas.
```java
// Configuración de las opciones de inicio de sesión de Google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Cliente de inicio de sesión de Google
        gsc = GoogleSignIn.getClient(getContext(), gso);
```

**Manejo de los Eventos onClick de los Botones:**

Cuando se hace clic en el botón "SÍ", se llama al método `signOut()` para cerrar sesión.
Después de cerrar sesión, se redirige al usuario a la pantalla de inicio de sesión (Login).
Cuando se hace clic en el botón "NO", se cancela la acción y se redirige al usuario de regreso al fragmento de inicio.
```java
btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(); // Cerrar sesión de Google

                // Redirigir al usuario a la pantalla de inicio de sesión
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar de regreso al fragmento de inicio
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.homeFragment);
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.navigation_home);
            }
        });
```

**Método para Cerrar Sesión de Google:**

El método `signOut()` llama al método `signOut()` del cliente de inicio de sesión de Google (`GoogleSignInClient`) para cerrar sesión.
Muestra un mensaje de éxito o error dependiendo del resultado del cierre de sesión.
```java
// Método para cerrar sesión de Google
    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Cierre de sesión exitoso
                    Toast.makeText(getContext(), "Se ha cerrado sesión correctamente", Toast.LENGTH_SHORT).show();
                    // Ahora puedes realizar cualquier acción adicional después del cierre de sesión
                } else {
                    // Error al cerrar sesión
                    Toast.makeText(getContext(), "No se pudo cerrar sesión. Inténtalo de nuevo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
```

### CAPTURA DE PANTALLA
![SignOut Fragment](https://i.imgur.com/c5UyJtQ.png)

## 💡 Funcionalidades Adicionales Implementadas

- Se han implementado las siguientes funcionalidades adicionales para obtener la puntuación máxima de 10:

    - Botón para editar en cada proyecto: Carga los datos del proyecto directamente en el fragment de edición.
    - Botón para eliminar en cada proyecto: Carga el id proyecto directamente en el fragment de eliminación.
    - Botón en el fragment de edición para cargar los datos de un proyecto por un id introducido.

## Versiones y Dependencias

- Android Studio: 2022.3.1
- Gradle Plugin: 8.1.3
- Gradle Version: 8.0
- API: 34

### Dependencias

- **com.squareup.retrofit2:retrofit:2.9.0**: Retrofit es una biblioteca de cliente HTTP para Android y Java que simplifica la comunicación con servicios web RESTful. Esta dependencia proporciona la biblioteca principal Retrofit.

- **com.squareup.retrofit2:converter-gson:2.9.0**: Esta dependencia proporciona un convertidor Gson para Retrofit. Gson es una biblioteca de Java que se utiliza para convertir objetos Java en su representación JSON y viceversa. Este convertidor Gson permite a Retrofit utilizar Gson para la serialización y deserialización de objetos JSON.

- **com.squareup.picasso:picasso:2.71828**: Picasso es una biblioteca de Android que simplifica la carga de imágenes desde URL o recursos locales a las vistas de tu aplicación. Esta biblioteca maneja automáticamente la memoria caché y la gestión de subprocesos para cargar imágenes de manera eficiente y sin problemas.

- **com.google.android.gms:play-services-auth:20.7.0**: Esta dependencia proporciona servicios de autenticación de Google Play. Permite a los usuarios autenticarse en tu aplicación utilizando sus cuentas de Google, lo que facilita la integración de la autenticación en aplicaciones Android.


## Observaciones

- Se ha cumplido con todos los requisitos mínimos especificados en el enunciado.
- Se han implementado funcionalidades adicionales para obtener la puntuación máxima de 10 según las opciones proporcionadas.


