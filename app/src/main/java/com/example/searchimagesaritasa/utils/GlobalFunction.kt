import android.app.Activity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.File

object GlobalFunction {
    fun logCacheSize(folder: File) {
        val cacheSize = getFolderSize(folder)
        Log.e("CacheSize", "Cache size: $cacheSize bytes")
    }

    fun getFolderSize(folder: File): Long {
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
}
