package com.buzz_ht.moviedatabase.UI.Utils

import android.content.Context
import android.widget.Toast

public class GeneralUtils {

    companion object {
        public fun showToastLong(context: Context, message: String) {

            Toast.makeText(context, message, Toast.LENGTH_LONG).show()

        }
    }


}