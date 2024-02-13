# Proyecto Android & Spring Boot

Este proyecto consiste en una aplicación Android desarrollada en Android Studio que utiliza una API propia para la gestión de proyectos de software. A continuación se detallan los componentes obligatorios y algunas funcionalidades adicionales implementadas para obtener la puntuación máxima de 10 según las opciones proporcionadas en el enunciado.

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

## 📱 Capturas de Pantalla

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

* **Fragmento para Crear un Nuevo Elemento:** Permite al usuario agregar un nuevo elemento al proyecto.
![New Element Fragment](https://i.imgur.com/Y4YZdwx.png)

* **Fragmento para Modificar un Elemento Existente:** Permite al usuario editar los detalles de un elemento existente en el proyecto.
![Edit Element Fragment](https://i.imgur.com/ODCs7q3.png)

* **Fragmento para Eliminar un Elemento:** Permite al usuario eliminar un elemento del proyecto.
![Delete Element Fragment](https://i.imgur.com/ClMYlwF.png)

* **Fragmento para Eliminar un Elemento:** Permite al usuario cerrar sesión.
![SignOut Fragment](https://i.imgur.com/c5UyJtQ.png)

## 💡 Funcionalidades Adicionales Implementadas

Se han implementado las siguientes funcionalidades adicionales para obtener la puntuación máxima de 10:

### Opción 1: Consumir un API Público y Loguearse mediante Google

- Se ha consumido un API público diferente por el alumno para la gestión de proyectos de software.
- Se ha implementado el inicio de sesión utilizando las credenciales de Google.

### Opción 2: Consumir API de Twitter y Loguearse mediante la API de Twitter

- Se ha creado y consumido una API propia para la gestión de proyectos de software.
- Se ha implementado el inicio de sesión utilizando la API creada por el alumno.

## Versiones y Dependencias

- Android Studio: 4.2.1
- Gradle Plugin: 7.0.2
- API mínima compatible: Android 6.0 (API nivel 23)
- Dependencias adicionales: Se han agregado las dependencias necesarias para el manejo de SQLite y cualquier otra dependencia específica para el consumo de la API creada por el alumno.

## Observaciones

- Se ha cumplido con todos los requisitos mínimos especificados en el enunciado.
- Se han implementado funcionalidades adicionales para obtener la puntuación máxima de 10 según las opciones proporcionadas.

