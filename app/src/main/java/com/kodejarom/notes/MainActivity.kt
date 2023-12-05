package com.kodejarom.notes


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val PREF_NOTES = "PREF_NOTES"
    private val KEY_NOTES_TEXT = "KEY_NOTES_TEXT"

    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(PREF_NOTES, Context.MODE_PRIVATE)

        // Load notes from SharedPreferences on app start
        loadNotesFromSharedPreferences()

        val fabNewNote: View = findViewById(R.id.fabNewNote)
        fabNewNote.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadNotesFromSharedPreferences() {
        val notesText = sharedPreferences.getString(KEY_NOTES_TEXT, "")
        findViewById<TextView>(R.id.tvNotesList).text = notesText
    }
}
