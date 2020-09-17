package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.notes

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.local.NotesDao
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.Resource
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room.NoteEntity
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Note

class NotesRepositoryImpl(private val notesDao: NotesDao) :
    NotesRepository {

    override fun getAll(): Observable<Resource<List<Note>>> {
        return notesDao
            .getAll()
            .map { list ->
                Resource.Success(list.map {
                    Note(
                        it.id,
                        it.title,
                        it.content,
                        it.archived
                    )
                })
            }
    }

    override fun getById(id: Long): Observable<Resource<Note>> {
        return notesDao
            .getById(id)
            .map {
                Resource.Success(
                    Note (
                        it.id,
                        it.title,
                        it.content,
                        it.archived
                    )
                )
            }
    }

    override fun insert(note: Note): Completable {
        return notesDao.insert(NoteEntity(note.id, note.title, note.content, note.archived))
    }

    override fun delete(id: Long): Completable {
        return notesDao.delete(id)
    }

    override fun filter(filter: String): Observable<Resource<List<Note>>> {
        return notesDao.filter(filter)
            .map { list ->
                Resource.Success(list.map {
                    Note(
                        it.id,
                        it.title,
                        it.content,
                        it.archived
                    )
                })
            }
    }
}