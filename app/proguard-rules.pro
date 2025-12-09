# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# ========================================
# DEBUGGING
# ========================================

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile


# ========================================
# HILT - INYECCIÓN DE DEPENDENCIAS
# ========================================

# Mantiene la clase Application anotada con @HiltAndroidApp
# NECESARIA: Hilt necesita esta clase para inicializar el grafo de dependencias.
# Sin ella, toda la inyección de dependencias falla y la app crashea al inicio.
-keep @dagger.hilt.android.HiltAndroidApp class * { *; }

# Mantiene constructores anotados con @Inject
# NECESARIA: Hilt usa reflection para llamar estos constructores e inyectar
# dependencias. Si R8 los elimina, las clases no pueden ser instanciadas y
# obtendrás crashes de inyección (ViewModel, Repository, LocationManager).
-keepclassmembers class * {
    @javax.inject.Inject <init>(...);
}

# ========================================
# ROOM DATABASE
# ========================================

# Mantiene todas las clases anotadas con @Entity
# NECESARIA: Room usa reflection para mapear estas clases a tablas SQL.
# Sin esta regla, Room no puede crear las tablas y crashea con
# "Cannot find implementation for database".
-keep @androidx.room.Entity class * { *; }

# Mantiene todas las interfaces/clases anotadas con @Dao
# NECESARIA: Room genera implementaciones de estos DAOs en tiempo de compilación
# y usa reflection para accederlos. Si se ofuscan, Room no puede encontrar los
# métodos de consulta y falla con "Cannot access database methods".
-keep @androidx.room.Dao interface * { *; }

# Mantener las clases de modelos

-keep class com.example.mod7_final.data.local.** { *; }
-keep class com.example.mod7_final.data.models.** { *; }