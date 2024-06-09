package com.example.searchimagesaritasa.ui.setting

import com.example.searchimagesaritasa.utils.GlobalFunction
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.searchimagesaritasa.appCache.MyApplication
import com.example.searchimagesaritasa.utils.Constants
import java.io.File

class SettingViewModel : ViewModel() {

    @OptIn(ExperimentalCoilApi::class)
    fun clearCache(context: Context) {
        val imageLoader = (context.applicationContext as MyApplication).newImageLoader()
        // Xóa tất cả cache trong bộ nhỏ
        imageLoader.memoryCache?.clear()
        // Xóa tất cả cache trên đĩa
        imageLoader.diskCache?.clear()

    }
    fun getCacheSize(cacheDir: File): Long {
        return GlobalFunction.getCacheSize(cacheDir)
    }
    fun changeLanguage(languageCode: String) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(languageCode))
    }
    fun openFacebook(context: Context) {
        GlobalFunction.openInBrowser(context, Constants.FACEBOOK_URL)
    }

}