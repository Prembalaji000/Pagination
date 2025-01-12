package com.example.pagination.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pagination.domain.models.useCase.GetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class ImageViewModel @Inject constructor(
    private val getImagesUseCase: GetImageUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")

    val image = _query
        .filter { it.isNotBlank() }
        .debounce(1000)
        .flatMapLatest { query ->
            getImagesUseCase.invoke(query).flow
        }.cachedIn(viewModelScope)

    fun updateQuery(q:String) = _query.update { q }

}