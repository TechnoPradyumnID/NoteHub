import android.widget.Filter
import android.widget.Filter.FilterResults
import com.technopradyumn.notehub.Models.Note
import com.technopradyumn.notehub.adapter.NoteRVAdapter
import java.util.Locale

class FilterNote(
    private val adapter: NoteRVAdapter,
    private val noteRV: ArrayList<Note>
) : Filter() {
    override fun performFiltering(searchingText: CharSequence?): FilterResults {
        val filterResults = FilterResults()
        val filterNote = ArrayList<Note>()

        if (!searchingText.isNullOrBlank()) {
            val query = searchingText.toString().trim().lowercase(Locale.getDefault())
            for (note in noteRV) {
                if (query.any {
                        note.noteTitle.contains(it, ignoreCase = true) ||
                        note.noteDescription.contains(it, ignoreCase = true)
                    }) {
                    filterNote.add(note)
                }
            }

            filterResults.apply {
                count = filterNote.size
                values = filterNote
            }
        } else {
            filterNote.addAll(noteRV)
        }

        filterResults.count = filterNote.size
        filterResults.values = filterNote
        return filterResults
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        results?.let {
            val filteredNotes = it.values as ArrayList<Note>
            adapter.updateList(filteredNotes)
        }
    }
}
