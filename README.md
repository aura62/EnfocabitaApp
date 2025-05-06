# 🧠 Enfocabita

**Enfocabita** es una aplicación de productividad desarrollada en Android con arquitectura MVVM y Jetpack Compose. Está orientada a la gestión de hábitos y sesiones de trabajo enfocadas utilizando la técnica Pomodoro.

---

## 📱 Características principales

- ✅ Clasificación de hábitos en: adquirir, mantener, abandonar
- ⏱️ Temporizador Pomodoro personalizable
- 📆 Visualización del progreso diario y semanal
- 📊 Estadísticas básicas de hábitos y rachas
- 🔔 Recordatorios para hábitos y sesiones Pomodoro
- 🌙 Modo claro/oscuro configurable
- 📂 Base de datos local con Room
- 🔐 Gestión de cuentas de usuario

---

## 📦 Estructura del proyecto

```plaintext
com.example.enfocabita/
├── data/               # Fuente de datos (Room, repositorios)
├── domain/             # Modelos de negocio, interfaces y casos de uso
├── presentation/       # Pantallas UI (Jetpack Compose)
├── viewmodel/          # ViewModels asociados a cada módulo
├── navigation/         # Configuración de rutas y navegación
├── di/                 # Módulos de inyección de dependencias
├── utils/              # Clases auxiliares, constantes, extensiones
├── MainActivity.kt
└── EnfocabitaApp.kt

🛠️ Tecnologías y herramientas utilizadas
Kotlin

Jetpack Compose

Room Database

Arquitectura MVVM (Model-View-ViewModel)

Koin (inyección de dependencias)

Navigation Component (Compose)

Coroutines y Flow

Android Studio Meerkat

🧪 Requisitos para ejecutar
Android Studio ladybug (o superior)

SDK mínimo: Android 8.0 (API 26)

📄 Licencia
Este proyecto se distribuye bajo licencia propietaria, tal como se indica en los términos del documento de licencia incluido en la aplicación.

🙋‍♀️ Autor
Desarrollado por Aura.V
Proyecto de fin de grado, orientado a la planificación, diseño e implementación de una aplicación real con objetivos funcionales y no funcionales definidos a lo largo de un proyecto.



