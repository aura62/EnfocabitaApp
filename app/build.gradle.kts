

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler)

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose
    implementation(libs.androidx.compose.bom)
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
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.android)

    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(kotlin("test"))
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


}