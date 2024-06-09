package com.example.searchimagesaritasa.ui.home

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.searchimagesaritasa.appCache.MyApplication
import com.example.searchimagesaritasa.data.model.Image
import com.example.searchimagesaritasa.data.repository.ImageRepository
import com.example.searchimagesaritasa.ui.imageDetail.ImageDetailActivity

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(
        factory = SearchViewModel.SearchViewModelFactory(
            ImageRepository()
        )
    )
) {
    // Search query
    var query by remember { mutableStateOf("") }

    // Images list from ViewModel
    val images by viewModel.images.observeAsState(emptyList())

    // Search history list from ViewModel
    val searchHistory by viewModel.searchHistory.observeAsState(emptyList())

    // Loading state
    val isLoading by viewModel.isLoading.observeAsState(false)

    // LazyGridState is used to load next page when user scrolls to the bottom
    val gridState = rememberLazyGridState()

    // focusManager and keyboardController are used to hide keyboard when search
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // shouldLoadNextPage is used to load next page when user scrolls to the bottom
    val shouldLoadNextPage = remember {
        derivedStateOf {
            // check if the last item is visible
            val lastVisibleItemIndex =
                gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= images.size - 1
        }
    }

    // Load next page when shouldLoadNextPage is true
    LaunchedEffect(shouldLoadNextPage.value) {
        if (shouldLoadNextPage.value) {
            Log.e("SearchScreen", "Loading next page")
            viewModel.loadNextPage()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.padding(
                top = 30.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        ) {
            SearchBarComponent(
                query = query,
                onQueryChange = { query = it },
                onSearch = {
                    viewModel.searchImages(it)

                    // hide keyboard when search
                    focusManager.clearFocus()
                    keyboardController?.hide()
                },
                historyList = searchHistory
            )
        }

        LazyVerticalGrid(
            // The grid will be more flexible
            columns = GridCells.Adaptive(minSize = 150.dp),
            state = gridState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(images) { image ->
                ImageItem(image, images)
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
fun ImageItem(image: Image, images: List<Image>) {
    val context = LocalContext.current
    val imageLoader = (context.applicationContext as MyApplication).newImageLoader()
    val cacheDir = context.cacheDir.resolve("image_cache")
    Log.e("ImageItem", "Cache: ${image.title}")
    GlobalFunction.logCacheSize(cacheDir)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                val selectedImageIndex = images.indexOf(image)
                val intent = Intent(context, ImageDetailActivity::class.java).apply {
                    putExtra("images", ArrayList(images))
                    putExtra("selectedIndex", selectedImageIndex)
                }
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = image.imageUrl,
                contentDescription = null,
                imageLoader = imageLoader,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Text(
                text = image.title,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}
