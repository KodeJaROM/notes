package com.kodejarom.notes

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    companion object {
        const val PREF_NOTES = "PREF_NOTES"
        const val KEY_NOTES_TEXT = "KEY_NOTES_TEXT"

        fun getSharedPreferences(context: Context) =
            context.getSharedPreferences(PREF_NOTES, Context.MODE_PRIVATE)
    }
}

