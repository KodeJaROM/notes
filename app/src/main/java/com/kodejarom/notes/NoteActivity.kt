package com.kodejarom.notes


import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class NoteActivity : AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etNewNoteField: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        sharedPreferences = MyApplication.getSharedPreferences(this)
        etNewNoteField = findViewById(R.id.etNewNoteField)

        // Retrieve the note key from the Intent if included
        val noteKey = intent.getStringExtra("noteKey")

        // Retrieve the note content based on the key from SharedPreferences
        val noteContent = sharedPreferences.getString(noteKey, "")

        // Set the note content in the EditText
        etNewNoteField.setText(noteContent)

        findViewById<Button>(R.id.btnSave).setOnClickListener { saveNote() }
        findViewById<ImageButton>(R.id.btnCancel).setOnClickListener { finish() }
        findViewById<ImageButton>(R.id.btnDelete).setOnClickListener { deleteNote() }
    }


    private fun saveNote() {
        val newText = etNewNoteField.text.toString()

        if (newText.isNotBlank()) {
            // Check if a note key is provided in the Intent
            val noteKeyFromIntent = intent.getStringExtra("noteKey")

            // Use the provided note key or generate a new unique timestamp key
            val uniqueKey = noteKeyFromIntent ?: getCurrentTimeStamp()

            sharedPreferences.edit().putString(uniqueKey, newText).apply()
            finish() // Closes the notes activity
        } else {
            val toast = Toast.makeText(
                this, getString(R.string.error_empty_input), Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }


    private fun deleteNote() {
        AlertDialog.Builder(this).setTitle("Delete note")
            .setMessage("Do you really want to delete this note? This action cannot be undone!")
            .setPositiveButton("Yes") { _, _ ->
                // Get the key from intent
                val noteToDelete = intent.getStringExtra("noteKey")

                // Remove the note from SharedPreferences
                sharedPreferences.edit().remove(noteToDelete).apply()

                // close NoteActivity
                finish()
            }.setNegativeButton("No", null).show()
    }


    private fun getCurrentTimeStamp(): String {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
    }
}
