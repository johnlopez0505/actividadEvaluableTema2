# Explicación de la APP Tema2

## Clase `Login`

### Descripción
Esta clase maneja la funcionalidad de inicio de sesión, validando las credenciales del usuario.

### Métodos

#### `onCreate`
- **Descripción**: Método principal llamado al crear la actividad.
- **Acciones**:
    - Infla la interfaz de usuario.
    - Inicializa eventos.
    - Carga el último usuario.

#### `initEvents`
- **Descripción**: Inicializa eventos para los botones de inicio de sesión y registro.

#### `validarCredenciales`
- **Descripción**: Valida las credenciales ingresadas por el usuario.
- **Acciones**:
    - Obtiene el nombre de usuario y contraseña.
    - Busca al usuario en la lista de usuarios.
    - Si las credenciales son válidas, inicia la actividad principal.

#### `registerUser`
- **Descripción**: Inicia la actividad de registro.

#### `getLastUsername`
- **Descripción**: Obtiene el último nombre de usuario y contraseña almacenados.
- **Retorno**: Una pareja de Strings (nombre de usuario y contraseña).

#### `guardarUltimoUsuario`
- **Descripción**: Guarda el último nombre de usuario y contraseña en las preferencias compartidas.

#### `loadLastUser`
- **Descripción**: Carga el último usuario almacenado y establece los campos de texto.



## Clase `Register`

### Descripción
Maneja la funcionalidad de registro de nuevos usuarios.

### Métodos

#### `onCreate`
- **Descripción**: Método principal llamado al crear la actividad.
- **Acciones**:
    - Infla la interfaz de usuario.
    - Inicializa eventos.

#### `initEvents`
- **Descripción**: Inicializa eventos para los botones de registro y regreso al inicio de sesión.

#### `comprobarEstado`
- **Descripción**: Verifica que todos los campos estén completos y coincide la contraseña con la confirmación.
- **Acciones**:
    - Si los campos son válidos, crea un nuevo usuario y regresa al inicio de sesión.

#### `regresarLogin`
- **Descripción**: Regresa a la actividad de inicio de sesión.



## Clase `Principal`

### Descripción
Representa la pantalla principal de la aplicación.

### Métodos

#### `onCreate`
- **Descripción**: Método principal llamado al crear la actividad.
- **Acciones**:
    - Infla la interfaz de usuario.
    - Inicializa eventos.
    - Inicia la animación de entrada.

#### `initHander`
- **Descripción**: Inicia un hilo que controla la animación de entrada en la interfaz de usuario.
- **Acciones**:
    - Muestra un gif y cambia el fondo después de un tiempo de espera.

#### `login`
- **Descripción**: Obtiene el nombre de usuario de la actividad anterior y lo muestra en la interfaz de usuario.

#### `initEvent`
- **Descripción**: Inicializa eventos para los botones de llamada, entrada de datos, chistes, URL, alarma y correo electrónico.

#### `loadGif`
- **Descripción**: Muestra un gif en la interfaz de usuario.

#### `hideGif`
- **Descripción**: Oculta el gif en la interfaz de usuario.




# Clase `Second`

## Descripción
Esta clase gestiona la actividad de una segunda pantalla con la capacidad de realizar llamadas telefónicas.

## Métodos

### `onCreate`
- **Descripción**: Método principal llamado al crear la actividad.
- **Acciones**:
    - Infla la interfaz de usuario.
    - Inicializa eventos.
    - Muestra datos recibidos.

### `showData`
- **Descripción**: Muestra los datos en la interfaz de usuario.
- **Acciones**:
    - Obtiene el nombre pasado por la actividad anterior.
    - Muestra un mensaje de éxito.

### `initEvent`
- **Descripción**: Inicializa eventos para el botón de llamada.

### `requestPermissions`
- **Descripción**: Solicita permisos para realizar llamadas.
- **Acciones**:
    - Comprueba la versión de Android.
    - Si la versión es mayor o igual a M, solicita permisos; de lo contrario, realiza la llamada directamente.

### `call`
- **Descripción**: Realiza la llamada telefónica.

### `permissionPhone`
- **Descripción**: Verifica si se tienen permisos para realizar llamadas telefónicas.
- **Retorno**: `true` si se tienen permisos, `false` de lo contrario.

### `requestPermissionLauncher`
- **Descripción**: Lanzador de solicitudes de permisos para llamadas telefónicas.

---

# Clase `SintetizarTextoEnVoz`

## Descripción
Maneja la síntesis de texto a voz y presenta chistes al usuario.

## Propiedades

- **`TOUCH_MAX_TIME`**: Tiempo máximo permitido entre toques para considerar una doble pulsación.
- **`touchLastTime`**: Tiempo del último toque.
- **`touchNumber`**: Número de toques (no utilizado actualmente).
- **`handler`**: Manejador para la ejecución de tareas en el hilo principal.
- **`chisteNumero`**: Número aleatorio para seleccionar un chiste.
- **`textToSpeech`**: Objeto para la síntesis de texto a voz.

## Métodos

### `onCreate`
- **Descripción**: Método principal llamado al crear la actividad.
- **Acciones**:
    - Configura la síntesis de texto a voz.
    - Inicia un hilo para el manejo de la interfaz.
    - Inicializa eventos.

### `initHander`
- **Descripción**: Inicia un hilo que controla la animación y carga de elementos en la interfaz.
- **Acciones**:
    - Muestra un gif y realiza cambios visuales después de un tiempo de espera.

### `configureTextToSpeech`
- **Descripción**: Configura la síntesis de texto a voz.

### `initEvent`
- **Descripción**: Inicializa eventos para los botones de salida y reproducción de chistes.
- **Acciones**:
    - Controla las pulsaciones para mostrar chistes y descripciones.

### `speakMeDescription`
- **Descripción**: Convierte un texto a voz y lo reproduce.

### `executorDoubleTouch`
- **Descripción**: Reproduce un chiste al detectar una doble pulsación.
- **Acciones**:
    - Muestra un mensaje y reproduce el chiste seleccionado.

### `onDestroy`
- **Descripción**: Detiene la síntesis de texto a voz al destruir la actividad.

### `hideGif`
- **Descripción**: Oculta el gif en la interfaz de usuario.

### `loadGif`
- **Descripción**: Carga un gif en la interfaz de usuario.

### `loadGif2`
- **Descripción**: Carga un segundo gif en la interfaz de usuario.

---

# Clase `Dados`

## Descripción
Controla la funcionalidad de la actividad de lanzamiento de dados.

## Propiedades

- **`sum`**: Variable para almacenar la suma de los dados.

## Métodos

### `onCreate`
- **Descripción**: Método principal llamado al crear la actividad.
- **Acciones**:
    - Infla la interfaz de usuario.
    - Inicializa eventos.

### `initEvent`
- **Descripción**: Inicializa eventos para los botones de jugar y salir.

### `loadGif`
- **Descripción**: Carga un gif en la interfaz de usuario.

### `game`
- **Descripción**: Inicia el juego de lanzamiento de dados.
- **Acciones**:
    - Limpia la imagen del dado.
    - Muestra el resultado del juego.

### `sheduleRun`
- **Descripción**: Planifica la ejecución de hilos para simular el lanzamiento de dados.

