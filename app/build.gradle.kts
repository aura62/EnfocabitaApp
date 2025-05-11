

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

}

android {
    namespace = "com.aura.enfocabita"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aura.enfocabita"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    val room_version = "2.7.1"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //androidTestImplementation(libs.androidx.compose.ui.test.junit)

    // Compose
    implementation(libs.androidx.ui)
    implementation (libs.androidx.material3)
    implementation (libs.androidx.activity.compose)
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    // Navigation Compose
    implementation (libs.androidx.navigation.compose)

    // Coroutines
    implementation (libs.kotlinx.coroutines.android)

    // — MVVM / StateFlow —
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // — Compose UI Tooling & Preview —
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation     (libs.androidx.compose.ui.tooling.preview)

    // — Room (persistencia local) —
    implementation(libs.androidx.room.runtime)
    implementation(libs.room.ktx)

    // — Coroutines —
    implementation(libs.kotlinx.coroutines.android)

    // — DataStore para prefs —
    implementation(libs.androidx.datastore.preferences)

    // — Seguridad: cifrado de datos sensibles —
    implementation(libs.androidx.security.crypto)


    //Para inyeccion de dependencias
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.android)







}