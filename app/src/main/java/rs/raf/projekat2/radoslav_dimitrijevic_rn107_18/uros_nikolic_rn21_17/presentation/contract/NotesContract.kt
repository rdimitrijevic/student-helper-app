package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Note
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state.NotesState

interface NotesContract {

    interface ViewModel {

        val notesState: LiveData<NotesState>

        val showArchived: Boolean

        fun getAllNotes()
        fun getById(id: Long)
        fun filterNotes(filter: String)
        fun toggleArchived()
        fun addNote(note: Note)
        fun removeNote(id: Long)
        fun updateNote(note: Note)
        fun archiveNote(note: Note)
        fun unarchiveNote(note: Note)
    }
}