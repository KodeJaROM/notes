package com.kodejarom.notes


import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class NoteActivity : AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        sharedPreferences = MyApplication.getSharedPreferences(this)

        findViewById<Button>(R.id.btnSave).setOnClickListener { saveNote() }
        findViewById<Button>(R.id.btnCancel).setOnClickListener { finish() }
    }


    private fun saveNote() {
        val etNewNoteField = findViewById<EditText>(R.id.etNewNoteField)
        val newText = etNewNoteField.text.toString()

        if (newText.isNotBlank()) {
            val uniqueKey = getCurrentTimeStamp()  // Unique key for each note based on timestamp

            sharedPreferences.edit().putString(uniqueKey, newText).apply()
            finish() // Closes the notes activity
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
}
