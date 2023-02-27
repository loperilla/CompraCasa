plugins {
    id("com.android.dynamic-feature")
    kotlin("android")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
}
android {
    namespace = "com.loperilla.login"
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
}

dependencies {
    implementation(project(":app"))
    implementation(project(":data"))
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    implementation(libs.appcompat)
    implementation(libs.android.material)
    implementation(libs.bundles.navigation)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.annotations)
}