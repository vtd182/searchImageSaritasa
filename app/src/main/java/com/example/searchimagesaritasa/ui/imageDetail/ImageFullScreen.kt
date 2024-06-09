package com.example.searchimagesaritasa.ui.imageDetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.searchimagesaritasa.data.model.Image
import com.example.searchimagesaritasa.ui.setting.SettingActivity
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageFullScreen(images: List<Image>, selectedIndex: Int, onBack: () -> Unit) {
    val pagerState = rememberPagerState(initialPage = selectedIndex)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = images[pagerState.currentPage].title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(context, SettingActivity::class.java)
                        context.startActivity(intent)

                    }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        openImageInBrowser(context, images[pagerState.currentPage].imageUrl)
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Open in Browser")
                    }
                    IconButton(onClick = {
                        shareImage(context, images[pagerState.currentPage].imageUrl)
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = {
                        downloadImage(context, images[pagerState.currentPage].imageUrl)
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Download")
                    }
                }
            }
        },

        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                HorizontalPager(
                    state = pagerState,
                    count = images.size,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    AsyncImage(
                        model = images[page].imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    )
}

private fun downloadImage(context: Context, imageUrl: String) {
    // TODO: Implement image download logic here
}

private fun openImageInBrowser(context: Context, imageUrl: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(imageUrl)
    )
    context.startActivity(intent)
}

private fun shareImage(context: Context, imageUrl: String) {
    val imageUri: Uri = Uri.parse(imageUrl)
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "image/*"
        putExtra(Intent.EXTRA_STREAM, imageUri)
    }
    context.startActivity(Intent.createChooser(shareIntent, null))
}
