# Proyecto Final - Gestión de Productos (Mod7_Final)

Aplicación Android desarrollada en Kotlin utilizando Jetpack Compose. Esta aplicación permite gestionar una lista de productos integrando persistencia de datos local y consumo de servicios REST.

## 🛠 Tecnologías Utilizadas

* **Lenguaje:** Kotlin
* **Interfaz de Usuario:** Jetpack Compose (Material3)
* **Arquitectura:** MVVM (Model-View-ViewModel)
* **Inyección de Dependencias:** Dagger Hilt
* **Base de Datos:** Room (SQLite)
* **Red:** Retrofit + Gson (API: FakeStoreAPI)
* **Asincronía:** Coroutines & Flow
* **Testing:** JUnit4, Espresso, Mockk

---

## 🚀 Instrucciones de Ejecución Local

Para ejecutar la aplicación en un entorno de desarrollo:

1.  **Requisitos:**
    * Android Studio (Versión Ladybug o superior recomendada).
    * JDK 17 configurado en el IDE.
    * Dispositivo físico o Emulador con Android 8.0 (API 26) o superior.

2.  **Pasos:**
    1.  Abrir Android Studio.
    2.  Seleccionar **File > Open** y navegar hasta la carpeta descomprimida del proyecto.
    3.  Esperar a que Gradle finalice la sincronización (**Sync Project**).
    4.  Seleccionar el dispositivo de destino en la barra superior.
    5.  Hacer clic en el botón **Run** (Icono de Play verde) o presionar `Shift + F10`.

---

## 🧪 Cómo ejecutar los Tests

El proyecto incluye dos tipos de pruebas automatizadas:

### 1. Tests Unitarios (Lógica de Negocio y Mappers)
Estas pruebas verifican la lógica interna sin necesidad de un emulador.

* **Ubicación:** `app/src/test/java/com/example/mod7_final/`
* **Archivos:**
    * `ProductMapperTest.kt`: Verifica la transformación correcta de datos entre Entidades y Modelos.
    * `ProductViewModelTest.kt`: Verifica la lógica del ViewModel y la actualización de estados.
* **Ejecución:**
    1.  En Android Studio, hacer clic derecho sobre la carpeta `test` (resaltada en verde).
    2.  Seleccionar **Run 'Tests in 'test''**.

### 2. Tests de Instrumentación (UI y Persistencia)
Estas pruebas requieren un dispositivo o emulador conectado, ya que interactúan con componentes del sistema Android (Base de datos y UI).

* **Ubicación:** `app/src/androidTest/java/com/example/mod7_final/`
* **Archivos:**
    * `ProductDaoTest.kt`: Verifica que Room guarde, lea y elimine datos correctamente en una base de datos en memoria.
    * `ProductScreenTest.kt`: Verifica que la interfaz gráfica muestre la lista de productos descargados de la API.
* **Ejecución:**
    1.  Asegurarse de que el emulador esté encendido.
    2.  Hacer clic derecho sobre la carpeta `androidTest`.
    3.  Seleccionar **Run 'Tests in 'androidTest''**.

---

## 📦 Generación del APK para Producción

Para generar el archivo instalable (`app-release.apk`) firmado y optimizado:

1.  En el menú superior de Android Studio, ir a **Build > Generate Signed Bundle / APK**.
2.  Seleccionar la opción **APK** y hacer clic en **Next**.
3.  **Configuración del Keystore:**
    * Si ya tiene uno, seleccione "Choose existing".
    * Si no, haga clic en "Create new" y complete los datos requeridos.
4.  Ingresar la contraseña del Keystore y la contraseña de la Llave (Key).
5.  Hacer clic en **Next**.
6.  Seleccionar la variante de compilación **release**.
7.  Hacer clic en **Create**.
8.  Una vez finalizado, aparecerá una notificación. Hacer clic en **locate** para abrir la carpeta que contiene el archivo `app-release.apk`.