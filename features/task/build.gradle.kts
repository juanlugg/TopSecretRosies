plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.moronlu18.taskFragment"
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {
    implementation(project(":app"))
    implementation(project(":domain:invoiceD"))
    implementation("androidx.core:core-ktx:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //Implementar la navegación por módulos
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")

    //Librería de animaciones lottie
    val lottieVersion = "3.4.0"
    implementation("com.airbnb.android:lottie:$lottieVersion")
    //Convertir un Flow a LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
}