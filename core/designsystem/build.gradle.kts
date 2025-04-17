plugins {
    alias(libs.plugins.gitgud.android.library)
}

android {
    namespace = "id.idham.gitgud.core.designsystem"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
}