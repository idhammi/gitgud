plugins {
    alias(libs.plugins.gitgud.android.library)
}

android {
    namespace = "id.idham.gitgud.core.ui"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}