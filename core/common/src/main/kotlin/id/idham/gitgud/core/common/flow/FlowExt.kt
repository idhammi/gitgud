package id.idham.gitgud.core.common.flow

import id.idham.gitgud.core.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T> Flow<List<T>>.mapToUiState(): Flow<UiState<List<T>>> =
    map { list ->
        if (list.isEmpty()) UiState.Empty else UiState.Success(list)
    }.onStart {
        emit(UiState.Loading)
    }.catch {
        emit(UiState.Error(it.message))
    }
