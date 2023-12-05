package com.kodejarom.notes


import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import android.app.AlertDialog
import android.content.SharedPreferences

@RequiresApi(Build.VERSION_CODES.O)
class NoteActivity : AppCompatActivity() {

    private val PREF_NOTES = "PREF_NOTES"
    private val KEY_NOTES_TEXT = "KEY_NOTES_TEXT"

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)


        findViewById<Button>(R.id.btnSave).setOnClickListener { saveNote() }
        findViewById<Button>(R.id.btnUndo).setOnClickListener { removeLastNote() }
    }

    private fun saveNote() {
        val etNewNoteField = findViewById<EditText>(R.id.etNewNoteField)
        val tvNotesList = findViewById<TextView>(R.id.tvNotesList)
        val newText = etNewNoteField.text.toString()
        if (newText.isNotBlank()) {
            val timeStamp = getCurrentTimeStamp()
            val formattedText = "$timeStamp\n$newText"

            val currentNotesText = sharedPreferences.getString(KEY_NOTES_TEXT, "")
            val newNotesText = if (currentNotesText.isNullOrEmpty()) {
                formattedText
            } else {
                "$formattedText\n\n$currentNotesText"
            }
            sharedPreferences.edit().putString(KEY_NOTES_TEXT, newNotesText).apply()
            etNewNoteField.text.clear()
            tvNotesList.text = newNotesText
        } else {
            val toast = Toast.makeText(
                this, getString(R.string.error_empty_input), Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }

    private fun getCurrentTimeStamp(): String {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
    }

    private fun removeLastNote() {
        val tvNotes = findViewById<TextView>(R.id.tvNotesList)
        AlertDialog.Builder(this).setTitle("Remove last note")
            .setMessage("Do you really want to remove the last note? This action cannot be undone!")
            .setPositiveButton("Yes") { _, _ ->
                val currentNotesText = sharedPreferences.getString(KEY_NOTES_TEXT, "")
                if (currentNotesText?.isNotBlank() == true) {
                    val lines = currentNotesText.split("\n\n")
                    val newNotesText = if (lines.size > 1) {
                        lines.subList(1, lines.size).joinToString("\n\n")
                    } else {
                        ""
                    }
                    sharedPreferences.edit().putString(KEY_NOTES_TEXT, newNotesText).apply()
                    tvNotes.text = newNotesText
                }
            }.setNegativeButton("No", null).show()
    }

}
