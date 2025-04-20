package id.idham.gitgud.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import id.idham.gitgud.feature.search.navigation.SearchRoute
import id.idham.gitgud.feature.search.navigation.searchScreen
import id.idham.gitgud.feature.user.navigation.navigateToUser
import id.idham.gitgud.feature.user.navigation.userScreen

@Composable
fun GitgudApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SearchRoute,
        modifier = modifier
    ) {
        searchScreen(
            onItemClicked = navController::navigateToUser
        )
        userScreen()
    }
}
