package com.example.searchimagesaritasa.ui.imageDetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.example.searchimagesaritasa.data.model.Image
import com.example.searchimagesaritasa.ui.theme.SearchImageSaritasaTheme
import com.example.searchimagesaritasa.utils.Constants

@Suppress("DEPRECATION")
class ImageDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the selected image index from the intent
        val images = intent.getSerializableExtra(Constants.IMAGES_KEY) as? List<Image>
        val selectedIndex = intent.getIntExtra(Constants.SELECTED_INDEX_KEY, 0)

        setContent {
            SearchImageSaritasaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    images?.let {
                        ImageFullScreen(
                            images = it,
                            selectedIndex = selectedIndex,
                            onBack = { finish() })
                    }
                }
            }
        }
    }
}