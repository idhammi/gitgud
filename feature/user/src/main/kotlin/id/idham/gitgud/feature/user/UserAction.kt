package id.idham.gitgud.feature.user

sealed interface UserAction {
    data object Load : UserAction
}
