package com.technopradyumn.notehub

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.technopradyumn.notehub.Models.Note
import com.technopradyumn.notehub.Models.NoteViewModal
import java.text.SimpleDateFormat
import java.util.Date

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var noteTitleEdt: EditText
    private lateinit var noteEdt: EditText
    private lateinit var saveBtn: ImageView

    private lateinit var viewModal: NoteViewModal
    private var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd MMM, yyyy - HH:mm:ss")
        val currentDateTime = dateFormat.format(currentDate)

        val dateTime = findViewById<TextView>(R.id.textView)

        dateTime.text = currentDateTime

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModal::class.java]

        noteTitleEdt = findViewById(R.id.idEdtNoteName)
        noteEdt = findViewById(R.id.idEdtNoteDesc)
        saveBtn = findViewById(R.id.idBtn)
        val backbtn = findViewById<ImageView>(R.id.backBtn)

        backbtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteId", -1)
            noteTitleEdt.setText(noteTitle)
            noteEdt.setText(noteDescription)
        }

        saveBtn.setOnClickListener {
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteEdt.text.toString()

            if (noteTitle.isEmpty() || noteDescription.isEmpty()) {
                // Show a message if either the title or description is empty
                Toast.makeText(this, "Please enter both title and description", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (noteType.equals("Edit")) {
                    if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm:ss")
                        val currentDateAndTime: String = sdf.format(Date())
                        val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                        updatedNote.id = noteID
                        viewModal.updateNote(updatedNote)
                        Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                    }
                } else {
                    if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm:ss")
                        val currentDateAndTime: String = sdf.format(Date())

                        viewModal.addNote(Note(noteTitle, noteDescription, currentDateAndTime))
                        Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                    }
                }
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }
    }
}