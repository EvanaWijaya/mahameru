plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

}

android {
    compileSdk = 35

    namespace = "com.example.myapplication"

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 26
        targetSdk = 35

        versionCode = 1
        versionName = "1.0"
    }

    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.compose.ui:ui:1.7.5")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.5")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation ("com.google.accompanist:accompanist-pager:0.31.3-beta")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.30.1")
    implementation ("com.jakewharton.threetenabp:threetenabp:1.4.4")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("com.google.accompanist:accompanist-insets:0.26.4-beta")



    implementation("androidx.compose.runtime:runtime:1.7.5")
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.ui.text)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.foundation.android)


    // Untuk dukungan preview di debug mode
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")

    // Dependensi AndroidX lainnya
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.compose.animation:animation:1.5.1")
    implementation ("androidx.navigation:navigation-compose:2.7.2")


    // Dependensi Material Components untuk dukungan tema lama
    implementation("com.google.android.material:material:1.8.0")
    implementation ("androidx.compose.material:material-icons-extended:1.3.1")

    // Dependensi JUnit untuk unit testing
    testImplementation("junit:junit:4.13.2")

    //punya herman
    implementation("androidx.compose.material:material:1.5.3")
    implementation("androidx.navigation:navigation-compose:2.7.2")
    implementation ("androidx.compose.material:material-icons-core:1.5.1")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0-alpha01")
    implementation("org.jetbrains.compose.material3:material3:1.8.0-alpha01")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation ("com.google.zxing:core:3.4.1")
}