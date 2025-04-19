package id.idham.gitgud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import id.idham.gitgud.core.designsystem.theme.GitGudTheme
import id.idham.gitgud.ui.GitgudApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.attributes.preferredRefreshRate = 90f
        enableEdgeToEdge()
        setContent {
            GitGudTheme {
                GitgudApp()
            }
        }
    }
}
