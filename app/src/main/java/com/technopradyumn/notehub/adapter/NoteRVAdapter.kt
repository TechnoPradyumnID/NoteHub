package com.technopradyumn.notehub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.technopradyumn.notehub.Models.Note
import com.technopradyumn.notehub.R
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

class NoteRVAdapter(
    val context: Context,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface
) :
    RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    private val allNotes = ArrayList<Note>()
    private val filteredNotes = ArrayList<Note>()

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
        holder.noteTV.setText(allNotes.get(position).noteTitle)
        holder.dateTV.setText("Last Updated : " + allNotes.get(position).timeStamp)
        holder.deleteIV.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(allNotes[position])
        }

        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes.get(position))
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

        if (search.isEmpty()) {
            filteredNotes.addAll(allNotes.sortedByDescending { it.timeStamp })
        } else {
            val filtered = allNotes.filter { note ->
                note.noteTitle.contains(search, true) || note.noteDescription.contains(search, true)
            }.sortedByDescending { it.timeStamp }

            val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
            val currentDateAndTime: String = sdf.format(Date())

            val updatedFilteredNotes = filtered.map { note ->
                Note(note.noteTitle, note.noteDescription, currentDateAndTime)
            }

            filteredNotes.addAll(updatedFilteredNotes)
        }

        notifyDataSetChanged()
    }




    fun randomColor(): Int {

        val list = ArrayList<Int>()
        list.add(R.color.darkPurple)
        list.add(R.color.lightPurple)

        val colorList = mutableListOf(
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


        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(colorList.size)
        return colorList[randomIndex]
    }

}

interface NoteClickDeleteInterface {
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    fun onNoteClick(note: Note)
}