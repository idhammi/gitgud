plugins {
    alias(libs.plugins.gitgud.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "id.idham.gitgud.core.designsystem"

    buildFeatures {
        compose = true
    }
}

dependencies {
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)
    debugApi(libs.androidx.ui.tooling)
    debugApi(libs.androidx.ui.test.manifest)
}
