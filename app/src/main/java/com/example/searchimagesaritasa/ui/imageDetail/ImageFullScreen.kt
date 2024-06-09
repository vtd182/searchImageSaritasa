package com.example.searchimagesaritasa.ui.imageDetail

import com.example.searchimagesaritasa.utils.GlobalFunction
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
                        Icon(Icons.Outlined.Settings, contentDescription = "Settings")
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
                        GlobalFunction.openInBrowser(context, images[pagerState.currentPage].imageUrl)
                    }) {
                        Icon(Icons.Outlined.OpenInBrowser, contentDescription = "Open in Browser")
                    }
                    IconButton(onClick = {
                        GlobalFunction.shareImage(context, images[pagerState.currentPage].imageUrl)
                    }) {
                        Icon(Icons.Outlined.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = {
                        GlobalFunction.downloadImage(context, images[pagerState.currentPage].imageUrl)
                    }) {
                        Icon(Icons.Outlined.Download, contentDescription = "Download")
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

