package com.technopradyumn.notehub

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.technopradyumn.notehub.Models.Note
import com.technopradyumn.notehub.Models.NoteViewModal
import com.technopradyumn.notehub.adapter.NoteClickDeleteInterface
import com.technopradyumn.notehub.adapter.NoteClickInterface
import com.technopradyumn.notehub.adapter.NoteRVAdapter


class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    private lateinit var viewModal: NoteViewModal
    private lateinit var notesRV: RecyclerView
    private lateinit var addFAB: FloatingActionButton
    private lateinit var adapter: NoteRVAdapter
    private lateinit var noteViewModel: NoteViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesRV = findViewById(R.id.notesRV)
        addFAB = findViewById(R.id.idFAB)

//        notesRV.layoutManager = LinearLayoutManager(this)

        val numberOfColumns = 2
        notesRV.setLayoutManager(GridLayoutManager(this, numberOfColumns))

        adapter = NoteRVAdapter(this, this, this)
        notesRV.adapter = adapter

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)

        viewModal.allNotes.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })

        addFAB.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)

        val searchView: SearchView = findViewById(R.id.search_bar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { adapter.filterList(it) }
                return true
            }
        })

        noteViewModel.allNotes.observe(this) { notes ->
            notes?.let {
                adapter.updateList(it)
            }
        }
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: Note) {
        viewModal.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}
