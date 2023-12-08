package com.kodejarom.notes

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var notesListView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = MyApplication.getSharedPreferences(this)

        val fabNewNote: View = findViewById(R.id.fabNewNote)
        fabNewNote.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteActivity::class.java)
            startActivity(intent)
        }

        notesListView = findViewById(R.id.notesListView)

        // Create an ArrayAdapter to bind the data to the ListView
        adapter = ArrayAdapter(this, R.layout.list_item_note)

        // Set the adapter to the ListView
        notesListView.adapter = adapter

        // Handle long press on notesListView items
        notesListView.setOnItemLongClickListener { _, _, position, _ ->
            deleteNoteAtPosition(position)
            true
        }

        //Handle pressing notesListView item
        notesListView.setOnItemClickListener { _, _, position, _ ->
            val noteToOpen = sharedPreferences.all.keys.toList()[position]
            val intent = Intent(this@MainActivity, NoteActivity::class.java)
            intent.putExtra("noteKey", noteToOpen)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadNotesFromSharedPreferences()
    }

    private fun loadNotesFromSharedPreferences() {
        // Clear the adapter before reloading notes
        adapter.clear()

        // Retrieve all keys from SharedPreferences
        val allKeys = sharedPreferences.all.keys.toList()

        // Iterate through the keys and retrieve the corresponding values (notes)
        for (key in allKeys) {
            val note = sharedPreferences.getString(key, "")
            if (!note.isNullOrEmpty()) {
                adapter.add(note)
            }
        }
    }

    private fun deleteNoteAtPosition(position: Int) {
        AlertDialog.Builder(this).setTitle("Delete note")
            .setMessage("Do you really want to delete the selected note? This action cannot be undone!")
            .setPositiveButton("Yes") { _, _ ->
                // Get the key at the selected position
                val keyToDelete = sharedPreferences.all.keys.toList()[position]

                // Remove the note from SharedPreferences
                sharedPreferences.edit().remove(keyToDelete).apply()

                // Reload notes after deletion
                loadNotesFromSharedPreferences()
            }.setNegativeButton("No", null).show()
    }
}
