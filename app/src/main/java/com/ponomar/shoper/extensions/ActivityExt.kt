package com.ponomar.shoper.extensions

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


class Auth {
    companion object{

        fun Activity.saveAuthToken(token: String):Boolean{
            Log.e("TOKEN", token)
            return try {
                val sp = this.getActivity()!!.getSharedPreferences(KEYS.SP_NAME, Context.MODE_PRIVATE)
                sp.edit().putString(KEYS.AUTH_TOKEN, token).apply()
                true
            }catch (e: Exception){
                false
            }
        }

        fun Activity.getAuthToken():String?{
            val sp = this.getActivity()!!.getSharedPreferences(KEYS.SP_NAME, Context.MODE_PRIVATE)
            return sp.getString(KEYS.AUTH_TOKEN, null)
        }

        fun Activity.isAuthTokenAvailable():Boolean{
            return this.getActivity()!!.getSharedPreferences(KEYS.SP_NAME, Context.MODE_PRIVATE).contains(KEYS.AUTH_TOKEN)
        }

        fun Activity.forgetAuthToken(){
            this.getActivity()!!.getSharedPreferences(KEYS.SP_NAME, Context.MODE_PRIVATE).edit().remove(KEYS.AUTH_TOKEN).apply()
        }
    }

    class KEYS {
        companion object {
            const val AUTH_TOKEN: String = "AUTH_TOKEN"
            const val SP_NAME: String = "SP_NAME"
        }
    }
}

fun Activity.hideKeyBoard(){
    val view: View? = this.currentFocus
    if (view != null) {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}