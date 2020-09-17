package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state

import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Note

sealed class NotesState {
    object Loading : NotesState()
    data class Success(var notes: List<Note>) : NotesState()
    data class Error(val err: String) : NotesState()
    data class NoteByIdSuccess(val note: Note) : NotesState()
    object NoteAddSuccess : NotesState()
    object NoteDeleteSuccess : NotesState()
}
