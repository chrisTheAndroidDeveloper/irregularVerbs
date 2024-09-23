plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.irregularverbs"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.irregularverbs"
        minSdk = 26
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
}

dependencies {

    implementation(libs.gson)
    val work_version = "2.9.1"
    implementation("androidx.work:work-runtime:$work_version")
    implementation("androidx.work:work-runtime-ktx:$work_version")
    implementation("com.google.guava:guava:33.3.0-android")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}