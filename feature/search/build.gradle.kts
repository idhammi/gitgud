plugins {
    alias(libs.plugins.gitgud.android.library)
    alias(libs.plugins.gitgud.hilt)
}

android {
    namespace = "id.idham.gitgud.feature.search"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}