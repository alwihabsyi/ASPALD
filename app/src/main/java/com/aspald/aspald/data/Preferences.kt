package com.aspald.aspald.data

import android.content.Context
import android.content.SharedPreferences
import com.aspald.aspald.utils.Constants.EMAIL
import com.aspald.aspald.utils.Constants.NAME
import com.aspald.aspald.utils.Constants.SESSION
import com.aspald.aspald.utils.Constants.TOKEN

object Preferences {

    fun init(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    private fun preferenceEditor(context: Context): SharedPreferences.Editor {
        val sharedPref = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
        return sharedPref.edit()
    }

    fun saveToken(token: String, name: String, email: String, context: Context) {
        val editor = preferenceEditor(context)
        editor.putString(TOKEN, token)
        editor.putString(NAME, name)
        editor.putString(EMAIL, email)
        editor.apply()
    }

    fun logOut(context: Context) {
        val editor = preferenceEditor(context)
        editor.remove(TOKEN)
        editor.remove(NAME)
        editor.remove(EMAIL)
        editor.remove("status")
        editor.apply()
    }

}