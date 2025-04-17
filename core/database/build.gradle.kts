plugins {
    alias(libs.plugins.gitgud.android.library)
    alias(libs.plugins.gitgud.hilt)
}

android {
    namespace = "id.idham.gitgud.core.database"
}

dependencies {
    implementation(projects.core.model)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}