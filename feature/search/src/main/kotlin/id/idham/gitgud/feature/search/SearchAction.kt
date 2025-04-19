package id.idham.gitgud.feature.search

sealed interface SearchAction {
    data class SetQuery(val query: String) : SearchAction
    data object SubmitSearch : SearchAction
    data object LoadInitialUsers : SearchAction
}
