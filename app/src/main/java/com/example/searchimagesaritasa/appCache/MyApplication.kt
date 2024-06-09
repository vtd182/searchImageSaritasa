package com.example.searchimagesaritasa.appCache

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache

class MyApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        val cacheDirectory = cacheDir.resolve("image_cache")
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs()
        }
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDirectory)
                    .maxSizePercent(0.05)
                    .build()
            }
            .build()
    }
}
