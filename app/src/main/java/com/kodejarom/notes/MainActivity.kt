package com.kodejarom.notes


import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
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

//        tvNotesList = findViewById(R.id.tvNotesList)
        var notesListView = findViewById<ListView>(R.id.notesListView)

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
        val notesListView = findViewById<ListView>(R.id.notesListView)

// Retrieve all keys from SharedPreferences
        val allKeys = sharedPreferences.all.keys.toList()

// Create an ArrayAdapter to bind the data to the ListView
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

// Iterate through the keys and retrieve the corresponding values (notes)
        for (key in allKeys) {
            val note = sharedPreferences.getString(key, "")
            if (note != null && note.isNotEmpty()) {
                adapter.add(note)
            }
        }

// Set the adapter to the ListView
        notesListView.adapter = adapter

    }

}
