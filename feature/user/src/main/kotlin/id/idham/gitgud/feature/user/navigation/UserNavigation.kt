package id.idham.gitgud.feature.user.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import id.idham.gitgud.feature.user.UserScreen
import id.idham.gitgud.feature.user.UserViewModel
import kotlinx.serialization.Serializable

@Serializable
data class UserRoute(val username: String)

fun NavController.navigateToUser(username: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = UserRoute(username)) {
        navOptions()
    }
}

fun NavGraphBuilder.userScreen() {
    composable<UserRoute> {
        val username = it.toRoute<UserRoute>().username
        UserScreen(
            viewModel = hiltViewModel<UserViewModel, UserViewModel.Factory>(
                key = username,
            ) { factory ->
                factory.create(username)
            },
        )
    }
}
