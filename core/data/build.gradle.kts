plugins {
    alias(libs.plugins.gitgud.android.library)
    alias(libs.plugins.gitgud.hilt)
}

android {
    namespace = "id.idham.gitgud.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.database)

    // Pagination
    implementation(libs.androidx.paging.runtime.ktx)
}