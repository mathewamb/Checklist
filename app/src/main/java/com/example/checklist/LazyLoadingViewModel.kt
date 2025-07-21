package com.example.checklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LazyLoadingViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<String>>(emptyList())
    val items: StateFlow<List<String>> = _items

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var page = 0
    private val pageSize = 20
    private var endReached = false

    init {
        loadMoreItems()
    }

    fun loadMoreItems() {
        if (_isLoading.value || endReached) return

        _isLoading.value = true
        viewModelScope.launch {
            delay(1500) // simulate network delay
            val newItems = (1..pageSize).map { "Item #${page * pageSize + it}" }
            // Stop after 100 items as a demo
            if (page >= 5) endReached = true

            _items.value += newItems
            page++
            _isLoading.value = false
        }
    }
}
