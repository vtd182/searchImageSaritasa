package com.example.searchimagesaritasa.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.searchimagesaritasa.data.model.Image
import com.example.searchimagesaritasa.data.repository.ImageRepository

class SearchViewModel(private val repository: ImageRepository) : ViewModel() {
    private val _images = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>> = _images

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchHistory = MutableLiveData<List<String>>(emptyList())
    val searchHistory: LiveData<List<String>> get() = _searchHistory

    private var currentPage = 1
    private var isLastPage = false
    private var lastQuery = ""

    fun searchImages(query: String) {
        lastQuery = query
        currentPage = 1
        isLastPage = false
        _images.value = emptyList()
        loadImages()

        // Update search history
        val currentHistory = _searchHistory.value.orEmpty().toMutableList()
        if (query.isNotEmpty() && !currentHistory.contains(query)) {
            currentHistory.add(0, query)
            if (currentHistory.size > 5) {
                currentHistory.removeAt(currentHistory.size - 1)
            }
            _searchHistory.value = currentHistory
        }
    }


    // Load the next page of images
    fun loadNextPage() {
        if (!_isLoading.value!! && !isLastPage) {
            currentPage++
            loadImages()
        }
    }


    // Load images from the repository
    private fun loadImages() {
        _isLoading.value = true
        Log.e("SearchViewModel", "loadImages: $currentPage $lastQuery")
        repository.searchImages(lastQuery, currentPage) { result ->
            _isLoading.value = false
            result?.let {
                if (it.images.isEmpty()) {
                    isLastPage = true
                } else {
                    _images.postValue((_images.value ?: emptyList()) + it.images)
                }
            }
        }
    }

    // Factory class for creating SearchViewModel instances
    class SearchViewModelFactory(private val repository: ImageRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
