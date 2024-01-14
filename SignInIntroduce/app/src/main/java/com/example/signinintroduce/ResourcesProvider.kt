package com.example.signinintroduce

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object ResourcesProvider {
    private var _context: Context? = null
    fun setContext(context: Context?) {
        _context = context
    }

    fun getString(resId: Int) = _context?.getString(resId) ?: ""
}