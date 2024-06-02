package com.example.searchimagesaritasa.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import coil.compose.AsyncImage
import com.example.searchimagesaritasa.data.model.Image

@Composable
fun ImageFullScreenDialog(
    images: List<Image>,
    selectedIndex: Int,
    onDismiss: () -> Unit,
    onOpenBrowser: (String) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = selectedIndex)

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            AlertDialog(
                onDismissRequest = onDismiss,
                shape = RoundedCornerShape(16.dp),
                containerColor = Color.White,
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            count = images.size,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ) { page ->
                            AsyncImage(
                                model = images[page].imageUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = images[pagerState.currentPage].title,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        onOpenBrowser(images[pagerState.currentPage].imageUrl)
                    }) {
                        Text("Open in Browser")
                    }
                },
                dismissButton = {
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
