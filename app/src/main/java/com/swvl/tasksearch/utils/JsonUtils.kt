package com.swvl.tasksearch.utils

import android.content.Context
import java.io.InputStream

object JsonUtils {
    fun readJSONFromAsset(mContext: Context, fileName: String): String? {
        var json: String? = null
        try {
            val inputStream: InputStream =
                mContext.assets.open("${fileName}.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}