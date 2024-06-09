package com.example.searchimagesaritasa

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.searchimagesaritasa.ui.home.SearchScreen
import com.example.searchimagesaritasa.ui.theme.SearchImageSaritasaTheme

class MainActivity : AppCompatActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                SearchImageSaritasaTheme {
                    var showExitDialog by remember { mutableStateOf(false) }

                    // Handle back button press
                    BackHandler {
                        showExitDialog = true
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        Scaffold(modifier = Modifier.fillMaxSize()) {
                            SearchScreen()
                        }

                        if (showExitDialog) {
                            ExitConfirmationDialog(
                                onConfirm = { finish() },
                                onDismiss = { showExitDialog = false }
                            )
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "onCreate: ", e)
        }
    }
}

@Composable
fun ExitConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Exit Confirmation") },
            text = { Text("Are you sure you want to exit?") },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("No")
                }
            }
        )
    }
}

