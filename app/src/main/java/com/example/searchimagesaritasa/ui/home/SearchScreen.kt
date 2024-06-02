package com.example.searchimagesaritasa.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.searchimagesaritasa.data.model.Image
import com.example.searchimagesaritasa.data.repository.ImageRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel = viewModel(factory = SearchViewModel.SearchViewModelFactory(
    ImageRepository()
))) {
    var query by remember { mutableStateOf("") }
    val images by viewModel.images.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val gridState = rememberLazyGridState()
    val shouldLoadNextPage = remember {
        derivedStateOf {
            val lastVisibleItemIndex = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= images.size - 1
        }
    }

    LaunchedEffect(shouldLoadNextPage.value) {
        if (shouldLoadNextPage.value) {
            Log.e("SearchScreen", "Loading next page")
            viewModel.loadNextPage()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBarComponent(
            query = query,
            onQueryChange = { query = it },
            onSearch = { viewModel.searchImages(it) },
            historyList = emptyList()
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // You can change the number of columns here
            state = gridState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(images) { image ->
                ImageItem(image)
            }
            if (isLoading) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun ImageItem(image: Image) {
    Log.e("ImageItem", "Image URL: ${image.imageUrl}")
    AsyncImage(
        model = image.imageUrl,
        contentDescription = null,
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                // Handle image click to navigate to detail screen
            }
            .fillMaxWidth()
            .aspectRatio(1f) // Maintain a square aspect ratio
    )
}
