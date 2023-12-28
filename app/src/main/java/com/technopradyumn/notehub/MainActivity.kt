package com.technopradyumn.notehub

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
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
    lateinit var itemAnimator: RecyclerView.ItemAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesRV = findViewById(R.id.notesRV)
        addFAB = findViewById(R.id.idFAB)

        notesRV.layoutManager = LinearLayoutManager(this)

//        val numberOfColumns = 2
//        notesRV.setLayoutManager(GridLayoutManager(this, numberOfColumns))

        adapter = NoteRVAdapter(notesRV,this, this, this)
        notesRV.adapter = adapter


        adapter.attachSwipeToDeleteCallback(notesRV)

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModal::class.java]

        viewModal.allNotes.observe(this) { list ->
            list?.let {
                adapter.updateList(it)
            }
        }

        addFAB.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModal::class.java]

        val searchView: SearchView = findViewById(R.id.search_bar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterList((newText ?: ""))

                return true
            }
        })


//        notesRV.itemAnimator = DefaultItemAnimator()
//
//        val animRes = resources.getIdentifier("item_animation", "anim", packageName)
//
//        itemAnimator = DefaultItemAnimator()
//        itemAnimator.setAddAnimation(animRes)
//        itemAnimator.setRemoveAnimation(animRes)
//
//        notesRV.itemAnimator = itemAnimator
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
