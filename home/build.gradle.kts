plugins {
    id("com.android.dynamic-feature")
    kotlin("android")
}
android {
    namespace = "com.loperilla.home"
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
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    implementation(libs.appcompat)
    implementation(libs.android.material)
    implementation(libs.bundles.navigation)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.annotations)
}