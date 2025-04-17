plugins {
    alias(libs.plugins.gitgud.android.library)
    alias(libs.plugins.gitgud.hilt)
}

android {
    namespace = "id.idham.gitgud.core.network"
}

dependencies {
    implementation(projects.core.model)

    // Network
    api(libs.retrofit)
    api(libs.retrofit.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    // Logging
    implementation(libs.okhttp.logging)
}
