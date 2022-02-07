package com.piashcse.experiment.mvvm_hilt.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.piashcse.experiment.mvvm_hilt.R
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.Serializable


fun debugLog(message: String) {
    Log.d("DebugLog ", message)
}

fun errorLog(message: String) {
    Log.e("ErrorLog ", message)
}

fun ResponseBody.jsonData(): JSONObject {
    return JSONObject(this.string())
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.pixelToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}
/**
 * loading image extension function using glide with placeholder
 */
fun ImageView.loadImage(imageUrl: String) {
    Glide.with(this.context.applicationContext)
        .load(imageUrl).placeholder(R.drawable.place_holder).into(this)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

// Read json file from assets
fun AssetManager.readAssetsFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }

inline fun <reified T : Activity> Activity.openActivity(vararg params: Pair<String, Any>) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(*params)
    this.startActivity(intent)

}

// this extension function is for resultContract
inline fun <reified T : Activity> Activity.openActivityResult(vararg params: Pair<String, Any>): Intent {
    val intent = Intent(this, T::class.java)
    return intent.putExtras(*params)
}

fun Activity.finishActivityResult(vararg params: Pair<String, Any>) {
    val intent = Intent()
    intent.putExtras(*params)
    setResult(Activity.RESULT_OK, intent)
    finish()
}

fun Intent.putExtras(vararg params: Pair<String, Any>): Intent {
    if (params.isEmpty()) return this
    params.forEach { (key, value) ->
        when (value) {
            is Int -> putExtra(key, value)
            is Byte -> putExtra(key, value)
            is Char -> putExtra(key, value)
            is Long -> putExtra(key, value)
            is Float -> putExtra(key, value)
            is Short -> putExtra(key, value)
            is Double -> putExtra(key, value)
            is Boolean -> putExtra(key, value)
            is Bundle -> putExtra(key, value)
            is String -> putExtra(key, value)
            is IntArray -> putExtra(key, value)
            is ByteArray -> putExtra(key, value)
            is CharArray -> putExtra(key, value)
            is LongArray -> putExtra(key, value)
            is FloatArray -> putExtra(key, value)
            is Parcelable -> putExtra(key, value)
            is ShortArray -> putExtra(key, value)
            is DoubleArray -> putExtra(key, value)
            is BooleanArray -> putExtra(key, value)
            is CharSequence -> putExtra(key, value)
            is Array<*> -> {
                when {
                    value.isArrayOf<String>() ->
                        putExtra(key, value as Array<String?>)
                    value.isArrayOf<Parcelable>() ->
                        putExtra(key, value as Array<Parcelable?>)
                    value.isArrayOf<CharSequence>() ->
                        putExtra(key, value as Array<CharSequence?>)
                    else -> putExtra(key, value)
                }
            }
            is Serializable -> putExtra(key, value)
        }
    }
    return this
}
