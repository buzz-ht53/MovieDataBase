package com.buzz_ht.moviedatabase.UI.Utils

import android.content.Context
import android.content.SharedPreferences
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import android.widget.Toast


public class GeneralUtils {

    companion object {
        public fun showToastLong(context: Context, message: String) {

            Toast.makeText(context, message, Toast.LENGTH_LONG).show()

        }

        public fun getScreenResolution(context: Context): String {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display: Display = wm.defaultDisplay
            val metrics = DisplayMetrics()
            display.getMetrics(metrics)
            val width = metrics.widthPixels
            val height = metrics.heightPixels
            return "{$width,$height}"
            /*val array = arrayOf<Int>(2)
            array[0] = width
            array[1] = height
            return array*/
        }

        public fun getSharedPreferenceData() {

            var sharedPreferences: SharedPreferences


        }
    }


}