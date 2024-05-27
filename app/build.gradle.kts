plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id ("kotlin-kapt")
}

android {
    namespace = "com.moronlu18.invoice"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moronlu18.invoice"
        minSdk = 24
        targetSdk = 33
        versionCode = 2
        versionName = "2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


    dynamicFeatures += setOf(
        ":features:accountsignin",
        ":features:accountsignup",
        ":infrastructure:firebase",
        ":infrastructure:printer",
        ":features:customerun",
        ":domain:invoiceD",
        ":features:invoice",
        ":features:item",
        ":features:task"
    )
}


dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.7.4")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Import the BoM for the Firebase platform
    implementation (platform("com.google.firebase:firebase-bom:32.3.1"))

    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.preference:preference:1.2.0")

    //Soporte de fechas en Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

    val room_version = "2.6.0"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    testImplementation ("com.google.truth:truth:1.4.0")



    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-common:2.6.1")

    //Convertir un Flow a LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
}