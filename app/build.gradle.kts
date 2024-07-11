plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.test.testairlines"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.test.testairlines"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    //modules
    implementation(project(":home"))
    implementation(project(":home"))


    //LiveData
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.8")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.1")


    //Dagger-hilt
    implementation ("com.google.dagger:hilt-android:2.50")
    implementation("androidx.annotation:annotation:1.8.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    kapt ("com.google.dagger:hilt-android-compiler:2.50")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    kapt ("androidx.hilt:hilt-compiler:1.2.0")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}