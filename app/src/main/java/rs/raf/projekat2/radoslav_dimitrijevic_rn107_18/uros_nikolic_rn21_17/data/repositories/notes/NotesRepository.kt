package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.notes

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.Resource
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Note

interface NotesRepository {

    fun getAll(): Observable<Resource<List<Note>>>
    fun getById(id: Long): Observable<Resource<Note>>
    fun insert(note: Note): Completable
    fun delete(id: Long): Completable
    fun filter(filter: String): Observable<Resource<List<Note>>>

}