plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.moronlu18.itemcreation"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":app"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation(project(mapOf("path" to ":domain:invoiceD")))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    implementation(project(":domain:invoiceD"))
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.preference:preference-ktx:1.1.1")


    //Implementar la navegación por módulos
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.5")

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")



    implementation("androidx.core:core-ktx:1.9.0")

    // Dependencias para las pruebas
    testImplementation("com.google.truth:truth:1.4.0")

}