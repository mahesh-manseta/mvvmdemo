package com.maheshmanseta.mvvm.ui.dashboard

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.StatFs
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _textSize = MutableLiveData<HashMap<String, Long>>().apply {
        value = getSize("/storage/")
    }
    val textSize: LiveData<HashMap<String, Long>> = _textSize

    private fun getSize(path: String): HashMap<String, Long> {
        Log.e("Size Calculate", "Check")
        val map = HashMap<String, Long>()
        val file = File(path)
        if (!file.isDirectory) {
            map["Not a directory"] = file.length()
            return map
        }
        if (!file.exists()) {
            map["Empty"] = 0
            return map
        }
        val dirs: LinkedList<File> = LinkedList<File>();
        dirs.add(file)
        var result: Long = 0
        while (!dirs.isEmpty()) {
            val dir: File = dirs.removeAt(0);
            if (!dir.exists()) {
                continue
            }
            val listFiles = dir.listFiles()
            if (listFiles == null || listFiles.isEmpty()) {
                continue
            }
            for (child in listFiles) {
                Log.e("Size Calculate in loop", child.name)
                val stat = StatFs(child.path)
                result+= stat.availableBlocks * stat.blockSize
                if (child.isDirectory) {
                    dirs.add(child)
                    map[child.name] = result / 1024
                }
            }
        }
        return map;
    }

}