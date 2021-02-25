package com.piashcse.experiment.mvvm_hilt.utils

import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONObject


fun debugLog(message: String) {
    Log.d("DebugLog ",message)
}
fun errorLog(message: String) {
    Log.e("ErrorLog ",message)
}
fun ResponseBody.jsonData(): JSONObject {
    return JSONObject(this.string())
}

