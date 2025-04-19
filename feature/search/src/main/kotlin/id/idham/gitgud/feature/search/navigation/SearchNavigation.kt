package id.idham.gitgud.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import id.idham.gitgud.core.model.SimpleUser
import id.idham.gitgud.feature.search.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

fun NavGraphBuilder.searchScreen(
    onItemClicked: (SimpleUser) -> Unit
) {
    composable<SearchRoute> {
        SearchScreen(onItemClicked = onItemClicked)
    }
}
