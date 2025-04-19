plugins {
    alias(libs.plugins.gitgud.android.library)
}

android {
    namespace = "id.idham.gitgud.core.common"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.android)
}
