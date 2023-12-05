package com.kodejarom.notes


import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tvNotesList: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = MyApplication.getSharedPreferences(this)

        tvNotesList = findViewById(R.id.tvNotesList)

        val fabNewNote: View = findViewById(R.id.fabNewNote)
        fabNewNote.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        loadNotesFromSharedPreferences()
    }

    private fun loadNotesFromSharedPreferences() {
        val notesText = sharedPreferences.getString(MyApplication.KEY_NOTES_TEXT, "")
        tvNotesList.text = notesText
    }
}
