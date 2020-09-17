package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room.NoteEntity

@Dao
abstract class NotesDao {

    @Query("SELECT * FROM notes")
    abstract fun getAll(): Observable<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: NoteEntity): Completable

    @Query("DELETE FROM notes WHERE id=:id")
    abstract fun delete(id: Long): Completable

    @Query("SELECT * FROM notes WHERE title LIKE '%'||:filter||'%' OR content LIKE  '%'||:filter||'%'")
    abstract fun filter(filter: String): Observable<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id=:id")
    abstract fun getById(id: Long): Observable<NoteEntity>

}