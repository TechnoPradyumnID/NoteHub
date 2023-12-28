package com.technopradyumn.notehub.adapter

import FilterNote
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.technopradyumn.notehub.Models.Note
import com.technopradyumn.notehub.R
import kotlin.random.Random

class NoteRVAdapter(recyclerView: RecyclerView, val context: Context, val noteClickDeleteInterface: NoteClickDeleteInterface, private val noteClickInterface: NoteClickInterface) :
    RecyclerView.Adapter<NoteRVAdapter.ViewHolder>(),Filterable{

    private val allNotes = ArrayList<Note>()
    private var filteredNotes = ArrayList<Note>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteItem = itemView.findViewById<CardView>(R.id.noteItem)
        val noteTV = itemView.findViewById<TextView>(R.id.idTVNote)
        val dateTV = itemView.findViewById<TextView>(R.id.idTVDate)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_rv_item,
            parent, false
        )
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.noteTV.text = allNotes[position].noteTitle
        holder.dateTV.text = "Last Updated: " + allNotes[position].timeStamp

        holder.deleteIV.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(allNotes[position])
        }

        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes[position])
        }

        holder.noteItem.setCardBackgroundColor(
            holder.itemView.resources.getColor(randomColor(), null)
        )
    }

    override fun getItemCount(): Int {
        return filteredNotes.size
    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        filterList("")
        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        filteredNotes.clear()

        if (search.isNotEmpty()) {
            val filtered = allNotes.filter { note ->
                note.noteTitle.contains(search, true)
            }
            filteredNotes.addAll(filtered.sortedBy { it.noteTitle })
        } else {
            filteredNotes.addAll(allNotes.sortedBy { it.timeStamp })
        }

        notifyDataSetChanged()
    }


    fun attachSwipeToDeleteCallback(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                // Ensure that the position is within a valid range
                if (position in 0 until allNotes.size) {
                    val deletedNote = allNotes[position]
                    noteClickDeleteInterface.onDeleteIconClick(deletedNote)
                    removeNoteAtPosition(position, recyclerView)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    fun removeNoteAtPosition(position: Int, recyclerView: RecyclerView) {
        if (position >= 0 && position < allNotes.size) {
            val deletedNote = allNotes[position]
            allNotes.removeAt(position)
            notifyItemRemoved(position)
            allNotes.add(position, deletedNote)
        }
    }

    fun randomColor(): Int {
        val colorList = mutableListOf(
            R.color.background1, R.color.background2, R.color.background3,
            R.color.background4, R.color.background5, R.color.background6, R.color.background7,
            R.color.background8, R.color.background9, R.color.background10, R.color.background11,
            R.color.background12, R.color.background13, R.color.background14, R.color.background15,
            R.color.background16, R.color.background17, R.color.background18, R.color.background19,
            R.color.background20
        )

        if (colorList.isNotEmpty()) {
            val randomIndex = Random.nextInt(colorList.size)
            return colorList[randomIndex]
        } else {
            // If the color list is empty, add all of the colors back in and then get a random color.
            colorList.addAll(
                listOf(
                    R.color.background1,
                    R.color.background2,
                    R.color.background3,
                    R.color.background4,
                    R.color.background5,
                    R.color.background6,
                    R.color.background7,
                    R.color.background8,
                    R.color.background9,
                    R.color.background10,
                    R.color.background11,
                    R.color.background12,
                    R.color.background13,
                    R.color.background14,
                    R.color.background15,
                    R.color.background16,
                    R.color.background17,
                    R.color.background18,
                    R.color.background19,
                    R.color.background20
                )
            )

            val randomIndex = Random.nextInt(colorList.size)
            return colorList[randomIndex]
        }
    }




    private val filter: FilterNote? = null
    override fun getFilter(): Filter {
        if (filter == null){
            return FilterNote(this,allNotes)
        }
        return filter as FilterNote
    }



}

interface NoteClickDeleteInterface {
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    fun onNoteClick(note: Note)
}