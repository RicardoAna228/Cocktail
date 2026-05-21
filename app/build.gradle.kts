
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.appcocktails"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.appcocktails"
        minSdk = 24
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures { compose = true }

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
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose BOM
    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)
    //noinspection UseTomlInstead
    implementation("androidx.compose.ui:ui")
    //noinspection UseTomlInstead
    implementation("androidx.compose.material3:material3")
    //noinspection UseTomlInstead
    implementation("androidx.compose.ui:ui-tooling-preview")
    //noinspection UseTomlInstead,GradleDependency
    implementation("androidx.activity:activity-compose:1.9.0")

    // Navigation
    //noinspection GradleDependency,UseTomlInstead
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Lifecycle / ViewModel
    //noinspection UseTomlInstead,GradleDependency
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    //noinspection UseTomlInstead,GradleDependency
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")

    // Retrofit + Gson
    //noinspection UseTomlInstead,NewerVersionAvailable
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    //noinspection UseTomlInstead,NewerVersionAvailable
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    //noinspection UseTomlInstead,NewerVersionAvailable
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Room
    //noinspection GradleDependency,UseTomlInstead
    implementation("androidx.room:room-runtime:2.6.1")
    //noinspection GradleDependency,UseTomlInstead
    implementation("androidx.room:room-ktx:2.6.1")

    // Coil (imágenes)
    //noinspection UseTomlInstead,NewerVersionAvailable
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Coroutines
    //noinspection UseTomlInstead,NewerVersionAvailable
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}