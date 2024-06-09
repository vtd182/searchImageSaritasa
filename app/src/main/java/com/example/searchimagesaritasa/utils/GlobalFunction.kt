package com.example.searchimagesaritasa.utils

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.File

object GlobalFunction {
    fun logCacheSize(folder: File) {
        val cacheSize = getFolderSize(folder)
        Log.e("CacheSize", "Cache size: $cacheSize bytes")
    }

    private fun getFolderSize(folder: File): Long {
        var size: Long = 0
        if (folder.isDirectory) {
            val files = folder.listFiles()
            if (files != null) {
                for (file in files) {
                    size += if (file.isDirectory) {
                        getFolderSize(file)
                    } else {
                        file.length()
                    }
                }
            }
        } else {
            size = folder.length()
        }
        return size
    }

    fun getCacheSize(folder: File): Long {
        return getFolderSize(folder)
    }

    fun openInBrowser(context: Context, url: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        context.startActivity(intent)
    }

    fun downloadImage(context: Context, imageUrl: String) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request = DownloadManager.Request(Uri.parse(imageUrl))
            .setTitle("Image Download")
            .setDescription("Downloading image...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image.jpg")

        downloadManager.enqueue(request)
    }


    fun shareImage(context: Context, imageUrl: String) {
        val imageUri: Uri = Uri.parse(imageUrl)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, imageUri)
        }
        context.startActivity(Intent.createChooser(shareIntent, null))
    }
}
