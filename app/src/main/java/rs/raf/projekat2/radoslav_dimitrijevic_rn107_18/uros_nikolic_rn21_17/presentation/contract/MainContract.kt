package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Note
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Subject

interface MainContract {
    interface ViewModel
        : Schedule,
        Notes,
        Stats {}

    interface Schedule {
        val searchSchedule: LiveData<String>
        val schedule: LiveData<List<Subject>>

        fun setSearchSchedule(string: String)
        fun filterSchedule(group: String, day: String, professor: String)
    }

    interface Notes {
        val notes: LiveData<List<Note>>
        val searchNotes: LiveData<String>

        fun setSearchNotes(string: String)
        fun toggleArchived()
        fun newNote(note: Note)
        fun removeNote(note: Note)
        fun archiveNote(note: Note)
        fun unarchiveNote(note: Note)
    }

    interface Stats {

    }
}